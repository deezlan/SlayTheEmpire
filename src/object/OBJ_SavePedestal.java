package object;

import main.UtilityTool;

import java.io.IOException;

public class OBJ_SavePedestal extends SuperObject {
    public OBJ_SavePedestal() {
        name = "Save Pedestal";
        message = "Saving not implemented... go away";

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
            defaultList.add(0, UtilityTool.loadSprite(dir + "00.png", "Missing Idle 0"));
            defaultList.add(1, UtilityTool.loadSprite(dir + "01.png", "Missing Idle 1"));
            defaultList.add(2, UtilityTool.loadSprite(dir + "02.png", "Missing Idle 2"));
            defaultList.add(3, UtilityTool.loadSprite(dir + "03.png", "Missing Idle 3"));
            defaultList.add(4, UtilityTool.loadSprite(dir + "04.png", "Missing Idle 4"));
            defaultList.add(5, UtilityTool.loadSprite(dir + "05.png", "Missing Idle 5"));
            defaultList.add(6, UtilityTool.loadSprite(dir + "06.png", "Missing Idle 6"));
            defaultList.add(7, UtilityTool.loadSprite(dir + "07.png", "Missing Idle 7"));

            UtilityTool.scaleObjectList(defaultList, 32, 96);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
