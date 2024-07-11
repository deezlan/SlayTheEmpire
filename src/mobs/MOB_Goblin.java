package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_PickUpCoin;

import java.io.IOException;
import java.util.Random;

public class MOB_Goblin extends Entity {
    GamePanel gp;
    public MOB_Goblin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_mob;
        defaultSpeed = 1;
        attack = 1;
        speed = defaultSpeed;
        maxLife = 4;
        currentLife = maxLife;
        action = "idleRight";
        mobNum = 5;
        damageSprite = 4;

        // Load mob sprites
        getMobSprites();

        // Set collision settings
        solidArea.x = 98;
        solidArea.y = 120;
        solidArea.width = 28;
        solidArea.height = 30;
        attackArea.width = 50;
        attackArea.height = 50;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void checkDrop() {
        dropItem(new OBJ_PickUpCoin(gp));
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
            checkWithinAttackRange(30,gp.TILE_SIZE*2,gp.TILE_SIZE*2); // CHANGE ATTACK RANGE
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/Goblin/";
        try {
            for (int i = 0; i <= 7; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 3; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 200, 200);
            UtilityTool.scaleEntityList(this,moveLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 200, 180);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 200, 200);
            UtilityTool.scaleEntityList(this,idleLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, idleRightList, 200, 200);

            System.out.println("Goblin sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}