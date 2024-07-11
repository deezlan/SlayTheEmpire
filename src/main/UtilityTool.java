package main;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class UtilityTool {
    public static void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    // SCALE & RETURN IMAGE
    public static BufferedImage scaleImage (BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
    // CLONE & RETURN CLONED ARRAYLIST
    public static ArrayList<BufferedImage> cloneList(ArrayList<BufferedImage> spriteList) {
        ArrayList<BufferedImage> clonedList = new ArrayList<> (spriteList.size());
        clonedList.addAll(spriteList);
        return clonedList;
    }
    // LOAD SPRITE
    public static BufferedImage loadSprite (String filePath, String errorMsg) throws IOException {
        try {
            return ImageIO.read(Objects.requireNonNull(UtilityTool.class.getResourceAsStream(filePath), errorMsg));
        } catch (IOException e) {
            throw new IOException(errorMsg, e);
        }

    }
    // SCALE & UPDATE SPECIFIED SPRITES ARRAYLIST
    public static void scaleObjectList (ArrayList<BufferedImage> spriteList, int width, int height) {
        ArrayList <BufferedImage> tempList = cloneList(spriteList);
        spriteList.clear();

        for (BufferedImage original : tempList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            spriteList.add(scaledSprite);
        }
    }
    // SCALE SPECIFIED SPRITES ARRAYLIST & PLACE INTO currentList
    public static void scaleEntityList (Entity entity, ArrayList<BufferedImage> spriteList, int width, int height) {
        ArrayList <BufferedImage> tempList = cloneList(spriteList);
        spriteList.clear();

        for (BufferedImage original : tempList) {
            BufferedImage scaledSprite = scaleImage(original, width, height);
            spriteList.add(scaledSprite);
        }
        if (entity.action.equals("idleRight")) {
            entity.currentList = spriteList;
        } else if (entity.action.equals("idleLeft")) {
            entity.currentList = spriteList;
        }
    }

//    public static void scaleEffectsList (ArrayList<BufferedImage> spriteList, int width, int height) {
//        ArrayList <BufferedImage> tempList = cloneList(spriteList);
//        spriteList.clear();
//        for (BufferedImage original : tempList) {
//            BufferedImage scaledSprite = scaleImage(original, width, height);
//            spriteList.add(scaledSprite);
//        }
//    }
}
