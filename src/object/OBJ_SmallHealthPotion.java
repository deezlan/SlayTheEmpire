package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SmallHealthPotion extends Entity {
    int count;
    public OBJ_SmallHealthPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Small Health Potion";
        description = "Restores a little of your health";
        price = 150;
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/02_Potions.png", "SmallHP Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "SmallHP Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }
}
