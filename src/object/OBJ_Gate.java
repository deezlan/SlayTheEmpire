package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Gate extends Entity {
    public OBJ_Gate (GamePanel gp) {
        super(gp);
        name = "Gate";
        message = "";
        isObject = true;

        type = type_block;
        locked = false;

        // Load save pedestal sprites
        getObjectSprites();

        // Set collision settings
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void getObjectSprites() {
        String dir = "/objects/gate/";
        try {
            // Load sprites
            for (int i = 0; i <= 6; i++) {
                defaultList.add(i, UtilityTool.loadSprite(dir + "unlock/" + i + ".png", "Missing savePedestal " + i));
                interactList.add(i, UtilityTool.loadSprite(dir + "lock/" + i + ".png", "Missing savePedestal " + i));
            }

            // Scale sprites up
            UtilityTool.scaleEntityList(this, defaultList, 48, 48);
            UtilityTool.scaleEntityList(this, interactList, 48, 48);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
