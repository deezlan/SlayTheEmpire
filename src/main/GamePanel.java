package main;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import TileInteractive.InteractiveTIle;
import ai.Pathfinder;
import entity.Cursor;
import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final double SCALE = 3;
    public final int TILE_SIZE = (int)(ORIGINAL_TILE_SIZE * SCALE);
    public final int MAX_SCREEN_COL = 17;
    public final int MAX_SCREEN_ROW = 13;
    public final int maxMap = 10;
    public int currentMap = 0;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public int playerClass = 0; // player class here

    // WORLD SETTINGS
    public int MAX_WORLD_COL = 50; //must be same as map size
    public int MAX_WORLD_ROW = 50; //must be same as map size

    // FPS SETTINGS
    final int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    // CURSOR SETTINGS
    public Cursor cursor = new Cursor(); // Initialize cursor
    public Player player = new Player(this, keyH, cursor, playerClass); // Pass cursor to player

    Thread gameThread;

    // ENTITY AND OBJECTS
    public AssetSetter aSetter = new AssetSetter(this);
    public Entity[][] objArr= new Entity[maxMap][10];
    public Entity[][] npcArr = new Entity[maxMap][10];
    public Entity[][] mobArr = new Entity[maxMap][10];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public InteractiveTIle[][] iTile = new InteractiveTIle[maxMap][50];
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    ArrayList<Entity> entityList = new ArrayList<>();

    // PATHFINDER
    public Pathfinder pFinder = new Pathfinder(this);

    // GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1; // NO USAGE SO FAR
    public final int pauseState = 2; // NO USAGE SO FAR
    public final int dialogueState = 3; // NO USAGE SO FAR
    public final int shopState = 4;
    public final int deathState = 5;

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

        java.awt.Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0,0), "blank cursor"
        );

        this.setCursor(blankCursor);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

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
                        mobArr[currentMap][mob] = null;
                    }
                }
            }

            for (int i = 0; i < iTile[1].length; i++){ // INTERACTIVE TILES
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }

            for (int i = 0; i < projectileList.size(); i++){
                if (projectileList.get(i) != null){
                    if (projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
        }
    }

    public void setMapColor () {
        switch (currentMap) {
            case 0:
                setBackground(Color.decode("#222034"));
                break;
            case 1:
                setBackground(Color.decode("#42393a"));
                break;
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

            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            for (Entity proj : projectileList)
                if (proj != null) proj.draw(g2);

            // EMPTY ENTITY LIST
            for (int i = 0; i < entityList.size(); i++){
                entityList.remove(i);
            }
            ui.draw(g2);

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
