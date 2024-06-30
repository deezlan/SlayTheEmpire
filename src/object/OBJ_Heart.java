package object;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";

        // Load heart sprites
        getObjectSprites();
    }

    public void getObjectSprites() {
        String dir = "/objects/life/";
        try {
            defaultList.add(0, UtilityTool.loadSprite(dir + "heart_blank.png", "Missing Blank Heart"));
            defaultList.add(1, UtilityTool.loadSprite(dir + "heart_half.png", "Missing Half Heart"));
            defaultList.add(2, UtilityTool.loadSprite(dir + "heart_full.png", "Missing Full Heart"));

            UtilityTool.scaleObjectList(defaultList, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
