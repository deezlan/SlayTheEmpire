package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_RobotGuardian extends Entity {
    GamePanel gp;
    public MOB_RobotGuardian(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 6, false, false, 3);
        setCollisionValues(70, 90, 40, 40);
        setAttackValues(1, 7, 60, 60);

        // Load mob sprites
        getMobSprites();
    }

//    public void setAction() {
//        if(onPath) {
//            // SEARCH DIRECTION TO GO
//            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
//        } else {
//            // CHECK IF START CHASING
//            checkStartChase(gp.player, 5 , 100);
//            // GET RANDOM DIRECTION
//            getRandomDirection();
//        }
//        // CHECK ATTACK ON PLAYER
//        if(!attacking){
//            checkWithinAttackRange(30,gp.TILE_SIZE*2,gp.TILE_SIZE*2); // CHANGE ATTACK RANGE
//        }
//    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/RobotGuardian/";
        try {
            for (int i = 0; i <= 7; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 5; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 9; i++) {
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 200, 200);
            UtilityTool.scaleEntityList(this,moveLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 200, 200);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 200, 200);
            UtilityTool.scaleEntityList(this,idleLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, idleRightList, 200, 200);

            System.out.println("Robot Guardian sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}