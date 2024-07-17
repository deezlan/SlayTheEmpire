package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_HealthPotion extends Entity {
    int count;
    public OBJ_HealthPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Health Potion";
        description = "Restores your health";
        price = 150;
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/00_Potions.png", "HP Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "Health Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }
}
