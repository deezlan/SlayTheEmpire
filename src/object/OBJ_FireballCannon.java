package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_FireballCannon extends Entity {
    GamePanel gp;

    public OBJ_FireballCannon(GamePanel gp) throws IOException {
        super(gp);
        this.gp = gp;

        name = "Fireball Cannon";
        description = "HADOUKEN!";
        price = 100;
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Snowball/SnowballCannon.png", "Cannon sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Cannon sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        projectile1 = new OBJ_Fireball(gp);
        System.out.println("Projectile initialized in Snowball Cannon: " + this.projectile1);

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 2;
    }
}
