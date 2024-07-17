package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Rock;

import java.io.IOException;

public class MOB_FlyingEye extends Entity {
    GamePanel gp;
    public MOB_FlyingEye(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, 8, 100);
        setCollisionValues(150, 170, 38, 30);
        setAttackValues(0, 0, 0, 0, true);
        projectile = new OBJ_Rock(gp);
        hasRanged = true;

        // Load mob sprites
        getMobSprites();
    }

    public void getMobSprites() {
        String dir = "/Mobs/FlyingEye/";
        try {
            for (int i = 0; i <= 7; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 300, 300);
            UtilityTool.scaleEntityList(this, moveLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, idleLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 300, 300);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}