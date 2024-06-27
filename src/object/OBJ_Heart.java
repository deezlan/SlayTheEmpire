package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;
    BufferedImage sprite1, sprite2, sprite3;
    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";

        // Load shop sprites
        try {
            sprite1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_blank.png"), "Missing life sprite 0"));
            sprite1 = uTool.scaleImage(sprite1, gp.TILE_SIZE, gp.TILE_SIZE);

            sprite2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_half.png"), "Missing life sprite 1"));
            sprite2 = uTool.scaleImage(sprite2, gp.TILE_SIZE, gp.TILE_SIZE);

            sprite3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_full.png"), "Missing life sprite 2"));
            sprite3 = uTool.scaleImage(sprite3, gp.TILE_SIZE, gp.TILE_SIZE);
            spriteList.add(0, sprite1);
            spriteList.add(1, sprite2);
            spriteList.add(2, sprite3);

        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
