package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class MOB_Ramses extends Entity {
    GamePanel gp;
    public MOB_Ramses(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        this.gp = gp;
        setStatValues(1, 4, false, false, 4);
        setCollisionValues(58, 98, 50, 30);
        setAttackValues(1, 3, 50, 50);

        // Load mob sprites
        getMobSprites();
    }

    public void getMobSprites() {
        String dir = "/Mobs/Ramses/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 100, 100);
            UtilityTool.scaleEntityList(this,moveLeftList, 100, 100);
            UtilityTool.scaleEntityList(this,idleLeftList, 100, 100);
            UtilityTool.scaleEntityList(this, idleRightList, 100, 100);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}