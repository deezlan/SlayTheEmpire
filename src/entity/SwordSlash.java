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
        solidArea.x = 0; // position of actual collision square
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 100; // outer area of collision square
        solidArea.height = 100;

        action = "moveUp";
    }

    public void draw (Graphics2D g2, String direction){
        AffineTransform old = g2.getTransform();

        if (weaponSpriteNum > weaponSpriteList.size()) weaponSpriteNum =1;
        BufferedImage weaponImage = weaponSpriteList.get(weaponSpriteNum);
        g2.translate(gp.player.worldX+60, gp.player.worldY + gp.TILE_SIZE + 10);

        g2.rotate(gp.cursor.getAngle());

        if (gp.gameArea == 1) {
            g2.drawImage(weaponImage, 0, -gp.TILE_SIZE, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            g2.draw(solidArea);
        } else if (gp.gameState == 1){
            g2.drawImage(weaponImage, 0, -gp.TILE_SIZE, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
        }

        g2.setTransform(old);
    }


    public void attacking(int index){
        double offsetX = 50; // This is the horizontal offset
        double offsetY = 30; // This is the vertical offset

        double rotatedOffsetX = offsetX * Math.sin(gp.cursor.getAngle()) - offsetY * Math.cos(gp.cursor.getAngle());
        double rotatedOffsetY = offsetX * Math.sin(gp.cursor.getAngle()) + offsetY * Math.cos(gp.cursor.getAngle());

        worldX = gp.player.worldX + (int) rotatedOffsetX;
        worldY = gp.player.worldY + (int) rotatedOffsetY;
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
