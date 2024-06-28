package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class NPCMystery extends Entity {
    public NPCMystery(GamePanel gp){
        super(gp);
        action = "idleRight";
//        message = "Wanna buy some magic crystals?";
        getNpcSprites();
    }

    public void getNpcSprites() {
        try {
            idleRightSpriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/shadyguy/shady_guy_00.png"), "Missing Mystery idle right sprite 1")));
            idleRightSpriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/shadyguy/shady_guy_01.png"), "Missing Mystery idle right sprite 2")));
            idleRightSpriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/shadyguy/shady_guy_02.png"), "Missing Mystery idle right sprite 3")));
            idleRightSpriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/shadyguy/shady_guy_03.png"), "Missing Mystery right sprite 4")));
            idleRightSpriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/NPCs/shadyguy/shady_guy_04.png"), "Missing Mystery idle right sprite 5")));
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
