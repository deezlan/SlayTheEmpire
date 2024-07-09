package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public int actionLockCounter;
    public int worldX, worldY;

    // ENTITY ATTRIBUTES
    public int defaultSpeed;
    public int speed;
    public boolean lookingRight;
    public int type; // 0 = player 1 = monster
    public int mobNum = 0; // FOR HP BAR
    public int attack;
    public int maxLife;
    public int life;
    public int damageSprite;

    public int solidAreaDefaultX, solidAreaDefaultY;
    public ArrayList<BufferedImage>
            currentActionList = new ArrayList<>(),
            idleRightList = new ArrayList<>(),
            idleLeftList = new ArrayList<>(),
            moveRightList = new ArrayList<>(),
            moveLeftList = new ArrayList<>(),
            playerRightAttackList = new ArrayList<>(),
            playerLeftAttackList = new ArrayList<>(),
            mobRightAttackList = new ArrayList<>(),
            mobLeftAttackList = new ArrayList<>(),
            projectileRight = new ArrayList<>(),
            projectileLeft = new ArrayList<>(),
            projectileUp = new ArrayList<>(),
            projectileDown = new ArrayList<>();

    public String[][] dialogs = new String[20][20];
    public int dialogueSet = 0;
    public int dialogueIndex = 0;

    public void speak() {}

    public void setAction() {}

    public void startDialogue(Entity entity, int setNum) {
        gp.gameState = gp.dialogueState;
        gp.ui.npc = entity;
        dialogueSet = setNum;
    }
    public void damageReaction() {
    }

    // HIT DETECTION
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean iframe = false;
    public int iframeCounter = 0;
    public boolean alive = true;
    public boolean dead = false;
    public boolean hpBarON = false;

    // PROJECTILES
    public int shotAvailableCounter = 0;
    public Projectile projectile;

    // KNOCK-BACK
    public Entity attacker;
    public String knockBackDirection;
    public boolean knockBack = false;

    // TRACKING
    public boolean onPath = false;

    public int getXdistance(Entity target) {
        return Math.abs(worldX - target.worldX);
    }

    public int getYdistance(Entity target) {
        return Math.abs(worldY - target.worldY);
    }

    public int getTileDistance(Entity target) {
        return (getXdistance(target) + getYdistance(target)) / gp.TILE_SIZE;
    }

    public int getGoalCol(Entity target) {
        return (target.worldX + target.solidArea.x) / gp.TILE_SIZE;
    }

    public int getGoalRow(Entity target) {
        return (target.worldY + target.solidArea.y) / gp.TILE_SIZE;
    }

    // ITEM ATTRIBUTES
    public int damage;
    public BufferedImage weaponSprite;
    public String name;
    public int price;
    public String description = "";

    public String action = "idleRight";
    public int spriteNum = 1;

    // OBJECTS
    public ArrayList<BufferedImage> defaultList = new ArrayList<>();
    public ArrayList<BufferedImage> interactList = new ArrayList<>();
    public String message;
    public boolean
            interacting = false,
            collision = false,
            isObject;

    // COUNTERS
    public int interactSpriteCounter = 0, interactSpriteNum = 0;
    public int animationCounter = 0;
    public int animationSpriteNum = 0;
    public int spriteCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;

    // ENTITY COLLISION DIRECTION
    public boolean upCollisionOn = false, downCollisionOn = false, leftCollisionOn = false, rightCollisionOn = false;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities

    public Entity(GamePanel gp) {
        this.gp = gp;
        lookingRight = true;
    }

    public void loopThroughSprites() {
        spriteNum = (spriteNum < currentActionList.size()) ? spriteNum + 1 : 1;
        spriteCounter = 0;
    }

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
                gp.player.life -= 1;
                gp.player.iframe = true;
            }
        }
    }

    public void update() {
        if (interacting) {
            startInteract();
        } else {
            if (knockBack) {
                checkCollision();
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                } else if (!upCollisionOn && !downCollisionOn && !leftCollisionOn && !rightCollisionOn) {
                    switch (knockBackDirection) {
                        case "moveUp":
                            worldY -= speed;
                            break;
                        case "moveDown":
                            worldY += speed;
                            break;
                        case "moveRight":
                            worldX += speed;
                            break;
                        case "moveLeft":
                            worldX -= speed;
                            break;
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

                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                }
            } else if (attacking) {
                attackAnimation();
            } else {
                setAction();
                checkCollision();

                if (!upCollisionOn && !downCollisionOn && !leftCollisionOn && !rightCollisionOn) {
                    switch (action) {
                        case "moveUp":
                            worldY -= speed;
                            break;
                        case "moveDown":
                            worldY += speed;
                            break;
                        case "moveRight":
                            worldX += speed;
                            break;
                        case "moveLeft":
                            worldX -= speed;
                            break;
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
                if (this.currentActionList.size() > 28) {
                    if (spriteCounter > 4) loopThroughSprites();
                } else if (this.currentActionList.size() > 21) {
                    if (spriteCounter > 6) loopThroughSprites();
                } else if (this.currentActionList.size() > 14) {
                    if (spriteCounter > 9) loopThroughSprites();
                }  else if (this.currentActionList.size() > 7) {
                    if (spriteCounter > 11) loopThroughSprites();
                } else {
                    if(spriteCounter > 13) loopThroughSprites();
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

    public void checkStartChase(Entity target, int distance, int rate) {
        if (getTileDistance(target) < distance) {
            int i = new Random().nextInt(rate);
            if (i == 0) {
                onPath = true;
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
    public void getRandomDirection() {
        actionLockCounter++;
        // GET A RANDOM DIRECTION
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(250) + 1;

            if (i <= 25) {
                action = "moveUp";
                currentActionList = moveRightList;
            }
            if (i > 25 && i <= 50) {
                action = "moveDown";
                currentActionList = moveLeftList;
            }
            if (i > 50 && i <= 75) {
                action = "moveLeft";
                currentActionList = moveLeftList;
            }
            if (i > 75 && i <= 100) {
                action = "moveRight";
                currentActionList = moveRightList;
            }
            if (i > 100 && i <= 125) {
                action = "idleRight";
                currentActionList = idleRightList;
            }
            if (i > 125 && i <= 150) {
                action = "idleLeft";
                currentActionList = idleLeftList;
            }
            if (i > 150 && i <= 175) {
                action = "moveUpRight";
                currentActionList = moveRightList;
            }
            if (i > 175 && i <= 200) {
                action = "moveDownRight";
                currentActionList = moveRightList;
            }
            if (i > 200 && i <= 225) {
                action = "moveUpLeft";
                currentActionList = moveLeftList;
            }
            if (i > 225) {
                action = "moveDownLeft";
                currentActionList = moveLeftList;
            }
            actionLockCounter = 0;
        }
    }

    public void checkShoot(int rate, int xOffset, int yOffset, int shotInterval) {
        int i = new Random().nextInt(rate);
        shotAvailableCounter = 0;
        if(i == 0 && !projectile.alive && shotAvailableCounter == shotInterval){
            projectile.set(worldX + (xOffset), worldY + (yOffset), action,true,this);
            for (int ii = 0; ii < gp.projectileList[1].length; ii++) {
                if(gp.projectileList[gp.currentMap][ii] == null){
                    gp.projectileList[gp.currentMap][ii] = projectile;
                    break;
                }
            }
        }
    }

    public void attackAnimation() { // animation attack
        switch (action) {
            case "moveUp", "moveDown":
                currentActionList = lookingRight ? mobRightAttackList : mobLeftAttackList; break;
            case "moveLeft":
                currentActionList = mobLeftAttackList; break;
            case "moveRight":
                currentActionList = mobRightAttackList; break;
        }

        if (animationSpriteNum == damageSprite) {
            // SAVE CURRENT DATA OF ENTITY
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // ADJUST FOR ATTACK
            switch (action) {
                case "moveUp":
                    worldY -= attackArea.height; break;
                case "moveDown":
                    worldY += attackArea.height; break;
                case "moveLeft":
                    worldX -= attackArea.width; break;
                case "moveRight":
                    worldX += attackArea.width; break;
            }

            // ATTACK AREA BECOMES SOLID AREA
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            if (type == 1) {
                if (gp.cChecker.checkPLayer(this))
                    damagePlayer(attack);
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

        animationCounter++;
        if (animationCounter <= 5) {
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
            animationSpriteNum = 0;
            animationCounter = 0;
            attacking = false;
        }
    }

    public void checkMobAttack(int rate, int straight, int horizontal) {
        boolean targetInRange = false;
        int xDis = getXdistance(gp.player);
        int yDis = getYdistance(gp.player);

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

    public void damagePlayer(int attack) {
        if (!gp.player.iframe) {
            int damage = attack;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.iframe = true;
        }
    }

    public void startInteract() {
        interactSpriteCounter++;
        loopThroughInteractSprites();
    }

    public void loopThroughInteractSprites() {
        if (interactSpriteCounter < 5) {
            interactSpriteNum = 0;
        } else if (interactSpriteCounter < 10) {
            interactSpriteNum = 1;
        } else if (interactSpriteCounter < 15) {
            interactSpriteNum = 2;
        } else if (interactSpriteCounter < 20) {
            interactSpriteNum = 3;
        } else if (interactSpriteCounter < 25) {
            interactSpriteNum = 4;
        } else if (interactSpriteCounter < 30) {
            interactSpriteNum = 5;
        } else if (interactSpriteCounter < 35) {
            interactSpriteNum = 6;
        } else if (interactSpriteCounter < 40) {
            interactSpriteNum = 7;
        } else if (interactSpriteCounter < 45) {
            interactSpriteNum = 8;
        } else if (interactSpriteCounter < 50) {
            interactSpriteNum = 9;
        } else if (interactSpriteCounter < 55) {
            interactSpriteNum = 10;
        } else if (interactSpriteCounter < 60) {
            interactSpriteNum = 11;
        } else if (interactSpriteCounter < 65) {
            interactSpriteNum = 12;
        } else if (interactSpriteCounter < 70) {
            interactSpriteNum = 13;
        } else if (interactSpriteCounter <= 75) {
            interactSpriteNum = 0;
            interactSpriteCounter = 0;
            interacting = false;
        }
    }

    public void dyingAnimation(Graphics2D g2) { // BLINKING EFFECT
        dyingCounter++;

        if (dyingCounter <= 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > 5 && dyingCounter <= 10) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 10 && dyingCounter <= 15) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > 15 && dyingCounter <= 20) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 20 && dyingCounter <= 25) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > 25 && dyingCounter <= 30) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 30 && dyingCounter <= 35) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > 35 && dyingCounter <= 40) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > 40) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
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
                currentActionList = moveRightList;
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.TILE_SIZE) {
                action = "moveDown";
                currentActionList = moveLeftList;
            } else if (enTopY >= nextY && enBottomY < nextY + gp.TILE_SIZE) {
                if (enLeftX > nextX) {
                    action = "moveLeft";
                    currentActionList = moveLeftList;
                }
                if (enLeftX < nextX) {
                    action = "moveRight";
                    currentActionList = moveRightList;
                }
            } else if (enTopY > nextY && enLeftX > nextX) {
                action = "moveUp";
                checkCollision();
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) { // check
                    action = "moveLeft";
                    currentActionList = moveLeftList;
                }
            } else if (enTopY > nextY && enLeftX < nextX) {
                action = "moveUp";
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) { // check
                    action = "moveRight";
                    currentActionList = moveRightList;
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                action = "moveDown";
                checkCollision(); // check
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) {
                    action = "moveLeft";
                    currentActionList = moveLeftList;
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                action = "moveDown";
                checkCollision(); // check
                if (upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn) {
                    action = "moveRight";
                    currentActionList = moveRightList;
                }
            }
            // IF REACH GOAL STOP
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//            if(nextCol == goalCol && nextRow == goalRow) {
//                onPath = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image;

        if (!alive) {
            return;
        }
        if (interacting) {
            image = interactList.get(interactSpriteNum);
        } else {
            image = currentActionList.get(spriteNum - 1);
        }

        if(gp.currentMap == 0) {
            if (iframe) {
                hpBarON = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.3f);
            }
            if (dead) {
                dyingAnimation(g2);
            }
            if(!attacking){
                g2.drawImage(image, worldX, worldY, null);
                changeAlpha(g2, 1f);
            }
            if (attacking) {
                if (animationSpriteNum >= currentActionList.size() - 1)
                    animationSpriteNum = 0;
                BufferedImage animationImage = currentActionList.get(animationSpriteNum);
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
                if (type == 1 && hpBarON) {
                    double oneScale = (double) gp.TILE_SIZE / maxLife;
                    double hpBarValue = oneScale * life;
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
                        hpBarON = false;
                    }
                }
                if (iframe) {
                    hpBarON = true;
                    hpBarCounter = 0;
                    changeAlpha(g2, 0.3f);
                }
                if (dead) {
                    dyingAnimation(g2);
                }
                if(!attacking){
                    g2.drawImage(image, screenX, screenY, null);
                    changeAlpha(g2, 1f);
                }
                if (attacking) {
                    if (animationSpriteNum >= currentActionList.size() - 1)
                        animationSpriteNum = 0;
                    BufferedImage animationImage = currentActionList.get(animationSpriteNum);
                    g2.drawImage(animationImage, screenX, screenY, null);
                }
            }
        }
    }
}



