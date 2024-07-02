package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Hammer extends Entity {
    public OBJ_Hammer(GamePanel gp) throws IOException {
        super(gp);

        name = "Hammer";
        description = "MAN SEE MAN SMASH!";
        price = "150";
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Hammer/Hammer.png", "Hammer sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Hammer sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 2;
    }
}
