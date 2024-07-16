package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Goblin extends Entity {
    GamePanel gp;
    public MOB_Goblin(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, false, 5);
        setCollisionValues(98, 120, 28, 30);
        setAttackValues(1, 4, 50, 50);

        // Load mob sprites
        getMobSprites();
    }

    public void getMobSprites() {
        String dir = "/Mobs/Goblin/";
        try {
            for (int i = 0; i <= 7; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                mobLeftAttackList.add(i, UtilityTool.loadSprite(dir + "attackLeft/" + i + ".png", "Missing idleLeft " + i));
                mobRightAttackList.add(i, UtilityTool.loadSprite(dir + "attackRight/" + i + ".png", "Missing idleRight " + i));
            }

            for (int i = 0; i <= 3; i++) {
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 200, 200);
            UtilityTool.scaleEntityList(this,moveLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, mobLeftAttackList, 200, 180);
            UtilityTool.scaleEntityList(this,mobRightAttackList, 200, 200);
            UtilityTool.scaleEntityList(this,idleLeftList, 200, 200);
            UtilityTool.scaleEntityList(this, idleRightList, 200, 200);

            System.out.println("Goblin sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}