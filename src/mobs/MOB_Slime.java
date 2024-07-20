package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Slime extends Entity {
    GamePanel gp;
    public MOB_Slime(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, 1, 15);
        setCollisionValues(70, 90, 40, 30);
        setAttackValues(1, 2, 50, 30, false);
        setHitboxValues(70, 100, 60, 45);

        attRangeHorz = 66;
        attRangeVert = 30;

        // Load mob sprites
        getMobSprites();
    }

    public void getMobSprites() {
        String dir = "/Mobs/Slime/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i < 4; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attack/" + i + ".png", "Missing Attack Animation " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attack/" + i + ".png", "Missing Attack Animation " + i));
                System.out.println("ATTACK is LOADED!");
            }

            UtilityTool.scaleEntityList(this, moveRightList, 200, 200);
            UtilityTool.scaleEntityList(this,moveLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 200, 200);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 200, 200);
            UtilityTool.scaleEntityList(this,idleLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, idleRightList, 200, 200);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}