package entity;

import main.GamePanel;
import main.UtilityTool;
import object.OBJ_ElectricBlaster;
import object.OBJ_Hammer;
import object.OBJ_Raygun;
import object.OBJ_FireballCannon;

import java.io.IOException;
import java.util.ArrayList;

public class NPC_Blacksmith extends Entity {
    public static ArrayList<Entity> shopItems = new ArrayList<>();
    public NPC_Blacksmith(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        action = "idleRight";
        type = type_npc;
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
        dialogs[0][0] = "placeholder 1";
        dialogs[0][1] = "placeholder 2";
        dialogs[0][2] = "placeholder 3";
        dialogs[0][3] = "placeholder 4";
        dialogs[0][4] = "placeholder 4";
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
        shopItems.add(new OBJ_FireballCannon(gp));
        shopItems.add(new OBJ_Raygun(gp));
        shopItems.add(new OBJ_ElectricBlaster(gp));
        shopItems.add(new OBJ_Hammer(gp));
    }

    public void buy(){
        if (gp.player.ownedWeapon.contains(gp.ui.slotRow) & !gp.player.hotbarList.contains(shopItems.get(gp.ui.slotRow))){
            if (gp.player.hotbarList.size() < 3){
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
            } else{
                gp.player.hotbarList.remove(2);
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
            }
        } else if (gp.player.hotbarList.contains(shopItems.get(gp.ui.slotRow))) {

        } else {
            if (gp.player.hotbarList.size() < 3){
                gp.player.totalCoins -= shopItems.get(gp.ui.slotRow).price;
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
                gp.player.ownedWeapon.add(gp.ui.slotRow);
            } else {
                gp.player.totalCoins -= shopItems.get(gp.ui.slotRow).price;
                gp.player.hotbarList.remove(2);
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
                gp.player.ownedWeapon.add(gp.ui.slotRow);
            }
        }

    }

    public ArrayList<Entity> getShopItems(){
        return shopItems;
    }
}