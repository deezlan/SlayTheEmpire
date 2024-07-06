package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.Rectangle;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.ArrayList; temp

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private final Cursor cursor;
    public final int screenX;
    public final int screenY;
    public String dir;
    public int totalCoins;
    public ArrayList<Entity> hotbarList = new ArrayList<>();
    public ArrayList<Integer> ownedWeapon = new ArrayList<>();
    public Entity currentWeapon = null;
    public int playerClass;
//    public int playerClass;

//    public ArrayList<Entity> inventory = new ArrayList<>(); temp commented
//    public final int inventorySize = 8; temp commented

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor, int playerClass) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.playerClass = playerClass;
        if (playerClass == 0) {
            screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE/2) - 90; // added screen position
            screenY = (gp.SCREEN_HEIGHT/2) - 72;
        } else if (playerClass == 1) {
            screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE/2) - 72; // added screen position
            screenY = (gp.SCREEN_HEIGHT/2) - 72;
        } else {
            screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE/2) - 48; // added screen position
            screenY = (gp.SCREEN_HEIGHT/2) - 72;
        }

        this.cursor = cursor;
        setDefaultValues();
        setCollisionValues();
        getPlayerSprites();
        getPlayerAttackAnimation();
        setItems();
    }

    public void setDefaultValues() {
        worldX = 588; // Player spawn location x
        worldY = 147; // player spawn location y
        defaultSpeed = 4;
        speed = defaultSpeed;
        action = "idleRight";
        lookingRight = true;

        //Status
        maxLife = 6;
        life = maxLife;
        totalCoins = 500;
        //Start
        damage = 0;
    }

    private void setCollisionValues() {
        // Set collision settings based on character class

        switch (playerClass) {
            case 0:
                solidArea = new Rectangle(); // draws a square at the centre of the player
                solidArea.x = 90; // position of actual collision square
                solidArea.y = 60;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;
                solidArea.width = 40; // outer area of collision square
                solidArea.height = 30;
                attackArea.width = 50;
                attackArea.height = 30;
                break;
            case 1:
                solidArea = new Rectangle(); // draws a square at the centre of the player
                solidArea.x = 75; // position of actual collision square
                solidArea.y = 60;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;
                solidArea.width = 50; // outer area of collision square
                solidArea.height = 30;
                break;
            case 2:
                solidArea = new Rectangle(); // draws a square at the centre of the player
                solidArea.x = 75; // position of actual collision square
                solidArea.y = 60;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;
                solidArea.width = 30; // outer area of collision square
                solidArea.height = 30;
        }
    }

    public void setItems() {
        //add inventory
    }

    @Override
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

                // CHECK MOB COLLISION
                int mobIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                interactMob(mobIndex);

                // CHECK INTERACTIVE TILE
