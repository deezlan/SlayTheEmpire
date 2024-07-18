package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_ChatBubble extends Entity {
    public OBJ_ChatBubble(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        name = "Chat Bubble";

        // Load save pedestal sprites
        getObjectSprites();
    }

    public void getObjectSprites() {
        String dir = "/objects/chatBubbles/";
        try {
            // Load sprites
            for (int i = 0; i <= 3; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing chatBubble " + i));
            // Scale sprites up
            UtilityTool.scaleEntityList(this,defaultList, 50, 50);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
