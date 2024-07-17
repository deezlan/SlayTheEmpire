package object;

import entity.Projectile;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_Energyball extends Projectile {
    GamePanel gp;
    public OBJ_Energyball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Energy Ball";
        speed = 8;
        maxLife = 80;
        currentLife = maxLife;
        damage = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        String dir = "/Projectile/energyball/";
        try {
            for (int i = 0; i <= 8; i++) {
                projectileRight.add(i, UtilityTool.loadSprite(dir + "right/" + i + ".png", "Missing Right Energy " + i));
                projectileLeft.add(i, UtilityTool.loadSprite(dir + "left/" + i + ".png", "Missing left Energy " + i));
                projectileUp.add(i, UtilityTool.loadSprite(dir + "up/" + i + ".png", "Missing up Energy " + i));
                projectileDown.add(i, UtilityTool.loadSprite(dir + "down/" + i + ".png", "Missing down Energy " + i));
            }
            UtilityTool.scaleEntityList(this, projectileRight, 96, 96);
            UtilityTool.scaleEntityList(this,projectileLeft, 96, 96);
            UtilityTool.scaleEntityList(this,projectileUp, 96, 96);
            UtilityTool.scaleEntityList(this, projectileDown, 96, 96);

            System.out.println("Energy BAll loaded successfully");
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
}
