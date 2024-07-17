package object;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;
import java.util.ArrayList;

public class OBJ_Shop extends Entity {
    public static ArrayList<Entity> shopItems = new ArrayList<>();
    public OBJ_Shop(GamePanel gp, int worldX, int worldY) throws IOException {
        super(gp, worldX, worldY);
        message = "Shop closed bitch";
        type = type_shop;

        // Load shop sprites
        getObjectSprites();
        setShopItems();

        // Set shop collision settings
        solidArea.x = 20;
        solidArea.y = 0;
        solidArea.width = 172;
        solidArea.height = 130;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;

        System.out.println(interactList.size());
    }

    private void setShopItems() {
        try {
            shopItems.add(new OBJ_HealthPotion(gp));
            shopItems.add(new OBJ_SpeedPotion(gp));
            shopItems.add(new OBJ_SmallHealthPotion(gp));
            shopItems.add(new OBJ_SmallSpeedPotion(gp));
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

    }
    public ArrayList<Entity> getShopItems(){
        return shopItems;
    }

    public void buy(){

    }

    public void getObjectSprites() {
        String dir = "/objects/shop/";
        try {
            // Load sprites
            for (int i = 0; i <= 3; i++)
                defaultList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing shop " + i));
            // Scale sprites up
            UtilityTool.scaleEntityList(this,defaultList, 173, 115);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
