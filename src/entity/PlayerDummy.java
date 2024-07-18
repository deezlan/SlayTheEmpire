package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class PlayerDummy extends Entity {
    public static final String npcName = "Dummy";
    String dir;

    public int playerClass;

    public PlayerDummy(GamePanel gp) {
        super(gp);
        name = npcName;
        playerClass = gp.player.playerClass;
        updatePlayerClassSprites();
    }

    public void updatePlayerClassSprites() {
        clearSpriteLists();  // Ensure old sprites are cleared before loading new ones
        try {
            switch (playerClass) {
                case 0: // WARRIOR
                    loadSprites("/player/Warrior/", 220, 96, 7, 5);
                    break;
                case 1: // KNIGHT
                    loadSprites("/player/Knight/", 200, 96, 9, 9);
                    break;
                case 2: // ASSASSIN
                    loadSprites("/player/Assassin/", 180, 96, 24, 17);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    private void clearSpriteLists() {
        moveRightList.clear();
        moveLeftList.clear();
        idleRightList.clear();
        idleLeftList.clear();
    }

    private void loadSprites(String dir, int scaleWidth, int scaleHeight, int moveFrames, int idleFrames) throws IOException {
        this.dir = dir;
        // Load sprites for movement
        for (int i = 0; i <= moveFrames; i++) {
            moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
            moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
        }
        // Load sprites for idle
        for (int i = 0; i <= idleFrames; i++) {
            idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
        }

        // Scale sprites up
        UtilityTool.scaleEntityList(this, moveRightList, scaleWidth, scaleHeight);
        UtilityTool.scaleEntityList(this, moveLeftList, scaleWidth, scaleHeight);
        UtilityTool.scaleEntityList(this, idleRightList, scaleWidth, scaleHeight);
        UtilityTool.scaleEntityList(this, idleLeftList, scaleWidth, scaleHeight);
    }
}
