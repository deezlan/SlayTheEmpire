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
        fullHeart = heart.scaledList.get(2);
        halfHeart = heart.scaledList.get(1);
        emptyHeart = heart.scaledList.get(0);
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 28));
        g2.setColor(Color.white);

//        if (gp.gameState == gp.playState) {
//
//        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        drawPlayerLife();
    }

    public void drawPlayerLife(){
        int posX = gp.TILE_SIZE/2;
        int posY = gp.TILE_SIZE/2;
        int i = 0;

        while (i < gp.player.maxLife/2){
            g2.drawImage(emptyHeart, posX, posY, null);
            i++;
            posX += gp.TILE_SIZE;
        }

        posX = gp.TILE_SIZE/2;
        i = 0;

        while (i < gp.player.life){
            g2.drawImage (halfHeart, posX, posY, null);
            i++;
            if (i < gp.player.life)
                g2.drawImage(fullHeart, posX, posY, null);
            i++;
            posX += gp.TILE_SIZE;
        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        String text = "GO TOUCH GRASS BITCH";
        int x = getXforCenteredText(text);
        int y = gp.SCREEN_HEIGHT/2;

        g2.drawString(text, x, y);

    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREEN_WIDTH/2 - length/2;
        return x;
    }
}
