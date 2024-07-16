package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Ramses extends Entity {
    GamePanel gp;
    public MOB_Ramses(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, false, 4);
        setCollisionValues(58, 98, 50, 30);
        setAttackValues(1, 3, 50, 50);
//        type = type_mob;
//        defaultSpeed = 1;
//        speed = defaultSpeed;
//        damage = 1;
//        maxLife = 4;
//        currentLife = maxLife;
//        action = "idleRight";
//        mobNum = 4;

        // Load mob sprites
        getMobSprites();

        // Set collision settings
//        solidArea.x = 58;
//        solidArea.y = gp.TILE_SIZE + 50;
//        solidArea.width = 50;
//        solidArea.height = 30;
//        solidAreaDefaultX = solidArea.x;
//        solidAreaDefaultY = solidArea.y;
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
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/Ramses/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 100, 100);
            UtilityTool.scaleEntityList(this,moveLeftList, 100, 100);
            UtilityTool.scaleEntityList(this,idleLeftList, 100, 100);
            UtilityTool.scaleEntityList(this, idleRightList, 100, 100);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}