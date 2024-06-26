package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SuperObject {
    public ArrayList<BufferedImage> imageList = new ArrayList<>();
    public String name;
//    public boolean colliison = false;
    public int worldX, worldY, spriteWidth, spriteHeight,
        spriteCounter = 0,
        spriteNum = 1;

    // Change current object sprite to next
    public void update() {
        spriteCounter++;
        if (this.imageList.size() > 7) {
            if (spriteCounter > 5) {
                loopThroughSprites();
            }
        } else {
            if (spriteCounter > 9) {
                loopThroughSprites();
            }
        }
    }

    public void loopThroughSprites() {
        spriteNum = (spriteNum < imageList.size()) ? spriteNum + 1 : 1;
        spriteCounter = 0;
    }

    // Draw latest object sprite
    public void draw(Graphics2D g2, int width, int height) {
        BufferedImage image = imageList.get(spriteNum - 1);
        g2.drawImage(image, worldX, worldY, width, height, null);
    }
}
