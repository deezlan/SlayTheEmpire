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
    }

    public void setDialog() {
        dialogs[0] = "I'm Cleaning";
        dialogs[1] = "I ain't no bitch";
        dialogs[2] = "You are disturbing me";
        dialogs[3] = "Fk off";
        dialogs[4] = "Hello my name is jeff,\nim a a good,\nintelij is stupid,\ntest est essd";
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
        super.speak();
    }
}
