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
        setStatValues(1, 4, false, false, 9);
        setCollisionValues(150, 150, 28, 40);
        setAttackValues(1, 7, 96, 96);

        // Load mob sprites
        getMobSprites();
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
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

            UtilityTool.scaleEntityList(this, moveRightList, 300, 300);
            UtilityTool.scaleEntityList(this, moveLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 300, 300);
            UtilityTool.scaleEntityList(this, idleLeftList, 300, 300);
            UtilityTool.scaleEntityList(this, idleRightList, 300, 300);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}