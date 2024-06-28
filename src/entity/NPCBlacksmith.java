package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class NPCBlacksmith extends Entity {
    public NPCBlacksmith(GamePanel gp) {
        super(gp);
        action = "idleRight";
//        message = "Hands off the hammer!";
        getNpcSprites();
    }

    public void getNpcSprites() {
        try {
            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/blacksmith/blacksmith_00.png"), "Missing smith idle right sprite 1")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/blacksmith/blacksmith_01.png"), "Missing smith idle right sprite 2")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/blacksmith/blacksmith_02.png"), "Missing smith idle right sprite 3")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/blacksmith/blacksmith_03.png"), "Missing smith idle right sprite 4")));
            idleRightSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/blacksmith/blacksmith_04.png"), "Missing smith idle right sprite 5")));
            System.out.println("NPC images loaded successfully");

            UtilityTool.scaleEntityList(this, idleRightSpriteList, 90, 90);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        solidArea.x = 20;
        solidArea.y = gp.TILE_SIZE;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}