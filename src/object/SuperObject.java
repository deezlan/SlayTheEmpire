package object;

import main.GamePanel;

import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SuperObject {

    public ArrayList<BufferedImage> imageList = new ArrayList<>();
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
        if (this.imageList.size() > 7) {
            if (spriteCounter > 5) loopThroughSprites();
        } else {
            if (spriteCounter > 9) loopThroughSprites();
        }
    }

    public void loopThroughSprites() {
        spriteNum = (spriteNum < imageList.size()) ? spriteNum + 1 : 1;
        spriteCounter = 0;
    }

    // Draw latest object sprite
    public void draw(Graphics2D g2, GamePanel gp, int i) {
        BufferedImage image = imageList.get(spriteNum - 1);
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)
        {
            g2.drawImage(image, screenX, screenY, gp.objArray[i].spriteWidth, gp.objArray[i].spriteHeight, null);
        }
    }
}
