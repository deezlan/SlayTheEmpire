package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        action = "idleRight";
        type = type_npc;
        setCollisionValues(20, 48, 48, 48);
        getNpcSprites();
        setDialog();

        dialogueSet = -1;
    }

    public void setDialog() {
        dialogs[0][0] = "Come Get Your Potions!!!!";
        dialogs[0][1] = "No Chemicals Are Added!!!";

        dialogs[1][0] = "Get Your Potions!!!!";
        dialogs[1][1] = "Side Effects Are Not Covered!";
    }


    public void getNpcSprites() {
        String dir = "/NPCs/merchant/";
        try {
            // Load sprites
            for (int i = 0; i <= 4; i++)
                idleRightList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing idleRight " + i));
            System.out.println("Merchant sprites loaded successfully");
            // Scale sprites up
            UtilityTool.scaleEntityList(this, idleRightList, 90, 90);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
    public void speak() {
        startDialogue(this,dialogueSet);
        dialogueSet++;

        if(dialogs[dialogueSet][0] == null){
            dialogueSet = 0;
        }
    }
}