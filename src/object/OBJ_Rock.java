package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Rock extends Projectile {
    GamePanel gp;
    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 5;
        maxLife = 80;
        currentLife = maxLife;
        damage = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        String dir = "/Projectile/flyingEye/";
        try {
            for (int i = 0; i <= 3; i++) {
                projectileRight.add(i, UtilityTool.loadSprite(dir + "right/" + i + ".png", "Missing Right Energy " + i));
                projectileLeft.add(i, UtilityTool.loadSprite(dir + "left/" + i + ".png", "Missing left Energy " + i));
                projectileUp.add(i, UtilityTool.loadSprite(dir + "up/" + i + ".png", "Missing up Energy " + i));
                projectileDown.add(i, UtilityTool.loadSprite(dir + "down/" + i + ".png", "Missing down Energy " + i));
            }
            UtilityTool.scaleEntityList(this, projectileRight, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this,projectileLeft, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this,projectileUp, gp.TILE_SIZE, gp.TILE_SIZE);
            UtilityTool.scaleEntityList(this, projectileDown, gp.TILE_SIZE, gp.TILE_SIZE);

            System.out.println("Energy BAll loaded successfully");
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
