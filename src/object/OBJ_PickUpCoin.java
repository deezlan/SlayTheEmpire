package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class OBJ_PickUpCoin extends Entity {
    GamePanel gp;
    public OBJ_PickUpCoin(GamePanel gp, int worldX, int worldY, int price){
        super(gp, worldX, worldY);
        this.gp = gp;
        type = type_pickup;
        name = "Coin";
        this.price = price;
        getObjectSprites();
    }

    @Override
    public void use(Entity entity) {
        gp.player.totalCoins += price;
        alive = false;
    }

    public void getObjectSprites() {
        String dir = "/objects/coin/";
        try {
            for (int i = 0; i <= 8; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing shop " + i));
            // Scale sprites
            UtilityTool.scaleEntityList(this, defaultList, 48, 48);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
