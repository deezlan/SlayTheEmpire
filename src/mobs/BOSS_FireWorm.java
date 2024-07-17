package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Fireball;

import java.io.IOException;

public class BOSS_FireWorm extends Entity {
    GamePanel gp;
    public static final String monName = "Lex";
    public BOSS_FireWorm(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        name = monName;
        type = type_mob;
        boss = true;
        bossNum = 6;
        defaultSpeed = 1;
        speed = defaultSpeed;
        attack = 1;
        maxLife = 10;
        currentLife = maxLife;
        action = "idleRight";
        damageSprite = 9;
        projectile = new OBJ_Fireball(gp);
        sleep = true;

        // Load mob sprites
        getMobSprites();
        setDialog();

        // Set collision settings
        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = 60;
        solidArea.height = 80;
        attackArea.width = 140;
        attackArea.height = 140;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        dialogueSet = 0;
    }


    @Override
    public void setAction() {

        if(!inRage && currentLife < maxLife/2) {
            inRage = true;
            defaultSpeed++;
            speed = defaultSpeed;
            attack = 2;
        }

        if(onPath) {
            // CHECK IF STOP CHASING
            checkStopChase(gp.player, 15, 100);
            // SEARCH DIRECTION TO GO
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));
        } else {
            // CHECK IF START CHASING
            checkStartChase(gp.player, 5 , 100);
        }
        // CHECK ATTACK ON PLAYER
        if(!attacking){
            checkWithinAttackRange(30,gp.TILE_SIZE*6,gp.TILE_SIZE*6); // Original
//            checkWithinAttackRange(30); // CHANGE ATTACK RANGE
        }
    }

    public void setDialog() {
        dialogs[0][0] = "Who are you.....";
        dialogs[0][1] = "YOU WILL PAY... WITH ICE!";

    }

    public void getMobSprites() {
        String dir = "/Mobs/FireWorm/";
        try {
            for (int i = 0; i <= 8; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 8; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 15; i++) {
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 150, 150);
            UtilityTool.scaleEntityList(this,moveLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 150, 150);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 150, 150);
            UtilityTool.scaleEntityList(this,idleLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, idleRightList, 150, 150);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}