package main;

import entity.Entity;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
        ArrayList <BufferedImage> tempList = cloneList(spriteList);
        spriteList.clear();
        for (BufferedImage original : tempList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            spriteList.add(scaledSprite);
        }

        if (entity.action.equals("idleRight")) {
            entity.currentSpriteList = spriteList;
        }
    }

    public static void scaleMobList (Entity entity, ArrayList<BufferedImage> spriteList, int width, int height) {
        ArrayList <BufferedImage> tempList = cloneList(spriteList);
        spriteList.clear();
        for (BufferedImage original : tempList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            spriteList.add(scaledSprite);
        }
        if (entity.action.equals("idleRight")) {
            entity.currentSpriteList = spriteList;
        }
    }

    public static ArrayList<BufferedImage> cloneList(ArrayList<BufferedImage> spriteList) {
        ArrayList<BufferedImage> clonedList = new ArrayList<> (spriteList.size());
        clonedList.addAll(spriteList);
        return clonedList;
    }

    public static BufferedImage weaponSetup (String path) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(UtilityTool.class.getResourceAsStream(path), "aa"));
        image = scaleImage(image, 48, 48);
        return image;
    }
}
