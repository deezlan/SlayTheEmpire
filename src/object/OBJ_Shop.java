package object;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Shop extends SuperObject {
    public OBJ_Shop(GamePanel gp) {
        name = "Shop";
        message = "Shop closed bitch";

        // Load shop sprites
        getObjectSprites(gp);

        // Set shop collision settings
        solidArea.x = 20;
        solidArea.y = 0;
        solidArea.width = 172;
        solidArea.height = 130;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
    }

    public void getObjectSprites(GamePanel gp) {
        String dir = "/objects/shop/";
        try {
            defaultList.add(0, UtilityTool.loadSprite(dir + "00.png", "Missing Shop 0"));
            defaultList.add(1, UtilityTool.loadSprite(dir + "01.png", "Missing Shop 1"));
            defaultList.add(2, UtilityTool.loadSprite(dir + "02.png", "Missing Shop 2"));
            defaultList.add(3, UtilityTool.loadSprite(dir + "03.png", "Missing Shop 3"));

            UtilityTool.scaleObjectList(defaultList, (int)(gp.TILE_SIZE*3.6), (int)(gp.TILE_SIZE*2.4));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
