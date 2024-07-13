package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_ChaosBall extends Projectile {
    GamePanel gp;
    public OBJ_ChaosBall(GamePanel gp) {
        super(gp);
        // maybe delete below
        this.gp = gp;

        speed = 10;
        maxLife = 200;
        currentLife = maxLife;
        damage = 2;
        alive = false;
        getImage();
        solidArea.width = gp.TILE_SIZE*2;
        solidArea.height = gp.TILE_SIZE*2;
    }

    public void getImage() {
        try {
            weaponSprite = UtilityTool.loadSprite("/Projectile/Chaos/Explosion_1.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Chaos/Explosion_2.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentList.add(weaponSprite);
            weaponSprite = UtilityTool.loadSprite("/Projectile/Chaos/Explosion_3.png", "flame not found");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, gp.TILE_SIZE*2, gp.TILE_SIZE*2);
            currentList.add(weaponSprite);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
