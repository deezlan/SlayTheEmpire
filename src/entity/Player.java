package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private Cursor cursor;

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor) {
        this.gp = gp;
        this.keyH = keyH;
        this.cursor = cursor;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 3;
        action = "idleRight";
        lookingRight = true;
    }

    public void getPlayerImage() {
        try {
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_4.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_5.png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_6.png"));
            right7 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_7.png"));
            right8 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/right/Warrior_Run_Right_8.png"));

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_4.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_5.png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_6.png"));
            left7 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_7.png"));
            left8 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/run/left/Warrior_Run_Left_8.png"));

            idleRight1 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/right/Warrior_Idle_1.png"));
            idleRight2 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/right/Warrior_Idle_2.png"));
            idleRight3 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/right/Warrior_Idle_3.png"));
            idleRight4 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/right/Warrior_Idle_4.png"));
            idleRight5 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/right/Warrior_Idle_5.png"));
            idleRight6 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/right/Warrior_Idle_6.png"));

            idleLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/left/Warrior_Idle_1.png"));
            idleLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/left/Warrior_Idle_2.png"));
            idleLeft3 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/left/Warrior_Idle_3.png"));
            idleLeft4 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/left/Warrior_Idle_4.png"));
            idleLeft5 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/left/Warrior_Idle_5.png"));
            idleLeft6 = ImageIO.read(getClass().getResourceAsStream("/player/Warrior/idle/left/Warrior_Idle_6.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if ((keyH.wPressed && keyH.sPressed) || (keyH.aPressed && keyH.dPressed)) { action = "stuck"; }

        if ((keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) && !action.equals("stuck")) {
            if (keyH.wPressed) {
                action = "up";
                y -= speed;
            }
            if (keyH.sPressed) {
                action = "down";
                y += speed;
            }
            if (keyH.aPressed) {
                action = "left";
                x -= speed;
            }
            if (keyH.dPressed) {
                action = "right";
                x += speed;
            }

            if (keyH.wPressed && keyH.dPressed) { action = "upRight"; }
            if (keyH.sPressed && keyH.dPressed) { action = "downRight"; }
            if (keyH.wPressed && keyH.aPressed) { action = "upLeft"; }
            if (keyH.sPressed && keyH.aPressed) { action = "downLeft"; }

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
        cursor.calculateAngle((int) (x + gp.tileSize * 1.5), y + gp.tileSize);
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

            case "up":
            case "down":
                if (lookingRight) {
                    if (spriteNum == 1) { image = right1; }
                    if (spriteNum == 2) { image = right2; }
                    if (spriteNum == 3) { image = right3; }
                    if (spriteNum == 4) { image = right4; }
                    if (spriteNum == 5) { image = right5; }
                    if (spriteNum == 6) { image = right6; }
                    if (spriteNum == 7) { image = right7; }
                    if (spriteNum == 8) { image = right8; }
                } else {
                    if (spriteNum == 1) { image = left1; }
                    if (spriteNum == 2) { image = left2; }
                    if (spriteNum == 3) { image = left3; }
                    if (spriteNum == 4) { image = left4; }
                    if (spriteNum == 5) { image = left5; }
                    if (spriteNum == 6) { image = left6; }
                    if (spriteNum == 7) { image = left7; }
                    if (spriteNum == 8) { image = left8; }
                }
                break;

            case "right":
            case "upRight":
            case "downRight":
                if (spriteNum == 1) { image = right1; }
                if (spriteNum == 2) { image = right2; }
                if (spriteNum == 3) { image = right3; }
                if (spriteNum == 4) { image = right4; }
                if (spriteNum == 5) { image = right5; }
                if (spriteNum == 6) { image = right6; }
                if (spriteNum == 7) { image = right7; }
                if (spriteNum == 8) { image = right8; }
                lookingRight = true;
                break;

            case "left":
            case "upLeft":
            case "downLeft":
                if (spriteNum == 1) { image = left1; }
                if (spriteNum == 2) { image = left2; }
                if (spriteNum == 3) { image = left3; }
                if (spriteNum == 4) { image = left4; }
                if (spriteNum == 5) { image = left5; }
                if (spriteNum == 6) { image = left6; }
                if (spriteNum == 7) { image = left7; }
                if (spriteNum == 8) { image = left8; }
                lookingRight = false;
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize*3, gp.tileSize*2, null);

        // draw arrow
        cursor.draw(g2, (int) (x + gp.tileSize * 1.5), y + gp.tileSize);
    }
}
