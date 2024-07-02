package entity;

import main.GamePanel;
import main.UtilityTool;
import object.OBJ_Flamethrower;
import object.OBJ_Hammer;
import object.OBJ_Raygun;
import object.OBJ_SnowballCannon;

import java.io.IOException;
import java.util.ArrayList;

public class NPC_Blacksmith extends Entity {
    public static ArrayList<Entity> shopItems = new ArrayList<>();
    public NPC_Blacksmith(GamePanel gp) {
        super(gp);
        action = "idleRight";
        getNpcSprites();
        setDialog();

        // Set collision settings
        solidArea.x = 20;
        solidArea.y = gp.TILE_SIZE;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialog();
    }

    public void setDialog() {
        dialogs[0] = "placeholder 1";
        dialogs[1] = "placeholder 2";
        dialogs[2] = "placeholder 3";
        dialogs[3] = "placeholder 4";
        dialogs[4] = "placeholder 4";
    }

    public void setDialog() {
        dialogs[0] = "placeholder 1";
        dialogs[1] = "placeholder 2";
        dialogs[2] = "placeholder 3";
        dialogs[3] = "placeholder 4";
        dialogs[4] = "placeholder 4";
    }

    public void getNpcSprites() {
        String dir = "/NPCs/blacksmith/";
        try {
            // Load sprites
            for (int i = 0; i <= 4; i++)
                idleRightList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing idleRight " + i));
            System.out.println("Blacksmith sprites loaded successfully");
            // Scale sprites up
            UtilityTool.scaleEntityList(this, idleRightList, 90, 90);

            setShopItems();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    private void setShopItems() throws IOException {
        shopItems.add(new OBJ_SnowballCannon(gp));
        shopItems.add(new OBJ_Raygun(gp));
        shopItems.add(new OBJ_Flamethrower(gp));
        shopItems.add(new OBJ_Hammer(gp));
    }

    public ArrayList<Entity> getShopItems(){
        return shopItems;
    }
}