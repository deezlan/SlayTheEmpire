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
        setStatValues(1, 4, false, false, 1);
        setCollisionValues(58, 98, 28, 30);
        setAttackValues(1, 2, 50, 30);

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

            UtilityTool.scaleEntityList(this, moveRightList, 150, 150);
            UtilityTool.scaleEntityList(this,moveLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 150, 150);
            UtilityTool.scaleEntityList(this, mobRightAttackList, 150, 150);
            UtilityTool.scaleEntityList(this,idleLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, idleRightList, 150, 150);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}