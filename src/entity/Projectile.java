package entity;

import main.GamePanel;
import object.OBJ_Gun_SnowBallCannon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Timer;

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
    public double pAngle = gp.cursor.angle;


    public Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        selectedProjectile = 0;
        projectileSelect(selectedProjectile);
        proX = getPlayerX();
        proY = getPlayerY();
    }

    public void projectileSelect(int selectedProjectile) {
        switch (selectedProjectile) {
            case 0:
                selectedProjectile = 0;
                break;
        }
    }

    public void update() {

        angleThroughSpeed();

        switch (selectedProjectile) {
            case 0:
                if (snc == null) {
                    snc = new OBJ_Gun_SnowBallCannon(gp);
                }
                snc.update();
                break;
        }

    }

    public void draw(Graphics2D g2) {
        if (snc == null) {
            snc = new OBJ_Gun_SnowBallCannon(gp);

        }
        if (proNum > 0 && proNum <= snc.spriteList.size()) {
            BufferedImage projectiles = snc.spriteList.get(snc.proNum - 1);
            g2.drawImage(projectiles, proX, proY, null);
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



    public void angleThroughSpeed() {
        if (gp.mouseH.leftClick) {
            gp.cursor.calculateAngle(gp.player.worldX, gp.player.worldY);
            mousePosX = gp.cursor.deltaX;
            mousePosY = gp.cursor.deltaY;
            dirY = (int) (pythag(mousePosX, mousePosY) * Math.sin(Math.atan2(mousePosX, mousePosY)));
            dirX = (int) (pythag(mousePosX, mousePosY) * Math.cos(Math.atan2(mousePosX, mousePosY)));

            System.out.println("X: " + dirX);
            System.out.println("Y: " + dirY);

            if (Math.abs(dirX) > 1) {
                proX += dirX / 100;
            } else if (Math.abs(dirX) <= 1) {
                proX -= dirX/100;
            }
            if (Math.abs(dirY) > 1) {
                proY += dirY / 100;
            } else if (Math.abs(dirY) <= 1) {
                proY -= dirY/100;
            }

        }
    }

    public double pythag(int x, int y){
        int c = (x*x) + (y*y);
        double length = Math.sqrt(c);
        return length;
    }

}



