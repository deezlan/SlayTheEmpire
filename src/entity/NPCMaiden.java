package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class NPCMaiden extends Entity {
    public NPCMaiden(GamePanel gp) {
        super(gp);
        action = "idleRight";
        speed = 1;
        spriteWidth = 70;
        spriteHeight= 70;
        getNPCImage();
    }

    public void getNPCImage() {
        try {
            idleRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_00.png"), "Missing Maiden idle right sprite 1"));
            idleRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_01.png"), "Missing Maiden idle right sprite 2"));
            idleRight3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_02.png"), "Missing Maiden idle right sprite 3"));
            idleRight4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_03.png"), "Missing Maiden idle right sprite 4"));
            idleRight5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/maiden/villager_01_04.png"), "Missing Maiden idle right sprite 5"));
            System.out.println("Images loaded successfully");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
