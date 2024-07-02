package main;

import entity.Entity;

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

    public static ArrayList<BufferedImage> cloneList(ArrayList<BufferedImage> spriteList) {
        ArrayList<BufferedImage> clonedList = new ArrayList<> (spriteList.size());
        clonedList.addAll(spriteList);
        return clonedList;
    }

    public static BufferedImage loadSprite (String filePath, String errorMsg) throws IOException {
        return ImageIO.read(Objects.requireNonNull(UtilityTool.class.getResourceAsStream(filePath), errorMsg));
    }

    public static void scaleObjectList (ArrayList<BufferedImage> spriteList, int width, int height) {
        ArrayList <BufferedImage> tempList = cloneList(spriteList);
        spriteList.clear();

        for (BufferedImage original : tempList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            spriteList.add(scaledSprite);
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
            entity.currentActionList = spriteList;
        } else if (entity.action.equals("idleLeft")) {
            entity.currentActionList = spriteList;
        }
    }

    public static void scaleEffectsList (ArrayList<BufferedImage> spriteList, int width, int height) {
        ArrayList <BufferedImage> tempList = cloneList(spriteList);
        spriteList.clear();
        for (BufferedImage original : tempList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            spriteList.add(scaledSprite);
        }
    }
}
