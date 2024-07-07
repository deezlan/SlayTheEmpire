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
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Comparator;

import entity.Cursor;
import entity.Entity;
import entity.Player;
import tile.TileManager;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int ORIGINAL_TILE_SIZE = 16;
    final double SCALE = 3;
    public final int TILE_SIZE = (int)(ORIGINAL_TILE_SIZE * SCALE);
    public final int MAX_SCREEN_COL = 17;
    public final int MAX_SCREEN_ROW = 13;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public int gameArea = 0;
    public int playerClass = 0; // player class here

    // WORLD SETTINGS
    public int MAX_WORLD_COL = 17; //must be same as map size
    public int MAX_WORLD_ROW = 14; //must be same as map size

    // FPS SETTINGS
    final int FPS = 60;

    // initialize login panel
    public LoginPanel loginPanel;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);

    // CURSOR SETTINGS
    public Cursor cursor = new Cursor(); // Initialize cursor
    public Player player = new Player(this, keyH, cursor, playerClass); // Pass cursor to player

    public MouseHandler mouseH = new MouseHandler();
    public boolean
            onUsername = false,
            onPassword = false;

    Thread gameThread;

    // ENTITY AND OBJECTS
    public AssetSetter aSetter = new AssetSetter(this);
    public Entity[] objArr= new Entity[10];
    public Entity[] npcArr = new Entity[10];
    public Entity[] mobArr = new Entity[10];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int startMenuState = 1;
    public final int playState = 2;
    public final int pauseState = 3;
    public final int loginState = 4;
    public final int dialogueState = 5; // NO USAGE SO FAR
    public final int shopState = 6;
    public final int registerState = 8;
    public final int characterSelectionState = 9;
    public final int optionState = 10;
    public final int deathState = 11;




    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#222034"));
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

        java.awt.Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0,0), "blank cursor"
        );

        this.setCursor(blankCursor);
    }

//    public Cursor getPCursor() {
//        return cursor;
//    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState; // TESTING LOGIN RIGHT NOW
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
            hideCursor(); // HIDE CURSOR
            player.update();

            for (Entity entity : objArr) { // OBJECTS
                if (entity != null) {entity.update();}
            }

            for (Entity entity : npcArr) { // NPCS
                if (entity != null) {entity.update();}
            }

            for (int mob = 0; mob < mobArr.length; mob++) { // MOBS
                if (mobArr[mob] != null) {
                    if (mobArr[mob].alive && (!mobArr[mob].dead)) {
                        mobArr[mob].update();
                    }
                    if (!mobArr[mob].alive){
                        mobArr[mob] = null;
                    }
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
//        } else if (gameState == playState) {
//               switch (gameArea) {
//                    case 0:
//                       this.player.worldX = 350;
//                       this.player.worldY = 30;
//                       break;
//                   case 1:
//                       this.player.worldX = 145;
//                       this.player.worldY = 232;break;
//                   default:System.out.println("Default Case GamePanel paintComponent");
//              }
//            tileM.draw(g2); // Draw tiles
//
//            for (Entity object : objArr)
//                if (object != null) object.draw(g2);
//
//            for (Entity entity : npcArr)
//                if (entity != null) entity.draw(g2);
//
//            for (Entity mob : mobArr)
//                if (mob != null) mob.draw(g2);
//
//            player.draw(g2); // Draw player
//
//            ui.draw(g2);
//            ui.drawPlayerMoney();

        } else {
            tileM.draw(g2); // Draw tiles

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (Entity entity : npcArr) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

//            for (Entity NPC : npcArr)
//                if (NPC != null) {
//                    entityList.add(npcArr[NPC]);
//                }

            for (Entity entity : objArr) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

//            for (Entity object : objArr)
//                if (object != null){
//                    entityList.add(objArr[object]);
//                }

            for (Entity entity : mobArr) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }
            for (Entity proj : projectileList)
                if (proj != null) proj.draw(g2);

//            for (Entity mob : mobArr)
//                if (mob != null) {
//                    entityList.add(mobArr[mob]);
//                }

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
        }
        g2.dispose();
    }
}
