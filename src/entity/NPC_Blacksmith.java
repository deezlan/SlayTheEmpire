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
        setCollisionValues(20, 48, 48, 48);
        getNpcSprites();
        setDialog();
    }

    public void setDialog() {
        switch(gp.player.playerClass) {
            case 0: // WARRIOR
                dialogs[0][0] = "Greetings, savage of the sand dunes.\n Cough up the coin, then I'll help.";
                break;
            case 1: // KNIGHT
                dialogs[0][0] = "Welcome, Sir. \nWhat services do you seek of me?";
                break;
            case 2: // ASSASSIN
                dialogs[0][0] = "Hello, shadow walker.";
                break;
        }
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

    public void buy() {
        if (gp.player.ownedWeapon.contains(gp.ui.slotRow) & !gp.player.hotbarList.contains(shopItems.get(gp.ui.slotRow))){
            if (gp.player.hotbarList.size() < 2){
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
            } else{
                gp.player.hotbarList.remove(1);
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
            }
        } else if (gp.player.hotbarList.contains(shopItems.get(gp.ui.slotRow))) {

        } else {
            if (gp.player.hotbarList.size() < 2){
                gp.player.totalCoins -= shopItems.get(gp.ui.slotRow).price;
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
                gp.player.ownedWeapon.add(gp.ui.slotRow);
            } else {
                gp.player.totalCoins -= shopItems.get(gp.ui.slotRow).price;
                gp.player.hotbarList.remove(1);
                gp.player.hotbarList.add(0, shopItems.get(gp.ui.slotRow));
                gp.player.ownedWeapon.add(gp.ui.slotRow);
            }
        }

    }

    public ArrayList<Entity> getShopItems(){
        return shopItems;
    }

    public void speak() {
        startDialogue(this,dialogueSet);
        dialogueSet++;
        if(dialogs[dialogueSet][0] == null){
            dialogueSet = 0;
        }
        gp.gameState = gp.BLACKSMITH_DIALOGUE_STATE;
    }
}