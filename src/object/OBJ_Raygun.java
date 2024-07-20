package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Raygun extends Entity {

    public OBJ_Raygun(GamePanel gp) throws IOException {
        super(gp);

        name = "Stickler";
        description = "Stick it, Forget it";
        price = 200;
        try {
            weaponSprite = UtilityTool.loadSprite("/Weapon/Raygun/Raygun.png", "Raygun sprite not loaded");
        } catch (IOException e){
            String errorMsg = "Raygun sprite not loaded";
            throw new IOException(errorMsg, e);
        }

        System.out.println("Projectile initialized in Flamethrower: " + this.projectile1);
        projectile1 = new OBJ_ChaosBall(gp);
        projectile2 = new OBJ_ChaosBall(gp);
        projectile3 = new OBJ_ChaosBall(gp);

        weaponSprite = UtilityTool.scaleImage(weaponSprite, 30, 30);
        damage = 3;
    }
}
