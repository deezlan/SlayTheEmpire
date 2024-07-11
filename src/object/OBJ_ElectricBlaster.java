package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_ElectricBlaster extends Entity {
    public OBJ_ElectricBlaster(GamePanel gp) throws IOException {
        super(gp);

        name = "Electric Blaster";
        description = "Beware, high voltage...";
        price = 250;
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Flamethrower/Flamethrower.png", "Flamethrower sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Flamethrower sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        projectile1 = new OBJ_Nova(gp);
        System.out.println("Projectile initialized in Flamethrower: " + this.projectile1);

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 2;
    }
}
