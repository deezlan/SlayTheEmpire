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
        speed = 1;
        maxLife = 4;
        life = maxLife;
        action = "idleRight";
        solidArea.x = 2;
        solidArea.y = gp.TILE_SIZE;
        solidArea.width = 42;
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
            UtilityTool.scaleEntityList(this, moveRightSpriteList, 100, 100);

            moveLeftSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/00_slime_run_left.png"), "Missing slime right sprite 1 ")));
            moveLeftSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/01_slime_run_left.png"), "Missing slime right sprite 2")));
            moveLeftSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/02_slime_run_left.png"), "Missing slime idle right sprite 3")));
            moveLeftSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/left/03_slime_run_left.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleEntityList(this,moveLeftSpriteList, 100, 100);

            idleLeftSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/00_Slime_Spiked_Idle.png"), "Missing slime right sprite 1")));
            idleLeftSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/01_Slime_Spiked_Idle.png"), "Missing slime right sprite 2")));
            idleLeftSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/02_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 3")));
            idleLeftSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/03_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleEntityList(this,idleLeftSpriteList, 100, 100);

            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/00_Slime_Spiked_Idle.png"), "Missing slime right sprite 1")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/01_Slime_Spiked_Idle.png"), "Missing slime right sprite 2")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/02_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 3")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/Mobs/Slime/idle/03_Slime_Spiked_Idle.png"), "Missing slime idle right sprite 4")));
            System.out.println("NPC images loaded successfully");
            UtilityTool.scaleEntityList(this,idleRightSpriteList, 100, 100);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }


    public void setAction() {
        actionLockCounter++;

        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if(i <= 25) {
                action = "moveUp";
                currentSpriteList = idleRightSpriteList;
            }
            if(i > 25 && i <= 50){
                action = "moveDown";
                currentSpriteList = idleRightSpriteList;
            }
            if(i > 50 && i <= 75) {
                action = "moveLeft";
                currentSpriteList = idleRightSpriteList;
            }
            if(i > 75) {
                action = "moveRight";
                currentSpriteList = idleRightSpriteList;
            }
            actionLockCounter = 0;
        }
    }
}