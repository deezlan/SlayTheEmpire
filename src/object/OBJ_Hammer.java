package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Hammer extends Entity {
    public OBJ_Hammer(GamePanel gp) throws IOException {
        super(gp);

        name = "Hammer";
        description = "Ever wanted to become thor?";
        price = 150;
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Hammer/12.png", "Hammer sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Hammer sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        System.out.println("Projectile initialized in Flamethrower: " + this.projectile1);
        projectile1 = new OBJ_Thunder(gp);
        projectile2 = new OBJ_Thunder(gp);
        projectile3 = new OBJ_Thunder(gp);
        projectile4 = new OBJ_Thunder(gp);

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 2;
    }
}
