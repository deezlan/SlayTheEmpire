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
    public final int screenX;
    public final int screenY;

    public Cursor cursor;

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.cursor = cursor;
        screenX = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE/2);
        screenY = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE/2);

        setDefaultValues();
        getPlayerImage();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 56; // actual collision square
        solidArea.y = 72;
        solidArea.width = 30; // outer area of collision square
        solidArea.height = 20;
    }

    public void setDefaultValues() {
        worldX = 100; // changed it for locating the player in the world map
        worldY = 100; // same thing
        speed = 3;
        action = "idleRight";
        lookingRight = true;
    }

    public void getPlayerImage() {
        try {
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_1.png"), "Missing right sprite 1")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_2.png"), "Missing right sprite 2")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_3.png"), "Missing right sprite 3")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_4.png"), "Missing right sprite 4")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_5.png"), "Missing right sprite 5")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_6.png"), "Missing right sprite 6")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_7.png"), "Missing right sprite 7")));
            moveRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_8.png"), "Missing right sprite 8")));

            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_1.png"), "Missing left sprite 1")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_2.png"), "Missing left sprite 2")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_3.png"), "Missing left sprite 3")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_4.png"), "Missing left sprite 4")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_5.png"), "Missing left sprite 5")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_6.png"), "Missing left sprite 6")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_7.png"), "Missing left sprite 7")));
            moveLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_8.png"), "Missing left sprite 8")));

            idleRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_1.png"), "Missing idle right sprite 1")));
            idleRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_2.png"), "Missing idle right sprite 2")));
            idleRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_3.png"), "Missing idle right sprite 3")));
            idleRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_4.png"), "Missing idle right sprite 4")));
            idleRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_5.png"), "Missing idle right sprite 5")));
            idleRightSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_6.png"), "Missing idle right sprite 6")));

            idleLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_1.png"), "Missing idle left sprite 1")));
            idleLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_2.png"), "Missing idle left sprite 2")));
            idleLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_3.png"), "Missing idle left sprite 3")));
            idleLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_4.png"), "Missing idle left sprite 4")));
            idleLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_5.png"), "Missing idle left sprite 5")));
            idleLeftSpriteList.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_6.png"), "Missing idle left sprite 6")));
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
            collisionOn = false; // turns on collision
            gp.cChecker.checkTile(this); // pass the current tile into the checker to check if it has collision

            //IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(!collisionOn){
                if (keyH.wPressed) {
                    worldY -= speed;
                }
                if (keyH.sPressed) {
                    worldY += speed;
                }
                if (keyH.aPressed) {
                    worldX -= speed;
                }
                if (keyH.dPressed) {
                    worldX += speed;
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
            }}

        // Update angle based on mouse position
        cursor.calculateAngle((int) (worldX + gp.TILE_SIZE * 1.5), worldY + gp.TILE_SIZE);
    }

    public void draw(Graphics2D g2) {
        if (spriteNum > currentSpriteList.size() - 1) spriteNum = 1;
        BufferedImage image = currentSpriteList.get(spriteNum - 1);

        if (gp.gameArea == 0) {
            g2.drawImage(image, worldX, worldY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
        } else {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
        }

        // draw arrow
        cursor.draw(g2, (int) (worldX + gp.TILE_SIZE * 1.5), worldY + gp.TILE_SIZE);
    }
}
