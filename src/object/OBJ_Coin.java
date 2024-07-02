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
//            for (int i = 0; i < 9; i++) {
                defaultList.add(0, UtilityTool.loadSprite(dir + "0.png", "Missing Sprite 0"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "1.png", "Missing Sprite 1"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "2.png", "Missing Sprite 2"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "3.png", "Missing Sprite 3"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "4.png", "Missing Sprite 4"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "5.png", "Missing Sprite 5"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "6.png", "Missing Sprite 6"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "7.png", "Missing Sprite 7"));
            defaultList.add(0, UtilityTool.loadSprite(dir + "8.png", "Missing Sprite 8"));
//            }

            UtilityTool.scaleObjectList(defaultList, 48, 48);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
