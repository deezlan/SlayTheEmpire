package object;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Coin extends SuperObject {
    GamePanel gp;

    public OBJ_Coin(GamePanel gp) {
        this.gp = gp;
        name = "Coin";
        collision = false;

        // Load save pedestal sprites
        getObjectSprites();
    }

    public void getObjectSprites() {
        String dir = "/objects/coin/";
        try {
            for (int i = 0; i <= 8; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing shop " + i));
            // Scale sprites
            UtilityTool.scaleObjectList(defaultList, 48, 48);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
