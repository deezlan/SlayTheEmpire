package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class BOSS_FrostGiant extends Entity {
    GamePanel gp;
    int soundEffectInterval = 60;
    int soundEffectCounter = 0;
    public static final String monName = "Frost Giant";
    public BOSS_FrostGiant(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        name = monName;
        type = type_mob;
        boss = true;
        bossNum = 1;
        defaultSpeed = 1;
        speed = defaultSpeed;
        attack = 1;
        maxLife = 10;
        currentLife = maxLife;
        action = "idleRight";
        damageSprite = 7;
        sleep = true;

        // Load mob sprites
        getMobSprites();
        setDialog();

        // Set collision settings
        solidArea.x = 150;
        solidArea.y = 180;
        solidArea.width = 80;
        solidArea.height = 100;
        attackArea.width = 140;
        attackArea.height = 140;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        dialogueSet = 0;

        if (currentLife <= 0) {
            gp.playSE(10);
        }
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
            soundEffectCounter++;

            // FOOTSTEP SFX
            if (soundEffectCounter >= soundEffectInterval) {
                gp.playSE(18);
                soundEffectCounter = 0;
            }

        } else {
            // CHECK IF START CHASING
            checkStartChase(gp.player, 5 , 100);
        }
        // CHECK ATTACK ON PLAYER
        if(!attacking){
//            checkWithinAttackRange(30,gp.TILE_SIZE*6,gp.TILE_SIZE*6); // Original
            checkWithinAttackRange(30); // CHANGE ATTACK RANGE
        }
    }

    public void setDialog() {
        dialogs[0][0] = "Who are you.....";
        dialogs[0][1] = "YOU WILL PAY... WITH ICE!";

    }

    public void getMobSprites() {
        String dir = "/Mobs/FrostGiant/";
        try {
            for (int i = 0; i <= 9; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 5; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 13; i++) {
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 400, 300);
            UtilityTool.scaleEntityList(this,moveLeftList, 400, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 400, 300);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 400, 300);
            UtilityTool.scaleEntityList(this,idleLeftList, 400, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 400, 300);

            System.out.println("Robot Guardian sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}