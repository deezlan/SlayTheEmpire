package main;

import entity.Entity;
import entity.NPC_Blacksmith;
import object.OBJ_Coin;
import object.OBJ_Heart;
//import object.SuperObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class UI {


    KeyHandler keyH;
    public Entity npc;
    GamePanel gp;
    BufferedImage fullHeart, halfHeart, emptyHeart, hotbar;
    Graphics2D g2;
    Entity coin;

    Font gameFont;
    private final Image titleGif;
    private final Image titleImage;
//    private final Image newGame;
//    private final Image loadGame;
//    private final Image options;
//    private final Image quit;
    private final Image warriorGif, knightGif, assassinGif;
    private final Image loginDefault, typeUsername, typePassword,
            blankErr, usernameErr, loginErr, usernameTakenErr;
    private final Image warriorSelected, knightSelected, assassinSelected;
    private final Image file1;
    private final Image file2;
    private final Image file3;
    private final Image emptySaveButton1;
    private final Image emptySaveButton2;
    private final Image emptySaveButton3;
    private final Image saveNotFound;
    private final Image saveFound;
    private final Image saveFileBackground;
    private final Image overrideBox;
    private final Image progressSavedBox;
    private final Image selectFileBox;
    private final Image bg;
    private final Image bg2;
    private final Image startMenu;
    private final Image toStartMenuBox;

    Rectangle saveButtonBounds1;
    Rectangle saveButtonBounds2;
    Rectangle saveButtonBounds3;
    Rectangle toStartMenuRect;

    public String currentDialog = "";
    public int slotRow = 0;
    public int slotRowMove = 0;
    int counter = 0;
    int charIndex = 0;
    String combinedText = "";
    public int commandNum = 0;
    int saveButtonX;
    int saveButtonY;
    int saveButtonWidth;
    int saveButtonHeight;
    int toStartHeight;
    int toStartWidth;
    int subState = 0;

    // Login variables
    public String inpUser = "", inpPass = "", inpPassHidden = "";
    public boolean hasBlankField = false,
            isInvalidUsername = false,
            isInvalidLogin = false,
            usernameTaken = false,
            validLogin = false,
            typingUsername = false,
            typingPassword = false;

    public String[] musicCredits = {
            "Music/SFX by:",
            "Sara Garrard",
            "RandomizedRandomizer",
            "Leohpaz"
    };

    public String[][] spriteCredits = {
            {"", "Sprites/Art by:", ""},
            {"Admurin", "David G.", "Cainos"},
            {"Eddie's Workshop", "Chierit", "CreativeKind"},
            {"Ho88it", "Kronovi", "HorusKDI"},
            {"LuizMelo", "RiLi_XL", "Snowhexart"},
            {"", "wxhaust", ""},
            {"", "", ""},
    };

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

        // FONT initialization
        try {
            GraphicsEnvironment font = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font gameFont = loadFont();
            font.registerFont(gameFont);
            this.gameFont = gameFont.deriveFont(Font.PLAIN, 13F);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace(System.out);
        }

        // INITIALIZE TITLE VIDEO
        ImageIcon icon = new ImageIcon("res/UI/Title.gif");
        titleGif = icon.getImage();

        // INITIALIZE TITLE
        titleImage = new ImageIcon("res/UI/Title.png").getImage();

        // INITIALIZE LOGIN SCREEN
        loginDefault = new ImageIcon("res/UI/login/loginDefault.png").getImage();
        typeUsername = new ImageIcon("res/UI/login/loginUsername.png").getImage();
        typePassword = new ImageIcon("res/UI/login/loginPassword.png").getImage();
        blankErr = new ImageIcon("res/UI/login/blankField.png").getImage();
        loginErr = new ImageIcon("res/UI/login/invalidLogin.png").getImage();
        usernameErr = new ImageIcon("res/UI/login/invalidUsername.png").getImage();
        usernameTakenErr = new ImageIcon("res/UI/login/usernameTaken.png").getImage();

        // INITIALIZE START MENU UI
        bg = new ImageIcon("res/UI/bg.png").getImage();
        bg2 = new ImageIcon("res/UI/bg2.png").getImage();
        startMenu = new ImageIcon("res/UI/startMenu.png").getImage();

        // INITIALIZE CHARACTER SELECT ASSETS
        warriorSelected = new ImageIcon("res/UI/character_select/warriorSelected.png").getImage();
        knightSelected = new ImageIcon("res/UI/character_select/knightSelected.png").getImage();
        assassinSelected = new ImageIcon("res/UI/character_select/assassinSelected.png").getImage();

        // INITIALIZE PLAYER PREVIEW
        ImageIcon warrior = new ImageIcon("res/player/WarriorIdle.gif");
        warriorGif = warrior.getImage();
        ImageIcon knight = new ImageIcon("res/player/KnightIdle.gif");
        knightGif = knight.getImage();
        ImageIcon assassin = new ImageIcon("res/player/AssassinIdle.gif");
        assassinGif = assassin.getImage();

        //INITIALIZE SAVE MENU UI
        file1 = new ImageIcon("res/UI/file1.png").getImage();
        file2 = new ImageIcon("res/UI/file2.png").getImage();
        file3 = new ImageIcon("res/UI/file3.png").getImage();
        saveFileBackground = new ImageIcon("res/UI/saveFileBackground.png").getImage();
        emptySaveButton1 = new ImageIcon("res/UI/emptySaveButton.png").getImage();
        emptySaveButton2 = new ImageIcon("res/UI/emptySaveButton.png").getImage();
        emptySaveButton3 = new ImageIcon("res/UI/emptySaveButton.png").getImage();
        saveFound = new ImageIcon("res/UI/saveFound.png").getImage();
        saveNotFound = new ImageIcon("res/UI/saveNotFound.png").getImage();
        overrideBox = new ImageIcon("res/UI/overrideBox.png").getImage();
        progressSavedBox = new ImageIcon("res/UI/progressSavedBox.png").getImage();
        selectFileBox = new ImageIcon("res/UI/selectFileBox.png").getImage();
        toStartMenuBox = new ImageIcon("res/UI/toStartMenuBox.png").getImage();

        // INITIALIZE SAVE BUTTON POSITIONS & IMAGE BOUNDS
        saveButtonX = 70 + ((gp.SCREEN_WIDTH - emptySaveButton1.getWidth(null)) / 2);
        saveButtonY = (gp.SCREEN_HEIGHT - emptySaveButton1.getHeight(null)) / 2;
        saveButtonWidth = (int)(emptySaveButton1.getWidth(null)/1.3);
        saveButtonHeight = (int)(emptySaveButton1.getHeight(null)/1.3);
        saveButtonBounds1 = new Rectangle(saveButtonX, (saveButtonY - 100), saveButtonWidth, saveButtonHeight);
        saveButtonBounds2 = new Rectangle(saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight);
        saveButtonBounds3 = new Rectangle(saveButtonX, (saveButtonY + 100), saveButtonWidth, saveButtonHeight);
        toStartHeight = (int)(toStartMenuBox.getHeight(null));
        toStartWidth = (int)(toStartMenuBox.getWidth(null));
        toStartMenuRect = new Rectangle(100, 100, toStartWidth, toStartHeight);
    }

    private Font loadFont() throws FontFormatException, IOException {
        File fontFile = new File("res/font/x12y16pxMaruMonica.ttf");
        return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fontFile));
    }

    public void drawBG() {
        g2.drawImage(bg, 0, 0, null);
    }

    public void drawBG2() {
        g2.drawImage(bg2, 0, 0, null);
    }

    public void drawCharacterSelectPreview() {
        int gifX = 300;
        int gifY = 150;

        switch (gp.ui.commandNum) {
            case 0 -> {
                g2.drawImage(warriorSelected, 0, 0, null);
                g2.drawImage(warriorGif, (gp.SCREEN_WIDTH - gifX)/2 - 225, (int) ((gp.SCREEN_HEIGHT - gifY)/3) + 40, gifX, gifY, null);
            }
            case 1 -> {
                g2.drawImage(knightSelected, 0, 0, null);
                g2.drawImage(knightGif, (gp.SCREEN_WIDTH - gifX)/2, (int) ((gp.SCREEN_HEIGHT - gifY)/3) + 50, gifX, gifY, null);
            }
            case 2 -> {
                g2.drawImage(assassinSelected, 0, 0, null);
                g2.drawImage(assassinGif, (gp.SCREEN_WIDTH - gifX)/2 + 225, (int) ((gp.SCREEN_HEIGHT - gifY)/3) + 40, gifX, gifY, null);
            }
        }
    }

    public void drawControls() {

    }

    // DRAW SAVE PAGE
    public void drawSavePage(){
        int imageWidth = (int)(gp.SCREEN_WIDTH/1.0000000002);
        int imageHeight = (int)(saveFileBackground.getHeight(null)*((double) imageWidth / saveFileBackground.getWidth(null)));
        int x = (gp.SCREEN_WIDTH - imageWidth) / 2;
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 2;

        g2.drawImage(saveFileBackground, x , y, imageWidth, imageHeight, null);
        g2.drawImage(toStartMenuBox, 100, 100, toStartWidth, toStartHeight, null);

        if (!gp.saveLoad.slotCheck[0] && !gp.saveLoad.slotCheck[1] && !gp.saveLoad.slotCheck[2]){
            saveFileDialogue(5);
        } else if (!gp.mouseH.leftClicked){
            saveFileDialogue(4);
        }


        if(!gp.saveLoad.isloadPage && gp.mouseH.leftClicked){
            if (saveButtonBounds1.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())){
                gp.saveLoad.clearSaveFile(0);
                gp.saveLoad.save(0);
                System.out.println("successfully saved file 2");
            } else if (saveButtonBounds2.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())){
                gp.saveLoad.clearSaveFile(1);
                gp.saveLoad.save(1);
                System.out.println("successfully saved file 2");
            } else if(saveButtonBounds3.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())){
                gp.saveLoad.clearSaveFile(2);
                gp.saveLoad.save(2);
                System.out.println("successfully saved file 3");
            }
        } else if (gp.saveLoad.isloadPage && gp.mouseH.leftClicked){
            if (saveButtonBounds1.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())){
                gp.saveLoad.load(0);
                gp.gameState = gp.playState;
            } else if (saveButtonBounds2.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())){
                gp.saveLoad.load(1);
                gp.gameState = gp.playState;
            } else if (saveButtonBounds3.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())){
                gp.saveLoad.load(2);
                gp.gameState = gp.playState;
            }
        }


        gp.mouseH.clearMouseClick();
        drawSaveButtons();

    }


    // DRAW FILE DIALOGUE
    public void saveFileDialogue(int boxNumber){
        int x  = ((gp.SCREEN_WIDTH - saveFound.getWidth(null)) / 2);
        int y  = (((gp.SCREEN_HEIGHT - saveFound.getHeight(null)) / 2) + 220);

        switch (boxNumber){
            case 0:
                g2.drawImage(overrideBox, x, y, saveFound.getWidth(null), saveFound.getHeight(null), null);
                break;
            case 1:
                g2.drawImage(progressSavedBox, x, y, saveFound.getWidth(null), saveFound.getHeight(null), null);
                break;
            case 2:
                g2.drawImage(selectFileBox, x, y, saveFound.getWidth(null), saveFound.getHeight(null), null);
                break;
            case 4:
                g2.drawImage(saveFound, x, y, saveFound.getWidth(null), saveFound.getHeight(null), null);
                break;
            case 5:
                g2.drawImage(saveNotFound, x, y, saveFound.getWidth(null), saveFound.getHeight(null), null);
                break;
        }
    }

    // DRAW SAVE BUTTONS

    public void drawSaveButtons(){
        if(gp.saveLoad.isSaveFileEmpty(0)){
            g2.drawImage(emptySaveButton1, saveButtonX, (saveButtonY - 100), saveButtonWidth, saveButtonHeight, null);
        } else{
            g2.drawImage(file1, saveButtonX, (saveButtonY - 100), saveButtonWidth, saveButtonHeight, null);
        }

        if(gp.saveLoad.isSaveFileEmpty(1)){
            g2.drawImage(emptySaveButton2, saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight, null);
        } else{
            g2.drawImage(file2, saveButtonX, saveButtonY, saveButtonWidth, saveButtonHeight, null);
        }

        if(gp.saveLoad.isSaveFileEmpty(2)){
            g2.drawImage(emptySaveButton3, saveButtonX, (saveButtonY + 100), saveButtonWidth, saveButtonHeight, null);
        } else{
            g2.drawImage(file3, saveButtonX, (saveButtonY + 100), saveButtonWidth, saveButtonHeight, null);
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

    public void drawCredits(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        g2.setColor(Color.WHITE);
        g2.drawImage(bg, 0, 0, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        String text = "Special Thanks";
        g2.drawString(text, getXForCenteredText(text, g2), gp.SCREEN_HEIGHT/8);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        g2.drawLine(getXForCenteredText(text, g2)-50, gp.SCREEN_HEIGHT/8 + 5, getXForCenteredText(text, g2) + g2.getFontMetrics().stringWidth(text) + 50, gp.SCREEN_HEIGHT/8 + 5);


        int musicY = gp.SCREEN_HEIGHT/4 - 20;
        int leftX = gp.TILE_SIZE*5;


        for (String line : musicCredits) {
            int musicX = getXForCenteredText(line, g2);
            g2.drawString(line,musicX,musicY);
            musicY += 40;
        }

        int spriteY = musicY+40;

        for (String[] spriteCredit : spriteCredits) {
            for (int j = 0; j < spriteCredit.length; j++) {
                String line = spriteCredit[j];
                int spriteX;
                if (j == 0) {
                    spriteX = getXForCenteredText(line, g2) - leftX;
                } else if (j == 1) {
                    spriteX = getXForCenteredText(line, g2);
                } else {
                    spriteX = getXForCenteredText(line, g2) + leftX;
                }
                g2.drawString(line, spriteX, spriteY);
            }
            spriteY += 40;
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
        // save composite
        Composite orgComposite = g2.getComposite();

        // set opacity of bg
        float opacity = 0.5f;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        // Draw black background
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // reset composite
        g2.setComposite(orgComposite);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        String text = "PAUSED";
        int x = getXForCenteredText(text, g2);
        int y = gp.SCREEN_HEIGHT/2;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+2, y+2);
        // Main PauseScreen
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

    }

    // Draw Title GIF
    public void drawTitleScreen() {
        g2.drawImage(titleGif, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
    }

    // DRAW TITLE IMAGE
    public void drawTitleImage() {
        int imageWidth = (int)(gp.SCREEN_WIDTH/1.3);
        int imageHeight = (int) (titleImage.getHeight(null) * ((double) imageWidth / titleImage.getWidth(null)));
        int x = (gp.SCREEN_WIDTH - imageWidth) / 2;
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 4;

        g2.drawImage(titleImage, x, y, imageWidth, imageHeight, null);
    }

    // Draw Login Screen
    public void drawLoginScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 18F));

        if (typingUsername) {
            g2.drawImage(typeUsername, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        } else if (typingPassword) {
            g2.drawImage(typePassword, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        } else {
            g2.drawImage(loginDefault, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        }

        validLogin = false;

        Rectangle nameRect = new Rectangle(264, 264, 288, 29);
        Rectangle passRect = new Rectangle(264, 331, 288, 28);
        Rectangle registerRect = new Rectangle(345, 380, 130, 28);
        Rectangle loginRect = new Rectangle(365, 425, 90, 28);

        g2.draw(nameRect);
        g2.draw(passRect);

        g2.setColor(Color.BLACK);
        g2.draw(registerRect);
        g2.draw(loginRect);


        g2.setColor(Color.WHITE);
        g2.drawString(inpUser, 275, 285);
//        g2.drawString(password, 275, 351);  // ENABLE TO VIEW PASSWORD DATA
        g2.drawString(inpPassHidden, 275, 355); // ENABLE TO HIDE PASSWORD DATA

        if (gp.mouseH.leftClicked) {
            if (nameRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
                System.out.println("I am within username.");
                typingUsername = true;
                typingPassword = false;
            } else if (passRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
                System.out.println("I am within password.");
                typingUsername = false;
                typingPassword = true;
            } else {
                typingUsername = false;
                typingPassword = false;
            }

            hasBlankField = false;
            isInvalidUsername = false;
            isInvalidLogin = false;
            usernameTaken = false;

            // Execute user registration on Register button click
            if (registerRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY()))
                checkRegister();
            // Execute user login on Login button click
            if (loginRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY()))
                checkLogin();

            gp.mouseH.clearMouseClick();
        }

        if (hasBlankField)
            g2.drawImage(blankErr, 320, 480, 250, 20, null);
        if (isInvalidUsername && !inpUser.isEmpty())
            g2.drawImage(usernameErr, 320, 480, 250, 20, null);
        if (isInvalidLogin)
            g2.drawImage(loginErr, 320, 480, 250, 20, null);
        if (usernameTaken)
            g2.drawImage(usernameTakenErr, 320, 480, 250, 20, null);
//        if (validLogin)
//            gp.gameState = gp.playState;

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3f));
        if (registerRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
            g2.drawRect(355, 410, 110, 0);
        } else if (loginRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
            g2.drawRect(375, 455, 72, 0);
        }
    }

    public void checkLogin () {
        if (inpUser.isEmpty() || inpPass.isEmpty()) {
            hasBlankField = true;
        } else if (!inpUser.matches("[a-zA-Z0-9.\\-_]*")) {
            System.out.println("Has illegal");
            isInvalidUsername = true;
        } else {
            System.out.println("Good login path");
            gp.loginSys.authLogin();
        }
    }

    public void checkRegister () {
        if (inpUser.isEmpty() || inpPass.isEmpty()) {
            hasBlankField = true;
        } else if (!inpUser.matches("[a-zA-Z0-9.\\-_]*")) {
            isInvalidUsername = true;
        } else {
            System.out.println("Good reg path");
            gp.loginSys.authRegister();
        }
    }

    // Draw Start Menu
    public void drawStartMenu() {
//        g2.setColor(Color.BLACK);
//        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));

        // Draw Title and Buttons
        int imgWidth = startMenu.getWidth(null);
        int imgHeight = startMenu.getHeight(null);
        int x = (int) ((gp.SCREEN_WIDTH - imgWidth));
        int y = (int) ((gp.SCREEN_HEIGHT - imgHeight));
        g2.drawImage(startMenu, x, y, imgWidth, imgHeight, null);

        int leftx = (int) (gp.SCREEN_WIDTH/2 - gp.TILE_SIZE*5.5);
        int rightx = (gp.SCREEN_WIDTH/2 + gp.TILE_SIZE*5);
        y = (int) ((gp.SCREEN_HEIGHT)/2 - 60);
        switch (commandNum) {
            case 0: {
                // 0.5 SCALE DIFF
                g2.drawString(">", leftx, y);
                g2.drawString("<", rightx, y);
                break;
            }
            case 1: {
                y += (int) (gp.TILE_SIZE*1.5);
                g2.drawString(">", leftx, y);
                g2.drawString("<", rightx, y);
                break;
            }
            case 2: {
                y += (int) (gp.TILE_SIZE*3);
                g2.drawString(">", leftx, y);
                g2.drawString("<", rightx, y);
                break;
            }
            case 3: {
                y += (int) (gp.TILE_SIZE*4.5);
                g2.drawString(">", leftx, y);
                g2.drawString("<", (gp.SCREEN_WIDTH/2 - 18), y);
                break;
            }
            case 4: {
                y += (int) (gp.TILE_SIZE*4.5);
                g2.drawString(">", (gp.SCREEN_WIDTH/2 - 5), y);
                g2.drawString("<", rightx, y);
                break;
            }
        }
    }

    // CHARACTER SELECTION SCREEN
    public void drawCharacterSelection() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        drawBG();

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        String text = "Select Your Class";
        int x = getXForCenteredText(text, g2);
        int y = gp.TILE_SIZE*3;
        g2.drawString(text, x, y);

        drawCharacterSelectPreview();
    }




    //Draw dialog
    public void drawDialogScreen() {
        // WINDOW
        int dialogX = gp.TILE_SIZE*2;
        int dialogY = gp.TILE_SIZE/2;
        int dialogWidth = gp.SCREEN_WIDTH - (gp.TILE_SIZE*4);
        int dialogHeight = gp.TILE_SIZE*4;
        drawSubWindow(dialogX,dialogY,dialogWidth,dialogHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
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
        x = getXForCenteredText(text, g2);
        y = gp.TILE_SIZE*4;
        g2.drawString(text,x,y);
        // MAIN MESSAGE
        g2.setColor(Color.white);
        g2.drawString(text, x-4,y-4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "PRESS E TO RETRY";
        x = getXForCenteredText(text, g2);
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

    public int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) this.g2.getFontMetrics().getStringBounds(text, this.g2).getWidth();
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
            gp.resetLevel();
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
                    // DRAW HP BAR BOX
                    g2.setColor(new Color(35, 35, 35));
                    switch (mob.mobNum) {
                        // SLIME
                        case 1: g2.fillRect(mob.getScreenX() + 51, mob.getScreenY() + 121, gp.TILE_SIZE, 10); break;
                        // SKELLINGTON
                        case 2: g2.fillRect(mob.getScreenX() + 51, mob.getScreenY() + 141, gp.TILE_SIZE, 10); break;
                        // ROBOT GUARDIAN
                        case 3: g2.fillRect(mob.getScreenX() + 81, mob.getScreenY() + 161, gp.TILE_SIZE, 10); break;
                        // RAMSES
                        case 4: g2.fillRect(mob.getScreenX() + 61, mob.getScreenY() + 121, gp.TILE_SIZE, 10); break;
                        // GOBLIN & SKELETON KNIGHT
                        case 5, 6: g2.fillRect(mob.getScreenX() + 81, mob.getScreenY() + 141, gp.TILE_SIZE, 10); break;
                        // ARMORED GUARDIAN
                        case 7: g2.fillRect(mob.getScreenX() + 51, mob.getScreenY() + 121, gp.TILE_SIZE, 10); break;
                        // FLYING EYE
                        case 8: g2.fillRect(mob.getScreenX() + 121, mob.getScreenY() + 191, gp.TILE_SIZE, 10); break;
                        // MUSHROOM
                        case 9: g2.fillRect(mob.getScreenX() + 126, mob.getScreenY() + 211, gp.TILE_SIZE, 10); break;
                        // CANINE
                        case 10: g2.fillRect(mob.getScreenX() + 21, mob.getScreenY() + 91, gp.TILE_SIZE, 10);
                    }

                    // FILL CURRENT HP
                    g2.setColor(new Color(255, 0, 30));
                    switch (mob.mobNum) {
                        // SLIME
                        case 1: g2.fillRect(mob.getScreenX() + 50, mob.getScreenY() + 120, (int) hpBarValue, 9); break;
                        // SKELLINGTON
                        case 2: g2.fillRect(mob.getScreenX() + 50, mob.getScreenY() + 140, (int) hpBarValue, 9); break;
                        // ROBOT GUARDIAN
                        case 3: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, (int) hpBarValue, 9); break;
                        // RAMSES
                        case 4: g2.fillRect(mob.getScreenX() + 60, mob.getScreenY() + 120, (int) hpBarValue, 9); break;
                        // GOBLIN & SKELETON KNIGHT
                        case 5, 6: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, (int) hpBarValue, 9); break;
                        // ARMORED GUARDIAN
                        case 7: g2.fillRect(mob.getScreenX() + 50, mob.getScreenY() + 120, (int) hpBarValue, 9); break;
                        // FLYING EYE
                        case 8: g2.fillRect(mob.getScreenX() + 120, mob.getScreenY() + 190, (int) hpBarValue, 9); break;
                        // MUSHROOM
                        case 9: g2.fillRect(mob.getScreenX() + 125, mob.getScreenY() + 210, (int) hpBarValue, 9); break;
                        // CANINE
                        case 10: g2.fillRect(mob.getScreenX() + 20, mob.getScreenY() + 90, (int) hpBarValue, 9);
                    }

                    mob.hpBarCounter++;
                    if (mob.hpBarCounter > 600) {
                        mob.hpBarCounter = 0;
                        mob.hpBarVisible = false;
                    }
                } else if(mob.boss && mob.onPath) {
                    double oneScale = (double) gp.TILE_SIZE*8 / mob.maxLife;
                    double hpBarValue = oneScale * mob.currentLife;
                    int x = gp.SCREEN_WIDTH/2 - gp.TILE_SIZE*2;
                    int y = gp.SCREEN_HEIGHT - 40;
                    // DRAW BOX
                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x-1, y-1, gp.TILE_SIZE*8 + 2, 22);
                    g2.setColor(new Color(255, 0, 30));
                    // FILL HP
                    switch(mob.bossNum){
                        case 1:
                            g2.fillRect(x, y, (int) hpBarValue, 20); break;
                        case 2:
                            g2.fillRect(x, y, (int) hpBarValue, 20); break;
                        case 3:
                            g2.fillRect(x, y, (int) hpBarValue, 20); break;
                        case 4:
                            g2.fillRect(x, y, (int) hpBarValue, 20); break;
                        case 5:
                            g2.fillRect(x, y, (int) hpBarValue, 20); break;
                        case 6:
                            g2.fillRect(x, y, (int) hpBarValue, 20); break;
                    }
                    // DRAW NAME
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD,24f));
                    g2.setColor(Color.white);
                    switch(mob.bossNum){
                        case 1:
                            g2.drawString(mob.name,x+4,y-10); break;
                        case 2:
                            g2.drawString(mob.name,x+4,y-10); break;
                        case 3:
                            g2.drawString(mob.name,x+4,y-10); break;
                        case 4:
                            g2.drawString(mob.name,x+4,y-10); break;
                        case 5:
                            g2.drawString(mob.name,x+4,y-10); break;
                        case 6:
                            g2.drawString(mob.name,x+4,y-10); break;
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

    public void drawOptions() {
        g2.setColor(Color.WHITE);
        g2.setFont((g2.getFont().deriveFont(30F)));

        // SUB WINDOW
        int frameX = (int) (gp.TILE_SIZE*4.5);
        int frameY = gp.TILE_SIZE;
        int frameWidth = gp.TILE_SIZE*8;
        int frameHeight = gp.TILE_SIZE*10;

        if (gp.gameState == gp.optionState) {
            subState = 0;
        } else if (gp.gameState == gp.optionState2) {
            subState = 1;
        }

        switch(subState) {
            case 0: drawSubWindow(frameX, frameY, frameWidth, frameHeight); options_playState(frameX, frameY);  break;
            case 1: options_startMenu(frameX, frameY); break;
            case 2: break;
        }
    }

    private void drawVolumeBar(int volumeScale, int x, int y) {
        int barWidth = 240;
        int barHeight = 20;
        if (gp.gameState == gp.optionState) {
            barWidth = 180;
        }
        int fillColor = (int) (barWidth * (volumeScale / 5.0f));

        // BACKGROUND BAR
        g2.setColor(Color.darkGray);
        g2.fillRect(x, y, barWidth, barHeight);

        // FILLED BAR
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, fillColor, barHeight);

    }

    public void options_playState(int frameX, int frameY) {
        int x, y;
        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        String text = "Options";
        x = getXForCenteredText(text, g2);
        y = frameY + gp.TILE_SIZE;
        g2.drawString(text,x,y);

        // Underline the title
        g2.drawLine(x, y + 5, x + g2.getFontMetrics().stringWidth(text), y + 5);

        // MUSIC
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));
        text = "Music";
        x = frameX + gp.TILE_SIZE;
        y += (int) (gp.TILE_SIZE*1.2);
        if (gp.ui.commandNum == 0) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
            g2.drawString(">", x-25, y);

        } else {
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
        }

        // SOUND EFFECTS
        text = "SFX";
        y += (int) (gp.TILE_SIZE*1.2);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 1) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
            g2.drawString(">", x-25, y);

        } else {
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
        }

        // CONTROL
        text = "Controls";
        y += (int) (gp.TILE_SIZE*1.2);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 2) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
            g2.drawString(">", x-25, y);

        } else {
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
        }

        // END GAME
        text = "End Game";
        y += (int) (gp.TILE_SIZE*1.2);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 3) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
            g2.drawString(">", x-25, y);

        } else {
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
        }

        // BACK
        text = "Back";
        y += (int) (gp.TILE_SIZE*3.5);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 4) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
            g2.drawString(">", x-25, y);

        } else {
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);
        }

        // VOLUME BAR
        x = (int) (frameX + gp.TILE_SIZE*3.75);
        y = (int) (frameY + gp.TILE_SIZE*1.65f + 2);
        drawVolumeBar(gp.music.volumeScale, x, y);

        // SFX BAR
        y += (int) (gp.TILE_SIZE*1.2);
        drawVolumeBar(gp.effect.volumeScale, x, y);
    }

    public void options_startMenu(int frameX, int frameY) {
        int x, y;
        drawBG2();
        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        String text = "Start Menu Options";
        x = getXForCenteredText(text, g2);
        y = frameY + gp.TILE_SIZE;
        g2.drawString(text,x,y);

        // Underline the title
        g2.drawLine(x, y + 5, x + g2.getFontMetrics().stringWidth(text), y + 5);

        // MUSIC
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));
        text = "Music";
        x = getXForCenteredText(text, g2);
        y += (int) (gp.TILE_SIZE*1.2);
        if (gp.ui.commandNum == 0) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
        } else {
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
        }

        // SOUND EFFECTS
        text = "SFX";
        x = getXForCenteredText(text, g2);
        y += (int) (gp.TILE_SIZE*2.4);
        if (gp.ui.commandNum == 1) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
        } else {
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
        }

        // CONTROL
        text = "Controls";
        x = getXForCenteredText(text, g2);
        y += (int) (gp.TILE_SIZE*2.4);
        if (gp.ui.commandNum == 2) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
        } else {
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
        }

        // BACK
        text = "Back";
        x = getXForCenteredText(text, g2);
        y += (int) (gp.TILE_SIZE*3);
        if (gp.ui.commandNum == 4) {
            g2.setColor(Color.YELLOW); // highlight color
            g2.drawString(text,x,y);
        } else {
            g2.setColor(Color.white);
            g2.drawString(text,x,y);
        }

        // VOLUME BAR
        x = gp.SCREEN_WIDTH/2 - 120;
        y = (int) (frameY + gp.TILE_SIZE*3);
        drawVolumeBar(gp.music.volumeScale, x, y);

        // SFX BAR
        y += (int) (frameY + gp.TILE_SIZE);
        drawVolumeBar(gp.effect.volumeScale, x, y);
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(gameFont);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // START MENU STATE
        if (gp.gameState == gp.startMenuState) {
            drawStartMenu();
        }

        // CHAR SELECTION SCREEN
        if (gp.gameState == gp.characterSelectionState) {
            drawCharacterSelection();
        }

        // LOGIN MENU
        if (gp.gameState == gp.loginState) {
            drawLoginScreen();
        }

        // OPTION 1 AND 2
        if (gp.gameState == gp.optionState) {
            drawOptions();
        }

        if(gp.gameState == gp.savePageState){
            drawSavePage();
        }

        if (gp.gameState == gp.optionState2) {
            drawBG();
            drawOptions();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerMoney();
            drawHotbar();
            drawPlayerLife();
            drawAllMobHP();
        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogScreen();
        }

        // SHOP STATE
        if (gp.gameState == gp.shopState){
            drawShop();
        }

        // DEATH STATE
        if (gp.gameState == gp.deathState) {
            drawDeathScreen();
        }

        if(gp.gameState == gp.transitionState){
            drawTransition();
        }

        if (gp.gameState == gp.creditsState) {
            drawCredits(g2);
        }
    }
}
