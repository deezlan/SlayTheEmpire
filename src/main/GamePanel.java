package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entity.Cursor;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import tile.TileManager;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {
    // Screen Setting
    final int ORIGINAL_TILE_SIZE = 16;
    final double SCALE = 3;
    public final int TILE_SIZE = (int)(ORIGINAL_TILE_SIZE * SCALE);
    public final int MAX_SCREEN_COL = 17;
    public final int MAX_SCREEN_ROW = 13;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public int gameArea;

    //World Settings
    public int MAX_WORLD_COL = 17; //must be same as map size
    public int MAX_WORLD_ROW = 14; //must be same as map size

    // FPS Settings
    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public Cursor cursor = new Cursor(); // Initialize cursor
    public Player player = new Player(this, keyH, cursor); // Pass cursor to player
    public MouseHandler mouseH = new MouseHandler();

    Thread gameThread;

    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject[] objArr= new SuperObject[10];
    public Entity[] npcArr = new Entity[10];
    public Entity[] mobArr = new Entity[10];
    public Projectile[] projectileArr = new Projectile[10];
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Projectile projectile = new Projectile(this);
    public UI ui = new UI(this);

    public EventHandler eHandler = new EventHandler(this);

    // Game States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1; // NO USAGE SO FAR
    public final int pauseState = 2; // NO USAGE SO FAR
    public final int dialogueState = 3; // NO USAGE SO FAR
    public final int shopState = 4;
    public final int deathState = 5;

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
        });
    }

    public void hideCursor() {
        BufferedImage cursorImg = new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB);

        java.awt.Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0,0), "blank cursor"
        );

        this.setCursor(blankCursor);
    }

    public Cursor getPCursor() {
        return cursor;
    }

//    public Cursor getPCursor() {
//        return cursor;
//    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
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

            for (SuperObject obj : objArr) {
                if (obj != null) obj.update();
            }

            for (Entity npc : npcArr) {
                if (npc != null) npc.update();
            }

            for (Entity mob : mobArr) {
                if (mob != null) mob.update();
            }

            if (projectile != null) {
                projectile.update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        } else if (gameState == playState) {
//            switch (gameArea) {
//                case 0:
//                    this.player.worldX = 350;
//                    this.player.worldY = 30;
//                    break;
//                case 1:
//                    this.player.worldX = 145;
//                    this.player.worldY = 232;
//                    break;
//                default:
//                    System.out.println("Default Case GamePanel paintComponent");
//            }
            tileM.draw(g2); // Draw tiles

            for (SuperObject superObject : objArr)
                if (superObject != null) superObject.draw(g2, this);

            for (Entity entity : npcArr)
                if (entity != null) entity.draw(g2);

            for (Entity mob : mobArr)
                if (mob != null) mob.draw(g2);

            if (projectile != null && mouseH.leftClick) {
                projectile.draw(g2);
            }

            player.draw(g2); // Draw player

            ui.draw(g2);

        } else if (gameState == dialogueState){
            tileM.draw(g2); // Draw tiles

            player.draw(g2); // Draw player

            for (SuperObject superObject : objArr)
                if (superObject != null) superObject.draw(g2, this);

            for (Entity entity : npcArr)
                if (entity != null) entity.draw(g2);

            if (projectile != null) {
                projectile.draw(g2);
            }
            ui.draw(g2);
        } else {
            tileM.draw(g2); // Draw tiles

            player.draw(g2); // Draw player

            for (SuperObject superObject : objArr)
                if (superObject != null) superObject.draw(g2, this);

            for (Entity entity : npcArr)
                if (entity != null) entity.draw(g2);

            for (Entity mob : mobArr)
                if (mob != null) mob.draw(g2);

            ui.draw(g2);
        }
        g2.dispose();
    }
}
