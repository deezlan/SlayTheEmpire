package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;
    BufferedImage image1, image2, image3;
    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";

        // Load shop sprites
        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_blank.png"), "Missing life sprite 0"));
            image1 = uTool.scaleImage(image1, gp.TILE_SIZE, gp.TILE_SIZE);
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_half.png"), "Missing life sprite 1"));
            image2 = uTool.scaleImage(image2, gp.TILE_SIZE, gp.TILE_SIZE);
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_full.png"), "Missing life sprite 2"));
            image3 = uTool.scaleImage(image3, gp.TILE_SIZE, gp.TILE_SIZE);
            imageList.add(0, image1);
            imageList.add(1, image2);
            imageList.add(2, image3);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
