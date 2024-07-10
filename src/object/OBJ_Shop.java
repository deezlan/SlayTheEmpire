package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Shop extends Entity {
    public OBJ_Shop(GamePanel gp) {
        super(gp);
        name = "Shop";
        message = "Shop closed bitch";
//        isObject = true;

        // Load shop sprites
        getObjectSprites();

        // Set shop collision settings
        solidArea.x = 20;
        solidArea.y = 0;
        solidArea.width = 172;
        solidArea.height = 130;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;

        System.out.println(interactList.size());
    }

    public void getObjectSprites() {
        String dir = "/objects/shop/";
        try {
            // Load sprites
            for (int i = 0; i <= 3; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing shop " + i));
            // Scale sprites up
            UtilityTool.scaleEntityList(this,defaultList, 173, 115);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
