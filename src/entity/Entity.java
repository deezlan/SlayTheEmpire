package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    String message;
    GamePanel gp;
    public int worldX, worldY, spriteWidth, spriteHeight;
    public int speed;
    public ArrayList<BufferedImage>
            currentSpriteList = new ArrayList<>(),
            idleRightSpriteList = new ArrayList<>(),
            idleLeftSpriteList = new ArrayList<>(),
            moveRightSpriteList = new ArrayList<>(),
            moveLeftSpriteList = new ArrayList<>();
    public String action;
    public boolean lookingRight;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // entity's collision directions
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;

    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public int solidAreaDefaultX, solidAreaDefaultY;

    public void loopThroughSprites() {
        spriteNum = (spriteNum < currentSpriteList.size()) ? spriteNum + 1 : 1;
        spriteCounter = 0;
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
    }

    public void draw(Graphics2D g2, int i) {
        BufferedImage image = currentSpriteList.get(spriteNum - 1);

        switch (gp.gameArea) {
            case 0:
                g2.drawImage(image, worldX, worldY, gp.npcArr[i].spriteWidth, gp.npcArr[i].spriteHeight, null);
                break;
            case 1:
            default:
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY; // Corrected worldY subtraction

                if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)
                {
                    g2.drawImage(image, screenX, screenY, gp.npcArr[i].spriteWidth, gp.npcArr[i].spriteHeight, null);
                }
        }
    }
}
