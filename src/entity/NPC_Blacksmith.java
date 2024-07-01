package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class NPC_Blacksmith extends Entity {
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
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}