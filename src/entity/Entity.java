package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    String message;
    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    boolean attacking = false;
    public Projectile projectile;
    public ArrayList<BufferedImage>
            currentSpriteList = new ArrayList<>(),
            idleRightSpriteList = new ArrayList<>(),
            idleLeftSpriteList = new ArrayList<>(),
            moveRightSpriteList = new ArrayList<>(),
            moveLeftSpriteList = new ArrayList<>(),
            weaponSpriteList = new ArrayList<>();
//            scaledList = new ArrayList<>();
    public String action;
    public boolean lookingRight;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int weaponSpriteCounter = 0;
    public int weaponSpriteNum = 1;

    // entity's collision directions
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;

    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities

    public Entity (GamePanel gp) {
        this.gp = gp;
    }

    public int solidAreaDefaultX, solidAreaDefaultY;

    public void loopThroughSprites() {
        spriteNum = (spriteNum < currentSpriteList.size()) ? spriteNum + 1 : 1;
        spriteCounter = 0;
    }

    public void loopThroughWeaponSprites() {
        if (weaponSpriteCounter <= 5){
            weaponSpriteNum = 0;
        } else if (weaponSpriteCounter <= 10) {
            weaponSpriteNum = 1;
        } else if (weaponSpriteCounter <= 15) {
            weaponSpriteNum = 2;
        } else if (weaponSpriteCounter <= 20) {
            weaponSpriteNum = 3;
        } else if (weaponSpriteCounter <= 25) {
            weaponSpriteNum = 4;
        } else if (weaponSpriteCounter <= 30) {
            weaponSpriteNum = 5;
        } else if (weaponSpriteCounter <= 35) {
            weaponSpriteNum = 1;
            weaponSpriteCounter = 0;
            attacking = false;
        }
    }

    public void update() {
        spriteCounter++;
        if (this.currentSpriteList.size() > 7) {
            if (spriteCounter > 5) loopThroughSprites();
        } else {
            if (spriteCounter > 9) loopThroughSprites();
        }
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkPLayer(this);

        if(projectile == null){
            projectile = new Projectile(gp);
        }
        projectile.update();

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = currentSpriteList.get(spriteNum - 1);

        switch (gp.gameArea) {
            case 0:
                g2.drawImage(image, worldX, worldY, null);
                break;
            case 1:
            case 2:
            default:
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY; // Corrected worldY subtraction

                if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)
                {
                    g2.drawImage(image, screenX, screenY, null);
                }
        }

    }

    public String name;
    public int attack;
}
