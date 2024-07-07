package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class MOB_Mushroom extends Entity {
    GamePanel gp;
    public MOB_Mushroom(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 1;
        defaultSpeed = 1;
        speed = defaultSpeed;
        attack = 2;
        maxLife = 4;
        life = maxLife;
        lookingRight = true;
        action = "idleRight";
        mobNum = 9;

        // Load mob sprites
        getMobSprites();
        getAttackAnimation();

        // Set collision settings
        solidArea = new Rectangle();
        solidArea.x = 150;
        solidArea.y = 170;
        solidArea.width = 28;
        solidArea.height = 30;
        attackArea.width = 100;
        attackArea.height = 100;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void setAction() {

        if(onPath) {
            // CHECK IF STOP CHASING
            checkStopChase(gp.player, 15, 100);
            // SEARCH DIRECTION TO GO
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        } else {
            // CHECK IF START CHASING
            checkStartChase(gp.player, 5 , 100);
            // GET RANDOM DIRECTION
            getRandomDirection();
        }
        // CHECK ATTACK ON PLAYER
        if(!attacking){
            checkMobAttack(30,gp.TILE_SIZE*4,gp.TILE_SIZE*3);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getAttackAnimation() {
        String dir = "/Mobs/Mushroom/";
        try {
            // Load sprites for attacking
            for (int i = 0; i <= 7; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attack/" + i + ".png", "Missing attackLeft " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attack/" + i + ".png", "Missing attackLeft " + i));
            }

            // Scale sprites up
            UtilityTool.scaleEntityList(this, mobRightAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 300, 300);
        } catch (IOException e){
            e.printStackTrace(System.out);
        }
    }

    public void getMobSprites() {
        String dir = "/Mobs/Mushroom/";
        try {
            for (int i = 0; i <= 7; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }
            for (int i = 0; i <= 3; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 300, 300);
            UtilityTool.scaleEntityList(this,moveLeftList, 300, 300);
            UtilityTool.scaleEntityList(this,idleLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 300, 300);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}