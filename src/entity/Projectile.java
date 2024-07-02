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
    public double proSpeed = 70;
    public int proNum = 1;
    public int proCounter = 0;
    public Timer timer;
    double dx;
    double dy;
    int iterate;



    public Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        selectedProjectile = 0;
        projectileSelect(selectedProjectile);
        timer = new Timer(20, e -> updatePosition());
        timer.start();
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

        switch (selectedProjectile) {
            case 0:
                if (snc == null) {
                    snc = new OBJ_Gun_SnowBallCannon(gp);
                }
                snc.update();
                break;
        }

        if(iterate % 5 == 0){
            proX = getPlayerX();
            proY = getPlayerY();

            System.out.println(proX);
            System.out.println(proY);
        }else{
            iterate++;
        }
        updatePosition();
    }

    public void draw(Graphics2D g2) {
        if (snc == null) {
            snc = new OBJ_Gun_SnowBallCannon(gp);

        }
        if (proNum > 0 && proNum <= snc.spriteList.size()) {
            BufferedImage projectiles = snc.spriteList.get(snc.proNum - 1);
            g2.drawImage(projectiles, proX + 10, proY - 30 , gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);
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
        dx = gp.cursor.deltaX;
        dy = gp.cursor.deltaY;;

//        System.out.println("X: " + dx);
//        System.out.println("y: " + dy);

        if (Math.abs(dx) > 1) {
            proX += (int) (dx / proSpeed);
        }
        if (Math.abs(dy) > 1) {
            proY += (int) (dy / proSpeed);
        }

        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
            timer.stop();
        }
    }


}



