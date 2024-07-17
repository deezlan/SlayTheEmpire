package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_DemonBlast extends Projectile {
    GamePanel gp;
    public OBJ_DemonBlast(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Demon Blast";
        speed = 0;
        maxLife = 80;
        currentLife = maxLife;
        damage = 3;
        alive = false;
        getImage();
        solidArea.width = gp.TILE_SIZE*5;
        solidArea.height = gp.TILE_SIZE*5;
    }

    public void getImage(){
        try {
            for (int i = 1; i <= 7; i++) {
                projectileRight.add(i-1, UtilityTool.loadSprite("/Projectile/DemonSlime/" + i + ".png", "Missing Demon Energy " + i));
                projectileLeft.add(i-1, UtilityTool.loadSprite("/Projectile/DemonSlime/" + i + ".png", "Missing Demon Energy " + i));
                projectileUp.add(i-1, UtilityTool.loadSprite("/Projectile/DemonSlime/" + i + ".png", "Missing Demon Energy " + i));
                projectileDown.add(i-1, UtilityTool.loadSprite("/Projectile/DemonSlime/" + i + ".png", "Missing Demon Energy " + i));
            }
            UtilityTool.scaleEntityList(this, projectileRight, gp.TILE_SIZE*5, gp.TILE_SIZE*3);
            UtilityTool.scaleEntityList(this, projectileLeft, gp.TILE_SIZE*5, gp.TILE_SIZE*3);
            UtilityTool.scaleEntityList(this, projectileUp, gp.TILE_SIZE*5, gp.TILE_SIZE*3);
            UtilityTool.scaleEntityList(this, projectileDown, gp.TILE_SIZE*5, gp.TILE_SIZE*3);
        }catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
