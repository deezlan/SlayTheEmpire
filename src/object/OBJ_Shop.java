package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shop extends SuperObject {
    public OBJ_Shop() {
        name = "Shop";
        spriteWidth = 198;
        spriteHeight = 128;

        // Load shop sprites
        try {
            imageList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/0.png"), "Missing shop sprite 0")));
            imageList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/1.png"), "Missing shop sprite 1")));
            imageList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/2.png"), "Missing shop sprite 2")));
            imageList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/3.png"), "Missing shop sprite 3")));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        solidArea.x = 32;
        solidArea.y = 96;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
    }
}
