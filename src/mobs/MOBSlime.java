package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;
import java.util.Random;

public class MOBSlime extends Entity {
    public MOBSlime(GamePanel gp) {
        super(gp);
        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;
        action = "idleRight";

        // Load mob sprites
        getMobSprites();

        // Set collision settings
        solidArea.x = 58;
        solidArea.y = gp.TILE_SIZE + 50;
        solidArea.width = 28;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(250)+1;

            if (i <= 25) {
                action = "moveUp";
                currentActionList = moveRightList;
            }
            if (i > 25 && i <= 50){
                action = "moveDown";
                currentActionList = moveLeftList;
            }
            if (i > 50 && i <= 75) {
                action = "moveLeft";
                currentActionList = moveLeftList;
            }
            if (i > 75 && i <= 100) {
                action = "moveRight";
                currentActionList = moveRightList;
            }
            if (i > 100 && i <= 125) {
                action = "idleRight";
                currentActionList = idleRightList;
            }
            if (i > 125 && i <= 150) {
                action = "idleLeft";
                currentActionList = idleLeftList;
            }
            if (i > 150 && i <= 175) {
                action = "moveUpRight";
                currentActionList = moveRightList;
            }
            if (i > 175 && i <= 200) {
                action = "moveDownRight";
                currentActionList = moveRightList;
            }
            if (i > 200 && i <= 225) {
                action = "moveUpLeft";
                currentActionList = moveLeftList;
            }
            if (i > 225) {
                action = "moveDownLeft";
                currentActionList = moveLeftList;
            }
            actionLockCounter = 0;
        }
    }

    public void getMobSprites() {
        String dir = "/Mobs/Slime/";
        try {
            moveRightList.add(0, UtilityTool.loadSprite(dir + "right/00.png", "Missing Move Right 0"));
            moveRightList.add(1, UtilityTool.loadSprite(dir + "right/01.png", "Missing Move Right 1"));
            moveRightList.add(2, UtilityTool.loadSprite(dir + "right/02.png", "Missing Move Right 2"));
            moveRightList.add(3, UtilityTool.loadSprite(dir + "right/03.png", "Missing Move Right 3"));
            UtilityTool.scaleEntityList(this, moveRightList, 150, 150);

            moveLeftList.add(0, UtilityTool.loadSprite(dir + "left/00.png", "Missing Move Left 0"));
            moveLeftList.add(1, UtilityTool.loadSprite(dir + "left/01.png", "Missing Move Left 1"));
            moveLeftList.add(2, UtilityTool.loadSprite(dir + "left/02.png", "Missing Move Left 2"));
            moveLeftList.add(3, UtilityTool.loadSprite(dir + "left/03.png", "Missing Move Left 3"));
            UtilityTool.scaleEntityList(this,moveLeftList, 150, 150);

            idleLeftList.add(0, UtilityTool.loadSprite(dir + "idle/00.png", "Missing Idle Left 0"));
            idleLeftList.add(1, UtilityTool.loadSprite(dir + "idle/01.png", "Missing Idle Left 1"));
            idleLeftList.add(2, UtilityTool.loadSprite(dir + "idle/02.png", "Missing Idle Left 2"));
            idleLeftList.add(3, UtilityTool.loadSprite(dir + "idle/03.png", "Missing Idle Left 3"));
            UtilityTool.scaleEntityList(this,idleLeftList, 150, 150);

            idleRightList.add(0, UtilityTool.loadSprite(dir + "idle/00.png", "Missing Idle Right 0"));
            idleRightList.add(1, UtilityTool.loadSprite(dir + "idle/01.png", "Missing Idle Right 1"));
            idleRightList.add(2, UtilityTool.loadSprite(dir + "idle/02.png", "Missing Idle Right 2"));
            idleRightList.add(3, UtilityTool.loadSprite(dir + "idle/03.png", "Missing Idle Right 3"));
            UtilityTool.scaleEntityList(this, idleRightList, 150, 150);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}