package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Slime extends Entity {
    GamePanel gp;
    public MOB_Slime(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, false, 1);
        setCollisionValues(58, 98, 28, 30);
        setAttackValues(1, 2, 50, 30);
//        type = type_mob;
//        defaultSpeed = 1;
//        speed = defaultSpeed;
//        maxLife = 4;
//        currentLife = maxLife;
//        attack = 1;
//        lookingRight = true;
//        action = "idleRight";
//        mobNum = 1;
//        damageSprite = 2;

        // Load mob sprites
        getMobSprites();

        // Set collision settings
//        solidArea.x = 58;
//        solidArea.y = gp.TILE_SIZE + 50;
//        solidArea.width = 28;
//        solidArea.height = 30;
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;
//        attackArea.width = 50;
//        attackArea.height = 30;
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
            checkWithinAttackRange(30,gp.TILE_SIZE*2,gp.TILE_SIZE*2);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/Slime/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i < 4; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attack/" + i + ".png", "Missing Attack Animation " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attack/" + i + ".png", "Missing Attack Animation " + i));
                System.out.println("ATTACK is LOADED!");
            }

            UtilityTool.scaleEntityList(this, moveRightList, 150, 150);
            UtilityTool.scaleEntityList(this,moveLeftList, 150, 150);
            UtilityTool.scaleEntityList(this,idleLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, idleRightList, 150, 150);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 150, 150);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 150, 150);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}