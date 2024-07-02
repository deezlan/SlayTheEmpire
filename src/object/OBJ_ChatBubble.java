package object;

import main.UtilityTool;

import java.io.IOException;

public class OBJ_ChatBubble extends SuperObject {
    public OBJ_ChatBubble() {
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
            // Load sprites
            for (int i = 0; i <= 3; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing chatBubble " + i));
            // Scale sprites up
            UtilityTool.scaleObjectList(defaultList, 50, 50);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
