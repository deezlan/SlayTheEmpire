package main;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import TileInteractive.InteractiveTIle;
import ai.Pathfinder;
import entity.Cursor;
import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    Thread gameThread;
    private final int
            ORIGINAL_TILE_SIZE = 16,
            SCALE = 3;
    public final int
            TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE,
            MAX_SCREEN_COL = 17,
            MAX_SCREEN_ROW = 13,
            SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL,
            SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    // WORLD SETTINGS
    public int currentMap = 0;
    public final int
            MAX_WORLD_COL = 50,
            MAX_WORLD_ROW = 50,
            maxMap = 3;
    public TileManager tileM = new TileManager(this);

    // PLAYER SETTINGS
    public int playerClass = 0;
    public KeyHandler keyH = new KeyHandler(this);

    // FPS SETTINGS
    private final int FPS = 60;

    // PLAYER SETTINGS
    public Cursor cursor = new Cursor(this); // Initialize cursor
    public Player player = new Player(this, keyH, cursor, playerClass);
    public UI ui = new UI(this);

    // ENTITY AND OBJECTS ARRAYS
    private final ArrayList<Entity> entityList = new ArrayList<>();
    public AssetSetter aSetter = new AssetSetter(this);
    public Entity[][]
            objArr= new Entity[maxMap][30],
            npcArr = new Entity[maxMap][10],
            mobArr = new Entity[maxMap][20],
            projectileArr = new Entity[maxMap][50];
    public InteractiveTIle[][] iTile = new InteractiveTIle[maxMap][50];

    // HANDLERS
    public CollisionChecker cChecker = new CollisionChecker(this);
    public EventHandler eHandler = new EventHandler(this);

    // PATHFINDER
    public Pathfinder pFinder = new Pathfinder(this);

    // CUTSCENE
    public boolean bossBattleOn = false;
    public CutsceneManager csManager = new CutsceneManager(this);
    // GAME STATES
    public int gameState;
    public final int
            titleState = 0,
            playState = 1,
            pauseState = 2,
            dialogueState = 3,
            shopState = 4,
            deathState = 5,
            transitionState = 6;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#181425"));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        hideCursor();

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursor.updateMousePosition(e.getX(), e.getY());
            }
        });
    }

    private void hideCursor() {
        BufferedImage cursorImg = new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB);

        java.awt.Cursor blankCursor =
                Toolkit.getDefaultToolkit().createCustomCursor
                    (cursorImg, new Point(0,0), "blank cursor");
        this.setCursor(blankCursor);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = playState;

        tempScreen = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
    }

    public void retry() {
        player.setDefaultPosition();
        player.restoreLife();
        aSetter.setMonster();
        aSetter.setNPC();
    }

    public void restart() {
        player.setDefaultValues();
        player.setDefaultPosition();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    // MAP SETTINGS
    public void setMapColor () {
        switch (currentMap) {
            case 0:
                setBackground(Color.decode("#181425"));
                break;
            case 1:
                setBackground(Color.decode("#42393a"));
//                setBackground(Color.decode("#000000"));
                break;
        }
    }

    // FPS SETTINGS
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    // GAME LOOP METHODS
    public void update() {

        if (gameState == playState) {
            player.update();

            for (int i = 0; i < objArr[1].length; i++){ // OBJECT
                if(objArr[currentMap][i] != null){
                    objArr[currentMap][i].update();
                }
            }

            for (int i = 0; i < npcArr[1].length; i++){ // NPCs
                if(npcArr[currentMap][i] != null){
                    npcArr[currentMap][i].update();
                }
            }

            for (int mob = 0; mob < mobArr[1].length; mob++) { // MOBS
                if (mobArr[currentMap][mob] != null) {
                    if (mobArr[currentMap][mob].alive && (!mobArr[currentMap][mob].dead)) {
                        mobArr[currentMap][mob].update();
                    }
                    if (!mobArr[currentMap][mob].alive){
                        mobArr[currentMap][mob].checkDrop();
                        mobArr[currentMap][mob] = null;
                    }
                }
            }

            for (int i = 0; i < iTile[1].length; i++){ // INTERACTIVE TILES
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }

            for (int i = 0; i < projectileArr[1].length; i++){
                if (projectileArr[currentMap][i] != null){
                    if (projectileArr[currentMap][i].alive){
                        projectileArr[currentMap][i].update();
                    }
                    if (!projectileArr[currentMap][i].alive){
                        projectileArr[currentMap][i]=null;
                    }
                }
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        } else {

            // DEBUG
            long drawStart = 0;
            if(keyH.showDebug){
            drawStart = System.nanoTime();
            }

            tileM.draw(g2); // Draw tiles

            // ADD INTERACTIVE TILES
            for (int i = 0; i < iTile[1].length; i++){ // INTERACTIVE TILES
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (int i = 0; i < npcArr[1].length; i++){ // NPCs
                if(npcArr[currentMap][i] != null){
                    entityList.add(npcArr[currentMap][i]);
                }
            }

            for (int i = 0; i < objArr[1].length; i++){ // OBJECT
                if(objArr[currentMap][i] != null){
                    entityList.add(objArr[currentMap][i]);
                }
            }

            for (int i = 0; i < mobArr[1].length; i++){ // MOBS
                if(mobArr[currentMap][i] != null){
                    entityList.add(mobArr[currentMap][i]);
                }
            }

            for (int i = 0; i < projectileArr[1].length; i++){ // PROJECTILES
                if(projectileArr[currentMap][i] != null){
                    entityList.add(projectileArr[currentMap][i]);
                }
            }

            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // EMPTY ENTITY LIST
            for (int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }

            ui.draw(g2);

            csManager.draw(g2);

            // DEBUG
            if(keyH.showDebug){
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial",Font.PLAIN,20));
                g2.setColor(Color.white);
                int x = 10;
                int y = 400;
                int lineHeight = 20;
                g2.drawString("WorldX: "+ player.worldX , x , y); y += lineHeight;
                g2.drawString("WorldY: "+ player.worldY , x , y); y += lineHeight;
                g2.drawString("Col: " + (player.worldX + player.solidArea.x)/TILE_SIZE, x, y); y += lineHeight;
                g2.drawString("Row: " + (player.worldY + player.solidArea.y)/TILE_SIZE, x, y); y += lineHeight;
                g2.drawString("Draw Time: " + passed, x, y);
            }
        }
        g2.dispose();
    }
}
