package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_PickUpHealth extends Entity {
    GamePanel gp;
    public OBJ_PickUpHealth(GamePanel gp, int worldX, int worldY){
        super(gp, worldX, worldY);
        this.gp = gp;
        type = type_pickup;
        name = "Health";
        getObjectSprites();
    }

    @Override
    public void use(Entity entity) {
        gp.player.currentLife += 3;
        alive = false;
    }

    public void getObjectSprites() {
        String dir = "/objects/potions/";
        try {
            defaultList.add(0, UtilityTool.loadSprite(dir + "00_Potions.png", "Missing sprite"));
            // Scale sprites
            UtilityTool.scaleEntityList(this, defaultList, 48, 48);
            System.out.println("Loaded potion successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
