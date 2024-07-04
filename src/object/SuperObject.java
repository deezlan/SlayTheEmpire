//package object;
//
//import main.GamePanel;
//import java.awt.Rectangle;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//
//public class SuperObject {
//    public ArrayList<BufferedImage> defaultList = new ArrayList<>();
//    public ArrayList<BufferedImage> interactList = new ArrayList<>();
//    public String name, message;
//    public boolean
//            interacting = false,
//            collision = false;
//
//    public int worldX, worldY,
//        spriteCounter = 0,
//        spriteNum = 0,
//        interactSpriteCounter = 0,
//        interactSpriteNum = 0;
//    public Rectangle solidArea = new Rectangle();
//    public int solidAreaDefaultX;
//    public int solidAreaDefaultY;
//
//    // Change current object sprite to next
//    public void update() {
//        if (interacting) startInteract();
//
//        spriteCounter++;
//        if (this.defaultList.size() == 14) {
//            if (spriteCounter > 12) loopThroughSprites();
//        } else if (this.defaultList.size() > 7) {
//            if (spriteCounter > 5) loopThroughSprites();
//        } else {
//            if (spriteCounter > 9) loopThroughSprites();
//        }
//    }
//
//    public void loopThroughSprites() {
//        spriteNum = (spriteNum < defaultList.size() - 1) ? spriteNum + 1 : 1;
//        spriteCounter = 0;
//    }
//
//    public void loopThroughInteractSprites() {
//        if (interactSpriteCounter < 5) {
//            interactSpriteNum = 0;
//        } else if (interactSpriteCounter < 10) {
//            interactSpriteNum = 1;
//        } else if (interactSpriteCounter < 15) {
//            interactSpriteNum = 2;
//        } else if (interactSpriteCounter < 20) {
//            interactSpriteNum = 3;
//        } else if (interactSpriteCounter < 25) {
//            interactSpriteNum = 4;
//        } else if (interactSpriteCounter < 30) {
//            interactSpriteNum = 5;
//        } else if (interactSpriteCounter < 35) {
//            interactSpriteNum = 6;
//        } else if (interactSpriteCounter < 40) {
//            interactSpriteNum = 7;
//        } else if (interactSpriteCounter < 45) {
//            interactSpriteNum = 8;
//        } else if (interactSpriteCounter < 50) {
//            interactSpriteNum = 9;
//        } else if (interactSpriteCounter < 55) {
//            interactSpriteNum = 10;
//        } else if (interactSpriteCounter < 60) {
//            interactSpriteNum = 11;
//        } else if (interactSpriteCounter < 65) {
//            interactSpriteNum = 12;
//        } else if (interactSpriteCounter < 70) {
//            interactSpriteNum = 13;
//        } else if (interactSpriteCounter <= 75){
//            interactSpriteNum = 0;
//            interactSpriteCounter = 0;
//            interacting = false;
//        }
//    }
//
//    public void startInteract(){
//        interactSpriteCounter++;
//        loopThroughInteractSprites();
//    }
//
//    // Draw latest object sprite
//    public void draw(Graphics2D g2, GamePanel gp) {
//        BufferedImage image;
//        if (interacting) {
//            image = interactList.get(interactSpriteNum);
//        } else {
//            image = defaultList.get(spriteNum);
//        }
//
//        switch (gp.gameArea) {
//            case 0:
//                g2.drawImage(image, worldX, worldY, null);
//                break;
//            case 1, 2:
//                int screenX = worldX - gp.player.worldX + gp.player.screenX;
//                int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//                if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
//                        worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
//                        worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
//                        worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)
//                {
//                    g2.drawImage(image, screenX, screenY, null);
//                }
//        }
//    }
//}
