package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Blacksmith extends Entity {
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
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}