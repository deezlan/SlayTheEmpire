package main;

import entity.Entity;
import object.SuperObject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

public class UtilityTool {
    public static BufferedImage scaleImage (BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

    public static void scaleObjectList (SuperObject obj, ArrayList<BufferedImage> spriteList, int width, int height) {
        for (BufferedImage original : spriteList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            obj.scaledList.add(scaledSprite);
        }
    }

    public static void scaleEntityList (Entity entity, ArrayList<BufferedImage> spriteList, int width, int height) {
        for (BufferedImage original : spriteList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            entity.currentSpriteList.add(scaledSprite);
        }
    }
}
