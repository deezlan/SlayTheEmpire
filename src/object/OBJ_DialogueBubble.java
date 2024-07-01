package object;

import main.UtilityTool;

import java.io.IOException;

public class OBJ_DialogueBubble extends SuperObject {
    public OBJ_DialogueBubble() {
        name = "Bubble";

        // Load save pedestal sprites
        getObjectSprites();

        // Set collision settings
        solidArea.x = 0;
        solidArea.y = 50;
        solidArea.width = 24;
        solidArea.height = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = false;
    }

    public void getObjectSprites() {
        String dir = "/objects/chatBubbles/";
        try {
            defaultList.add(0, UtilityTool.loadSprite(dir + "00.png", "Missing Idle 0"));
            defaultList.add(1, UtilityTool.loadSprite(dir + "01.png", "Missing Idle 1"));
            defaultList.add(2, UtilityTool.loadSprite(dir + "02.png", "Missing Idle 2"));
            defaultList.add(3, UtilityTool.loadSprite(dir + "03.png", "Missing Idle 3"));

            UtilityTool.scaleObjectList(defaultList, 50, 50);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
