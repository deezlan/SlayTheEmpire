package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SavePedestal extends Entity {
    public OBJ_SavePedestal(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        message = "Saving not implemented... go away";
        setCollisionValues(0, 50, 24, 60);
        collision = true;

        // Load save pedestal sprites
        getObjectSprites();
    }

    public void getObjectSprites() {
        String dir = "/objects/savePedestal/";
        try {
            // Load sprites
            for (int i = 0; i <= 7; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing savePedestal " + i));
            // Scale sprites up
            UtilityTool.scaleEntityList(this,defaultList, 32, 96);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
