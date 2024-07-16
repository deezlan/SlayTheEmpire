package entity;

import main.GamePanel;
import main.UtilityTool;
import object.OBJ_PickUpCoin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public abstract class Entity {
    public String name;
    public GamePanel gp;
    public boolean lookingRight = true;
    public Projectile projectile1;
    public Projectile projectile2;
    public Projectile projectile3;
    public Projectile projectile4;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public Entity(GamePanel gp, int worldX, int worldY) {
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
    }

    // PLAYER & MOB ATTRIBUTES
    public int
            // POSITION OFF OF FULL GAME MAP
            worldX,
            worldY;

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
            // STATUS VALUES
            defaultSpeed,
            speed,
            mobNum = 0,
            attack,
            maxLife,
            currentLife,
            bossNum,

            // COLLISION ATTRIBUTES
            solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities
    public String action = "idleRight"; // DEFAULT ACTION
    public boolean
            hasRanged,
            inRage = false,
            sleep,
            boss,
            tempScene = false,
            drawing = true,
            alpha = false;

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

    // KNOCK-BACK
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
//    public String name;
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

    // MOB INITIALIZATION METHODS
    public void setStatValues(int defaultSpeed, int maxLife, boolean hasRanged, boolean isBoss, int mobBossNum) {
        this.type = type_mob;
        this.defaultSpeed = defaultSpeed;
        this.speed = defaultSpeed;
        this.maxLife = maxLife;
        this.currentLife = maxLife;
        this.hasRanged = hasRanged;

        if (isBoss) {
            boss = true;
            sleep = true;
            dialogueSet = 0;
            bossNum = mobBossNum;
        } else { mobNum = mobBossNum; }
    }
    public void setCollisionValues(int x, int y, int width, int height) {
        solidArea.x = x;
        solidArea.y = y;
        solidArea.width = width;
        solidArea.height = height;
        solidAreaDefaultX = x;
        solidAreaDefaultY = y;
    }
    public void setAttackValues(int damage, int damageSprite, int attWidth, int attHeight) {
        attack = damage;
        this.damageSprite = damageSprite;
        attackArea.width = attWidth;
        attackArea.height = attHeight;
    }

    // INTERFACE METHODS
    public void use(Entity entity) {} // PLAYER
    public void speak() {} // NPC

    // MOB
//    public void setAction() {}
    public void specialAttack() {}
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

    // MOB COMBAT METHODS
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
            int enRightX = worldX + solidArea.x;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y;

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
    public void setAction() {
        if(onPath) {
            // SEARCH DIRECTION TO GO
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

            if (hasRanged) {
                System.out.println("I have range onPath");
                checkShoot(200, idleRightList.get(0).getWidth()/2, idleRightList.get(0).getHeight()/2, 0);

            }
        } else {
            // CHECK IF START CHASING
            checkStartChase(gp.player, 5 , 100);
            // GET RANDOM DIRECTION
            getRandomDirection();
        }
        // CHECK ATTACK ON PLAYER
        if (!attacking) {
            if (hasRanged) {
                System.out.println("I have range");
                checkWithinAttackRange(30, gp.TILE_SIZE*5, gp.TILE_SIZE*2); // CHANGE ATTACK RANGE
                checkShoot(200, idleRightList.get(0).getWidth()/2, idleRightList.get(0).getHeight()/2, 0);
            } else {
                checkWithinAttackRange(30, gp.TILE_SIZE*2, gp.TILE_SIZE*2); // CHANGE ATTACK RANGE
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
        if (i == 0 && !projectile.alive && shotAvailableCounter == shotInterval) {
            projectile.set(worldX + (xOffset) - projectile.currentList.get(0).getWidth()/2,
                    worldY + (yOffset) - projectile.currentList.get(0).getHeight()/2,
                    action, true, this, gp.player.worldX, gp.player.worldY);
            for (int ii = 0; ii < gp.projectileArr[1].length; ii++) {
                if (gp.projectileArr[gp.currentMap][ii] == null) {
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
    public void checkWithinAttackRange(int rate, int vertical, int horizontal) {
        boolean targetInRange = false;
        int xDis = getDistanceX(gp.player);
        int yDis = getDistanceY(gp.player);

        switch (action) {
            case "moveUp":
                if (gp.player.worldY < worldY && yDis < vertical && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "moveDown":
                if (gp.player.worldY > worldY && yDis < vertical && xDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "moveLeft":
                if (gp.player.worldX < worldX && xDis < vertical && yDis < horizontal) {
                    targetInRange = true;
                }
                break;
            case "moveRight":
                if (gp.player.worldX > worldX && xDis < vertical && yDis < horizontal) {
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
    public void checkDrop() {
        int i = 0;
        while (gp.objArr[gp.currentMap][i] != null)
            i++;

        gp.objArr[gp.currentMap][i] = new OBJ_PickUpCoin(gp,
                worldX + idleRightList.get(0).getWidth()/2 - 24,
                worldY + idleRightList.get(0).getHeight()/2 - 24);
    }

    // PLAYER & MOB METHODS
    public void checkCollision() {
        upCollisionOn = false; // resets collisions off
        downCollisionOn = false;
        leftCollisionOn = false;
        rightCollisionOn = false;
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkGate(this, false);
        gp.cChecker.checkPLayer(this);
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntityCollision(this, gp.npcArr);
        boolean contactPlayer = gp.cChecker.checkPLayer(this);

        if (this.type == type_mob && contactPlayer) {
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

            // ATTACK AREA BECOMES SOLID AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // ADJUST FOR ATTACK
            switch (action) {
                case "moveUp": worldY -= (mobRightAttackList.get(0).getHeight() - attackArea.height)/2; break;
                case "moveDown": worldY += (mobLeftAttackList.get(0).getHeight() - attackArea.height)/2; break;
                case "moveLeft": worldX -= (mobLeftAttackList.get(0).getWidth() - attackArea.width)/2; break;
                case "moveRight": worldX += (mobRightAttackList.get(0).getWidth() - attackArea.width)/2; break;
            }

            if (type == 1) {
                if (gp.cChecker.checkPLayer(this)) damagePlayer(attack);
            } else { // FOR PLAYER
                // CHECK MONSTER COLLISION
                int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                gp.player.damageMonster(monsterIndex, attack, this);

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
        if (animationSpriteNum < mobRightAttackList.size() && animationCounter % 5 == 0) {
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
        if (interactSpriteNum < interactList.size() && interactSpriteCounter % 5 == 0) {
            interactSpriteNum++;
        }
        if (interactSpriteNum >= interactList.size() - 1) {
            interactSpriteNum = 0;
            interactSpriteCounter = 0;
            interacting = false;
            if (type == type_obelisk)
                gp.eHandler.changeMap();
        }
    }

    // GATE METHODS
    public void runLockAnimation() {}
    public void runUnlockingAnimation() {}

    // CAMERA METHODS
    public int getScreenX() {
        return worldX - gp.player.worldX + gp.player.screenX;
    }
    public int getScreenY() {
        return worldY - gp.player.worldY + gp.player.screenY;
    }
    public boolean inCamera() {
        return worldX + gp.TILE_SIZE * 5 > gp.player.worldX - gp.player.screenX - 48 * 4 && // added values due to player sprite not centered
                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX + 48 * 4 &&
                worldY + gp.TILE_SIZE * 5 > gp.player.worldY - gp.player.screenY - 48 * 2 &&
                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY + 48 * 2;
    }

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
                if (type == type_mob) {
                    System.out.println("For mobNum " + mobNum + " the type is " + type);
                    setAction();
                }

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
                } else if (this.currentList.size() > 7) {
                    if (spriteCounter > 11) runCurrentListAnimation();
                } else {
                    if (spriteCounter > 13) runCurrentListAnimation();
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

        if (interacting) {
            image = interactList.get(interactSpriteNum);
        } else if (type == type_gate) {
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
        } else {
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (inCamera()) {
                if (iframe) {
                    hpBarVisible = true;
                    hpBarCounter = 0;
                    UtilityTool.changeAlpha(g2, 0.3f);
                }
                if (dead) {
                    dyingAnimation(g2);
                }
                if (!attacking) {
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



