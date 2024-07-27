package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_MagicRing extends Projectile {
    public OBJ_MagicRing(GamePanel gp) {
        super(gp);

        name = "Magic Ring";
        speed = 0;
        maxLife = 270;
        currentLife = maxLife;
        damage = 1;
        alive = false;
        getImage();
        solidArea.width = gp.TILE_SIZE*3;
        solidArea.height = gp.TILE_SIZE*3;
        type = 1;
    }

    public void getImage(){
        try {
            for (int i = 1; i <= 8; i++) {
                projectileRight.add(i-1, UtilityTool.loadSprite("/Projectile/MagicPotion/" + i + ".png", "Missing Magic Energy " + i));
                projectileLeft.add(i-1, UtilityTool.loadSprite("/Projectile/MagicPotion/" + i + ".png", "Missing Magic Energy " + i));
                projectileUp.add(i-1, UtilityTool.loadSprite("/Projectile/MagicPotion/" + i + ".png", "Missing Magic Energy " + i));
                projectileDown.add(i-1, UtilityTool.loadSprite("/Projectile/MagicPotion/" + i + ".png", "Missing Magic Energy " + i));
            }
            UtilityTool.scaleEntityList(this, projectileRight, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            UtilityTool.scaleEntityList(this, projectileLeft, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            UtilityTool.scaleEntityList(this, projectileUp, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
            UtilityTool.scaleEntityList(this, projectileDown, gp.TILE_SIZE*3, gp.TILE_SIZE*3);
        }catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
