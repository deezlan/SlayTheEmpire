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
    public BOSS_DemonSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = monName;
        type = type_mob;
        boss = true;
        bossNum = 2;
        defaultSpeed = 1;
        speed = defaultSpeed;
        attack = 1;
        maxLife = 10;
        currentLife = maxLife;
        action = "idleRight";
        damageSprite = 9;
        sleep = true;
        projectile = new OBJ_DemonBlast(gp);

        // Load mob sprites
        getMobSprites();
        setDialog();

        // Set collision settings
        setCollisionValues(180, 230, 60, 80);
        attackArea.width = 140;
        attackArea.height = 140;
        dialogueSet = 0;
    }

    @Override
    public void setAction() {
        specialAttackCounter++;
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
        attackCheck();
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
            checkWithinAttackRange(30,gp.TILE_SIZE*6,gp.TILE_SIZE*6); // Original
//            checkWithinAttackRange(30); // CHANGE ATTACK RANGE
        }
    }

    @Override
    public void specialAttack(){
        System.out.println("now cumming");
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

    public void setDialog() {
        dialogs[0][0] = "Who are you.....";
        dialogs[0][1] = "YOU WILL PAY... WITH ICE!";

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