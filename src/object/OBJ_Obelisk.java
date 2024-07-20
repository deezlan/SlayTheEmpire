package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Obelisk extends Entity {
    public OBJ_Obelisk(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        name = "Obelisk";
        message = "teleport";
        type = type_obelisk;
        setCollisionValues(48, 160, 20, 30);
        // Load sprites
        getObjectSprites();

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