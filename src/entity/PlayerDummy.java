package entity;

import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;

public class PlayerDummy extends Entity{
    public static final String npcName = "Dummy";
    String dir;
    public PlayerDummy(GamePanel gp){
        super(gp);
        name = npcName;
        getPlayerSprites();
    }

    public void getPlayerSprites() {
        try {
            switch (gp.playerClass) {
                case 0: // WARRIOR
                    dir = "/player/Warrior/";
                    // Load sprites for movement
                    for (int i = 0; i <= 7; i++) {
                        moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                        moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                    }
                    // Load sprites for idle
                    for (int i = 0; i <= 5; i++) {
                        idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                        idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                    }

                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, moveRightList, 220, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 220, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 220, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 220, 96);
                    break;
                case 1: // KNIGHT
                    dir = "/player/Knight/";
                    // Load sprites for movement
                    for (int i = 0; i <= 9; i++) {
                        moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                        moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                    }
                    // Load sprites for idle
                    for (int i = 0; i <= 9; i++) {
                        idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                        idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                    }
                    System.out.println("Loaded Knight sprites");
                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, moveRightList, 200, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 200, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 200, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 200, 96);
                    break;
                case 2:
                    dir = "/player/Assassin/";
                    // Load sprites for movement
                    for (int i = 0; i <= 24; i++) {
                        moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                        moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                    }
                    // Load sprites for idle
                    for (int i = 0; i <= 17; i++) {
                        idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
                        idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                    }

                    // Scale sprites up
                    UtilityTool.scaleEntityList(this, moveRightList, 180, 96);
                    UtilityTool.scaleEntityList(this, moveLeftList, 180, 96);
                    UtilityTool.scaleEntityList(this, idleRightList, 180, 96);
                    UtilityTool.scaleEntityList(this, idleLeftList, 180, 96);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
