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
        setStatValues(1, 4, false, 9, 350);
        setCollisionValues(70, 90, 40, 40);
        setAttackValues(1, 6, gp.TILE_SIZE*2, gp.TILE_SIZE*2, false);

        // Load mob sprites
        getMobSprites();
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

            UtilityTool.scaleEntityList(this, moveRightList, 200, 200);
            UtilityTool.scaleEntityList(this, moveLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 200, 200);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 200, 200);
            UtilityTool.scaleEntityList(this, idleLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, idleRightList, 200, 200);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}