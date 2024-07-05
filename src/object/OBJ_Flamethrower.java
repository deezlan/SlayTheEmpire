package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Flamethrower extends Entity {
    public OBJ_Flamethrower(GamePanel gp) throws IOException {
        super(gp);

        name = "Flamethrower";
        description = "Burn? Burn!";
        price = 250;
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Flamethrower/Flamethrower.png", "Flamethrower sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Flamethrower sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 2;
    }
}
