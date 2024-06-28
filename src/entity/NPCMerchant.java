package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class NPCMerchant extends Entity {
    public NPCMerchant(GamePanel gp) {
        super(gp);
        action = "idleRight";
        message = "Potions are best of quality!";
        spriteWidth = 90;
        spriteHeight= 90;
        getNpcSprites();
    }

    public void getNpcSprites() {
        try {
            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/Merchant/merchant_00.png"), "Missing merchant idle right sprite 1")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/Merchant/merchant_01.png"), "Missing merchant idle right sprite 2")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/Merchant/merchant_02.png"), "Missing merchant idle right sprite 3")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/Merchant/merchant_02.png"), "Missing merchant idle right sprite 4")));
            idleRightSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/Merchant/merchant_04.png"), "Missing merchant idle right sprite 5")));
            System.out.println("NPC images loaded successfully");
            currentSpriteList = idleRightSpriteList;
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