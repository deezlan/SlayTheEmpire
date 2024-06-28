package object;

import entity.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Gun_SnowBallCannon extends Projectile{
    private BufferedImage[] frames;


    public OBJ_Gun_SnowBallCannon(GamePanel gp) {
        super(gp);
        speed = 5;
        attack = 2;
        loadImages();
    }

private void loadImages() {
        frames = new BufferedImage[16]; // Assuming there are 16 frames
        try {
            frames[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon0")));
            frames[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon1")));
            frames[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon2")));
            frames[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon3")));
            frames[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon4")));
            frames[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon5")));
            frames[6] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon6")));
            frames[7] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon7")));
            frames[8] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/projectile/snowballcannon/snowballcannon8")));;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}