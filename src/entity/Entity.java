package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    public ArrayList<BufferedImage> imageList = new ArrayList<>();
    GamePanel gp;
    public int worldX, worldY;
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

    public Rectangle solidArea; // draw area around player
    public Entity(GamePanel gp){
        this.gp=gp;
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldX - gp.player.worldX + gp.player.screenY;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
            worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
            worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
            worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY
        ) {
            BufferedImage image = null;
            switch (action) {
                case "idleRight":
                    if (spriteNum == 1) { image = idleRight1; }
                    if (spriteNum == 2) { image = idleRight2; }
                    if (spriteNum == 3) { image = idleRight3; }
                    if (spriteNum == 4) { image = idleRight4; }
                    if (spriteNum == 5) { image = idleRight5; }
                    if (spriteNum == 6) { image = idleRight6; }
                    break;

                case "idleLeft":
                    if (spriteNum == 1) { image = idleLeft1; }
                    if (spriteNum == 2) { image = idleLeft2; }
                    if (spriteNum == 3) { image = idleLeft3; }
                    if (spriteNum == 4) { image = idleLeft4; }
                    if (spriteNum == 5) { image = idleLeft5; }
                    if (spriteNum == 6) { image = idleLeft6; }
                    break;

                case "moveUp":
                case "moveDown":
                    if (lookingRight) {
                        if (spriteNum == 1) { image = moveRight1; }
                        if (spriteNum == 2) { image = moveRight2; }
                        if (spriteNum == 3) { image = moveRight3; }
                        if (spriteNum == 4) { image = moveRight4; }
                        if (spriteNum == 5) { image = moveRight5; }
                        if (spriteNum == 6) { image = moveRight6; }
                        if (spriteNum == 7) { image = moveRight7; }
                        if (spriteNum == 8) { image = moveRight8; }
                    } else {
                        if (spriteNum == 1) { image = moveLeft1; }
                        if (spriteNum == 2) { image = moveLeft2; }
                        if (spriteNum == 3) { image = moveLeft3; }
                        if (spriteNum == 4) { image = moveLeft4; }
                        if (spriteNum == 5) { image = moveLeft5; }
                        if (spriteNum == 6) { image = moveLeft6; }
                        if (spriteNum == 7) { image = moveLeft7; }
                        if (spriteNum == 8) { image = moveLeft8; }
                    }
                    break;

                case "moveRight":
                case "moveUpRight":
                case "moveDownRight":
                    if (spriteNum == 1) { image = moveRight1; }
                    if (spriteNum == 2) { image = moveRight2; }
                    if (spriteNum == 3) { image = moveRight3; }
                    if (spriteNum == 4) { image = moveRight4; }
                    if (spriteNum == 5) { image = moveRight5; }
                    if (spriteNum == 6) { image = moveRight6; }
                    if (spriteNum == 7) { image = moveRight7; }
                    if (spriteNum == 8) { image = moveRight8; }
                    lookingRight = true;
                    break;

                case "moveLeft":
                case "moveUpLeft":
                case "moveDownLeft":
                    if (spriteNum == 1) { image = moveLeft1; }
                    if (spriteNum == 2) { image = moveLeft2; }
                    if (spriteNum == 3) { image = moveLeft3; }
                    if (spriteNum == 4) { image = moveLeft4; }
                    if (spriteNum == 5) { image = moveLeft5; }
                    if (spriteNum == 6) { image = moveLeft6; }
                    if (spriteNum == 7) { image = moveLeft7; }
                    if (spriteNum == 8) { image = moveLeft8; }
                    lookingRight = false;
                    break;
            }
            g2.drawImage(image,screenX,screenY,gp.TILE_SIZE,gp.TILE_SIZE,null);
        }
    }


    public Rectangle solidArea = new Rectangle(0,0,48,48); // draw area around entities
    public int solidAreaDefaultX, solidAreaDefaultY;

    // entity's collision directions
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;
}
