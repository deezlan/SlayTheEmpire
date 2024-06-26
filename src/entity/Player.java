package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public Player (GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImages();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
    }

    public void setDefaultValues() {
        worldX = 100; // changed it for locating the player in the world map
        worldY = 100; // same thing
        speed = 3;
        action = "idleRight";
        lookingRight = true;
    }

    public void getPlayerImages() {
        try {
            moveRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_1.png"), "Missing right sprite 1"));
            moveRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_2.png"), "Missing right sprite 2"));
            moveRight3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_3.png"), "Missing right sprite 3"));
            moveRight4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_4.png"), "Missing right sprite 4"));
            moveRight5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_5.png"), "Missing right sprite 5"));
            moveRight6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_6.png"), "Missing right sprite 6"));
            moveRight7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_7.png"), "Missing right sprite 7"));
            moveRight8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_8.png"), "Missing right sprite 8"));

            moveLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_1.png"), "Missing left sprite 1"));
            moveLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_2.png"), "Missing left sprite 2"));
            moveLeft3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_3.png"), "Missing left sprite 3"));
            moveLeft4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_4.png"), "Missing left sprite 4"));
            moveLeft5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_5.png"), "Missing left sprite 5"));
            moveLeft6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_6.png"), "Missing left sprite 6"));
            moveLeft7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_7.png"), "Missing left sprite 7"));
            moveLeft8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_8.png"), "Missing left sprite 8"));

            idleRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_1.png"), "Missing idle right sprite 1"));
            idleRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_2.png"), "Missing idle right sprite 2"));
            idleRight3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_3.png"), "Missing idle right sprite 3"));
            idleRight4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_4.png"), "Missing idle right sprite 4"));
            idleRight5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_5.png"), "Missing idle right sprite 5"));
            idleRight6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_6.png"), "Missing idle right sprite 6"));

            idleLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_1.png"), "Missing idle left sprite 1"));
            idleLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_2.png"), "Missing idle left sprite 2"));
            idleLeft3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_3.png"), "Missing idle left sprite 3"));
            idleLeft4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_4.png"), "Missing idle left sprite 4"));
            idleLeft5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_5.png"), "Missing idle left sprite 5"));
            idleLeft6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_6.png"), "Missing idle left sprite 6"));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void update() {
        if ((keyH.wPressed && keyH.sPressed) || (keyH.aPressed && keyH.dPressed)) { action = "stuckOppositeDirection"; }

        if ((keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) && !action.equals("stuckOppositeDirection")) {
            if (keyH.wPressed) {
                action = "moveUp";
            }
            if (keyH.sPressed) {
                action = "moveDown";
            }
            if (keyH.aPressed) {
                action = "moveLeft";
            }
            if (keyH.dPressed) {
                action = "moveRight";
            }

            // CHECK TILE COLLISION
            collisionOn = true; // turns on collision
            gp.cChecker.checkTile(this); // pass the current tile into the checker to check if it has collision

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false){
                switch(action){
                    case "moveUp":
                        worldY -= speed; // move player movement to here
                        break;
                    case "moveDown":
                        worldY += speed;
                        break;
                    case "moveLeft":
                        worldX -= speed;
                        break;
                    case "moveRight":
                        worldX += speed;
                        break;

                }
            }

            if (keyH.wPressed && keyH.dPressed) { action = "moveUpRight"; }
            if (keyH.sPressed && keyH.dPressed) { action = "moveDownRight"; }
            if (keyH.wPressed && keyH.aPressed) { action = "moveUpLeft"; }
            if (keyH.sPressed && keyH.aPressed) { action = "moveDownLeft"; }

            spriteCounter++;
            if (spriteCounter > 5) {
                switch (spriteNum) {
                    case 1: spriteNum = 2; break;
                    case 2: spriteNum = 3; break;
                    case 3: spriteNum = 4; break;
                    case 4: spriteNum = 5; break;
                    case 5: spriteNum = 6; break;
                    case 6: spriteNum = 7; break;
                    case 7: spriteNum = 8; break;
                    case 8: spriteNum = 1; break;
                }
                spriteCounter = 0;
            }
        } else {
            action = lookingRight ? "idleRight" : "idleLeft";
            if (spriteNum > 6) { spriteNum = 1; }
            spriteCounter++;

            if (spriteCounter > 5) {
                switch (spriteNum) {
                    case 1: spriteNum = 2; break;
                    case 2: spriteNum = 3; break;
                    case 3: spriteNum = 4; break;
                    case 4: spriteNum = 5; break;
                    case 5: spriteNum = 6; break;
                    case 6: spriteNum = 1; break;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (action) {
//            case "stuck":
            case "idleRight":
                if (spriteNum == 1) { image = idleRight1; }
                if (spriteNum == 2) { image = idleRight2; }
                if (spriteNum == 3) { image = idleRight3; }
                if (spriteNum == 4) { image = idleRight4; }
                if (spriteNum == 5) { image = idleRight5; }
                if (spriteNum == 6) { image = idleRight6; }
                break;

            case "idleLeft":
                if (spriteNum == 1) { image = idleLeft1; }
                if (spriteNum == 2) { image = idleLeft2; }
                if (spriteNum == 3) { image = idleLeft3; }
                if (spriteNum == 4) { image = idleLeft4; }
                if (spriteNum == 5) { image = idleLeft5; }
                if (spriteNum == 6) { image = idleLeft6; }
                break;

            case "moveUp":
            case "moveDown":
                if (lookingRight) {
                    if (spriteNum == 1) { image = moveRight1; }
                    if (spriteNum == 2) { image = moveRight2; }
                    if (spriteNum == 3) { image = moveRight3; }
                    if (spriteNum == 4) { image = moveRight4; }
                    if (spriteNum == 5) { image = moveRight5; }
                    if (spriteNum == 6) { image = moveRight6; }
                    if (spriteNum == 7) { image = moveRight7; }
                    if (spriteNum == 8) { image = moveRight8; }
                } else {
                    if (spriteNum == 1) { image = moveLeft1; }
                    if (spriteNum == 2) { image = moveLeft2; }
                    if (spriteNum == 3) { image = moveLeft3; }
                    if (spriteNum == 4) { image = moveLeft4; }
                    if (spriteNum == 5) { image = moveLeft5; }
                    if (spriteNum == 6) { image = moveLeft6; }
                    if (spriteNum == 7) { image = moveLeft7; }
                    if (spriteNum == 8) { image = moveLeft8; }
                }
                break;

            case "moveRight":
            case "moveUpRight":
            case "moveDownRight":
                if (spriteNum == 1) { image = moveRight1; }
                if (spriteNum == 2) { image = moveRight2; }
                if (spriteNum == 3) { image = moveRight3; }
                if (spriteNum == 4) { image = moveRight4; }
                if (spriteNum == 5) { image = moveRight5; }
                if (spriteNum == 6) { image = moveRight6; }
                if (spriteNum == 7) { image = moveRight7; }
                if (spriteNum == 8) { image = moveRight8; }
                lookingRight = true;
                break;

            case "moveLeft":
            case "moveUpLeft":
            case "moveDownLeft":
                if (spriteNum == 1) { image = moveLeft1; }
                if (spriteNum == 2) { image = moveLeft2; }
                if (spriteNum == 3) { image = moveLeft3; }
                if (spriteNum == 4) { image = moveLeft4; }
                if (spriteNum == 5) { image = moveLeft5; }
                if (spriteNum == 6) { image = moveLeft6; }
                if (spriteNum == 7) { image = moveLeft7; }
                if (spriteNum == 8) { image = moveLeft8; }
                lookingRight = false;
                break;
        }

        g2.drawImage(image, worldX, worldY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
    }
}