//                int iTileIndex = gp.cChecker.checkEntityCollision(this,gp.iTile);

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

            // Animation speed
            spriteCounter++;
            if (currentActionList.size() > 14) {
                if (spriteCounter > 4) loopThroughSprites();
            } else if (currentActionList.size() > 7) {
                if (spriteCounter > 6) loopThroughSprites();
            } else {
                if (spriteCounter > 9) loopThroughSprites();
            }

            // CALCULATE CENTRAL AXIS OF CURSOR
            if (playerClass == 0) {
                // WARRIOR
                cursor.calculateAngle((int)(screenX + gp.TILE_SIZE * 2.3), screenY + gp.TILE_SIZE + 10);
            } else if (playerClass == 1) {
                // KNIGHT
                cursor.calculateAngle((screenX + gp.TILE_SIZE * 2 + 5), screenY + gp.TILE_SIZE);
            } else if (playerClass == 2) {
                // ASSASSIN
                cursor.calculateAngle((int)(screenX + gp.TILE_SIZE * 1.9), screenY + gp.TILE_SIZE);
            }
        }

        if (gp.keyH.shotKeyPressed){
            if (currentWeapon == null){
                attacking = true;
            } else {
                projectile.set(worldX, worldY, action, true, this);

                gp.projectileList.add(projectile);
            }
        }

        if (gp.keyH.onePressed){
            if (hotbarList.get(0) != null){
                currentWeapon = hotbarList.get(0);
                projectile = currentWeapon.projectile;
                System.out.println("Current Weapon is: " + currentWeapon.projectile);
                System.out.println(projectile);
            } else {
                System.out.println("No weapon");
            }
        }
        if (gp.keyH.twoPressed){
            if (hotbarList.get(1) != null){
                currentWeapon = hotbarList.get(1);
            } else {
                System.out.println("No weapon");
            }
        }
        if (gp.keyH.threePressed){
            if (hotbarList.get(2) != null){
                currentWeapon = hotbarList.get(2);
            } else {
                System.out.println("No weapon");
            }
        }
    }

    public void attackAnimation(){ // animation attack
        animationCounter++;
        switch (playerClass) {
            case 0:
                if (animationCounter <= 5){
                    animationSpriteNum = 0;
                } else if (animationCounter <= 10) {
                    animationSpriteNum = 1;
                } else if (animationCounter <= 15) {
                    animationSpriteNum = 2;

                    // SAVE CURRENT DATA OF PLAYER
                    int currentWorldX = worldX;
                    int currentWorldY = worldY;
                    int solidAreaWidth = solidArea.width;
                    int solidAreaHeight = solidArea.height;

                    // ADJUST FOR ATTACK
                    switch (action) {
                        case "moveUp": worldY -= attackArea.height; break;
                        case "moveDown": worldY += attackArea.height; break;
                        case "moveLeft": worldX -= attackArea.width; break;
                        case "moveRight": worldX += attackArea.width; break;
                    }

                    // ATTACK AREA BECOMES SOLID AREA
                    solidArea.width = attackArea.width;
                    solidArea.height = attackArea.height;
                    // CHECK MONSTER COLLISION
                    int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                    damageMonster(monsterIndex, this);

                    int iTileIndex = gp.cChecker.checkEntityCollision(this,gp.iTile);
                    damageInteractiveTile(iTileIndex);

                    // CHANGE BACK TO ORIGINAL
                    worldX = currentWorldX;
                    worldY = currentWorldY;
                    solidArea.width = solidAreaWidth;
                    solidArea.height = solidAreaHeight;

                } else if (animationCounter <= 20) {
                    animationSpriteNum = 3;
                } else if (animationCounter <= 25) {
                    animationSpriteNum = 4;
                } else if (animationCounter <= 30) {
                    animationSpriteNum = 5;
                } else if (animationCounter <= 35) {
                    animationSpriteNum = 6;
                } else if (animationCounter <= 40) {
                    animationSpriteNum = 7;
                } else if (animationCounter <= 45) {
                    animationSpriteNum = 8;
                } else if (animationCounter <= 50) {
                    animationSpriteNum = 0;
                    animationCounter = 0;
                    attacking = false;
                }
                break;
            case 1:
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
                } else if (animationCounter <= 30) {
                    animationSpriteNum = 5;
                } else if (animationCounter <= 35) {
                    animationSpriteNum = 0;
                    animationCounter = 0;
                    attacking = false;
                }
                break;
            case 2:
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
                } else if (animationCounter <= 30) {
                    animationSpriteNum = 5;
                } else if (animationCounter <= 35) {
                    animationSpriteNum = 6;
                } else if (animationCounter <= 40) {
                    animationSpriteNum = 7;
                } else if (animationCounter <= 45) {
                    animationSpriteNum = 8;
                } else if (animationCounter <= 50) {
                    animationSpriteNum = 9;
                } else if (animationCounter <= 55) {
                    animationSpriteNum = 10;
                } else if (animationCounter <= 60) {
                    animationSpriteNum = 11;
                } else if (animationCounter <= 65) {
                    animationSpriteNum = 12;
                } else if (animationCounter <= 70) {
                    animationSpriteNum = 13;
                } else if (animationCounter <= 75) {
                    animationSpriteNum = 14;
                } else if (animationCounter <= 80) {
                    animationSpriteNum = 15;
                } else if (animationCounter <= 85) {
                    animationSpriteNum = 16;
                } else if (animationCounter <= 90) {
                    animationSpriteNum = 17;
                } else if (animationCounter <= 95) {
                    animationSpriteNum = 18;
                } else if (animationCounter <= 100) {
                    animationSpriteNum = 19;
                } else if (animationCounter <= 105) {
                    animationSpriteNum = 20;
                } else if (animationCounter <= 110) {
                    animationSpriteNum = 21;
                } else if (animationCounter <= 115) {
                    animationSpriteNum = 22;
                } else if (animationCounter <= 120) {
                    animationSpriteNum = 23;
                } else if (animationCounter <= 125) {
                    animationSpriteNum = 24;
                } else if (animationCounter <= 130) {
                    animationSpriteNum = 25;
                } else if (animationCounter <= 135) {
                    animationSpriteNum = 0;
                    animationCounter = 0;
                    attacking = false;
                }
                break;
        }
    }

    public void interactObject (int index) {
        if (index == 0){
            gp.gameState = gp.shopState;
        } else if (index != 999) {
//            gp.objArray[index] = null;
            System.out.println(gp.objArr[gp.currentMap][index].message);
            if (!gp.objArr[gp.currentMap][index].interactList.isEmpty())
                gp.objArr[gp.currentMap][index].interacting = true;
        }
    }

    public void interactNPC (int index) {
            if (index != 999) {
                gp.gameState = gp.dialogueState;
                gp.npcArr[gp.currentMap][index].speak();
            }
            if (index == 1) {
                gp.gameState = gp.shopState;
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

    public void damageMonster(int i, Entity attacker) {
        if (i != 999){

            if(!gp.mobArr[gp.currentMap][i].iframe){
                knockBack(gp.mobArr[gp.currentMap][i],attacker);
                gp.mobArr[gp.currentMap][i].life -= 1;
                gp.mobArr[gp.currentMap][i].iframe = true;
                gp.mobArr[gp.currentMap][i].damageReaction();
                System.out.println("hit");

                if(gp.mobArr[gp.currentMap][i].life <= 0) {
                    gp.mobArr[gp.currentMap][i].dead = true;
                }
            } else {
                System.out.println("miss");
            }
        }
    }

    public void knockBack(Entity target,Entity attacker){
        this.attacker = attacker;
        target.knockBackDirection = attacker.action;
        target.speed += 10;
        target.knockBack = true;
    }

    public void damageInteractiveTile(int i) {
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible){
            gp.iTile[i] = null;
        }
    }

    public void draw(Graphics2D g2) {
        if (spriteNum > currentActionList.size() - 1)
            spriteNum = 1;
        BufferedImage image = currentActionList.get(spriteNum - 1);

        if (animationSpriteNum > playerRightAttackList.size())
            animationSpriteNum = 0;
        BufferedImage animationImage = lookingRight? playerRightAttackList.get(animationSpriteNum) : playerLeftAttackList.get(animationSpriteNum);

        if (!attacking){
            if(iframe){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            g2.drawImage(image, screenX, screenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        } if (attacking){
            g2.drawImage(animationImage, screenX, screenY,null); // draw attack animation
        }
//        if (gp.gameArea == 0) { // OLD PLAYER DRAW CODE
//            if (!attacking){
//                if(iframe){
//                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
//                }
//                g2.drawImage(image, worldX, worldY, null);
//                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//            } if (attacking){
//                g2.drawImage(animationImage, worldX, worldY,null);
//            }
//        } else if (gp.gameArea == 1){
//            if (!attacking){
//                if(iframe){
//                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
//                }
//                g2.drawImage(image, screenX, screenY, null);
//                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//            } if (attacking){
//                g2.drawImage(animationImage, screenX, screenY,null); // draw attack animation
//            }
//            } else {
//                 g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
//        }

        // Draw arrow
        if (playerClass == 0) {
            // WARRIOR
            cursor.draw(g2, (int)(screenX + gp.TILE_SIZE * 2.3), screenY + gp.TILE_SIZE);
        } else if (playerClass == 1) {
            // KNIGHT
//            cursor.draw(g2, worldX + gp.TILE_SIZE * 2 + 5, worldY + gp.TILE_SIZE); // For fixed camera
            cursor.draw(g2, (int)(screenX + gp.TILE_SIZE * 2.09), screenY + gp.TILE_SIZE);
        } else if (playerClass == 2) {
            // ASSASSIN
//            cursor.draw(g2, (int)(worldX + gp.TILE_SIZE * 1.9), worldY + gp.TILE_SIZE); // For fixed camera
            cursor.draw(g2, (int)(screenX + gp.TILE_SIZE * 1.86), screenY + gp.TILE_SIZE);
        }
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
                case 0: // WARRIOR
                    dir = "/player/Warrior/";
                    // Load sprites for attacking
                    for (int i = 0; i <= 8; i++) {
                        playerRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing attackLeft " + i));
                        playerLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing attackLeft " + i));
                    }

                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, playerRightAttackList, 220, 96);
                    UtilityTool.scaleEntityList(this, playerLeftAttackList, 220, 96);
                    break;
                case 1: // KNIGHT
                    dir = "/player/Knight/";
                    // Load sprites for attacking
                    for (int i = 0; i <= 5; i++) {
                        playerRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing attackRight " + i));
                        playerLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing attackLeft " + i));
                    }
                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, playerRightAttackList, 200, 96);
                    UtilityTool.scaleEntityList(this, playerLeftAttackList, 200, 96);
                    break;
                case 2: // ASSASSIN
                    dir = "/player/Assassin/";
                    for (int i = 0; i <= 25; i++) {
                        playerRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing attackRight " + i));
                        playerLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing attackLeft " + i));
                    }
                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, playerRightAttackList, 180, 96);
                    UtilityTool.scaleEntityList(this, playerLeftAttackList, 180, 96);
            }
        } catch (IOException e){
            e.printStackTrace(System.out);
        }


    }

    public void getPlayerSprites() {
        try {
            switch (playerClass) {
                case 0: // WARRIOR
                    dir = "/player/Warrior/";
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
                    UtilityTool.scaleEntityList(this, moveRightList, 220, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 220, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 220, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 220, 96);
                    break;
                case 1: // KNIGHT
                    dir = "/player/Knight/";
                    // Load sprites for movement
                    for (int i = 0; i <= 9; i++) {
                        moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                        moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                    }
                    // Load sprites for idle
                    for (int i = 0; i <= 9; i++) {
                        idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                        idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                    }
                    System.out.println("Loaded Knight sprites");
                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, moveRightList, 200, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 200, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 200, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 200, 96);
                    break;
                case 2:
                    dir = "/player/Assassin/";
                    // Load sprites for movement
                    for (int i = 0; i <= 24; i++) {
                        moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                        moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                    }
                    // Load sprites for idle
                    for (int i = 0; i <= 17; i++) {
                        idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                        idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                    }

                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, moveRightList, 180, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 180, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 180, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 180, 96);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
