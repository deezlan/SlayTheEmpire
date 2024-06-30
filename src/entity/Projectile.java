package entity;

import main.GamePanel;
import object.OBJ_Gun_SnowBallCannon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Projectile extends Entity {
    Entity user;
    public ArrayList <BufferedImage> spriteList = new ArrayList<>();
    int selectedProjectile;
    OBJ_Gun_SnowBallCannon snc;
    public BufferedImage image;
    private Graphics2D g2;
    public int proX = 100;
    public int proY = 100;
    public int proSpeed = 5;
    public int proNum = 1;

    public Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        selectedProjectile = 0;
        projectileSelect(selectedProjectile);

        set(proX, proY, user);
    }

    public void projectileSelect (int selectedProjectile){
        switch(selectedProjectile){
            case 0:
                selectedProjectile = 0;
                break;
        }
    }

    public void update(){
        switch(selectedProjectile){
            case 0:
                if (snc == null){
                    snc = new OBJ_Gun_SnowBallCannon(gp);
                }
                snc.update();
                proX -= proSpeed;
                System.out.println("proY is now: " + proX);
                break;
        }

    }

    public void draw(Graphics2D g2) {
        if (snc == null) {
            snc = new OBJ_Gun_SnowBallCannon(gp);

        }
        if (proNum > 0 && proNum <= snc.spriteList.size()) {
            BufferedImage projectiles = snc.spriteList.get(proNum - 1);
            g2.drawImage(projectiles, proX, proY, null);
        } else {
            System.out.println("Invalid spriteNum: " + proNum);
        }


    }



    public void set(int worldX, int worldY, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.user = user;
    }

}

