package entity;

import main.GamePanel;
import object.OBJ_Gun_SnowBallCannon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends Entity {
    Entity user;
    public ArrayList<BufferedImage> spriteList = new ArrayList<>();
    int selectedProjectile;
    OBJ_Gun_SnowBallCannon snc;
    public BufferedImage image;
    private Graphics2D g2;
    public int proX;
    public int proY;
    public double proSpeed = 0.7;
    public int proNum = 1;
    public int proCounter = 0;
    public int mousePosX;
    public int mousePosY;
    public int dirX;
    public int dirY;
    public Timer timer;



    public Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        selectedProjectile = 0;
        projectileSelect(selectedProjectile);
        proX = getPlayerX();
        proY = getPlayerY();
        timer = new Timer(50, e -> updatePosition());
        timer.start();

    }

    public void projectileSelect(int selectedProjectile) {
        switch (selectedProjectile) {
            case 0:
                selectedProjectile = 0;
                break;
        }
    }

    public void update() {



        switch (selectedProjectile) {
            case 0:
                if (snc == null) {
                    snc = new OBJ_Gun_SnowBallCannon(gp);
                }
                snc.update();
                break;
        }

        updatePosition();
    }

    public void draw(Graphics2D g2) {
        if (snc == null) {
            snc = new OBJ_Gun_SnowBallCannon(gp);

        }
        if (proNum > 0 && proNum <= snc.spriteList.size()) {
            BufferedImage projectiles = snc.spriteList.get(snc.proNum - 1);
            g2.drawImage(projectiles, proX, proY - 30 , null);
        } else {
            System.out.println("Invalid spriteNum: " + proNum);
        }


    }

    public int getPlayerX() {
        return gp.player.worldX;
    }

    public int getPlayerY() {
        return gp.player.worldY;
    }



    private void updatePosition() {
        // Calculate the difference in position
        int dx = gp.cursor.deltaX;
        int dy = gp.cursor.deltaY;

        System.out.println("X: " + dx);
        System.out.println("y: " + dy);

        if (Math.abs(dx) > 1) {
            proX += dx / 90;
        }
        if (Math.abs(dy) > 1) {
            proY += dy / 90;
        }

        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
            timer.stop();
        }
    }


}



