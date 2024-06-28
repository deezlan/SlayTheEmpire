package object;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_DialogueBubble extends SuperObject {
    public OBJ_DialogueBubble() {
        name = "Bubble";

        // Load save pedestal sprites
        try {
            spriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/chatBubbles/01.png"), "Missing bubble sprite 0")));
            spriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/chatBubbles/02.png"), "Missing bubble pedestal sprite 1")));
            spriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/chatBubbles/03.png"), "Missing bubble pedestal sprite 2")));
            spriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/chatBubbles/04.png"), "Missing bubble pedestal sprite 3")));

            UtilityTool.scaleObjectList(this, spriteList, 50, 50);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        solidArea.x = 0;
        solidArea.y = 50;
        solidArea.width = 24;
        solidArea.height = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = false;
    }
}
