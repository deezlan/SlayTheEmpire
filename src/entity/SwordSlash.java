package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SwordSlash extends Entity{

    public SwordSlash(GamePanel gp) {
        super(gp);
        this.gp = gp;
        getAttackImage();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 56; // position of actual collision square
        solidArea.y = 72;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30; // outer area of collision square
        solidArea.height = 20;

        action = "moveUp";
    }

    public void draw (Graphics2D g2, String direction){
        AffineTransform old = g2.getTransform();

        if (weaponSpriteNum > weaponSpriteList.size()) weaponSpriteNum =1;
        BufferedImage weaponImage = weaponSpriteList.get(weaponSpriteNum);
        if (direction.equalsIgnoreCase("idleRight")){
            g2.translate(gp.player.worldX+50, gp.player.worldY + gp.TILE_SIZE + 10);
        } else if (direction.equalsIgnoreCase("idleLeft")){
            g2.translate(gp.player.worldX+70, gp.player.worldY + gp.TILE_SIZE + 10);
        }

        g2.rotate(gp.cursor.getAngle());

        if (gp.gameArea == 1) {
            g2.drawImage(weaponImage, 0, -gp.TILE_SIZE, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
        } else if (gp.gameState == 1){
            g2.drawImage(weaponImage, 0, -gp.TILE_SIZE, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
        }

        g2.setTransform(old);
    }

    public void attacking(int index){
        worldX = gp.player.worldX;
        worldY = gp.player.worldY;
        weaponSpriteCounter++;
        if (weaponSpriteCounter <= 5){
            weaponSpriteNum = 0;
            damageMonster(index);
        } else if (weaponSpriteCounter <= 10) {
            weaponSpriteNum = 1;
            damageMonster(index);
        } else if (weaponSpriteCounter <= 15) {
            weaponSpriteNum = 2;
            damageMonster(index);
        } else if (weaponSpriteCounter <= 20) {
            weaponSpriteNum = 3;
            damageMonster(index);
        } else if (weaponSpriteCounter <= 25) {
            weaponSpriteNum = 4;
            damageMonster(index);
        } else if (weaponSpriteCounter <= 30) {
            weaponSpriteNum = 5;
            damageMonster(index);
        } else if (weaponSpriteCounter <= 35) {
            weaponSpriteNum = 1;
            weaponSpriteCounter = 0;
            gp.player.attacking = false;
        }

    }

    public void damageMonster(int index){
        if (index != 999){
            System.out.println("Hit!");
        } else {
            System.out.println("Miss");
        }
    }

    public void getAttackImage() {
        try {
            weaponSpriteList.add(0, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_1.png"));
            weaponSpriteList.add(1, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_2.png"));
            weaponSpriteList.add(2, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_3.png"));
            weaponSpriteList.add(3, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_4.png"));
            weaponSpriteList.add(4, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_5.png"));
            weaponSpriteList.add(5, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_6.png"));
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }
}
