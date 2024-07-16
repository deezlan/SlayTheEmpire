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

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    // UI ATTRIBUTES
    public ArrayList<Entity> hotbarList = new ArrayList<>();
    public ArrayList<Integer> ownedWeapon = new ArrayList<>();
    public Entity currentWeapon = null;
    private final Cursor cursor;

    // PLAYER POSITION
    public final int screenX, screenY; // POSITION OFF OF GAME WINDOW
    public String dir;

    // PLAYER STATUS
    public int totalCoins;
    public int playerClass;
    private int delta;

//    public ArrayList<Entity> inventory = new ArrayList<>(); temp commented
//    public final int inventorySize = 8; temp commented

    public Player (GamePanel gp, KeyHandler keyH, Cursor cursor, int playerClass) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        this.playerClass = playerClass;
        this.cursor = cursor;

        // CENTER PLAYER SCREEN POSITION BASED ON PLAYER CLASS
        switch (playerClass) {
            case 0:
                screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE/2) - 90; // CENTERED PLAYER 0 POSITION
                screenY = (gp.SCREEN_HEIGHT/2) - 72;
                break;
            case 1:
                screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE/2) - 72; // CENTERED PLAYER 1 POSITION
                screenY = (gp.SCREEN_HEIGHT/2) - 72;
                break;
            case 2:
                default:
                screenX = (gp.SCREEN_WIDTH/2) - (gp.TILE_SIZE/2) - 48; // CENTERED PLAYER 2 POSITION
                screenY = (gp.SCREEN_HEIGHT/2) - 72;
        }

        setDefaultValues();
        setCollisionValues();
        setItems();
        getPlayerSprites();
        getPlayerAttackSprites();
    }

    @Override
    public void setStatValues(int defaultSpeed, int maxLife, boolean hasRanged, boolean isBoss, int mobBossNum) {
        super.setStatValues(defaultSpeed, maxLife, hasRanged, isBoss, mobBossNum);
    }

    @Override
    public void setAttackValues(int damage, int damageSprite, int attWidth, int attHeight) {
        super.setAttackValues(damage, damageSprite, attWidth, attHeight);
    }

    // DEFAULT INITIALIZATION
    public void setDefaultValues() {
        worldX = 303; // PLAYER SPAWN X
        worldY = 9; // PLAYER SPAWN Y
        defaultSpeed = 3;
        speed = defaultSpeed;
        type = type_player;

        // ATTRIBUTES
        maxLife = 10;
        currentLife = maxLife;
        totalCoins = 500;
        damage = 1;
        damageSprite = 2;
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
                attackArea.width = gp.TILE_SIZE*2;
                attackArea.height = gp.TILE_SIZE*3;
                break;
            case 1:
                solidArea = new Rectangle(); // draws a square at the centre of the player
                solidArea.x = 75; // position of actual collision square
                solidArea.y = 60;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;
                solidArea.width = 50; // outer area of collision square
                solidArea.height = 30;

                // IMPLEMENT THESE VALUES
                attackArea.width = gp.TILE_SIZE*2;
                attackArea.height = gp.TILE_SIZE*3;

                break;
            case 2:
                solidArea = new Rectangle(); // draws a square at the centre of the player
                solidArea.x = 75; // position of actual collision square
                solidArea.y = 60;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;
                solidArea.width = 30; // outer area of collision square
                solidArea.height = 30;

                // IMPLEMENT THESE VALUES
                attackArea.width = gp.TILE_SIZE*2;
                attackArea.height = gp.TILE_SIZE*3;
        }
    }
    public void setItems() {
        //add inventory
    }
    public void setDefaultPosition() {
        gp.currentMap = 0;
        worldX = 303; // PLAYER SPAWN X
        worldY = 9; // PLAYER SPAWN Y
        action = "idleRight";
    }

    // RESET METHODS
    public void restoreLife() {
        currentLife = maxLife;
        iframe = false;
    }

    // ATTACK METHODS
    @Override
    public void checkDamageSprite() {
        if (animationSpriteNum == damageSprite) {
            // SAVE CURRENT DATA OF PLAYER
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST FOR ATTACK
            switch (action) {
                case "idleRight", "moveRight": worldX += attackArea.width; break;
                case "idleLeft", "moveLeft": worldX -= attackArea.width; break;
                case "moveUp": worldY -= attackArea.height; break;
                case "moveDown": worldY += attackArea.height; break;
                case "moveUpRight":
                    worldX += attackArea.width;
                    worldY -= attackArea.height;
                    break;
                case "moveDownRight":
                    worldX += attackArea.width;
                    worldY += attackArea.height;
                    break;
                case "moveUpLeft":
                    worldX -= attackArea.width;
                    worldY -= attackArea.height;
                    break;
                case "moveDownLeft":
                    worldX -= attackArea.width;
                    worldY += attackArea.height;
            }

            // ATTACK AREA BECOMES SOLID AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
            damageMonster(monsterIndex, damage,this);

            int iTileIndex = gp.cChecker.checkEntityCollision(this,gp.iTile);
            damageInteractiveTile(iTileIndex);

            // CHANGE BACK TO ORIGINAL
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

            if (playerClass == 2) {
                switch (damageSprite) {
                    case 2:
                        damageSprite = 9;
                        break;
                    case 9:
                        damageSprite = 16;
                        break;
                    case 16:
                        damageSprite = 2;
                }
            }
        }
    }
    @Override
    public void runAttackAnimation() {
        animationCounter++;
        if (animationSpriteNum < playerRightAttackList.size() && animationCounter%5 == 0) {
            animationSpriteNum++;
        }
        if (animationSpriteNum >= playerRightAttackList.size() - 1) {
            animationSpriteNum = 0;
            animationCounter = 0;
            attacking = false;
        }
    }
    @Override
    public void startAttack(){
        checkDamageSprite();
        runAttackAnimation();
    }
    public void damageMonster(int i, int attack, Entity attacker) {
        if (i != 999){
            if(!gp.mobArr[gp.currentMap][i].iframe){
                knockBack(gp.mobArr[gp.currentMap][i],attacker);
                gp.mobArr[gp.currentMap][i].currentLife -= attack;
                gp.mobArr[gp.currentMap][i].iframe = true;
                gp.mobArr[gp.currentMap][i].damageReaction();
                System.out.println("hit");

                if(gp.mobArr[gp.currentMap][i].currentLife <= 0) {
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

    // INTERACT METHODS
    public void interactObject (int index) {
        if (index != 999) {
            if (gp.objArr[gp.currentMap][index].type == type_pickup) {
                gp.objArr[gp.currentMap][index].use(this);
                gp.objArr[gp.currentMap][index] = null;
            } else if (type == type_shop){
                gp.gameState = gp.shopState;
            } else {
                if (!gp.objArr[gp.currentMap][index].message.isEmpty())
                    System.out.println(gp.objArr[gp.currentMap][index].message);
                if (!gp.objArr[gp.currentMap][index].interactList.isEmpty()) {
                    if (gp.objArr[gp.currentMap][index].type != type_gate)
                        gp.objArr[gp.currentMap][index].interacting = true;
                }
            }
        }
    }
    public void interactNPC (int index) {
        if (index != 999) {
            gp.npcArr[gp.currentMap][index].speak();
        }
        if (index == 1) {
            gp.gameState = gp.shopState;
        }
    }
    public void interactMob (int index) {
        if (index != 999) {
            if (!iframe && !gp.mobArr[gp.currentMap][index].dead){
                currentLife -= 1;
                iframe = true;
            }
        }
    }

    // GAME LOOP METHODS
    @Override
    public void update() {
        delta++;
        if(!keyH.godModeOn){
            if (currentLife <= 0){
                gp.gameState = gp.deathState;
            }
        }

        if (currentLife > maxLife){
            currentLife = maxLife;
        }

        if (attacking)
            startAttack();
        else {

            if ((keyH.wPressed && keyH.sPressed) || (keyH.aPressed && keyH.dPressed)) {
                action = "stuckOppositeDirection";
                currentList = lookingRight ? idleRightList : idleLeftList;
            }

            if (keyH.wPressed && keyH.sPressed && keyH.aPressed) action = "moveLeft";
            if (keyH.wPressed && keyH.sPressed && keyH.dPressed) action = "moveRight";
            if (keyH.aPressed && keyH.dPressed && keyH.wPressed) action = "moveUp";
            if (keyH.aPressed && keyH.dPressed && keyH.sPressed) action = "moveDown";

            if ((keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed) && !action.equals("stuckOppositeDirection")) {
                if (keyH.wPressed) action = "moveUp";
                if (keyH.sPressed) action = "moveDown";
                if (keyH.aPressed) action = "moveLeft";
                if (keyH.dPressed) action = "moveRight";
                if (keyH.wPressed && keyH.dPressed) action = "moveUpRight";
                if (keyH.sPressed && keyH.dPressed) action = "moveDownRight";
                if (keyH.wPressed && keyH.aPressed) action = "moveUpLeft";
                if (keyH.sPressed && keyH.aPressed) action = "moveDownLeft";

                if (keyH.enterPressed) attacking = true;

                if (!upCollisionOn)
                    if (keyH.wPressed) worldY -= speed;
                if (!downCollisionOn)
                    if (keyH.sPressed) worldY += speed;
                if (!leftCollisionOn)
                    if (keyH.aPressed) worldX -= speed;
                if (!rightCollisionOn)
                    if (keyH.dPressed) worldX += speed;

                // CHECK TILE COLLISION
                upCollisionOn = false;
                downCollisionOn = false;
                leftCollisionOn = false;
                rightCollisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK GATE COLLISION
                gp.cChecker.checkGate(this, true);

                // CHECK OBJECT COLLISION BEFORE INTERACTING
                gp.cChecker.checkObject(this, true);
                int objIndex = gp.cChecker.checkObject(this, true);
                interactObject(objIndex);

                // CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntityCollision(this, gp.npcArr);
                interactNPC(npcIndex);

                //CHECK EVENT
                gp.eHandler.checkEvent();
                gp.keyH.ePressed = false;

                // CHECK MOB COLLISION
                int mobIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                interactMob(mobIndex);

                switch (action) {
                    case "moveUp", "moveDown":
                        currentList = lookingRight ? moveRightList : moveLeftList;
                        break;
                    case "moveLeft", "moveUpLeft", "moveDownLeft":
                        currentList = moveLeftList;
                        lookingRight = false;
                        break;
                    case "moveRight", "moveUpRight", "moveDownRight":
                        currentList = moveRightList;
                        lookingRight = true;
                }
            } else {
                action = lookingRight ? "idleRight" : "idleLeft";
                currentList = action.equals("idleRight") ? idleRightList : idleLeftList;

                if (keyH.enterPressed) attacking = true;
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
            if (currentList.size() > 14) {
                if (spriteCounter > 4) runCurrentListAnimation();
            } else if (currentList.size() > 7) {
                if (spriteCounter > 6) runCurrentListAnimation();
            } else {
                if (spriteCounter > 9) runCurrentListAnimation();
            }

            // CALCULATE CENTRAL AXIS OF CURSOR
            if (gp.currentMap == 0) {
                if (playerClass == 0) {
                    // WARRIOR
                    cursor.calculateAngle((int)(worldX + gp.TILE_SIZE * 2.3), worldY + gp.TILE_SIZE + 10);
                } else if (playerClass == 1) {
                    // KNIGHT
                    cursor.calculateAngle((worldX + gp.TILE_SIZE * 2 + 5), worldY + gp.TILE_SIZE);
                } else if (playerClass == 2) {
                    // ASSASSIN
                    cursor.calculateAngle((int)(worldX + gp.TILE_SIZE * 1.9), worldY + gp.TILE_SIZE);
                }
            } else {
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
        }

        if (gp.keyH.shotKeyPressed && shotAvailableCounter == 30){
            if (currentWeapon == null){
                attacking = true;
            } else {
                if (currentWeapon.name.equalsIgnoreCase("fireball cannon") && delta>60){
                    delta = 0;
                    projectile1.set(worldX + currentList.get(0).getWidth()/2 - projectile1.currentList.get(0).getWidth()/2,
                            worldY + currentList.get(0).getHeight()/2 - projectile1.currentList.get(0).getHeight()/2, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][1] = projectile1;
                } else if (currentWeapon.name.equalsIgnoreCase("stickler") && delta>120) {
                    delta = 0;
                    projectile1.set(worldX, worldY-48, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][47] = projectile1;
                    projectile2.set(worldX+48, worldY, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][48] = projectile2;
                    projectile3.set(worldX-48, worldY+48, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][49] = projectile3;
                } else if (currentWeapon.name.equalsIgnoreCase("electric blaster")) {
                    projectile1.set(worldX, worldY, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][1] = projectile1;
                } else if (currentWeapon.name.equalsIgnoreCase("hammer") && delta>200) {
                    delta = 0;
                    projectile1.set(gp.player.worldX+48, gp.player.worldY-24, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][47] = projectile1;
                    projectile2.set(gp.player.worldX-72, gp.player.worldY-24, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][48] = projectile2;
                    projectile3.set(gp.player.worldX-12, gp.player.worldY+24, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][49] = projectile3;
                    projectile4.set(gp.player.worldX-12, gp.player.worldY-72, action, true, this, gp.cursor.deltaX, gp.cursor.deltaY);
                    gp.projectileArr[gp.currentMap][46] = projectile4;
                }

                shotAvailableCounter = 0; // ADDED COOL-DOWN
//                for (int i = 0; i < gp.projectileList[1].length; i++) {
//                    if(gp.projectileList[gp.currentMap][i] == null){
//                        gp.projectileList[gp.currentMap][i] = projectile;
//                        break;
//                    }
//                }

            }
        }

        if (gp.keyH.onePressed){
            if (hotbarList.get(0) != null){
                currentWeapon = hotbarList.get(0);
                projectile1 = currentWeapon.projectile1;
                projectile2 = currentWeapon.projectile2;
                projectile3 = currentWeapon.projectile3;
                projectile4 = currentWeapon.projectile4;
                System.out.println("Current Weapon is: " + currentWeapon);
                System.out.println(projectile1);
            } else {
                System.out.println("No weapon");
            }
        }
        if (gp.keyH.twoPressed){
            if (hotbarList.get(1) != null){
                currentWeapon = hotbarList.get(1);
                projectile1 = currentWeapon.projectile1;
                projectile2 = currentWeapon.projectile2;
                projectile3 = currentWeapon.projectile3;
                projectile4 = currentWeapon.projectile4;
                System.out.println("Current Weapon is: " + currentWeapon);
                System.out.println(projectile1);
            } else {
                System.out.println("No weapon");
            }
        }
        if (gp.keyH.threePressed){
            if (hotbarList.get(2) != null){
                currentWeapon = hotbarList.get(2);
                projectile1 = currentWeapon.projectile1;
                projectile2 = currentWeapon.projectile2;
                projectile3 = currentWeapon.projectile3;
                projectile4 = currentWeapon.projectile4;
            } else {
                System.out.println("No weapon");
            }
        }

        if(shotAvailableCounter < 30){
            shotAvailableCounter++;
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        if (spriteNum > currentList.size() - 1)
            spriteNum = 0;
        BufferedImage image = currentList.get(spriteNum);

        if (animationSpriteNum > playerRightAttackList.size())
            animationSpriteNum = 0;
        BufferedImage animationImage = lookingRight? playerRightAttackList.get(animationSpriteNum) : playerLeftAttackList.get(animationSpriteNum);

        switch (gp.currentMap){ // SWITCH TO SWITCH STATEMENT
            case 0:
                if(iframe){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                }

                if (attacking){
                    g2.drawImage(animationImage, worldX, worldY,null); // draw attack animation
                } else {
                    g2.drawImage(image, worldX, worldY, null);
                }

                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                break;
            case 1, 2:
                if(drawing){
                    if(iframe){
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                    }

                    if (attacking){
                        g2.drawImage(animationImage, screenX, screenY,null); // draw attack animation
                    } else {
                        g2.drawImage(image, screenX, screenY, null); // STOPPED HERE NEED TO FIND DRAW TEMP SCREEN
                    }
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    break;
                }
        }
        // Draw arrow
        switch (gp.currentMap){
            case 0:
                if (playerClass == 0) {
                    // WARRIOR
                    cursor.draw(g2, (int)(worldX + gp.TILE_SIZE * 2.3), worldY + gp.TILE_SIZE);
                } else if (playerClass == 1) {
                    // KNIGHT
                    cursor.draw(g2, worldX + gp.TILE_SIZE * 2 + 5, worldY + gp.TILE_SIZE); // For fixed camera
                } else if (playerClass == 2) {
                    // ASSASSIN
                    cursor.draw(g2, (int)(worldX + gp.TILE_SIZE * 1.9), worldY + gp.TILE_SIZE); // For fixed camera
                }
                break;
            case 1,2:
                if (playerClass == 0) {
                    // WARRIOR
                    cursor.draw(g2, (int)(screenX + gp.TILE_SIZE * 2.3), screenY + gp.TILE_SIZE);
                } else if (playerClass == 1) {
                    // KNIGHT
                    cursor.draw(g2, (int)(screenX + gp.TILE_SIZE * 2.09), screenY + gp.TILE_SIZE);
                } else if (playerClass == 2) {
                    // ASSASSIN
                    cursor.draw(g2, (int)(screenX + gp.TILE_SIZE * 1.86), screenY + gp.TILE_SIZE);
                }
                break;
        }
    }

    // SPRITE LOADING METHODS
    public void getPlayerSprites() {
        try {
            switch (playerClass) {
                case 0: // WARRIOR
                    System.out.println("Player is a Warrior");
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
                    System.out.println("Player is a Knight");
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
                    System.out.println("Player is an Assassin");
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
    public void getPlayerAttackSprites() {
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
}
