package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SavePedestal extends Entity {
    public OBJ_SavePedestal(GamePanel gp) {
        super(gp);
        name = "Save Pedestal";
        message = "Saving not implemented... go away";
//        isObject = true;

        // Load save pedestal sprites
        getObjectSprites();

        // Set collision settings
        solidArea.x = 0;
        solidArea.y = 50;
        solidArea.width = 24;
        solidArea.height = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
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
