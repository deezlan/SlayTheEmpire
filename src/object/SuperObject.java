package object;

import main.GamePanel;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SuperObject {
    public ArrayList<BufferedImage> spriteList = new ArrayList<>();
    public ArrayList<BufferedImage> scaledList = new ArrayList<>();
    public String name, message;
    public boolean collision = false;

    public int worldX, worldY,
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
    public void draw(Graphics2D g2, GamePanel gp) {
        BufferedImage image = scaledList.get(spriteNum - 1);

        switch (gp.gameArea) {
            case 0:
                g2.drawImage(image, worldX, worldY, null);
                break;
            case 1:
            default:
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)
                {
                    g2.drawImage(image, screenX, screenY, null);
                }
        }
    }
}
