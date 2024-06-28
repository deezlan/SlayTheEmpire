package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;

import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    BufferedImage fullHeart, halfHeart, emptyHeart;
    Graphics2D g2;
    public String currentDialog = "";

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
        drawPlayerLife();

//        if (gp.gameState == gp.playState) {
//
//        }
        //Pause State
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        //Dialog State
        if(gp.gameState == gp.dialogueState){
            drawDialogScreen();
        }

//        if (gp.gameState == gp.shopState){
//            draw
//        }

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

    // Draw Pause Screen
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        String text = "GO TOUCH GRASS BITCH";
        int x = getXforCenteredText(text);
        int y = gp.SCREEN_HEIGHT/2;

        g2.drawString(text, x, y);

    }

    //Draw dialog
    public void drawDialogScreen() {
        // WINDOW
        int dialogX = gp.TILE_SIZE*2;
        int dialogY = gp.TILE_SIZE/2;
        int dialogWidth = gp.SCREEN_WIDTH - (gp.TILE_SIZE*4);
        int dialogHeight = gp.TILE_SIZE*4;
        drawSubWindow(dialogX,dialogY,dialogWidth,dialogHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32));
        dialogX += gp.TILE_SIZE;
        dialogY += gp.TILE_SIZE;
        for(String dialogs : currentDialog.split("/n")){ // breaks long dialogues // for up to use
            g2.drawString(currentDialog,dialogX,dialogY);
            dialogY += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width,int height){
        Color custom = new Color(0,0,0,220);
        g2.setColor(custom);
        g2.fillRoundRect(x,y,width,height,35,35);

        custom = new Color(255,255,255);
        g2.setColor(custom);
        g2.setStroke(new BasicStroke((5)));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.SCREEN_WIDTH/2 - length/2;
    }
}
