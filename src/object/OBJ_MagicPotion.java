package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_MagicPotion extends Entity {
    public OBJ_MagicPotion(GamePanel gp) throws IOException {
        super(gp);

        name = "Magic Potion";
        description = "Summons magical ring";
        price = 150;
        projectile = new OBJ_MagicRing(gp);
        try {
            weaponSprite = UtilityTool.loadSprite("/objects/potions/02_Potions.png", "SmallHP Potion sprite not loaded");
            weaponSprite = UtilityTool.scaleImage(weaponSprite, 36, 36);
        } catch (IOException e){
            String errorMsg = "Magic Potion sprite not loaded";
            throw new IOException(errorMsg, e);
        }
    }

    @Override
    public void consume() {
        int count = gp.player.ownedPotion.get("Magic Potion");
        gp.player.ownedPotion.put("Magic Potion", count-1);
    }
}
