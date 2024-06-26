package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SuperObject {
    public ArrayList<BufferedImage> imageList = new ArrayList<>();
    public String name;
//    public boolean colliison = false;
    public int worldX, worldY, spriteCounter = 0, spriteNum = 1;

    public void update() {
        spriteCounter++;
        if (spriteCounter > 9) {
            spriteNum = (spriteNum < imageList.size()) ? spriteNum + 1 : 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = imageList.get(spriteNum - 1);
        g2.drawImage(image, worldX, worldY, 192, 128, null);
    }
}
