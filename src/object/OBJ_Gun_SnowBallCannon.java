package object;

import entity.Projectile;
import main.GamePanel;

import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class OBJ_Gun_SnowBallCannon extends Projectile{
    GamePanel gp;
    public OBJ_Gun_SnowBallCannon(GamePanel gp) {
        super(gp);
        this.gp = gp;
        speed = 5;
        attack = 2;
        loadImages();
    }

    private void loadImages() {
        try {
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon0.png"), "Missing snowballcannon0")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon1.png"), "Missing snowballcannon1")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon2.png"), "Missing snowballcannon2")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon3.png"), "Missing snowballcannon3")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon4.png"), "Missing snowballcannon4")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon5.png"), "Missing snowballcannon5")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon6.png"), "Missing snowballcannon6")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon7.png"), "Missing snowballcannon7")));
            spriteList.add( ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/projectile/snowballcannon/snowballcannon8.png"), "Missing snowballcannon8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    NOT USED YET
    public void update(){
    }


}

