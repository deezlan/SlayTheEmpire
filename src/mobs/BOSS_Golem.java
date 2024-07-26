package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Energyball;

import java.io.IOException;

public class BOSS_Golem extends Entity {
    GamePanel gp;
    public static final String monName = "Golem";
    public BOSS_Golem(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        name = monName;
        setStatValues(1, 10, true, 4, 500);
        setCollisionValues(190, 140, 50, 100);
        setAttackValues(15, 6, gp.TILE_SIZE * 4, gp.TILE_SIZE * 4, false);
        setHitboxValues(48, 25, 200, 250);

        // Load mob sprites
        getMobSprites();
        setDialog();
        dialogueSet = 0;

        attRangeHorz = gp.TILE_SIZE * 4;
        attRangeVert = gp.TILE_SIZE * 4;

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

        if (onPath) {
            // SEARCH DIRECTION TO GO
            searchPath(getGoalCol(gp.player),getGoalRow(gp.player));

            if (hasRanged) {
                checkShoot(200, idleRightList.get(0).getWidth()/2, idleRightList.get(0).getHeight()/2, 0);
            }

        } else {
            // CHECK IF START CHASING
            if (!sleep)
                checkStartChase(gp.player, 10, 100);
        }
        // CHECK ATTACK ON PLAYER
        if (!attacking) {
            if (hasRanged) {
                checkWithinAttackRange(30); // CHANGE ATTACK RANGE
                checkShoot(200, idleRightList.get(0).getWidth()/2, idleRightList.get(0).getHeight()/2, 0);
            } else {
                checkWithinAttackRange(30); // CHANGE ATTACK RANGE
            }
        }
    }

    public void setDialog() {
        dialogs[0][0] = "Who are you.....";
        dialogs[0][1] = "YOU WILL PAY... WITH ICE!";
    }

    public void getMobSprites() {
        String dir = "/Mobs/Golem/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 7; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 6; i++) {
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 300, 300);
            UtilityTool.scaleEntityList(this,moveLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 300, 300);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 300, 300);
            UtilityTool.scaleEntityList(this,idleLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 300, 300);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}