package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class Entity {
    GamePanel gp;
    public int worldX, worldY, spriteWidth, spriteHeight;
    public int speed;
    public BufferedImage
            idleRight1, idleRight2, idleRight3, idleRight4, idleRight5, idleRight6,
            idleLeft1, idleLeft2, idleLeft3, idleLeft4, idleLeft5, idleLeft6,
            moveLeft1, moveLeft2, moveLeft3, moveLeft4, moveLeft5, moveLeft6, moveLeft7, moveLeft8,
            moveRight1, moveRight2, moveRight3, moveRight4, moveRight5, moveRight6, moveRight7, moveRight8;

    public String action;
    public boolean lookingRight;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2, int a) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY; // Corrected worldY subtraction

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {

            switch (action) {
                case "idleRight":
                    if (spriteNum == 1) { image = idleRight1; }
                    else if (spriteNum == 2) { image = idleRight2; }
                    else if (spriteNum == 3) { image = idleRight3; }
                    else if (spriteNum == 4) { image = idleRight4; }
                    else if (spriteNum == 5) { image = idleRight5; }
                    else if (spriteNum == 6) { image = idleRight6; }
                    break;

                case "idleLeft":
                    if (spriteNum == 1) { image = idleLeft1; }
                    else if (spriteNum == 2) { image = idleLeft2; }
                    else if (spriteNum == 3) { image = idleLeft3; }
                    else if (spriteNum == 4) { image = idleLeft4; }
                    else if (spriteNum == 5) { image = idleLeft5; }
                    else if (spriteNum == 6) { image = idleLeft6; }
                    break;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.npc[a].spriteWidth, gp.npc[a].spriteHeight, null);

//    public void draw(Graphics2D g2) {
//        BufferedImage image = null;
//        int screenX = worldX - gp.player.worldX + gp.player.screenX;
//        int screenY = worldX - gp.player.worldX + gp.player.screenY;
//
//        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
//                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
//                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
//                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
//
//            switch (action) {
//                case "idleRight":
//                    if (spriteNum == 1) { image = idleRight1; }
//                    if (spriteNum == 2) { image = idleRight2; }
//                    if (spriteNum == 3) { image = idleRight3; }
//                    if (spriteNum == 4) { image = idleRight4; }
//                    if (spriteNum == 5) { image = idleRight5; }
//                    if (spriteNum == 6) { image = idleRight6; }
//                    break;
//
//                case "idleLeft":
//                    if (spriteNum == 1) { image = idleLeft1; }
//                    if (spriteNum == 2) { image = idleLeft2; }
//                    if (spriteNum == 3) { image = idleLeft3; }
//                    if (spriteNum == 4) { image = idleLeft4; }
//                    if (spriteNum == 5) { image = idleLeft5; }
//                    if (spriteNum == 6) { image = idleLeft6; }
//                    break; }
//        }
//            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
    }

    public int solidAreaDefaultX, solidAreaDefaultY;

    // entity's collision directions
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;
}
