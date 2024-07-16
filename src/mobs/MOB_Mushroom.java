package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Mushroom extends Entity {
    GamePanel gp;
    public MOB_Mushroom(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, false, 9);
        setCollisionValues(150, 150, 28, 40);
        setAttackValues(1, 8, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
//        type = type_mob;
//        defaultSpeed = 1;
//        speed = defaultSpeed;
//        attack = 1;
//        maxLife = 4;
//        currentLife = maxLife;
//        lookingRight = true;
//        action = "idleRight";
//        mobNum = 9;
//        damageSprite = 8;

        // Load mob sprites
        getMobSprites();

        // Set collision settings
//        solidArea = new Rectangle();
//        solidArea.x = 150;
//        solidArea.y = 150;
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;
//        solidArea.width = 28;
//        solidArea.height = 40;
//        attackArea.width = gp.TILE_SIZE*2;
//        attackArea.height = gp.TILE_SIZE*2;
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
            checkWithinAttackRange(30,gp.TILE_SIZE*3,gp.TILE_SIZE*3);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
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
            for (int i = 0; i <= 7; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing attackRight " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing attackLeft " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 300, 300);
            UtilityTool.scaleEntityList(this,moveLeftList, 300, 300);
            UtilityTool.scaleEntityList(this,idleLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 300, 300);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 300, 300);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}