package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_WaterPotion extends Entity {
    public OBJ_WaterPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Water Potion";
        description = "Summons Water Shield";
        price = 150;
        projectile = new OBJ_WaterShield(gp);
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/03_Potions.png", "SmallSpeed Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "Water Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public void consume() {
        int count = gp.player.ownedPotion.get("Water Potion");
        gp.player.ownedPotion.put("Water Potion", count-1);
        gp.player.setShieldBuff(true);
    }
}
