package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity {
    GamePanel gp;
    public int actionLockCounter;
    public int worldX, worldY;
    public int speed;

    public Projectile projectile;

    // HIT DETECTION
    boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public boolean iframe = false;
    public int iframeCounter = 0;
    public boolean alive = true;
    public boolean dead = false;

    int dyingCounter = 0;

    //Item Attributes
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

    public int spriteCounter = 0;
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
            isObject;

    public int interactSpriteCounter = 0, interactSpriteNum = 0;
    public int animationCounter = 0;
    public int animationSpriteNum = 0;

    public int interactionCounter = 0;
    String[] dialogs = new String[20];
    public void speak() {
        if (dialogs[interactionCounter] == null) interactionCounter = 0;
        gp.ui.currentDialog = dialogs[interactionCounter];
        interactionCounter++;
    }

    public void setAction(){}

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

    // Ananda's old slash animation
//    public void loopThroughWeaponSprites() {
//        if (weaponSpriteCounter <= 5){
//            weaponSpriteNum = 0;
//        } else if (weaponSpriteCounter <= 10) {
//            weaponSpriteNum = 1;
//        } else if (weaponSpriteCounter <= 15) {
//            weaponSpriteNum = 2;
//        } else if (weaponSpriteCounter <= 20) {
//            weaponSpriteNum = 3;
//        } else if (weaponSpriteCounter <= 25) {
//            weaponSpriteNum = 4;
//        } else if (weaponSpriteCounter <= 30) {
//            weaponSpriteNum = 5;
//        } else if (weaponSpriteCounter <= 35) {
//            weaponSpriteNum = 0;
//            weaponSpriteCounter = 0;
//            attacking = false;
//        }
//    }

    public void update() {
        if (interacting) {
            startInteract();
        } else {
            upCollisionOn = false; // resets collisions off
            downCollisionOn = false;
            leftCollisionOn = false;
            rightCollisionOn = false;
            gp.cChecker.checkObject(this, false);
            gp.cChecker.checkPLayer(this);
            gp.cChecker.checkTile(this);
            gp.cChecker.checkEntityCollision(this, gp.npcArr);
            setAction();

            boolean contactPlayer = gp.cChecker.checkPLayer(this);

            if (this.type == 2 && contactPlayer) {
                if (!gp.player.iframe) {
                    gp.player.life -= 1;
                    gp.player.iframe = true;
                }
            }
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
            if (spriteCounter > 5) loopThroughSprites();
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


    public void draw(Graphics2D g2) {
        BufferedImage image;
        if (!alive) {
            return; // Do not draw if the entity is not alive
        }
        if (interacting) {
            image = interactList.get(interactSpriteNum);
        } else {
            image = currentActionList.get(spriteNum - 1);
        }
        switch (gp.gameArea) {
            case 0, 1:
                if(iframe){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                }
                if(dead){
                    dyingAnimation(g2);
                }
                g2.drawImage(image, worldX, worldY, null);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                break;
            case 2:
                break;
            default:
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY; // Corrected worldY subtraction

                if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                        worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                        worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                        worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY)
                {
                    if(iframe){
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                    }
                    if(dead){
                        dyingAnimation(g2);
                    }
                    g2.drawImage(image, screenX, screenY, null);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
        }
    }
}

