package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import entity.Entity;
import entity.Player;
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
    public final int MAX_WORLD_COL = 17; //must be same as map size
    public final int MAX_WORLD_ROW = 13; //must be same as map size

    // FPS Settings
    final int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;

    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);
    public SuperObject[] objArr= new SuperObject[10];
    public Entity[] npcArr = new Entity[10];
    public CollisionChecker cChecker = new CollisionChecker(this);
    public UI ui = new UI(this);

    // Game States
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1; // NO USAGE SO FAR
    public final int pauseState = 2; // NO USAGE SO FAR
//    public final int dialogueState = 3; // NO USAGE SO FAR


    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#222034"));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupLobby() {
        gameArea = 0;
        aSetter.setObject();
        aSetter.setNPC();
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

            for (SuperObject obj : objArr) {
                if (obj != null) {
                    obj.update();
                }
            }

            for (Entity npc : npcArr) {
                if (npc != null) {
                    npc.update();
                }
            }
        }
//        if (gameState == pauseState) {
//            // placeholder
//        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Title Screen
        if (gameState == titleState) {
            ui.draw(g2);
        } else if (gameState == playState) {
            tileM.draw(g2); // Draw tiles

            player.draw(g2); // Draw player

            ui.draw(g2);

            for (SuperObject superObject : objArr)
                if (superObject != null) superObject.draw(g2, this);

            for (Entity entity : npcArr)
                if (entity != null) entity.draw(g2);
//        } else if (gameState == pauseState) {

        } else {
            tileM.draw(g2); // Draw tiles

            player.draw(g2); // Draw player

            ui.draw(g2);
        }

        g2.dispose();
    }
}
