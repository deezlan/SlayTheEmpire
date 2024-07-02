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

    public void getNpcSprites() {
        String dir = "/NPCs/blacksmith/";
        try {
            idleRightList.add(0, UtilityTool.loadSprite(dir + "00.png", "Missing Idle Right 0"));
            idleRightList.add(1, UtilityTool.loadSprite(dir + "01.png", "Missing Idle Right 1"));
            idleRightList.add(2, UtilityTool.loadSprite(dir + "02.png", "Missing Idle Right 2"));
            idleRightList.add(3, UtilityTool.loadSprite(dir + "03.png", "Missing Idle Right 3"));
            idleRightList.add(4, UtilityTool.loadSprite(dir + "04.png", "Missing Idle Right 4"));
            System.out.println("Blacksmith sprites loaded successfully");

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