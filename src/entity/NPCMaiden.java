package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class NPCMaiden extends Entity {
    public NPCMaiden(GamePanel gp) {
        super(gp);
        action = "idleRight";
        spriteWidth = 90;
        spriteHeight= 90;
        getNpcSprites();
    }

    public void getNpcSprites() {
        try {
            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_00.png"), "Missing Maiden idle right sprite 1")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_01.png"), "Missing Maiden idle right sprite 2")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_02.png"), "Missing Maiden idle right sprite 3")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_03.png"), "Missing Maiden idle right sprite 4")));
            idleRightSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_04.png"), "Missing Maiden idle right sprite 5")));
            System.out.println("NPC images loaded successfully");
            currentSpriteList = idleRightSpriteList;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
