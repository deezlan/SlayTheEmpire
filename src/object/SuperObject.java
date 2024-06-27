package object;

import main.UtilityTool;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SuperObject {
    public ArrayList<BufferedImage> spriteList = new ArrayList<>();
    public String name, message;
    public boolean collision = false;

    UtilityTool uTool = new UtilityTool();
    public int worldX, worldY, spriteWidth, spriteHeight,
        spriteCounter = 0,
        spriteNum = 1;
    public Rectangle solidArea = new Rectangle();
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;

    // Change current object sprite to next
    public void update() {
        spriteCounter++;
        if (this.spriteList.size() > 7) {
            if (spriteCounter > 5) loopThroughSprites();
        } else {
            if (spriteCounter > 9) loopThroughSprites();
        }
    }

    public void loopThroughSprites() {
        spriteNum = (spriteNum < spriteList.size()) ? spriteNum + 1 : 1;
        spriteCounter = 0;
    }

    // Draw latest object sprite
    public void draw(Graphics2D g2, int width, int height) {
        BufferedImage image = spriteList.get(spriteNum - 1);
        g2.drawImage(image, worldX, worldY, width, height, null);
    }
}
