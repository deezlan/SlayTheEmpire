package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private final Cursor cursor;
//    public SwordSlash slash;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH, Cursor cursor) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2); // added screen position
        screenY = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);

        this.cursor = cursor;
        setDefaultValues();
        getPlayerSprites();
        getPlayerAttackImage();
        getPlayerAttackAnimation();

        setItems();

        solidArea = new Rectangle(); // draws a square at the centre of the player
        solidArea.x = 48; // position of actual collision square
        solidArea.y = 50;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 50; // outer area of collision square
        solidArea.height = 35;

//        SwordSlash slash1 = new SwordSlash(gp);
//        slash = slash1;
    }


    public void setDefaultValues() {
        worldX = 340; // Player spawn location x
        worldY = 60; // player spawn location y
        speed = 3;
        action = "idleRight";
        lookingRight = true;
        direction = "right";

        //Status
        maxLife = 6;
        life = maxLife;
    }

    public void setItems() {
        //add inventory
    }

    public void update() {
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
                interactMerchant(npcIndex);

                //CHECK MOB COLLISION
                int mobIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                interactMob(mobIndex);

                //CHECK EVENT
                gp.eHandler.checkEvent();

                switch (action) {
                    case "moveUp", "moveDown":
                        currentActionList = lookingRight ? moveRightList : moveLeftList;
                        currentAnimationList = lookingRight ? playerRightAttackList : playerLeftAttackList;
                        break;
                    case "moveLeft", "moveUpLeft", "moveDownLeft":
                        currentActionList = moveLeftList;
                        currentAnimationList = playerLeftAttackList;
                        lookingRight = false;
                        break;
                    case "moveRight", "moveUpRight", "moveDownRight":
                        currentActionList = moveRightList;
                        currentAnimationList = playerRightAttackList;
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
        if (index != 999) {
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
        }

    public void interactMerchant(int index){
        switch (index) {
            case 999:
                break;
            case 2:
                gp.gameState = gp.shopState;
                break;
            default:
                gp.gameState = gp.dialogueState; gp.npcArr[index].speak();
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

//        int tempScreenX = screenX;
//        int tempScreenY = screenY;

        if (spriteNum > currentActionList.size() - 1)
            spriteNum = 1;
        BufferedImage image = currentActionList.get(spriteNum - 1);

        if (animationSpriteNum > currentAnimationList.size())
            animationSpriteNum = 0;
        BufferedImage animationImage = currentAnimationList.get(animationSpriteNum);

        if (weaponSpriteNum > weaponList.size())
            weaponSpriteNum = 0;
        BufferedImage weaponImage = weaponList.get(weaponSpriteNum);

        if (gp.gameArea == 0) {
            if (!attacking){
                g2.drawImage(image, worldX, worldY, null);
            } if (attacking){
                g2.drawImage(animationImage, worldX, worldY,null);
                g2.drawImage(weaponImage, worldX + 40, worldY, null);
            }
        } else if (gp.gameArea == 1){ // current game area
            if (!attacking){
                if(iframe){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                }
                g2.drawImage(image, worldX, worldY, null);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            } if (attacking){
                g2.drawImage(animationImage, worldX, worldY,null); // draw attack animation
            }
        } else {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*2, null);
            if (attacking){
                g2.drawImage(weaponImage, worldX + 40, worldY, null);
            }
        }
        // draw arrow
        cursor.draw(g2, (int) (worldX + gp.TILE_SIZE * 1.5), worldY + gp.TILE_SIZE);
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

    public void getPlayerAttackAnimation() {
        String dir = "/player/Warrior/";
        try {
            playerRightAttackList.add(0, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_0.png", "Missing RAttack Animation 0"));
            playerRightAttackList.add(1, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_1.png", "Missing RAttack Animation 1"));
            playerRightAttackList.add(2, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_2.png", "Missing RAttack Animation 2"));
            playerRightAttackList.add(3, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_3.png", "Missing RAttack Animation 3"));
            playerRightAttackList.add(4, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_4.png", "Missing RAttack Animation 4"));
            playerRightAttackList.add(5, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_5.png", "Missing RAttack Animation 5"));
            playerRightAttackList.add(6, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_6.png", "Missing RAttack Animation 6"));
            playerRightAttackList.add(7, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_7.png", "Missing RAttack Animation 7"));
            playerRightAttackList.add(8, UtilityTool.loadSprite(dir + "Warrior_Attack_Right/Warrior_Attack_Right_8.png", "Missing RAttack Animation 8"));
            UtilityTool.scaleEntityList(this, playerRightAttackList, 144, 96);

            playerLeftAttackList.add(0, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_0.png", "Missing LAttack Animation 0"));
            playerLeftAttackList.add(1, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_1.png", "Missing LAttack Animation 1"));
            playerLeftAttackList.add(2, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_2.png", "Missing LAttack Animation 2"));
            playerLeftAttackList.add(3, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_3.png", "Missing LAttack Animation 3"));
            playerLeftAttackList.add(4, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_4.png", "Missing LAttack Animation 4"));
            playerLeftAttackList.add(5, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_5.png", "Missing LAttack Animation 5"));
            playerLeftAttackList.add(6, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_6.png", "Missing LAttack Animation 6"));
            playerLeftAttackList.add(7, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_7.png", "Missing LAttack Animation 7"));
            playerLeftAttackList.add(8, UtilityTool.loadSprite(dir + "Warrior_Attack_Left/Warrior_Attack_Left_8.png", "Missing LAttack Animation 8"));
            UtilityTool.scaleEntityList(this, playerLeftAttackList, 144, 96);
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
