package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Thunder extends Projectile {
    GamePanel gp;
    public OBJ_Thunder(GamePanel gp) {
        super(gp);
        this.gp = gp;

        speed = 0;
        maxLife = 50;
        life = maxLife;
        damage = 5;
        alive = false;
        getImage();
        solidArea.width = gp.TILE_SIZE*3;
        solidArea.height = gp.TILE_SIZE*3;
    }

    public void getImage() {
        try {
            weaponSprite = UtilityTool.loadSprite("/Projectile/Electric/Explosion_1.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentActionList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Electric/Explosion_2.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentActionList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Electric/Explosion_3.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentActionList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Electric/Explosion_4.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentActionList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Electric/Explosion_5.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentActionList.add(weaponSprite);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
