package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shop extends SuperObject {
    public OBJ_Shop() {
        name = "Shop";

        // Load shop sprites
        try {
            imageList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/0.png"), "Missing shop sprite 1")));
            imageList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/1.png"), "Missing shop sprite 2")));
            imageList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/2.png"), "Missing shop sprite 3")));
            imageList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/shop/3.png"), "Missing shop sprite 4")));
            System.out.println(imageList.size());
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
