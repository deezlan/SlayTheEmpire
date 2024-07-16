package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Canine extends Entity {
    GamePanel gp;
    public MOB_Canine(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(2, 4, false, false, 10);
        setCollisionValues(30, 50, 40, 30);
        setAttackValues(1, 3, 50, 50);

        // Load mob sprites
        getMobSprites();
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/Canine/";
        try {
            for (int i = 0; i <= 5; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
            }

            for (int i = 0; i <= 3; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 6; i++) {
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleLeft " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 80, 80);
            UtilityTool.scaleEntityList(this,moveLeftList, 80, 80);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 80, 80);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 80, 80);
            UtilityTool.scaleEntityList(this,idleLeftList, 80, 80);
            UtilityTool.scaleEntityList(this, idleRightList, 80, 80);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}