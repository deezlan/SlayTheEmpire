package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_PickUpCoin;
import object.OBJ_Rock;

import java.io.IOException;
import java.util.Random;

public class MOB_FlyingEye extends Entity {
    GamePanel gp;
    public MOB_FlyingEye(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_mob;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        currentLife = maxLife;
        action = "idleRight";
        mobNum = 8;
        projectile = new OBJ_Rock(gp);

        // Load mob sprites
        getMobSprites();

        // Set collision settings
        solidArea.x = 150;
        solidArea.y = 170;
        solidArea.width = 28;
        solidArea.height = 30;
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

            checkShoot(200,0,100,0);
        } else {
            // CHECK IF START CHASING
            checkStartChase(gp.player, 5 , 100);
            // GET RANDOM DIRECTION
            getRandomDirection();
        }
        // CHECK ATTACK ON PLAYER
        if(!attacking){
            checkWithinAttackRange(30,gp.TILE_SIZE*5,gp.TILE_SIZE*2); // CHANGE ATTACK RANGE
            checkShoot(200,0,100,0);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/FlyingEye/";
        try {
            for (int i = 0; i <= 7; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 300, 300);
            UtilityTool.scaleEntityList(this, moveLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 300, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 300, 300);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}