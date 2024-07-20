package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Energyball;

import java.io.IOException;
public class MOB_ArmoredGuardian extends Entity {
    GamePanel gp;
    public MOB_ArmoredGuardian(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, 7, 200);
        setCollisionValues(70, 90, 40, 40);
        setAttackValues(0, 0, 0, 0, true);
        setHitboxValues(75, 75, 50, 50);
        projectile = new OBJ_Energyball(gp);

        attRangeHorz = 96;
        attRangeVert = 96;

        getMobSprites();
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

            UtilityTool.scaleEntityList(this, moveRightList, 200, 200);
            UtilityTool.scaleEntityList(this,moveLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 200, 200);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 200, 200);
            UtilityTool.scaleEntityList(this,idleLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, idleRightList, 200, 200);
            System.out.println("ATTACK is LOADED!");

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}


