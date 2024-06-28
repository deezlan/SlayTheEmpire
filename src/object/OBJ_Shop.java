package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shop extends SuperObject {
    public OBJ_Shop(GamePanel gp) {
        name = "Shop";
        message = "Shop closed bitch";
//        spriteWidth = 198;
//        spriteHeight = 128;

        // Load shop sprites
        try {
            spriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/0.png"), "Missing shop sprite 0")));
            spriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/1.png"), "Missing shop sprite 1")));
            spriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/2.png"), "Missing shop sprite 2")));
            spriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/3.png"), "Missing shop sprite 3")));

            UtilityTool.scaleObjectList(this, spriteList, (int)(gp.TILE_SIZE*3.6), (int)(gp.TILE_SIZE*2.4));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        solidArea.x = 20;
        solidArea.y = 0;
        solidArea.width = 172;
        solidArea.height = 130;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
    }
}
