package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_SnowballCannon;

import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
//import java.util.ArrayList; temp

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private final Cursor cursor;
    public final int screenX;
    public final int screenY;
    public int totalCoins;
    public int playerClass,
            warrior = 0,
            assassin = 1,
            knight = 2;

//    public ArrayList<Entity> inventory = new ArrayList<>(); temp commented
//    public final int inventorySize = 8; temp commented

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor, int playerClass) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2); // added screen position
        screenY = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);

        this.cursor = cursor;
        setDefaultValues();
        getPlayerSprites();
        getPlayerAttackAnimation();

        setItems();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 59; // position of actual collision square
        solidArea.y = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 25; // outer area of collision square
        solidArea.height = 25;

//        SwordSlash slash1 = new SwordSlash(gp);
//        slash = slash1;
    }


    public void setDefaultValues() {
        worldX = 350; // Player spawn location x
        worldY = 30; // player spawn location y
//        worldX = 200; // second area spawn
//        worldY = 200;
        speed = 3;
        action = "idleRight";
        lookingRight = true;

        //Status
        maxLife = 6;
        life = maxLife;
        totalCoins = 0;

        //Start
        try {
            currentWeapon = new OBJ_SnowballCannon(gp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        damage = currentWeapon.damage;
    }

    public void setItems() {
        //add inventory
    }

    public void update() {

        if (life == 0) {
            gp.gameState = gp.deathState;
        }
        if (attacking) {
            attackAnimation();
        } else {

            if ((keyH.wPressed && keyH.sPressed) || (keyH.aPressed && keyH.dPressed)) {
                action = "stuckOppositeDirection";
                currentActionList = lookingRight ? idleRightList : idleLeftList;

            }

            if (keyH.wPressed && keyH.sPressed && keyH.aPressed) {
                action = "moveLeft";
            }
            if (keyH.wPressed && keyH.sPressed && keyH.dPressed) {
                action = "moveRight";
            }
            if (keyH.aPressed && keyH.dPressed && keyH.wPressed) {
                action = "moveUp";
            }
            if (keyH.aPressed && keyH.dPressed && keyH.sPressed) {
                action = "moveDown";
            }



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
                if (keyH.wPressed && keyH.dPressed) {
                    action = "moveUpRight";
                }
                if (keyH.sPressed && keyH.dPressed) {
                    action = "moveDownRight";
                }
                if (keyH.wPressed && keyH.aPressed) {
                    action = "moveUpLeft";
                }
                if (keyH.sPressed && keyH.aPressed) {
                    action = "moveDownLeft";
                }

                if (keyH.enterPressed) {
                    attacking = true;
                }

                if (!upCollisionOn)
                    if (keyH.wPressed) {
                        worldY -= speed;
                    }
                if (!downCollisionOn)
                    if (keyH.sPressed) {
                        worldY += speed;
                    }
                if (!leftCollisionOn)
                    if (keyH.aPressed) {
                        worldX -= speed;
                    }
                if (!rightCollisionOn)
                    if (keyH.dPressed) {
                        worldX += speed;
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

                //CHECK EVENT
                gp.eHandler.checkEvent();
                gp.keyH.ePressed = false; // after player interact with tile;

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

                if (keyH.enterPressed) {
                    attacking = true;
                }
            }

            if (iframe) {
                iframeCounter++;
                if (iframeCounter > 60) {
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
    }

    public void attackAnimation(){ // animation attack
        animationCounter++;
        if (animationCounter <= 5){
            animationSpriteNum = 0;
        } else if (animationCounter <= 10) {
            animationSpriteNum = 1;
        } else if (animationCounter <= 15) {
            animationSpriteNum = 2;
        } else if (animationCounter <= 20) {
            animationSpriteNum = 3;
        } else if (animationCounter <= 25) {
            animationSpriteNum = 4;
        } else if (animationCounter<= 30) {
            animationSpriteNum = 5;
        } else if (animationCounter <= 35) {
            animationSpriteNum = 6;
        } else if (animationCounter <= 40) {
            animationSpriteNum = 7;
        } else if (animationCounter <= 45) {
            animationSpriteNum = 8;
        }else if (animationCounter <= 50) {
            animationSpriteNum = 1;
            animationCounter = 0;
            attacking = false;
        }
    }

    public void interactObject (int index) {
        if (index == 0){
            gp.gameState = gp.shopState;
        } else if (index != 999) {
//            gp.objArray[index] = null;
            System.out.println(gp.objArr[index].message);
            if (!gp.objArr[index].interactList.isEmpty())
                gp.objArr[index].interacting = true;
        }
    }

    public void interactNPC (int index) {
            if (index != 999) {
                gp.gameState = gp.dialogueState;
                gp.npcArr[index].speak();
            }
            if (index == 1) {
                gp.gameState = gp.shopState;
        }
    }


//    public void interactMerchant(int index){
//        if (keyH.ePressed){
//            switch (index) {
//                case 999:
//                    break;
//                case 1:
//                    gp.gameState = gp.shopState;
//                    break;
//                default:
//                    gp.gameState = gp.dialogueState; gp.npcArr[index].speak();
//                    break;
//        }
//    }
//}


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

        if (animationSpriteNum > playerRightAttackList.size())
            animationSpriteNum = 0;
        BufferedImage animationImage = lookingRight? playerRightAttackList.get(animationSpriteNum) : playerLeftAttackList.get(animationSpriteNum);

        if (gp.gameArea == 0) { // lobby game area
            if (!attacking){
                if(iframe){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                }
                g2.drawImage(image, worldX, worldY, null);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } if (attacking){
                g2.drawImage(animationImage, worldX, worldY,null);
            }
        } else if (gp.gameArea == 1){
            if (!attacking){
                if(iframe){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                }
                g2.drawImage(image, screenX, screenY, null);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } if (attacking){
                g2.drawImage(animationImage, screenX, screenY,null); // draw attack animation
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
        }
        // draw arrow
        cursor.draw(g2, (int) (worldX + gp.TILE_SIZE * 1.5), worldY + gp.TILE_SIZE);
    }

    // Ananda's old slash code
//    public void getPlayerAttackImage() {
//        String dir = "/Weapon/Sword/";
//        try {
//            weaponList.add(0, UtilityTool.loadSprite(dir + "00.png", "Missing Attack 0"));
//            weaponList.add(1, UtilityTool.loadSprite(dir + "01.png", "Missing Attack 1"));
//            weaponList.add(2, UtilityTool.loadSprite(dir + "02.png", "Missing Attack 2"));
//            weaponList.add(3, UtilityTool.loadSprite(dir + "03.png", "Missing Attack 3"));
//            weaponList.add(4, UtilityTool.loadSprite(dir + "04.png", "Missing Attack 4"));
//            weaponList.add(5, UtilityTool.loadSprite(dir + "05.png", "Missing Attack 5"));
//
//            UtilityTool.scaleEffectsList(weaponList, 144, 96);
//        } catch (IOException e){
//            e.printStackTrace(System.out);
//        }
//    }

    public void getPlayerAttackAnimation() {
        try {
            switch (playerClass) {
                case 0:
                    String dir = "/player/Warrior/";
                    // Load sprites for attacking
                    for (int i = 0; i <= 8; i++) {
                        playerRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing attackLeft " + i));
                        playerLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing attackLeft " + i));
                    }

                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, playerRightAttackList, 144, 96);
                    UtilityTool.scaleEntityList(this, playerLeftAttackList, 144, 96);
                    break;
                case 1:
                    break;
                case 2:
            }
        } catch (IOException e){
            e.printStackTrace(System.out);
        }


    }

    public void getPlayerSprites() {
        try {
            switch (playerClass) {
                case 0:
                    String dir = "/player/Warrior/";
                    // Load sprites for movement
                    for (int i = 0; i <= 7; i++) {
                        moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                        moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                    }
                    // Load sprites for idle
                    for (int i = 0; i <= 5; i++) {
                        idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                        idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                    }

                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, moveRightList, 144, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 144, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 144, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 144, 96);
                    break;
                case 1:
                    break;
                case 2:
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
