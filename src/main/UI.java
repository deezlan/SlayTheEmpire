package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    BufferedImage fullHeart, halfHeart, emptyHeart;
    Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;
    public int slotColMove = 0;
    public int slotRowMove = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        //HUD Components
        SuperObject heart = new OBJ_Heart(gp);
        fullHeart = heart.spriteList.get(2);
        halfHeart = heart.spriteList.get(1);
        emptyHeart = heart.spriteList.get(0);
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        drawPlayerLife();
        drawInventory();
    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0, 0, 0, 220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public void drawInventory() {
        int frameX = gp.TILE_SIZE*3;
        int frameY = gp.TILE_SIZE;
        int frameWidth = (gp.TILE_SIZE*5) + 25;
        int frameHeight= (gp.TILE_SIZE*9) + 25;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 35;
        final int slotYstart = frameY + 35;
        int slotX = slotXstart;
        int slotY = slotYstart;

        //DRAW PLAYER INVENTORY
        for (int i = 0; i < gp.player.inventory.size(); i++){
            //insert weapons
        }

        //CURSOR
        int cursorX = slotXstart + (gp.TILE_SIZE * slotColMove);
        int cursorY = slotYstart + (gp.TILE_SIZE * slotRowMove);
        int cursorWidth = gp.TILE_SIZE*2;
        int cursorHeight = gp.TILE_SIZE*2;
        //DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
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
            g2.drawImage (halfHeart, postX, postY, null);
            i++;
            if (i < gp.player.life)
                g2.drawImage(fullHeart, postX, postY, null);
            i++;
            postX += gp.TILE_SIZE;
        }
    }
}
