package main;

import object.OBJ_Coin;
import object.OBJ_Heart;
import object.SuperObject;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    GamePanel gp;

    BufferedImage fullHeart, halfHeart, emptyHeart;
    Graphics2D g2;
    SuperObject coin = new OBJ_Coin(gp);

    public String currentDialog = "";
    public int slotRow = 0;
    public int slotRowMove = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        //HUD Components
        SuperObject heart = new OBJ_Heart(gp);
        fullHeart = heart.defaultList.get(2);
        halfHeart = heart.defaultList.get(1);
        emptyHeart = heart.defaultList.get(0);

//        coin = new OBJ_Coin(gp);
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 28));
        g2.setColor(Color.white);
        drawPlayerLife();
        if (gp.gameState == gp.playState) drawPlayerMoney();

        //Pause State
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        //Dialog State
        if(gp.gameState == gp.dialogueState){
            drawDialogScreen();
        }

        if (gp.gameState == gp.shopState){
            drawShop();
        }

        if (gp.gameState == gp.deathState) {
            drawDeathScreen();
        }
    }

    public void drawShop() {
        try {
            InputStream is = new FileInputStream("ARCADE_N.TTF");
            Font arcade = Font.createFont(Font.TRUETYPE_FONT, is);
            arcade = arcade.deriveFont(Font.PLAIN, 16);

        int frameX = gp.TILE_SIZE*2;
        int frameY = gp.TILE_SIZE;
        int frameWidth = (gp.TILE_SIZE*9) + 25;
        int frameHeight= (gp.TILE_SIZE*4) + 40;
        g2.setFont(arcade);

        g2.setColor(Color.BLACK);
        g2.fillRoundRect(frameX,frameY,frameWidth,frameHeight,0,0);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke((5)));
        g2.drawRoundRect(frameX,frameY,frameWidth,frameHeight,0,0);

        g2.setColor(Color.WHITE);

        //SLOT
        final int slotXstart = frameX + 15;
        final int slotYstart = frameY + 38;
        int slotY = slotYstart;
        g2.drawString("Item", frameX + 15, frameY + 30);
        g2.drawString("Price", frameX + gp.TILE_SIZE*8 - 15, frameY + 30);
        //DRAW SHOP
        for (int i = 0; i < 4; i++){
            NPC_Blacksmith bs = (NPC_Blacksmith) gp.npcArr[2];
            BufferedImage BI = bs.getShopItems().get(i).weaponSprite;

            g2.drawImage(BI, slotXstart + gp.TILE_SIZE/4, slotY + gp.TILE_SIZE/4, null);
            g2.drawString(bs.getShopItems().get(i).name, slotXstart + gp.TILE_SIZE + 10, slotY + 32);
            g2.drawString(bs.getShopItems().get(i).price, slotXstart + gp.TILE_SIZE*8, slotY + 32);

            slotY += gp.TILE_SIZE;
        }

        //CURSOR
        int cursorY = slotYstart + (gp.TILE_SIZE * slotRowMove);
        int cursorWidth = gp.TILE_SIZE*9+15;
        int cursorHeight = gp.TILE_SIZE;
        //DRAW CURSOR
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(frameX+3, cursorY, cursorWidth+3, cursorHeight, 0, 0);

        } catch (FontFormatException | IOException fn){
            fn.printStackTrace();
        }
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

    public void drawPlayerMoney() {
        coin.spriteCounter++;
        if (coin.spriteCounter > 4) coin.loopThroughSprites();
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
        g2.drawString ("" + gp.player.totalCoins, 38, 117);
        g2.drawImage(coin.defaultList.get(coin.spriteNum), 78, 78, null);
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

        for(String line : currentDialog.split("\n")){ // breaks long dialogues // for up to use
            g2.drawString(line,dialogX,dialogY);
            dialogY += 40;
        }
    }

    public void drawDeathScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD));
        String text = "KAMU DAH MATI";
        int x = getXforCenteredText(text);
        int y = gp.SCREEN_HEIGHT/2;

        g2.drawString(text, x, y);
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
