package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Shop extends Entity {
    public OBJ_Shop(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        message = "Shop closed bitch";
        type = type_shop;
        setCollisionValues(20, 0, 172, 130);
        collision = true;

        // Load shop sprites
        getObjectSprites();
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
