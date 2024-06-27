package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    BufferedImage fullHeart, halfHeart, emptyHeart;
    Graphics2D g2;

    public UI(GamePanel gp) {
        this.gp = gp;

        //HUD Components
        SuperObject heart = new OBJ_Heart(gp);
        fullHeart = heart.imageList.get(2);
        halfHeart = heart.imageList.get(1);
        emptyHeart = heart.imageList.get(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        drawPlayerLife();
    }

    public void drawPlayerLife(){

        int postX = gp.TILE_SIZE/2;
        int postY = gp.TILE_SIZE/2;
        int i = 0;

        while (i < gp.player.maxLife/2){
            g2.drawImage(emptyHeart, postX, postY, null);
            i++;
            postX += gp.TILE_SIZE;
        }

        postX = gp.TILE_SIZE/2;
        i = 0;

        while (i < gp.player.life){
            g2.drawImage(halfHeart, postX, postY, null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(fullHeart, postX, postY, null);
            }
            i++;
            postX += gp.TILE_SIZE;
        }
    }
}
