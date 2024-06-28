package object;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        this.gp = gp;
        name = "Heart";

        // Load shop sprites
        try {
            spriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_blank.png"), "Missing life sprite 0")));
            spriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_half.png"), "Missing life sprite 1")));
            spriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/life/heart_full.png"), "Missing life sprite 2")));

            UtilityTool.scaleObjectList(this, spriteList, gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
