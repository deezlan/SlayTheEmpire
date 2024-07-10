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
        maxLife = 80;
        currentLife = maxLife;
        damage = 2;
        alive = false;
        getImage();
    }

    public void getImage(){
        String dir = "/Projectile/fireball/";
        try {
            for (int i = 0; i <= 1; i++) {
                projectileRight.add(i, UtilityTool.loadSprite(dir + "right/" + i + ".png", "Missing Right Fireball " + i));
                projectileLeft.add(i, UtilityTool.loadSprite(dir + "left/" + i + ".png", "Missing left Fireball " + i));
                projectileUp.add(i, UtilityTool.loadSprite(dir + "up/" + i + ".png", "Missing up Fireball " + i));
                projectileDown.add(i, UtilityTool.loadSprite(dir + "down/" + i + ".png", "Missing down Fireball " + i));
            }
            UtilityTool.scaleEntityList(this, projectileRight, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this,projectileLeft, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this,projectileUp, gp.TILE_SIZE, gp.TILE_SIZE);
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
