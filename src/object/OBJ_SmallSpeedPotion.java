package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SmallSpeedPotion extends Entity {
    public OBJ_SmallSpeedPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Small Speed Potion";
        description = "speeeeeeeeeeed";
        price = 150;
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/03_Potions.png", "SmallSpeed Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "SmallSpeed Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }
}
