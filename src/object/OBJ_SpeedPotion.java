package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SpeedPotion extends Entity {
    int count;
    public OBJ_SpeedPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Speed Potion";
        description = "SPEEEEEEEEEEEEEED";
        price = 150;
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/01_Potions.png", "Speed Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "Speed Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }
}
