package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SnowballCannon extends Entity {
    GamePanel gp;

    public OBJ_SnowballCannon(GamePanel gp) throws IOException {
        super(gp);
        this.gp = gp;

        name = "Snowball Cannon";
        description = "HADOUKEN!";
        price = 100;
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Snowball/SnowballCannon.png", "Cannon sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Cannon sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        projectile = new OBJ_Projectile(gp);
        System.out.println("Projectile initialized in Snowball Cannon: " + this.projectile);

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 1;
    }
}
