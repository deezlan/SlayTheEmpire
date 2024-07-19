package main;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import TileInteractive.InteractiveTIle;
import ai.Pathfinder;
import data.SaveLoad;
import entity.Cursor;
import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    Thread gameThread;
    BufferedImage tempScreen;
    Graphics2D g2;

    // SCREEN SETTINGS
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
    public int playerClass;
    public KeyHandler keyH = new KeyHandler(this);

    // LOGIN SETTINGS
    public LoginSystem loginSys = new LoginSystem(this);

    // PLAYER SETTINGS
    Sound music = new Sound();
    Sound effect = new Sound();
    public Cursor cursor = new Cursor(this); // Initialize cursor
    public Player player;
    public MouseHandler mouseH = new MouseHandler();
    public UI ui = new UI(this);

    // ENTITY AND OBJECTS ARRAYS
    private final ArrayList<Entity> entityList = new ArrayList<>();
    public AssetSetter aSetter = new AssetSetter(this);
    public Entity[][]
            objArr = new Entity[maxMap][30],
            npcArr = new Entity[maxMap][10],
            mobArr = new Entity[maxMap][20],
            gateArr = new Entity[maxMap][50],
            projectileArr = new Entity[maxMap][50];
    public InteractiveTIle[][] iTile = new InteractiveTIle[maxMap][50];

    // HANDLERS
    public CollisionChecker cChecker = new CollisionChecker(this);
    public EventHandler eHandler = new EventHandler(this);
    public SaveLoad saveLoad = new SaveLoad(this, 3);

    // PATHFINDER
    public Pathfinder pFinder = new Pathfinder(this);

    // CUTSCENE
    public boolean bossBattleOn = false;
    public CutsceneManager csManager = new CutsceneManager(this);

    // GAME SETTINGS
    public int gameState;
    public final int
            titleState = 0,
            playState = 1,
            pauseState = 2,
            dialogueState = 3,
            shopState = 4,
            deathState = 5,
            transitionState = 6,
            loginState = 7,
            creditsState = 8,
            characterSelectionState = 9,
            optionState = 10,
            optionState2 = 12,
            startMenuState = 11,
            cutsceneState = 13,
            controlsState = 14,
            savePageState = 15,
            savePageState2 = 16;

    public final int
                easyMode = 1,
                normalMode = 2,
                hardMode = 3;
    public int gameMode = easyMode;
    public int progressSaved = 1;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#181425"));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true); // pass the player instance variable to the KeyHandler constructor

        // DEFAULT VOLUME
        music.volumeScale = 3;
        effect.volumeScale = 3;

//        hideCursor();

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursor.updateMousePosition(e.getX(), e.getY());
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseH.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mouseH.mouseEntered(e);
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

    public void showCursor() {
        java.awt.Cursor defaultCursor = java.awt.Cursor.getDefaultCursor();
        this.setCursor(defaultCursor);
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    // STOP MUSIC
    public void stopMusic() {
        music.stop();
    }
    // PLAY SOUND EFFECT
    public void playSE(int i) {
        effect.setFile(i);
        effect.play();
    }

    public void setupGame() {
        tempScreen = new BufferedImage(SCREEN_WIDTH,SCREEN_HEIGHT,BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();
        gameState = titleState; // TESTING LOGIN RIGHT NOW
        playMusic(0);
    }

    public void retry() {
        player.setDefaultPosition();
        player.restoreLife();
        aSetter.setMonster();
        aSetter.setNPC();
        aSetter.setGates();
    }

//    public void restart() {
//        player.setDefaultValues();
//        player.setDefaultPosition();
//        player.restoreLife();
//        aSetter.setMonster();
//        aSetter.setNPC();
//        aSetter.setObject();
//        aSetter.setInteractiveTile();
//    }

    public void loadLevel() {
        bossBattleOn = false;
        aSetter.loadAssets();
        gameState = playState;
    }

    // MAP SETTINGS
    public void setMapColor () {
        switch (currentMap) {
            case 0:
                setBackground(Color.decode("#181425"));
                break;
            case 1, 2:
                setBackground(Color.decode("#42393A"));
        }
    }

    // FPS SETTINGS
    @Override
    public void run() {
        // FPS SETTINGS
        int FPS = 60;
        double drawInterval = (double) 1000000000 / FPS;
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
            hideCursor(); // HIDE CURSOR
            player.update();

            // OBJECT
            for (int i = 0; i < objArr[1].length; i++) {
                if(objArr[currentMap][i] != null){
                    objArr[currentMap][i].update();
                }
            }
            // NPCs
            for (int i = 0; i < npcArr[1].length; i++) {
                if(npcArr[currentMap][i] != null){
                    npcArr[currentMap][i].update();
                }
            }
            // MOBS
            for (int mob = 0; mob < mobArr[1].length; mob++) {
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
            // INTERACTIVE TILES
            for (int i = 0; i < iTile[1].length; i++) {
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            // PROJECTILES
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

            for (int i = 0; i < gateArr[1].length; i++) {
                if (gateArr[currentMap][i] != null) {
                    gateArr[currentMap][i].update();
                }
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (gameState == optionState){
            showCursor();
        }

        // Title Screen
        if (gameState == playState ||
                gameState == shopState ||
                gameState == dialogueState ||
                gameState == pauseState ||
                gameState == optionState ||
                gameState == transitionState ||
                gameState == cutsceneState) {

            // DEBUG
            long drawStart = 0;
            if(keyH.showDebug){
                drawStart = System.nanoTime();
            }

            tileM.draw(g2); // Draw tiles
            ui.draw(g2);

            for (Entity gate : gateArr[currentMap])
                if (gate != null) gate.draw(g2);

            // ADD ALL ENTITIES TO entityList //
            // PLAYER
            entityList.add(player);
            // NPCs
            for (int i = 0; i < npcArr[1].length; i++){
                if(npcArr[currentMap][i] != null){
                    entityList.add(npcArr[currentMap][i]);
                }
            }
            // OBJECT
            for (int i = 0; i < objArr[1].length; i++){
                if(objArr[currentMap][i] != null){
                    entityList.add(objArr[currentMap][i]);
                }
            }
            // MOBS
            for (int i = 0; i < mobArr[1].length; i++){
                if(mobArr[currentMap][i] != null){
                    entityList.add(mobArr[currentMap][i]);
                }
            }
            // PROJECTILES
            for (int i = 0; i < projectileArr[1].length; i++){
                if(projectileArr[currentMap][i] != null){
                    entityList.add(projectileArr[currentMap][i]);
                }
            }

            // SORT FOR RENDERING ORDER
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ALL ENTITIES
            for (Entity entity : entityList)
                entity.draw(g2);

            // EMPTY ENTITY LIST
            for (int i = 0; i < entityList.size(); i++)
                entityList.remove(i);

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
                g2.drawString("God Mode: " + keyH.godModeOn, x, y); y += lineHeight;
                g2.drawString("Draw Time: " + passed, x, y);
            }
        } else {
            ui.draw(g2);
        }
        g2.dispose();
    }
}
