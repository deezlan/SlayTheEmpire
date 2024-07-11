package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Obelisk extends Entity {
    public OBJ_Obelisk(GamePanel gp) {
        super(gp);
        name = "Obelisk";
        message = "teleport";
        type = type_obelisk;
        // Load sprites
        getObjectSprites();

        // Set collision settings
        solidArea.x = 48;
        solidArea.y = 160;
        solidArea.width = 20;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
    }

    public void getObjectSprites() {
        String dir = "/objects/Obelisk/";
        try {
            // Load sprites
            for (int i = 0; i <= 13; i++) {
                defaultList.add(i, UtilityTool.loadSprite(dir + "idle/" + i + ".png", "Missing idle " + i));
                interactList.add(i, UtilityTool.loadSprite(dir + "interact/" + i + ".png", "Missing interact " + i));
            }
            // Scale sprites up
            UtilityTool.scaleEntityList(this, defaultList, 119, 214);
            UtilityTool.scaleObjectList(interactList, 119, 214);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}