package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Maiden extends Entity {
    public NPC_Maiden(GamePanel gp, int worldX, int worldY) {
        super(gp, worldX, worldY);
        action = "idleRight";
        type = type_npc;
        setCollisionValues(20, 48, 48, 48);
        getNpcSprites();
        setDialog();

        dialogueSet = -1;
    }

    public void setDialog() {
        dialogs[0][0] = "I'm Cleaning";
        dialogs[0][1] = "People Here Don't know How to Keep Clean";

        dialogs[1][0] = "You are disturbing me";
        dialogs[1][1] = "Please Leave";

        dialogs[2][0] = "Our Princess?";
        dialogs[2][1] = "She went missing a long time ago...";
    }

    public void getNpcSprites() {
        String dir = "/NPCs/maiden/";
        try {
            // Load sprites
            for (int i = 0; i <= 4; i++)
                idleRightList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing idleRight " + i));
            // Scale sprites up
            UtilityTool.scaleEntityList(this, idleRightList, 90, 90);
            System.out.println("Maiden sprites loaded successfully");
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
