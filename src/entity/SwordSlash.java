//package entity;
//
//import main.GamePanel;
//import main.UtilityTool;
//
//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//
//public class SwordSlash extends Entity{
//    public SwordSlash(GamePanel gp) {
//        super(gp);
//        this.gp = gp;
//        getAttackImage();
//
//        solidArea = new Rectangle(); // draws a square at the centre of the player
//        solidArea.x = -28;
//        solidArea.y = -35;
//        solidArea.width = 50; // outer area of collision square
//        solidArea.height = 90;
//        action = "moveUp";
//
//        speed = 0;
//    }
//
//    public void draw (Graphics2D g2, String direction){
//        AffineTransform old = g2.getTransform();
//
//        if (weaponSpriteNum > weaponList.size()) weaponSpriteNum =1;
//        BufferedImage weaponImage = weaponList.get(weaponSpriteNum);
//        g2.translate(gp.player.worldX+60, gp.player.worldY + gp.TILE_SIZE + 10);
//        g2.rotate(gp.cursor.getAngle());
//
//
//        if (gp.gameArea == 1) {
//            g2.drawImage(weaponImage, 0, -gp.TILE_SIZE, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
//        } else if (gp.gameState == 1){
//            g2.drawImage(weaponImage, 0, -gp.TILE_SIZE, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
//        }
//
//        g2.setTransform(old);
//        g2.draw(solidArea);
//    }
//
//
//    public void attacking(){
//
//        weaponSpriteCounter++;
//        if (weaponSpriteCounter <= 5){
//            weaponSpriteNum = 0;
//            checkHit();
//        } else if (weaponSpriteCounter <= 10) {
//            weaponSpriteNum = 1;
//            checkHit();
//        } else if (weaponSpriteCounter <= 15) {
//            weaponSpriteNum = 2;
//            checkHit();
//        } else if (weaponSpriteCounter <= 20) {
//            weaponSpriteNum = 3;
//            checkHit();
//        } else if (weaponSpriteCounter <= 25) {
//            weaponSpriteNum = 3;
//            checkHit();
//        } else if (weaponSpriteCounter <= 30) {
//            weaponSpriteNum = 4;
//            checkHit();
//        } else if (weaponSpriteCounter <= 35) {
//            weaponSpriteNum = 4;
//            checkHit();
//        } else if (weaponSpriteCounter <= 40) {
//            weaponSpriteNum = 5;
//            checkHit();
//        } else if (weaponSpriteCounter <= 45) {
//            weaponSpriteNum = 5;
//            checkHit();
//        } else if (weaponSpriteCounter <= 50) {
//            weaponSpriteNum = 5;
//            weaponSpriteCounter = 0;
//            gp.player.attacking = false;
//            checkHit();
//        }
//
//    }
//
//    public void checkHit(){
//        worldX = gp.player.worldX;
//        worldY = gp.player.worldY;
//        int wepIndex = gp.cChecker.checkEntityCollision(this, gp.npcArr);
//        System.out.println(worldX);
//        System.out.println(worldY);
//        damageMonster(wepIndex);
//    }
//
//    public void damageMonster(int index){
//        if (index != 999){
//            System.out.println(index);
//        } else {
//            System.out.println("Miss");
//        }
//    }
//
//    public void getAttackImage() {
//        try {
//            weaponList.add(0, UtilityTool.loadSprite("/Weapon/Sword/00.png", "Not found weapon image"));
//            weaponList.add(1, UtilityTool.loadSprite("/Weapon/Sword/01.png", "Not found weapon image"));
//            weaponList.add(2, UtilityTool.loadSprite("/Weapon/Sword/02.png", "Not found weapon image"));
//            weaponList.add(3, UtilityTool.loadSprite("/Weapon/Sword/03.png", "Not found weapon image"));
//            weaponList.add(4, UtilityTool.loadSprite("/Weapon/Sword/04.png", "Not found weapon image"));
//            weaponList.add(5, UtilityTool.loadSprite("/Weapon/Sword/05.png", "Not found weapon image"));
//        } catch (IOException e){
//            e.printStackTrace(System.out);
//        }
//    }
//}
