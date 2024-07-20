package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_SpeedPotion extends Entity {
    public OBJ_SpeedPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Speed Potion";
        description = "SPEEEEEEEEEEEEEED";
        price = 150;
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/01_Potions.png", "Speed Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "Speed Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public void consume() {
        int curCount = gp.player.ownedPotion.get(name);
        if (curCount > 0){
            gp.player.ownedPotion.put(name, curCount-1);
            gp.player.setSpeedBuff(true);
        }
    }
}
