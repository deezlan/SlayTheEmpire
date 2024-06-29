package mobs;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class MOBSlime extends Entity {

    public MOBSlime(GamePanel gp) {
        super(gp);
        type = 2;
        speed = 1;
        maxLife = 4;
        life = maxLife;
        action = "idleRight";
        solidArea.x = 58;
        solidArea.y = gp.TILE_SIZE + 50;
        solidArea.width = 28;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage() {
        try {
            moveRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/right/00_Slime_Spiked_Run.png"), "Missing slime right sprite ")));
            moveRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/right/01_Slime_Spiked_Run.png"), "Missing slime right sprite 2")));
            moveRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/right/02_Slime_Spiked_Run.png"), "Missing slime idle right sprite 3")));
            moveRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/right/03_Slime_Spiked_Run.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleMobList(this, moveRightSpriteList, 150, 150);

            moveLeftSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/00_slime_run_left.png"), "Missing slime right sprite 1 ")));
            moveLeftSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/01_slime_run_left.png"), "Missing slime right sprite 2")));
            moveLeftSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/02_slime_run_left.png"), "Missing slime idle right sprite 3")));
            moveLeftSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/03_slime_run_left.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleMobList(this,moveLeftSpriteList, 150, 150);

            idleLeftSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/00_Slime_Spiked_Idle.png"), "Missing slime right sprite 1")));
            idleLeftSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/01_Slime_Spiked_Idle.png"), "Missing slime right sprite 2")));
            idleLeftSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/02_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 3")));
            idleLeftSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/03_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleMobList(this,idleLeftSpriteList, 150, 150);

            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/00_Slime_Spiked_Idle.png"), "Missing slime right sprite 1")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/01_Slime_Spiked_Idle.png"), "Missing slime right sprite 2")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/02_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 3")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/03_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleMobList(this, idleRightSpriteList, 150, 150);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(250)+1;

            if (i <= 25) {
                action = "moveUp";
                currentSpriteList = moveRightSpriteList;
            }
            if (i > 25 && i <= 50){
                action = "moveDown";
                currentSpriteList = moveLeftSpriteList;
            }
            if (i > 50 && i <= 75) {
                action = "moveLeft";
                currentSpriteList = moveLeftSpriteList;
            }
            if (i > 75 && i <= 100) {
                action = "moveRight";
                currentSpriteList = moveRightSpriteList;
            }
            if (i > 100 && i <= 125) {
                action = "idleRight";
                currentSpriteList = idleRightSpriteList;
            }
            if (i > 125 && i <= 150) {
                action = "idleLeft";
                currentSpriteList = idleLeftSpriteList;
            }
            if (i > 150 && i <= 175) {
                action = "moveUpRight";
                currentSpriteList = moveRightSpriteList;
            }
            if (i > 175 && i <= 200) {
                action = "moveDownRight";
                currentSpriteList = moveRightSpriteList;
            }
            if (i > 200 && i <= 225) {
                action = "moveUpLeft";
                currentSpriteList = moveLeftSpriteList;
            }
            if (i > 225) {
                action = "moveDownLeft";
                currentSpriteList = moveLeftSpriteList;
            }
            actionLockCounter = 0;
        }
    }
}