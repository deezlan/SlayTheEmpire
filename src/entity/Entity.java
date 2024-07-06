package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity {
    GamePanel gp;
    public int actionLockCounter;
    public int worldX, worldY;
    public Projectile projectile;

    // HIT DETECTION
    boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public boolean iframe = false;
    public int iframeCounter = 0;
    public boolean alive = true;
    public boolean dead = false;
    public boolean hpBarON = false;

    public int mobNum = 0;

//    public boolean knockBack = false;

    // TRACKING
    public boolean onPath = false;

    // PLAYER ATTRIBUTES
    public int defaultSpeed;
    public int speed;

    // ITEM ATTRIBUTES
    public int damage;
    public BufferedImage weaponSprite;
    public String name;
    public int price;
    public String description = "";
    public ArrayList<BufferedImage>
            currentActionList = new ArrayList<>(),
            idleRightList = new ArrayList<>(),
            idleLeftList = new ArrayList<>(),
            moveRightList = new ArrayList<>(),
            moveLeftList = new ArrayList<>(),
            playerRightAttackList = new ArrayList<>(),
            playerLeftAttackList = new ArrayList<>();
    public String action = "idleRight";
    public boolean lookingRight;
    public int type; // 0 = player 1 = monster
    public int spriteNum = 1;
//    public int weaponSpriteCounter = 0; // Ananda's old slash variables
//    public int weaponSpriteNum = 1; // " "

    // SuperObject Items
    public ArrayList<BufferedImage> defaultList = new ArrayList<>();
    public ArrayList<BufferedImage> interactList = new ArrayList<>();
    public String message;
    public boolean
            interacting = false,
            collision = false,
            isObject,
            knockBack = false;

    // COUNTERS
    public int interactSpriteCounter = 0, interactSpriteNum = 0;
    public int animationCounter = 0;
    public int animationSpriteNum = 0;
    public int spriteCounter = 0;
    public int interactionCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    int knockBackCounter = 0;
    String[] dialogs = new String[20];
    public void speak() {
        if (dialogs[interactionCounter] == null) interactionCounter = 0;
        gp.ui.currentDialog = dialogs[interactionCounter];
        interactionCounter++;
    }

    public void setAction(){}
    public void damageReaction(){}

    // entity's collision directions
    public boolean
            upCollisionOn = false,
            downCollisionOn = false,
            leftCollisionOn = false,
            rightCollisionOn = false;

    public int maxLife;
    public int life;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // draw area around entities

    public Entity(GamePanel gp) {
        this.gp = gp;
        lookingRight = true;
    }

    public int solidAreaDefaultX, solidAreaDefaultY;

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

        if (this.type == 2 && contactPlayer) {
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
            if (iframe) {
                iframeCounter++;
                if (iframeCounter > 30) {
                    iframe = false;
                    iframeCounter = 0;
                }
            }
        }
        // Animation speed
        spriteCounter++;
        if (this.currentActionList.size() > 14) {
            if (spriteCounter > 4) loopThroughSprites();
        } else if (this.currentActionList.size() > 7) {
            if (spriteCounter > 6) loopThroughSprites();
        } else {
            if (spriteCounter > 9) loopThroughSprites();
        }
    }

    public void startInteract(){
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
        } else if (interactSpriteCounter <= 75){
            interactSpriteNum = 0;
            interactSpriteCounter = 0;
            interacting = false;
        }
    }

    public void dyingAnimation(Graphics2D g2) { // BLINKING EFFECT
        dyingCounter++;

        if (dyingCounter <= 5){changeAlpha(g2,0f);}
        if (dyingCounter > 5 && dyingCounter <= 10){changeAlpha(g2,1f);}
        if (dyingCounter > 10 && dyingCounter <= 15){changeAlpha(g2,0f);}
        if (dyingCounter > 15 && dyingCounter <= 20){changeAlpha(g2,1f);}
        if (dyingCounter > 20 && dyingCounter <= 25){changeAlpha(g2,0f);}
        if (dyingCounter > 25 && dyingCounter <= 30){changeAlpha(g2,1f);}
        if (dyingCounter > 30 && dyingCounter <= 35){changeAlpha(g2,0f);}
        if (dyingCounter > 35 && dyingCounter <= 40){changeAlpha(g2,1f);}
        if (dyingCounter > 40) {
            dead = true;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x)/gp.TILE_SIZE;
        int startRow = (worldY + solidArea.y)/gp.TILE_SIZE;

        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);

        if (gp.pFinder.search()) {
            // NEXT WORLD X & Y
            int nextX = gp.pFinder.pathList.get(0).col * gp.TILE_SIZE;
            int nextY = gp.pFinder.pathList.get(0).row * gp.TILE_SIZE;

            // ENTITY SOLID AREA POSITION
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.TILE_SIZE){
                action = "moveUp";
                currentActionList = moveRightList;
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.TILE_SIZE){
                action = "moveDown";
                currentActionList = moveLeftList;
            } else if (enTopY >= nextY && enBottomY < nextY + gp.TILE_SIZE){
                if (enLeftX > nextX){
                    action = "moveLeft";
                    currentActionList = moveLeftList;
                }
                if(enLeftX < nextX){
                    action = "moveRight";
                    currentActionList = moveRightList;
                }
            }
            else if (enTopY > nextY && enLeftX > nextX){
                action = "moveUp";
                checkCollision();
                if(upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn){ // check
                    action = "moveLeft";
                    currentActionList = moveLeftList;
                }
            }
            else if (enTopY > nextY && enLeftX < nextX){
                action = "moveUp";
                if(upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn){ // check
                    action = "moveRight";
                    currentActionList = moveRightList;
                }
            }
            else if (enTopY < nextY && enLeftX > nextX){
                action = "moveDown";
                checkCollision(); // check
                if(upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn){
                    action = "moveLeft";
                    currentActionList = moveLeftList;
                }
            }
            else if (enTopY < nextY && enLeftX < nextX){
                action = "moveDown";
                checkCollision(); // check
                if(upCollisionOn && downCollisionOn && leftCollisionOn && rightCollisionOn){
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
            return; // Do not draw if the entity is not alive
        }
        if (interacting) {image = interactList.get(interactSpriteNum);} else {image = currentActionList.get(spriteNum - 1);}
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY; // Corrected worldY subtraction

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX - 48*4 && // added values due to player sprite not centered
                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX + 48*4 &&
                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY - 48*2 &&
                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY + 48*2)
        {
            // MONSTER HP BAR
            if(type == 2 && hpBarON) {
                double oneScale = (double)gp.TILE_SIZE/maxLife;
                double hpBarValue = oneScale*life;
                if (mobNum == 1){ // SLIME
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+51,screenY+121 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+50,screenY + 120, (int)hpBarValue,9);
                }
                if (mobNum == 2){ // SKELLINGTON
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+51,screenY+141 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+50,screenY + 140, (int)hpBarValue,9);
                }
                if (mobNum == 3){ // ROBOT GUARDIAN
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+81,screenY+161 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+80,screenY + 160, (int)hpBarValue,9);
                }
                if (mobNum == 4){ // RAMSES
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+61,screenY+121 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+60,screenY + 120, (int)hpBarValue,9);
                }
                if (mobNum == 5){ // GOBLIN
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+61,screenY+131 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+60,screenY + 130, (int)hpBarValue,9);
                }
                if (mobNum == 7){ // ARMORED GUARDIAN
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+51,screenY+121 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+50,screenY + 120, (int)hpBarValue,9);
                }
                if (mobNum == 8){ // FLYING EYE
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+141,screenY+191 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+140,screenY + 190, (int)hpBarValue,9);
                }
                if (mobNum == 9){ // MUSHROOM
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+126,screenY+211 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+125,screenY + 210, (int)hpBarValue,9);
                }
                if (mobNum == 10){ // CANINE
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(screenX+21,screenY+91 , gp.TILE_SIZE,10);
                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX+20,screenY + 90, (int)hpBarValue,9);
                }

                hpBarCounter++;
                if(hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarON = false;
                }
            }
            if(iframe){
                hpBarON = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.3f);
            }
            if(dead){
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX, screenY, null);
            changeAlpha(g2, 1f);
        }
    }
}



