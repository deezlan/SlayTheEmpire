package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_SavePedestal extends SuperObject {
    public OBJ_SavePedestal() {
        name = "Save Pedestal";
        message = "Saving not implemented... go away";
        spriteWidth = 32;
        spriteHeight = 96;

        // Load save pedestal sprites
        try {
            spriteList.add(0, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/0.png"), "Missing save pedestal sprite 0")));
            spriteList.add(1, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/1.png"), "Missing save pedestal sprite 1")));
            spriteList.add(2, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/2.png"), "Missing save pedestal sprite 2")));
            spriteList.add(3, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/3.png"), "Missing save pedestal sprite 3")));
            spriteList.add(4, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/4.png"), "Missing save pedestal sprite 4")));
            spriteList.add(5, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/5.png"), "Missing save pedestal sprite 5")));
            spriteList.add(6, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/6.png"), "Missing save pedestal sprite 6")));
            spriteList.add(7, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/objects/savePedestal/7.png"), "Missing save pedestal sprite 7")));
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        solidArea.x = 0;
        solidArea.y = 50;
        solidArea.width = 24;
        solidArea.height = 60;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collision = true;
    }
}
