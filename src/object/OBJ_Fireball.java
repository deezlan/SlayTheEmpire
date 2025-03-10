package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Fireball extends Projectile {
    GamePanel gp;
    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 10;
        maxLife = 80;
        currentLife = maxLife;
        damage = 2;
        alive = false;
        getImage();
        solidArea.width = gp.TILE_SIZE;
        solidArea.height = gp.TILE_SIZE;
    }

    public void getImage(){
        try {
            projectileRight.add(UtilityTool.loadSprite("/Projectile/fireball/0.png","Fire ball not found"));
            projectileRight.add(UtilityTool.loadSprite("/Projectile/fireball/1.png","Fire ball not found"));
            projectileLeft.add(UtilityTool.loadSprite("/Projectile/fireball/0.png","Fire ball not found"));
            projectileLeft.add(UtilityTool.loadSprite("/Projectile/fireball/1.png","Fire ball not found"));
            projectileUp.add(UtilityTool.loadSprite("/Projectile/fireball/0.png","Fire ball not found"));
            projectileUp.add(UtilityTool.loadSprite("/Projectile/fireball/1.png","Fire ball not found"));
            projectileDown.add(UtilityTool.loadSprite("/Projectile/fireball/0.png","Fire ball not found"));
            projectileDown.add(UtilityTool.loadSprite("/Projectile/fireball/1.png","Fire ball not found"));
            UtilityTool.scaleEntityList(this, projectileRight, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this, projectileLeft, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this, projectileUp, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this, projectileDown, gp.TILE_SIZE, gp.TILE_SIZE);
//            weaponSprite = UtilityTool.loadSprite("/Projectile/fireball/fireball_right_1.png", "fireball not found");
//            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE, gp.TILE_SIZE);
//            currentActionList.add(weaponSprite);
//            weaponSprite = UtilityTool.loadSprite("/Projectile/fireball/fireball_right_2.png", "fireball not found");
//            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE, gp.TILE_SIZE);
//            currentActionList.add(weaponSprite);
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
