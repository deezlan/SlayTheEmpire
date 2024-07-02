package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Raygun extends Entity {

    public OBJ_Raygun(GamePanel gp) throws IOException {
        super(gp);

        name = "Raygun";
        price = "200";
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Raygun/Raygun.png", "Raygun sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Raygun sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 3;
    }
}
