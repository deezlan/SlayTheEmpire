package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
//import java.util.ArrayList; temp
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private Cursor cursor;

    public final int screenX;
    public final int screenY;
//    public ArrayList<Entity> inventory = new ArrayList<>(); temp commented
//    public final int inventorySize = 8; temp commented

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.cursor = cursor;
        screenX = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE/2); // added screen position
        screenY = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE/2);

        this.cursor = cursor;
        setDefaultValues();
        getPlayerSprites();
        getPlayerAttackImage();
        setItems();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 56; // position of actual collision square
        solidArea.y = 72;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30; // outer area of collision square
        solidArea.height = 20;
    }

    public void setDefaultValues() {
        worldX = 350; // Player spawn location x
        worldY = 30; // player spawn location y
        speed = 3;
        action = "idleRight";
        currentSpriteList = idleRightSpriteList;
        lookingRight = true;

        //Status
        maxLife = 6;
        life = maxLife;
    }

    public void setItems(){
        //add inventory
    }

    public void update() {
        if (attacking) attacking();

        if ((keyH.wPressed && keyH.sPressed) || (keyH.aPressed && keyH.dPressed)) {
            action = "stuckOppositeDirection";
            currentSpriteList = lookingRight ? idleRightSpriteList : idleLeftSpriteList;
        }
        if (keyH.wPressed && keyH.sPressed && keyH.aPressed) { action = "moveLeft"; }
        if (keyH.wPressed && keyH.sPressed && keyH.dPressed) { action = "moveRight"; }
        if (keyH.aPressed && keyH.dPressed && keyH.wPressed) { action = "moveUp"; }
        if (keyH.aPressed && keyH.dPressed && keyH.sPressed) { action = "moveDown"; }

        if ((keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) && !action.equals("stuckOppositeDirection")) {

            if (keyH.wPressed) { action = "moveUp"; }
            if (keyH.sPressed) { action = "moveDown"; }
            if (keyH.aPressed) { action = "moveLeft";}
            if (keyH.dPressed) { action = "moveRight"; }

            if (keyH.wPressed && keyH.dPressed) { action = "moveUpRight"; }
            if (keyH.sPressed && keyH.dPressed) { action = "moveDownRight"; }
            if (keyH.wPressed && keyH.aPressed) { action = "moveUpLeft"; }
            if (keyH.sPressed && keyH.aPressed) { action = "moveDownLeft"; }

            if (!upCollisionOn)
                if (keyH.wPressed) { worldY -= speed; }
            if (!downCollisionOn)
                if (keyH.sPressed) { worldY += speed; }
            if (!leftCollisionOn)
                if (keyH.aPressed) { worldX -= speed; }
            if (!rightCollisionOn)
                if (keyH.dPressed) { worldX += speed; }
            if (keyH.enterPressed){
                attacking = true;
            }

            // CHECK TILE COLLISION
            upCollisionOn = false; // resets collisions off
            downCollisionOn = false;
            leftCollisionOn = false;
            rightCollisionOn = false;
            gp.cChecker.checkTile(this); // pass the current tile into the checker to check if it has collision

            // CHECK OBJECT COLLISION BEFORE INTERACTING
            gp.cChecker.checkObject(this, true);
            int objIndex = gp.cChecker.checkObject(this, true);
            interactObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntityCollision(this, gp.npcArr);
            interactNPC(npcIndex);

            switch (action) {
                case "moveUp":
                case "moveDown":
                    currentSpriteList = lookingRight ? moveRightSpriteList : moveLeftSpriteList;
                    break;
                case "moveLeft":
                case "moveUpLeft":
                case "moveDownLeft":
                    currentSpriteList = moveLeftSpriteList;
                    lookingRight = false;
                    break;
                case "moveRight":
                case "moveUpRight":
                case "moveDownRight":
                    currentSpriteList = moveRightSpriteList;
                    lookingRight = true;
                    break;
            }
        } else {
            action = lookingRight ? "idleRight" : "idleLeft";
            currentSpriteList = action.equals("idleRight") ? idleRightSpriteList : idleLeftSpriteList;

            if (keyH.enterPressed){
                attacking = true;
            }
        }

        spriteCounter++;
        if (spriteCounter > 5) {
            loopThroughSprites();
        }
        cursor.calculateAngle((int) (x + gp.tileSize * 1.5), y + gp.tileSize);
    }

    public void interactObject (int index) {
        if (index != 999) {
//            gp.objArray[index] = null;
            System.out.println(gp.objArr[index].message);
        }
        // Update angle based on mouse position

    }

    public void interactNPC (int index) {
        switch (index) {
            case 999:
                break;
            case 2:
                gp.gameState = gp.shopState;
                break;
            default:
                gp.gameState = gp.dialogueState;
                gp.npcArr[index].speak();
                break;
        }
    }

    public void draw(Graphics2D g2) {
        if (spriteNum > currentSpriteList.size() - 1) spriteNum = 1;
        BufferedImage image = currentSpriteList.get(spriteNum - 1);
        if (weaponSpriteNum > weaponSpriteList.size()) weaponSpriteNum =1;
        BufferedImage weaponImage = weaponSpriteList.get(weaponSpriteNum);
        if (gp.gameArea == 1) {
            g2.drawImage(image, worldX, worldY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            if (attacking){
                g2.drawImage(weaponImage, worldX + 40, worldY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            }
        } else if (gp.gameState == 1){
            g2.drawImage(image, worldX, worldY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            if (attacking){
                g2.drawImage(weaponImage, worldX + 40, worldY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            if (attacking){
                g2.drawImage(weaponImage, screenX + 40, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            }
        }
        g2.drawImage(image, x, y, gp.tileSize*3, gp.tileSize*2, null);

        // draw arrow
        cursor.draw(g2, (int) (x + gp.tileSize * 1.5), y + gp.tileSize);
    }

    public void getPlayerSprites() {
        try {
            moveRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_1.png"), "Missing right sprite 0")));
            moveRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_2.png"), "Missing right sprite 1")));
            moveRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_3.png"), "Missing right sprite 2")));
            moveRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_4.png"), "Missing right sprite 3")));
            moveRightSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_5.png"), "Missing right sprite 4")));
            moveRightSpriteList.add(5, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_6.png"), "Missing right sprite 5")));
            moveRightSpriteList.add(6, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_7.png"), "Missing right sprite 6")));
            moveRightSpriteList.add(7,ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/right/Warrior_Run_Right_8.png"), "Missing right sprite 7")));

            moveLeftSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_1.png"), "Missing left sprite 0")));
            moveLeftSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_2.png"), "Missing left sprite 1")));
            moveLeftSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_3.png"), "Missing left sprite 2")));
            moveLeftSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_4.png"), "Missing left sprite 3")));
            moveLeftSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_5.png"), "Missing left sprite 4")));
            moveLeftSpriteList.add(5, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_6.png"), "Missing left sprite 5")));
            moveLeftSpriteList.add(6, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_7.png"), "Missing left sprite 6")));
            moveLeftSpriteList.add(7, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/run/left/Warrior_Run_Left_8.png"), "Missing left sprite 7")));

            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_1.png"), "Missing idle right sprite 0")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_2.png"), "Missing idle right sprite 1")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_3.png"), "Missing idle right sprite 2")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_4.png"), "Missing idle right sprite 3")));
            idleRightSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_5.png"), "Missing idle right sprite 4")));
            idleRightSpriteList.add(5, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/right/Warrior_Idle_6.png"), "Missing idle right sprite 5")));

            idleLeftSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_1.png"), "Missing idle left sprite 0")));
            idleLeftSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_2.png"), "Missing idle left sprite 1")));
            idleLeftSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_3.png"), "Missing idle left sprite 2")));
            idleLeftSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_4.png"), "Missing idle left sprite 3")));
            idleLeftSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_5.png"), "Missing idle left sprite 4")));
            idleLeftSpriteList.add(5, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/player/Warrior/idle/left/Warrior_Idle_6.png"), "Missing idle left sprite 5")));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
    public void getPlayerAttackImage() {
        try {
            weaponSpriteList.add(0, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_1.png"));
            weaponSpriteList.add(1, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_2.png"));
            weaponSpriteList.add(2, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_3.png"));
            weaponSpriteList.add(3, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_4.png"));
            weaponSpriteList.add(4, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_5.png"));
            weaponSpriteList.add(5, UtilityTool.weaponSetup("/Weapon/Sword/sword_slash_6.png"));
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }

    public void attacking(){
        weaponSpriteCounter++;
        loopThroughWeaponSprites();

    }
}
