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
        proCounter++;
        loopThroughSNCSprites();
    }

    public void loopThroughSNCSprites(){
        proNum = (proNum < spriteList.size()) ? proNum + 1 : 1;
        if (proCounter <= 5) {
            proNum = 1;
        } else if (proCounter <= 10) {
            proNum = 2;
        } else if (proCounter <= 15) {
            proNum = 3;
        } else if (proCounter <= 20) {
            proNum = 4;
        } else if (proCounter <= 25) {
            proNum = 5;
        } else if (proCounter <= 30) {
            proNum = 6;
        } else if (proCounter <= 35) {
            proNum = 7;
        } else if (proCounter <= 40) {
            proNum = 8;
        } else if (proCounter <= 45) {
            proNum = 9;
        } else if (proCounter <= 55 ){
            proNum = 1;
            proCounter = 0;
        }
    }

}

