package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Mystery extends Entity {
    public NPC_Mystery(GamePanel gp, int worldX, int worldY){
        super(gp, worldX, worldY);
        action = "idleRight";
        type = type_npc;
        setCollisionValues(20, 48, 48, 48);
        getNpcSprites();
        setDialog();

        dialogueSet = -1;
    }

    public void setDialog() {
        dialogs[0][0] = "Change Difficulty?";
    }

    public void getNpcSprites() {
        String dir = "/NPCs/shadyguy/";
        try {
            // Load sprites
            for (int i = 0; i <= 4; i++)
                idleRightList.add(i, UtilityTool.loadSprite(dir + i + ".png", "Missing idleRight " + i));
            System.out.println("Shady Guy sprites loaded successfully");
            // Scale sprites up
            UtilityTool.scaleEntityList(this, idleRightList, 90, 90);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
    public void speak() {
        if (gp.gameMode == gp.EASY_MODE) {
            System.out.println("NORMAL NORMAL NORMAL NORMAL NORMAL NORMAL");
            gp.gameMode = gp.NORMAL_MODE;
        } else if (gp.gameMode == gp.NORMAL_MODE) {
            System.out.println("HARD HARD HARD HARD HARD HARD");
            gp.gameMode = gp.HARD_MODE;
        } else {
            System.out.println("EASY EASY EASY EASY EASY EASY");
            gp.gameMode = gp.EASY_MODE;
        }
        startDialogue(this,dialogueSet);
        dialogueSet++;
        if(dialogs[dialogueSet][0] == null){
            dialogueSet = 0;
        }
        gp.gameState = gp.DIFF_DIALOGUE_STATE;
        gp.ui.npc = this;
    }
}
