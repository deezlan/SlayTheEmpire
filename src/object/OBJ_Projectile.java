package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Projectile extends Projectile {
    GamePanel gp;
    public OBJ_Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Projectile";
        speed = 10;
        maxLife = 210;
        life = maxLife;
        damage = 2;
        getImage();
    }

    public void getImage(){
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/fireball/fireball_right_1.png", "fireball not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE, gp.TILE_SIZE);
            currentActionList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/objects/fireball/fireball_right_2.png", "fireball not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE, gp.TILE_SIZE);
            currentActionList.add(weaponSprite);
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
