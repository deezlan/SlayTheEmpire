package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Mystery extends Entity {
    public NPC_Mystery(GamePanel gp, int worldX, int worldY){
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
      startDialogue(this,dialogueSet);
        dialogueSet++;
        if(dialogs[dialogueSet][0] == null){
            dialogueSet = 0;
        }

        gp.gameState = gp.difficultyState;
        gp.ui.npc = this;

    }
}
