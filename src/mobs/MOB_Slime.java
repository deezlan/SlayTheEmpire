package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.io.IOException;
import java.util.Random;

public class MOB_Slime extends Entity {
    GamePanel gp;
    public MOB_Slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 2;
        speed = 1;
        defaultSpeed = 1;
        maxLife = 4;
        life = maxLife;
        action = "idleRight";
        mobNum = 1;

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

    public void update() {
        super.update();
        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);
        int tileDistance = (xDistance + yDistance)/gp.TILE_SIZE;

        if(!onPath && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            if(i > 50) {
                onPath = true;
            }
        }
        if(onPath && tileDistance > 20){ // if player is 20 tiles away evade agro
            onPath = false;
        }
    }

    @Override
    public void setAction() {
        if (onPath) {
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.TILE_SIZE;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.TILE_SIZE;

            searchPath(goalCol,goalRow);

        } else {
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
    }

    public void damageReaction() {
        actionLockCounter = 0;
//        action = gp.player.action;
        onPath = true;
    }

    public void getMobSprites() {
        String dir = "/Mobs/Slime/";
        try {
            for (int i = 0; i <= 3; i++) {
                moveRightList.add(i, UtilityTool.loadSprite(dir + "moveRight/" + i + ".png", "Missing moveRight " + i));
                moveLeftList.add(i, UtilityTool.loadSprite(dir + "moveLeft/" + i + ".png", "Missing moveLeft " + i));
                idleLeftList.add(i, UtilityTool.loadSprite(dir + "idleLeft/" + i + ".png", "Missing idleLeft " + i));
                idleRightList.add(i, UtilityTool.loadSprite(dir + "idleRight/" + i + ".png", "Missing idleRight " + i));
            }

            UtilityTool.scaleEntityList(this, moveRightList, 150, 150);
            UtilityTool.scaleEntityList(this,moveLeftList, 150, 150);
            UtilityTool.scaleEntityList(this,idleLeftList, 150, 150);
            UtilityTool.scaleEntityList(this, idleRightList, 150, 150);

            System.out.println("Slime sprites loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}