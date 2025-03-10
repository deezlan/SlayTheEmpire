package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Energyball;
import object.OBJ_PickUpCoin;

import java.io.IOException;
public class MOB_ArmoredGuardian extends Entity {
    GamePanel gp;
    public MOB_ArmoredGuardian(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_mob;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        currentLife = maxLife;
        action = "idleRight";
        mobNum = 7;
        projectile1 = new OBJ_Energyball(gp);

        // Load mob sprites
        getMobSprites();

        // Set collision settings
        solidArea.x = 58;
        solidArea.y = gp.TILE_SIZE + 50;
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

            checkShoot(200,0,50,0);
        } else {
            // CHECK IF START CHASING
            checkStartChase(gp.player, 5 , 100);
            // GET RANDOM DIRECTION
            getRandomDirection();
        }
        // CHECK ATTACK ON PLAYER
        if(!attacking){
            checkWithinAttackRange(30,gp.TILE_SIZE*4,gp.TILE_SIZE*2);
            checkShoot(200,0,50,0);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/ArmoredGuardian/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }
            for (int i = 0; i < 4; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing Guardian Attack Animation " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing  Guardian Attack Animation " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 150, 150);
            UtilityTool.scaleEntityList(this,moveLeftList, 150, 150);
            UtilityTool.scaleEntityList(this,idleLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, idleRightList, 150, 150);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 150, 150);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 150, 150);
            System.out.println("ATTACK is LOADED!");

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}


