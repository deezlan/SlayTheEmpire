package entity;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    public GamePanel gp;
    public boolean lookingRight = true;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    // ENTITY TYPE
    public int type;
    public final int
            type_player = 0,
            type_mob = 1,
            type_npc = 2,
            type_consumable = 3,
            type_pickup = 4,
            type_gate = 5,
            type_shop = 6,
            type_obelisk = 7;

    // PLAYER & MOB ATTRIBUTES
    public int
            // POSITION OFF OF FULL GAME MAP
            worldX,
            worldY,

            // STATUS VALUES
            defaultSpeed,
            speed,
            mobNum = 0,
            attack,
            maxLife,
            currentLife,

            // COLLISION ATTRIBUTES
            solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities
    public String action = "idleRight"; // DEFAULT ACTION

    // PLAYER & MOB COLLISION DIRECTION
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;

    // MOB MOVEMENT ALGORITHM
    public int actionLockCounter; // RANDOMIZER
    public boolean onPath = false; // ACTIVE PLAYER TRACKING

    public ArrayList<BufferedImage>
            // ALL ENTITIES CURRENT ANIMATION LIST
            currentList = new ArrayList<>(),

            // PLAYER, NPC & MOB MOVEMENT ANIMATION LIST
            idleRightList = new ArrayList<>(),
            idleLeftList = new ArrayList<>(),
            moveRightList = new ArrayList<>(),
            moveLeftList = new ArrayList<>(),

            // PLAYER & MOB ATTACK ANIMATION LIST
            playerRightAttackList = new ArrayList<>(),
            playerLeftAttackList = new ArrayList<>(),
            mobRightAttackList = new ArrayList<>(),
            mobLeftAttackList = new ArrayList<>(),

            // INTERACTABLE OBJECT ANIMATION LIST
            defaultList = new ArrayList<>(),
            interactList = new ArrayList<>(),

            // PROJECTILE ANIMATION LIST
            projectileRight = new ArrayList<>(),
            projectileLeft = new ArrayList<>(),
            projectileUp = new ArrayList<>(),
            projectileDown = new ArrayList<>();

    // NPC ATTRIBUTES
    public String[][] dialogs = new String[20][20];
    public int dialogueSet = 0,
            dialogueIndex = 0;

    // HIT DETECTION
    public boolean
            attacking = false,
            iframe = false,
            alive = true,
            dead = false,
            hpBarVisible = false;
    public int
            damageSprite,
            iframeCounter = 0;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    // MOB KNOCK-BACK
    public Entity attacker;
    public String knockBackDirection;
    public boolean knockBack = false;

    // PROJECTILES
    public int shotAvailableCounter = 0;
    public Projectile projectile;

    // GATE BLOCKING
    public boolean
            locked = false,
            locking = false,
            unlocking = false;

    // ITEM ATTRIBUTES
    public int damage;
    public BufferedImage weaponSprite;
    public String name;
    public int price;
    public String description = "";

    // OBJECTS ATTRIBUTES
    public String message = "";
    public boolean
            interacting = false,
            collision = false;

    // COUNTERS
    public int
            // currentList
            spriteNum = 0,
            spriteCounter = 0,

            // interactList
            interactSpriteNum = 0,
            interactSpriteCounter = 0,

            // attackList
            animationSpriteNum = 0,
            animationCounter = 0,

            dyingCounter = 0,
            hpBarCounter = 0,
            knockBackCounter = 0;

    // INTERFACE METHODS
    public void use(Entity entity) {} // PLAYER
    public void speak() {} // NPC
    public void checkDrop() {} // MOB
    public void setAction() {} // MOB
    public void damageReaction() {
    } // MOB

    // NPC METHODS
    public void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }

    // TRACKING METHODS
    public int getDistanceX(Entity target) {
        return Math.abs(worldX - target.worldX);
    }
    public int getDistanceY(Entity target) {
        return Math.abs(worldY - target.worldY);
    }
    public int getTileDistance(Entity target) {
        return (getDistanceX(target) + getDistanceY(target)) / gp.TILE_SIZE;
    }
    public int getGoalCol(Entity target) {
        return (target.worldX + target.solidArea.x) / gp.TILE_SIZE;
    }
    public int getGoalRow(Entity target) {
        return (target.worldY + target.solidArea.y) / gp.TILE_SIZE;
    }

    // MOB ACTION METHODS
    public void getRandomDirection() {
        actionLockCounter++;
        // GET A RANDOM DIRECTION
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(250) + 1;

            if (i <= 25) {
                action = "moveUp";
                currentList = moveRightList;
            }
            if (i > 25 && i <= 50) {
                action = "moveDown";
                currentList = moveLeftList;
            }
            if (i > 50 && i <= 75) {
                action = "moveLeft";
                currentList = moveLeftList;
            }
            if (i > 75 && i <= 100) {
                action = "moveRight";
                currentList = moveRightList;
            }
            if (i > 100 && i <= 125) {
                action = "idleRight";
                currentList = idleRightList;
            }
            if (i > 125 && i <= 150) {
                action = "idleLeft";
                currentList = idleLeftList;
            }
            if (i > 150 && i <= 175) {
                action = "moveUpRight";
                currentList = moveRightList;
            }
            if (i > 175 && i <= 200) {
                action = "moveDownRight";
                currentList = moveRightList;
            }
            if (i > 200 && i <= 225) {
                action = "moveUpLeft";
                currentList = moveLeftList;
            }
            if (i > 225) {
                action = "moveDownLeft";
                currentList = moveLeftList;
            }
            actionLockCounter = 0;
        }
    }
    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.TILE_SIZE;
        int startRow = (worldY + solidArea.y) / gp.TILE_SIZE;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            // NEXT WORLD X & Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.TILE_SIZE;
            int nextY = gp.pFinder.pathList.get(0).row * gp.TILE_SIZE;

            // ENTITY SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.TILE_SIZE) {
                action = "moveUp";
                currentList = moveRightList;
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.TILE_SIZE) {
                action = "moveDown";
                currentList = moveLeftList;
            } else if (enTopY >= nextY && enBottomY < nextY + gp.TILE_SIZE) {
                if (enLeftX > nextX) {
                    action = "moveLeft";
                    currentList = moveLeftList;
                }
                if (enLeftX < nextX) {
                    action = "moveRight";
                    currentList = moveRightList;
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                action = "moveUp";
                checkCollision();
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) { // check
                    action = "moveLeft";
                    currentList = moveLeftList;
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                action = "moveUp";
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) { // check
                    action = "moveRight";
                    currentList = moveRightList;
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                action = "moveDown";
                checkCollision(); // check
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) {
                    action = "moveLeft";
                    currentList = moveLeftList;
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                action = "moveDown";
                checkCollision(); // check
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) {
                    action = "moveRight";
                    currentList = moveRightList;
                }
            }
            // IF REACH GOAL STOP
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow) {
//                onPath = false;
        }
    }
    public void checkStartChase(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
            }
        }
    }
    public void damagePlayer(int attack) {
        if (!gp.player.iframe) {
            int damage = attack;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.currentLife -= damage;
            gp.player.iframe = true;
        }
    }
    public void checkShoot(int rate, int xOffset, int yOffset, int shotInterval) {
        int i = new Random().nextInt(rate);
        shotAvailableCounter = 0;
        if(i == 0 && !projectile.alive && shotAvailableCounter == shotInterval){
            projectile.set(worldX + (xOffset), worldY + (yOffset), action,true,this);
            for (int ii = 0; ii < gp.projectileArr[1].length; ii++) {
                if(gp.projectileArr[gp.currentMap][ii] == null){
                    gp.projectileArr[gp.currentMap][ii] = projectile;
                    break;
                }
            }
        }
    }
    public void checkStopChase(Entity target, int distance, int rate) {
        if (getTileDistance(target) > distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = false;
            }
        }
    }
    public void checkWithinAttackRange(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getDistanceX(gp.player);
        int yDis = getDistanceY(gp.player);

        switch (action) {
            case "moveUp":
                if (gp.player.worldY < worldY && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "moveDown":
                if (gp.player.worldY > worldY && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "moveLeft":
                if (gp.player.worldX < worldX && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "moveRight":
                if (gp.player.worldX > worldX && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
        }

        if (targetInRange) {
//          CHECK ATTACK HAPPENS
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
        }
    }
}
    public void dyingAnimation(Graphics2D g2) { // BLINKING EFFECT
        dyingCounter++;

        if (dyingCounter <= 5) {
            UtilityTool.changeAlpha(g2, 0f);
        }
        if (dyingCounter > 5 && dyingCounter <= 10) {
            UtilityTool.changeAlpha(g2, 1f);
        }
        if (dyingCounter > 10 && dyingCounter <= 15) {
            UtilityTool.changeAlpha(g2, 0f);
        }
        if (dyingCounter > 15 && dyingCounter <= 20) {
            UtilityTool.changeAlpha(g2, 1f);
        }
        if (dyingCounter > 20 && dyingCounter <= 25) {
            UtilityTool.changeAlpha(g2, 0f);
        }
        if (dyingCounter > 25 && dyingCounter <= 30) {
            UtilityTool.changeAlpha(g2, 1f);
        }
        if (dyingCounter > 30 && dyingCounter <= 35) {
            UtilityTool.changeAlpha(g2, 0f);
        }
        if (dyingCounter > 35 && dyingCounter <= 40) {
            UtilityTool.changeAlpha(g2, 1f);
        }
        if (dyingCounter > 40) {
            alive = false;
        }
    }
    public void dropItem(Entity droppedItem) {
        for(int i = 0; i < gp.objArr.length; i++){
            if(gp.objArr[gp.currentMap][i] == null){
                gp.objArr[gp.currentMap][i] = droppedItem;
                gp.objArr[gp.currentMap][i].worldX = worldX + gp.TILE_SIZE*2;
                gp.objArr[gp.currentMap][i].worldY = worldY + gp.TILE_SIZE*2;
                break;
            }
        }
    }

    // PLAYER & MOB METHODS
    public void checkCollision() {
        upCollisionOn = false; // resets collisions off
        downCollisionOn = false;
        leftCollisionOn = false;
        rightCollisionOn = false;
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkPLayer(this);
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntityCollision(this, gp.npcArr);
        boolean contactPlayer = gp.cChecker.checkPLayer(this);

        if (this.type == 1 && contactPlayer) {
            if (!gp.player.iframe) {
                gp.player.currentLife -= 1;
                gp.player.iframe = true;
            }
        }
    }
    public void runCurrentListAnimation() {
        spriteNum = (spriteNum >= currentList.size()) ? 0 : spriteNum + 1;
        spriteCounter = 0;
    }

    // ATTACK METHODS
    public void checkDamageSprite() {
        if (animationSpriteNum == damageSprite) {
            // SAVE CURRENT DATA OF ENTITY
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
            if (type == 1) {
                if (gp.cChecker.checkPLayer(this)) damagePlayer(attack);
            } else { // FOR PLAYER
                // CHECK MONSTER COLLISION
                int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                gp.player.damageMonster(monsterIndex, attack,this);

                int iTileIndex = gp.cChecker.checkEntityCollision(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);
            }

            // CHANGE BACK TO ORIGINAL
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
    }
    public void runAttackAnimation() {
        animationCounter++;
        if (animationSpriteNum < mobRightAttackList.size() && animationCounter%5 == 0) {
            animationSpriteNum++;
        }
        if (animationSpriteNum >= mobRightAttackList.size() - 1) {
            animationSpriteNum = 0;
            animationCounter = 0;
            attacking = false;
        }
    }
    public void startAttack() { // animation attack
        checkDamageSprite();
        switch (action) {
            case "moveUp", "moveDown":
                currentList = lookingRight ? mobRightAttackList : mobLeftAttackList; break;
            case "moveLeft":
                currentList = mobLeftAttackList; break;
            case "moveRight":
                currentList = mobRightAttackList; break;
        }
        runAttackAnimation();
    }

    // OBJECT METHODS
    public void runInteractSprites() {
        interactSpriteCounter++;
        if (interactSpriteNum < interactList.size() && interactSpriteCounter%5 == 0) {
            interactSpriteNum++;
        }
        if (interactSpriteNum >= interactList.size() - 1) {
            interactSpriteNum = 0;
            interactSpriteCounter = 0;
            interacting = false;
            System.out.println("cake");
            if (type == type_obelisk)
                gp.eHandler.changeMap();
        }
    }

    // GATE METHODS
    public void runLockAnimation() {}
    public void runUnlockingAnimation() {}

    // GAME LOOP METHODS
    public void update() {
        if (interacting) {
            runInteractSprites();
        }
        if (type == type_gate) {
            if (locking) runLockAnimation();
            if (unlocking) runUnlockingAnimation();
        } else {
            if (knockBack) {
                checkCollision();
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                } else if (!upCollisionOn && !downCollisionOn && !leftCollisionOn && !rightCollisionOn) {
                    switch (knockBackDirection) {
                        case "moveUp": worldY -= speed; break;
                        case "moveDown": worldY += speed; break;
                        case "moveRight": worldX += speed; break;
                        case "moveLeft": worldX -= speed; break;

                        case "moveUpRight":
                            worldX += speed;
                            worldY -= speed;
                            break;
                        case "moveDownRight":
                            worldX += speed;
                            worldY += speed;
                            break;
                        case "moveUpLeft":
                            worldX -= speed;
                            worldY -= speed;
                            break;
                        case "moveDownLeft":
                            worldX -= speed;
                            worldY += speed;
                    }
                }

                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                }
            } else if (attacking) {
                startAttack();
            } else {
                setAction();
                checkCollision();

                if (!upCollisionOn && !downCollisionOn && !leftCollisionOn && !rightCollisionOn) {
                    switch (action) {
                        case "moveUp": worldY -= speed; break;
                        case "moveDown": worldY += speed; break;
                        case "moveRight": worldX += speed; break;
                        case "moveLeft": worldX -= speed; break;

                        case "moveUpRight":
                            worldX += speed;
                            worldY -= speed;
                            break;
                        case "moveDownRight":
                            worldX += speed;
                            worldY += speed;
                            break;
                        case "moveUpLeft":
                            worldX -= speed;
                            worldY -= speed;
                            break;
                        case "moveDownLeft":
                            worldX -= speed;
                            worldY += speed;
                            break;
                    }
                }

                // Animation speed
                spriteCounter++;
                if (this.currentList.size() > 28) {
                    if (spriteCounter > 4) runCurrentListAnimation();
                } else if (this.currentList.size() > 21) {
                    if (spriteCounter > 6) runCurrentListAnimation();
                } else if (this.currentList.size() > 14) {
                    if (spriteCounter > 9) runCurrentListAnimation();
                }  else if (this.currentList.size() > 7) {
                    if (spriteCounter > 11) runCurrentListAnimation();
                } else {
                    if(spriteCounter > 13) runCurrentListAnimation();
                }
            }

            if (iframe) {
                iframeCounter++;
                if (iframeCounter > 30) {
                    iframe = false;
                    iframeCounter = 0;
                }
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image;
        if (spriteNum >= currentList.size() - 1) spriteNum = 0;

        if (!alive) return;

        if (interacting)
            image = interactList.get(interactSpriteNum);
        else if (type == type_gate) {
            if (locked && !unlocking) // LOCKED
                image = defaultList.get(6);
            else if (locked) // UNLOCKING
                image = interactList.get(interactSpriteNum);
            else if (!locking) // UNLOCKED
                image = defaultList.get(0);
            else // LOCKING
                image = defaultList.get(spriteNum);
        } else {
            image = currentList.get(spriteNum);
        }

        if(gp.currentMap == 0) {
            if (iframe) {
                hpBarVisible = true;
                hpBarCounter = 0;
                UtilityTool.changeAlpha(g2, 0.3f);
            }

            if (dead) dyingAnimation(g2);

            if(!attacking){
                g2.drawImage(image, worldX, worldY, null);
                UtilityTool.changeAlpha(g2, 1f);
            }

            if (attacking) {
                if (animationSpriteNum >= currentList.size() - 1)
                    animationSpriteNum = 0;
                BufferedImage animationImage = currentList.get(animationSpriteNum);
                g2.drawImage(animationImage, worldX, worldY, null);
            }
        } else if (gp.currentMap == 1) {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX - 48 * 4 && // added values due to player sprite not centered
                    worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX + 48 * 4 &&
                    worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY - 48 * 2 &&
                    worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY + 48 * 2) {
                // MONSTER HP BAR
                if (type == type_mob && hpBarVisible) {
                    double oneScale = (double) gp.TILE_SIZE / maxLife;
                    double hpBarValue = oneScale * currentLife;
                    if (mobNum == 1) { // SLIME
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 51, screenY + 121, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 50, screenY + 120, (int) hpBarValue, 9);
                    }
                    if (mobNum == 2) { // SKELLINGTON
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 51, screenY + 141, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 50, screenY + 140, (int) hpBarValue, 9);
                    }
                    if (mobNum == 3) { // ROBOT GUARDIAN
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 81, screenY + 161, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 80, screenY + 160, (int) hpBarValue, 9);
                    }
                    if (mobNum == 4) { // RAMSES
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 61, screenY + 121, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 60, screenY + 120, (int) hpBarValue, 9);
                    }
                    if (mobNum == 5) { // GOBLIN
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 81, screenY + 141, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 80, screenY + 140, (int) hpBarValue, 9);
                    }
                    if (mobNum == 6) { // SKELETON KNIGHT
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 81, screenY + 141, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 80, screenY + 140, (int) hpBarValue, 9);
                    }
                    if (mobNum == 7) { // ARMORED GUARDIAN
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 51, screenY + 121, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 50, screenY + 120, (int) hpBarValue, 9);
                    }
                    if (mobNum == 8) { // FLYING EYE
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 121, screenY + 191, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 120, screenY + 190, (int) hpBarValue, 9);
                    }
                    if (mobNum == 9) { // MUSHROOM
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 126, screenY + 211, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 125, screenY + 210, (int) hpBarValue, 9);
                    }
                    if (mobNum == 10) { // CANINE
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(screenX + 21, screenY + 91, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(screenX + 20, screenY + 90, (int) hpBarValue, 9);
                    }

                    hpBarCounter++;
                    if (hpBarCounter > 600) {
                        hpBarCounter = 0;
                        hpBarVisible = false;
                    }
                }
                if (iframe) {
                    hpBarVisible = true;
                    hpBarCounter = 0;
                    UtilityTool.changeAlpha(g2, 0.3f);
                }
                if (dead) {
                    dyingAnimation(g2);
                }
                if(!attacking){
                    g2.drawImage(image, screenX, screenY, null);
                    UtilityTool.changeAlpha(g2, 1f);
                }
                if (attacking) {
                    if (animationSpriteNum >= currentList.size() - 1)
                        animationSpriteNum = 0;
                    BufferedImage animationImage = currentList.get(animationSpriteNum);
                    g2.drawImage(animationImage, screenX, screenY, null);
                }
            }
        }
    }
}



