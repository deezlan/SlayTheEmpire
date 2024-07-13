package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_PickUpCoin;

import java.io.IOException;

public class MOB_Canine extends Entity {
    GamePanel gp;
    public MOB_Canine(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        type = type_mob;
        defaultSpeed = 2;
        speed = defaultSpeed;
        attack = 1;
        maxLife = 4;
        currentLife = maxLife;
        action = "idleRight";
        mobNum = 10;
        damageSprite = 3;

        // Load mob sprites
        getMobSprites();

        // Set collision settings
        solidArea.x = 30;
        solidArea.y = 50;
        solidArea.width = 40;
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
        String dir = "/Mobs/Canine/";
        try {
            for (int i = 0; i <= 5; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 3; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 6; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleLeft " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 80, 80);
            UtilityTool.scaleEntityList(this,moveLeftList, 80, 80);
            UtilityTool.scaleEntityList(this,idleLeftList, 80, 80);
            UtilityTool.scaleEntityList(this, idleRightList, 80, 80);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 80, 80);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 80, 80);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}