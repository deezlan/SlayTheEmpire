package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    public int worldX, worldY;
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

    public int maxLife;
    public int life;

    public Rectangle solidArea; // draw area around player
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
    }

    // entity's collision directions
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;
}
