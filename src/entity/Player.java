package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
//import java.util.ArrayList; temp

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private final Cursor cursor;
    public SwordSlash slash;
    public final int screenX;
    public final int screenY;

//    public ArrayList<Entity> inventory = new ArrayList<>(); temp commented
//    public final int inventorySize = 8; temp commented

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.SCREEN_WIDTH/2 - (gp.TILE_SIZE/2); // added screen position
        screenY = gp.SCREEN_HEIGHT/2 - (gp.TILE_SIZE/2);

        this.cursor = cursor;
        setDefaultValues();
        getPlayerSprites();
        getPlayerAttackImage();

        setItems();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 59; // position of actual collision square
        solidArea.y = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 25; // outer area of collision square
        solidArea.height = 25;

        SwordSlash slash1 = new SwordSlash(gp);
        slash = slash1;
    }

    public void setDefaultValues() {
        worldX = 350; // Player spawn location x
        worldY = 30; // player spawn location y
        speed = 3;
        action = "idleRight";
        lookingRight = true;

        //Status
        maxLife = 6;
        life = maxLife;
    }

    public void setItems(){
        //add inventory
    }

    public void update() {
        if (attacking) startAttack ();

        if ((keyH.wPressed && keyH.sPressed) || (keyH.aPressed && keyH.dPressed)) {
            action = "stuckOppositeDirection";
            currentActionList = lookingRight ? idleRightList : idleLeftList;
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

            if (keyH.enterPressed) { attacking = true; }

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

            //CHECK MOB COLLISION
            int mobIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
            interactMob(mobIndex);

            switch (action) {
                case "moveUp", "moveDown":
                    currentActionList = lookingRight ? moveRightList : moveLeftList;
                    break;
                case "moveLeft", "moveUpLeft", "moveDownLeft":
                    currentActionList = moveLeftList;
                    lookingRight = false;
                    break;
                case "moveRight", "moveUpRight", "moveDownRight":
                    currentActionList = moveRightList;
                    lookingRight = true;
                    break;
            }
        } else {
            action = lookingRight ? "idleRight" : "idleLeft";
            currentActionList = action.equals("idleRight") ? idleRightList : idleLeftList;

            if (keyH.enterPressed){
                attacking = true;
            }
        }

        if (iframe){
            iframeCounter++;
            if (iframeCounter > 60){
                iframe = false;
                iframeCounter = 0;
            }
        }

        spriteCounter++;
        if (spriteCounter > 5) {
            loopThroughSprites();
        }
        cursor.calculateAngle((int) (worldX + gp.TILE_SIZE * 1.5), worldY + gp.TILE_SIZE);
    }

    public void interactObject (int index) {
        if (index != 999) {
//            gp.objArray[index] = null;
            System.out.println(gp.objArr[index].message);
            if (!gp.objArr[index].interactList.isEmpty())
                gp.objArr[index].interacting = true;
        }
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

    public void interactMob (int index) {
        if ( index != 999) {
            if (!iframe){
                life -= 1;
                iframe = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (spriteNum > currentActionList.size() - 1)
            spriteNum = 1;
        BufferedImage image = currentActionList.get(spriteNum - 1);

        if (weaponSpriteNum > weaponList.size())
            weaponSpriteNum = 0;
        BufferedImage weaponImage = weaponList.get(weaponSpriteNum);

        if (gp.gameArea == 0) {
            g2.drawImage(image, worldX, worldY, null);

            if (attacking){
                g2.drawImage(weaponImage, worldX + 40, worldY, null);
            }
        } else if (gp.gameArea == 1){
            if(iframe){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            g2.drawImage(image, screenX, screenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            if (attacking){
                g2.drawImage(weaponImage, worldX + 40, worldY, null);
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            if (attacking){
                g2.drawImage(weaponImage, worldX + 40, worldY, null);
            }
            System.out.println("Wrong case for Player draw");
        }
        // draw arrow
        cursor.draw(g2, (int) (worldX + gp.TILE_SIZE * 1.5), worldY + gp.TILE_SIZE);
    }

    public void startAttack (){
        weaponSpriteCounter++;
        loopThroughWeaponSprites();
    }

    public void getPlayerAttackImage() {
        String dir = "/Weapon/Sword/";
        try {
            weaponList.add(0, UtilityTool.loadSprite(dir + "00.png", "Missing Attack 0"));
            weaponList.add(1, UtilityTool.loadSprite(dir + "01.png", "Missing Attack 1"));
            weaponList.add(2, UtilityTool.loadSprite(dir + "02.png", "Missing Attack 2"));
            weaponList.add(3, UtilityTool.loadSprite(dir + "03.png", "Missing Attack 3"));
            weaponList.add(4, UtilityTool.loadSprite(dir + "04.png", "Missing Attack 4"));
            weaponList.add(5, UtilityTool.loadSprite(dir + "05.png", "Missing Attack 5"));

            UtilityTool.scaleEffectsList(weaponList, 144, 96);
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }

    public void getPlayerSprites() {
        String dir = "/player/Warrior/";
        try {
            moveRightList.add(0, UtilityTool.loadSprite(dir + "run/right/00.png", "Missing Run Right 0"));
            moveRightList.add(1, UtilityTool.loadSprite(dir + "run/right/01.png", "Missing Run Right 1"));
            moveRightList.add(2, UtilityTool.loadSprite(dir + "run/right/02.png", "Missing Run Right 2"));
            moveRightList.add(3, UtilityTool.loadSprite(dir + "run/right/03.png", "Missing Run Right 3"));
            moveRightList.add(4, UtilityTool.loadSprite(dir + "run/right/04.png", "Missing Run Right 4"));
            moveRightList.add(5, UtilityTool.loadSprite(dir + "run/right/05.png", "Missing Run Right 5"));
            moveRightList.add(6, UtilityTool.loadSprite(dir + "run/right/06.png", "Missing Run Right 6"));
            moveRightList.add(7, UtilityTool.loadSprite(dir + "run/right/07.png", "Missing Run Right 7"));
            UtilityTool.scaleEntityList(this, moveRightList, 144, 96);

            moveLeftList.add(0, UtilityTool.loadSprite(dir + "run/left/00.png", "Missing Run Left 0"));
            moveLeftList.add(1, UtilityTool.loadSprite(dir + "run/left/01.png", "Missing Run Left 1"));
            moveLeftList.add(2, UtilityTool.loadSprite(dir + "run/left/02.png", "Missing Run Left 2"));
            moveLeftList.add(3, UtilityTool.loadSprite(dir + "run/left/03.png", "Missing Run Left 3"));
            moveLeftList.add(4, UtilityTool.loadSprite(dir + "run/left/04.png", "Missing Run Left 4"));
            moveLeftList.add(5, UtilityTool.loadSprite(dir + "run/left/05.png", "Missing Run Left 5"));
            moveLeftList.add(6, UtilityTool.loadSprite(dir + "run/left/06.png", "Missing Run Left 6"));
            moveLeftList.add(7, UtilityTool.loadSprite(dir + "run/left/07.png", "Missing Run Left 7"));
            UtilityTool.scaleEntityList(this, moveLeftList, 144, 96);

            idleRightList.add(0, UtilityTool.loadSprite(dir + "idle/right/00.png", "Missing Idle Right 0"));
            idleRightList.add(1, UtilityTool.loadSprite(dir + "idle/right/01.png", "Missing Idle Right 1"));
            idleRightList.add(2, UtilityTool.loadSprite(dir + "idle/right/02.png", "Missing Idle Right 2"));
            idleRightList.add(3, UtilityTool.loadSprite(dir + "idle/right/03.png", "Missing Idle Right 3"));
            idleRightList.add(4, UtilityTool.loadSprite(dir + "idle/right/04.png", "Missing Idle Right 4"));
            idleRightList.add(5, UtilityTool.loadSprite(dir + "idle/right/05.png", "Missing Idle Right 5"));
            UtilityTool.scaleEntityList(this, idleRightList, 144, 96);

            idleLeftList.add(0, UtilityTool.loadSprite(dir + "idle/left/00.png", "Missing Idle Left 0"));
            idleLeftList.add(1, UtilityTool.loadSprite(dir + "idle/left/01.png", "Missing Idle Left 1"));
            idleLeftList.add(2, UtilityTool.loadSprite(dir + "idle/left/02.png", "Missing Idle Left 2"));
            idleLeftList.add(3, UtilityTool.loadSprite(dir + "idle/left/03.png", "Missing Idle Left 3"));
            idleLeftList.add(4, UtilityTool.loadSprite(dir + "idle/left/04.png", "Missing Idle Left 4"));
            idleLeftList.add(5, UtilityTool.loadSprite(dir + "idle/left/05.png", "Missing Idle Left 5"));
            UtilityTool.scaleEntityList(this, idleLeftList, 144, 96);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }


}
