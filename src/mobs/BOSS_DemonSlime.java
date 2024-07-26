package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_DemonBlast;

import java.io.IOException;

public class BOSS_DemonSlime extends Entity {
    int specialAttackCounter;
    GamePanel gp;
    public static final String monName = "Demon Slime";
    public BOSS_DemonSlime(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        name = monName;
        type = type_mob;
        setStatValues(1, 10, true, 3, 500);
        setCollisionValues(190, 180, 50, 100);
        setAttackValues(15, 8, gp.TILE_SIZE * 4, gp.TILE_SIZE * 4, false);
        setHitboxValues(48, 80, 200, 250);

        // Load mob sprites
        getMobSprites();

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

    void attackCheck(){
        if (specialAttackCounter >= 20){
            speed = defaultSpeed;
        }
        if (specialAttackCounter >= 150 & inCamera() & onPath){
            specialAttacking = true;
            specialAttackCounter = 0;
            spriteCounter = 0;
            spriteNum = 1;
            checkShoot(0,24,144,0);
        } else if (!attacking & !specialAttacking){
//            checkWithinAttackRange(30,gp.TILE_SIZE*6,gp.TILE_SIZE*6); // Original
            checkWithinAttackRange(30); // CHANGE ATTACK RANGE
        }
    }

    @Override
    public void specialAttack(){
        speed = 0;
        currentList = mobSpecialAttackList;

        runSpecialAttackAnimation();
    }

    @Override
    public void checkShoot(int rate, int xOffset, int yOffset, int shotInterval){
        projectile.set(worldX + (xOffset), worldY + (yOffset), action, true, this, gp.player.worldX, gp.player.worldY);
        for (int ii = 0; ii < gp.projectileArr[1].length; ii++) {
            if (gp.projectileArr[gp.currentMap][ii] == null) {
                gp.projectileArr[gp.currentMap][ii] = projectile;
                break;
            }
        }
    }

    public void getMobSprites() {
        String dir = "/Mobs/DemonSlime/";
        try {
            for (int i = 0; i <= 11; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 5; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 14; i++) {
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 1; i <= 7; i++) {
                mobSpecialAttackList.add(i-1, UtilityTool.loadSprite("/Mobs/DemonSlime/DemonBlast/" + i + ".png", "Missing demon " + i));
            }

            UtilityTool.scaleEntityList(this, mobSpecialAttackList, 450, 300);
            UtilityTool.scaleEntityList(this, moveRightList, 450, 300);
            UtilityTool.scaleEntityList(this,moveLeftList, 450, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 450, 300);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 450, 300);
            UtilityTool.scaleEntityList(this,idleLeftList, 450, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 450, 300);


        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}