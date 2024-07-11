package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Nova extends Projectile {
    GamePanel gp;
    public OBJ_Nova(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Flame";
        speed = 0;
        maxLife = 30;
        currentLife = maxLife;
        damage = 1;
        alive = false;
        getImage();
        solidArea.width = gp.TILE_SIZE*3;
        solidArea.height = gp.TILE_SIZE*3;
    }

    public void getImage(){
        try {
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_3.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_4.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_5.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_6.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_7.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_8.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_9.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Nova/Explosion_10.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            currentList.add(weaponSprite);
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
