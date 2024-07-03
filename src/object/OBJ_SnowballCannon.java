package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SnowballCannon extends Entity {

    public OBJ_SnowballCannon(GamePanel gp) throws IOException {
        super(gp);

        name = "Snowball Cannon";
        price = "100";
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Snowball/SnowballCannon.png", "Cannon sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Cannon sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 1;
    }
}
