package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Coin extends Entity {
    GamePanel gp;

    public OBJ_Coin(GamePanel gp) {
        super(gp);
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
            UtilityTool.scaleEntityList(this, defaultList, 48, 48);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
