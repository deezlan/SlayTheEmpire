package main;

import entity.Entity;
import entity.NPC_Blacksmith;
import object.OBJ_Coin;
import object.OBJ_Heart;
//import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    public Entity npc;
    GamePanel gp;

    BufferedImage fullHeart, halfHeart, emptyHeart, hotbar;
    Graphics2D g2;
    Entity coin;

    public String currentDialog = "";
    public int slotRow = 0;
    public int slotRowMove = 0;
    int counter = 0;
    int charIndex = 0;
    String combinedText = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        //HUD Components
        Entity heart = new OBJ_Heart(gp);
        fullHeart = heart.defaultList.get(2);
        halfHeart = heart.defaultList.get(1);
        emptyHeart = heart.defaultList.get(0);
        try {
            hotbar = UtilityTool.loadSprite("/objects/hotbar/hotbar.png", "Cannot load hotbar");
            hotbar = UtilityTool.scaleImage(hotbar, gp.TILE_SIZE+24, gp.TILE_SIZE+24);
        } catch (IOException e){
            e.printStackTrace(System.out);
        }

        coin = new OBJ_Coin(gp);
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 28));
        g2.setColor(Color.white);
        if (gp.gameState == gp.playState) {
            drawPlayerMoney();
            drawHotbar();
            drawPlayerLife();
            drawAllMobHP();
        }

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

        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
    }

    public void drawHotbar() {
        int frameX = gp.TILE_SIZE/2;
        int frameY = gp.TILE_SIZE*11-5;
        int frameWidth = (gp.TILE_SIZE + gp.TILE_SIZE/2)*3;
        int frameHeight= gp.TILE_SIZE+gp.TILE_SIZE/2;

        Color custom = new Color(0,0,0,0);
        g2.setColor(custom);
        g2.fillRoundRect(frameX,frameY,frameWidth,frameHeight,0,0);
        for (int i = 0; i<3; i++){
            g2.drawImage(hotbar, frameX+((gp.TILE_SIZE + gp.TILE_SIZE/2)*i), frameY, null);
        }
        int x = 0;
        for (Entity i : gp.player.hotbarList){
            BufferedImage image = i.weaponSprite;
            image = UtilityTool.scaleImage(image, gp.TILE_SIZE-8, gp.TILE_SIZE-8);
            g2.drawImage(image, frameX+16+((gp.TILE_SIZE + gp.TILE_SIZE/2)*x), frameY+16, null);
            x++;
        }

    }

    public void drawShop() {
        try {
        NPC_Blacksmith bs = (NPC_Blacksmith) gp.npcArr[gp.currentMap][1];
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
            BufferedImage BI = bs.getShopItems().get(i).weaponSprite;
            BufferedImage coinImage = coin.defaultList.get(0);
            coinImage = UtilityTool.scaleImage(coinImage, 36, 36);

            g2.drawImage(BI, slotXstart + gp.TILE_SIZE/4, slotY + gp.TILE_SIZE/4, null);
            g2.drawString(bs.getShopItems().get(i).name, slotXstart + gp.TILE_SIZE + 10, slotY + 32);
            g2.drawString(Integer.toString(bs.getShopItems().get(i).price), slotXstart + gp.TILE_SIZE*8 - 40, slotY + 32);
            g2.drawImage(coinImage, slotXstart + gp.TILE_SIZE*8+15, slotY+7, null);

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

        //DESC FRAME
        int descX = frameX;
        int descY = frameY + frameHeight;
        int descWidth = frameWidth;
        int descHeight = gp.TILE_SIZE*3;
        drawSubWindow(descX, descY, descWidth, descHeight);

        g2.setColor(Color.BLACK);
        g2.fillRoundRect(descX, descY, descWidth, descHeight, 0, 0);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke((5)));
        g2.drawRoundRect(descX, descY, descWidth, descHeight, 0, 0);
        //DRAW DESC TEXT
        int textX = descX + 20;
        int textY= descY + gp.TILE_SIZE;
        g2.setFont(arcade);
        if (slotRow == 0){
            g2.drawString(bs.getShopItems().get(0).description, textX, textY);
            g2.drawString("Damage: " + bs.getShopItems().get(0).damage, textX, textY+30);
            if (gp.player.ownedWeapon.contains(0)){
                g2.drawString("Item Bought, ENTER to equip", textX, textY+60);
            } else if (gp.player.totalCoins < bs.getShopItems().get(slotRow).price){
                g2.drawString("You don't have money!", textX, textY+60);
            }
        } else if (slotRow == 1) {
            g2.drawString(bs.getShopItems().get(1).description, textX, textY);
            g2.drawString("Damage: " + bs.getShopItems().get(1).damage, textX, textY+30);
            if (gp.player.ownedWeapon.contains(1)){
                g2.drawString("Item Bought, ENTER to equip", textX, textY+60);
            } else if (gp.player.totalCoins < bs.getShopItems().get(slotRow).price){
                g2.drawString("You don't have money!", textX, textY+60);
            }
        } else if (slotRow == 2) {
            g2.drawString(bs.getShopItems().get(2).description, textX, textY);
            g2.drawString("Damage: " + bs.getShopItems().get(2).damage, textX, textY+30);
            if (gp.player.ownedWeapon.contains(2)){
                g2.drawString("Item Bought, ENTER to equip", textX, textY+60);
            } else if (gp.player.totalCoins < bs.getShopItems().get(slotRow).price){
                g2.drawString("You don't have money!", textX, textY+60);
            }
        } else if (slotRow == 3) {
            g2.drawString(bs.getShopItems().get(3).description, textX, textY);
            g2.drawString("Damage: " + bs.getShopItems().get(3).damage, textX, textY+30);
            if (gp.player.ownedWeapon.contains(3)){
                g2.drawString("Item Bought, ENTER to equip", textX, textY+60);
            } else if (gp.player.totalCoins < bs.getShopItems().get(slotRow).price){
                g2.drawString("You don't have money!", textX, textY+60);
            }
        }
        } catch (FontFormatException | IOException e){
            e.printStackTrace(System.out);
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

        while (i < gp.player.currentLife){
            g2.drawImage (halfHeart, posX, posY, null);
            i++;
            if (i < gp.player.currentLife)
                g2.drawImage(fullHeart, posX, posY, null);
            i++;
            posX += gp.TILE_SIZE;
        }
    }

    public void drawPlayerMoney() {
        coin.spriteCounter++;
        if (coin.spriteCounter > 4) coin.runCurrentListAnimation();
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40));
        g2.drawString ("" + gp.player.totalCoins, 38, 117);
        if (coin.spriteNum == coin.defaultList.size() - 1)
            coin.spriteNum = 0;
        g2.drawImage(coin.defaultList.get(coin.spriteNum), 118, 78, null);
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

        if(npc.dialogs[npc.dialogueSet][npc.dialogueIndex] != null){
//            currentDialog = npc.dialogs[npc.dialogueSet][npc.dialogueIndex];
            char[] characters = npc.dialogs[npc.dialogueSet][npc.dialogueIndex].toCharArray(); // DISABLE IF YOU WANT ALL TEXT TO DISPLAY
            if(charIndex < characters.length){
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialog = combinedText;
                charIndex++;
            }
            if(gp.keyH.ePressed){
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.dialogueState){
                    npc.dialogueIndex++;
                    gp.keyH.ePressed = false;
                } else if (gp.gameState == gp.cutsceneState) {
                    npc.dialogueIndex++;
                    gp.keyH.ePressed = false;
                }
            }
        } else {
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            } else if(gp.gameState == gp.cutsceneState){
                gp.csManager.scenePhase++;
            }
        }

        for(String line : currentDialog.split("\n")){ // breaks long dialogues // for up to use
            g2.drawString(line,dialogX,dialogY);
            dialogY += 40;
        }
    }

    public void drawDeathScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0, gp.SCREEN_WIDTH , gp.SCREEN_HEIGHT);
        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text = "KAMU DAH MATI";

        // SHADOW
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.TILE_SIZE*4;
        g2.drawString(text,x,y);
        // MAIN MESSAGE
        g2.setColor(Color.white);
        g2.drawString(text, x-4,y-4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "PRESS E TO RETRY";
        x = getXforCenteredText(text);
        y += gp.TILE_SIZE*4;
        g2.drawString(text,x,y);

        if(gp.keyH.ePressed){
            drawDeathTransition();
        }
//        int x = getXforCenteredText(text);
//        int y = gp.SCREEN_HEIGHT/2;
//
//        g2.drawString(text, x, y);
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
    //  DRAWS TRANSITION EVENT WHEN ENTERING OR EXITING
    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.setMapColor();
            gp.player.worldX = gp.TILE_SIZE * gp.eHandler.tempCol;
            gp.player.worldY = gp.TILE_SIZE * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawAllMobHP() {
        for(int i = 0; i < gp.mobArr[1].length; i++){
            Entity mob = gp.mobArr[gp.currentMap][i]; // LOCAL ENTITY FOR SHORTER CODE
            if(mob != null && mob.inCamera()){
                if (mob.hpBarVisible && !mob.boss) {
                    double oneScale = (double) gp.TILE_SIZE / mob.maxLife;
                    double hpBarValue = oneScale * mob.currentLife;
                    if (mob.mobNum == 1) { // SLIME
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 51, mob.getScreenY() + 121, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 50, mob.getScreenY() + 120, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 2) { // SKELLINGTON
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 51, mob.getScreenY() + 141, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 50, mob.getScreenY() + 140, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 3) { // ROBOT GUARDIAN
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 81, mob.getScreenY() + 161, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 4) { // RAMSES
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 61, mob.getScreenY() + 121, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 60, mob.getScreenY() + 120, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 5) { // GOBLIN
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 81, mob.getScreenY() + 141, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 6) { // SKELETON KNIGHT
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 81, mob.getScreenY() + 141, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX()+ 80, mob.getScreenY() + 140, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 7) { // ARMORED GUARDIAN
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 51, mob.getScreenY() + 121, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 50, mob.getScreenY() + 120, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 8) { // FLYING EYE
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 121, mob.getScreenY() + 191, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX() + 120, mob.getScreenY() + 190, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 9) { // MUSHROOM
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 126, mob.getScreenY() + 211, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX()+ 125, mob.getScreenY() + 210, (int) hpBarValue, 9);
                    }
                    if (mob.mobNum == 10) { // CANINE
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(mob.getScreenX() + 21, mob.getScreenY()+ 91, gp.TILE_SIZE, 10);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(mob.getScreenX()+ 20, mob.getScreenY() + 90, (int) hpBarValue, 9);
                    }
                    mob.hpBarCounter++;
                    if (mob.hpBarCounter > 600) {
                        mob.hpBarCounter = 0;
                        mob.hpBarVisible = false;
                    }
                } else if(mob.boss) {
                    double oneScale = (double) gp.TILE_SIZE*8 / mob.maxLife;
                    double hpBarValue = oneScale * mob.currentLife;

                    int x = gp.SCREEN_WIDTH/2 - gp.TILE_SIZE*2;
                    int y = gp.SCREEN_HEIGHT - 40;
                    if(mob.bossNum == 1){ // FROST GIANT
                        g2.setColor(new Color(35, 35, 35));
                        g2.fillRect(x-1, y-1, gp.TILE_SIZE*8 + 2, 22);
                        g2.setColor(new Color(255, 0, 30));
                        g2.fillRect(x, y, (int) hpBarValue, 20);

                        g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
                        g2.setColor(Color.white);
                        g2.drawString(mob.name,x+4,y-10);
                    }
                }
            }
        }
    }

    public void drawDeathTransition() {
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        if(counter == 50){
            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = 0;
            gp.setMapColor();
            gp.retry();
        }
    }
}
