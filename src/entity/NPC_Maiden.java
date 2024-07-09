package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Maiden extends Entity {
    public NPC_Maiden(GamePanel gp) {
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

        dialogueSet = -1;
    }

    public void setDialog() {
        dialogs[0][0] = "I'm Cleaning";
        dialogs[0][1] = "I ain't no bitch";

        dialogs[1][0] = "You are disturbing me";
        dialogs[1][1] = "Fk off";

        dialogs[2][0] = "Hello my name is jeff,\nim a a good,\nintelij is stupid,\ntest est essd";
        dialogs[2][1] = "You are a bitch";
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
