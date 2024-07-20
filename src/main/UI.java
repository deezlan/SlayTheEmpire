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

    public Entity npc;
    GamePanel gp;
    BufferedImage fullHeart, halfHeart, emptyHeart, hotbar;
    Graphics2D g2;
    Entity coin;
    Font gameFont;

    // IMAGE AND GIFS
    private final Image
            bg, bg2,
            // TITLE STATE
            titleGif, titleImage,
            // LOGIN STATE
            loginDefault, typeUsername, typePassword, errorBG,
            blankErr, usernameErr, loginErr, usernameTakenErr,
            // MAIN MENU STATE
            startMenu,
            // CHARACTER SELECTION STATE
            warriorGif, knightGif, assassinGif,
            warriorSelected, knightSelected, assassinSelected;

    public String currentDialog = "",
            combinedText = "";
    int counter = 0,
        charIndex = 0;
    // OPTIONS
    public int commandNum = 0;
    int subState = 0;

    public int slotRow = 0,
            slotRowMove = 0;

    // Login variables
    public String inpUser = "", inpPass = "", inpPassHidden = "";
    public boolean
            hasBlankField = false,
            hasInvalidChar = false,
            isInvalidLogin = false,
            isUserTaken = false,
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
        errorBG = new ImageIcon("res/UI/login/errorBG.png").getImage();
        blankErr = new ImageIcon("res/UI/login/blankField.png").getImage();
        loginErr = new ImageIcon("res/UI/login/invalidLogin.png").getImage();
        usernameErr = new ImageIcon("res/UI/login/invalidChar.png").getImage();
        usernameTakenErr = new ImageIcon("res/UI/login/userTaken.png").getImage();

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
        int descY = frameY + frameHeight;
        int descHeight = gp.TILE_SIZE*3;
        drawSubWindow(frameX, descY, frameWidth, descHeight);

        g2.setColor(Color.BLACK);
        g2.fillRoundRect(frameX, descY, frameWidth, descHeight, 0, 0);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke((5)));
        g2.drawRoundRect(frameX, descY, frameWidth, descHeight, 0, 0);
        //DRAW DESC TEXT
        int textX = frameX + 20;
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
        g2.drawString(text, getXForCenteredText(text), gp.SCREEN_HEIGHT/8);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        g2.drawLine(getXForCenteredText(text)-50, gp.SCREEN_HEIGHT/8 + 5, getXForCenteredText(text) + g2.getFontMetrics().stringWidth(text) + 50, gp.SCREEN_HEIGHT/8 + 5);


        int musicY = gp.SCREEN_HEIGHT/4 - 20;
        int leftX = gp.TILE_SIZE*5;


        for (String line : musicCredits) {
            int musicX = getXForCenteredText(line);
            g2.drawString(line,musicX,musicY);
            musicY += 40;
        }

        int spriteY = musicY+40;

        for (String[] spriteCredit : spriteCredits) {
            for (int j = 0; j < spriteCredit.length; j++) {
                String line = spriteCredit[j];
                int spriteX;
                if (j == 0) {
                    spriteX = getXForCenteredText(line) - leftX;
                } else if (j == 1) {
                    spriteX = getXForCenteredText(line);
                } else {
                    spriteX = getXForCenteredText(line) + leftX;
                }
                g2.drawString(line, spriteX, spriteY);
            }
            spriteY += 40;
        }

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        text = "(Esc to Exit)";
        g2.drawString(text,10,30);
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
        if (coin.spriteCounter > 10) coin.runCurrentListAnimation();
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
        int x = getXForCenteredText(text);
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
            hasInvalidChar = false;
            isInvalidLogin = false;
            isUserTaken = false;

            // Execute user registration on Register button click
            if (registerRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY()))
                checkRegister();
            // Execute user login on Login button click
            if (loginRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY()))
                checkLogin();

            gp.mouseH.clearMouseClick();
        }

        if (hasBlankField || hasInvalidChar || isInvalidLogin || isUserTaken) {
            g2.drawImage(errorBG, 0, 0, errorBG.getWidth(null), errorBG.getHeight(null), null);
        }
        if (hasBlankField)
            g2.drawImage(blankErr, 209, 243, 397, 137, null);
        if (hasInvalidChar && !inpUser.isEmpty())
            g2.drawImage(usernameErr, 209, 243, 397, 137, null);
        if (isInvalidLogin)
            g2.drawImage(loginErr, 209, 243, 397, 137, null);
        if (isUserTaken)
            g2.drawImage(usernameTakenErr, 209, 243, 397, 137, null);

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
            hasInvalidChar = true;
        } else {
            System.out.println("Good login path");
            gp.loginSys.authLogin();
        }
    }

    public void checkRegister () {
        if (inpUser.isEmpty() || inpPass.isEmpty()) {
            hasBlankField = true;
        } else if (!inpUser.matches("[a-zA-Z0-9.\\-_]*")) {
            hasInvalidChar = true;
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
        int x = gp.SCREEN_WIDTH - imgWidth;
        int y = gp.SCREEN_HEIGHT - imgHeight;
        g2.drawImage(startMenu, x, y, imgWidth, imgHeight, null);

        int leftx = (int) ((double) gp.SCREEN_WIDTH /2 - gp.TILE_SIZE*5.5);
        int rightx = (gp.SCREEN_WIDTH/2 + gp.TILE_SIZE*5);
        y = gp.SCREEN_HEIGHT/2 - 60;
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
                y += gp.TILE_SIZE*3;
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

    public void drawCharacterSelectPreview() {
        int gifX = 300;
        int gifY = 150;

        switch (gp.ui.commandNum) {
            case 0 -> {
                g2.drawImage(warriorSelected, 0, 0, null);
                g2.drawImage(warriorGif, (gp.SCREEN_WIDTH - gifX)/2 - 225,  (gp.SCREEN_HEIGHT - gifY)/3 + 40, gifX, gifY, null);
            }
            case 1 -> {
                g2.drawImage(knightSelected, 0, 0, null);
                g2.drawImage(knightGif, (gp.SCREEN_WIDTH - gifX)/2, (gp.SCREEN_HEIGHT - gifY)/3 + 50, gifX, gifY, null);
            }
            case 2 -> {
                g2.drawImage(assassinSelected, 0, 0, null);
                g2.drawImage(assassinGif, (gp.SCREEN_WIDTH - gifX)/2 + 225, (gp.SCREEN_HEIGHT - gifY)/3 + 40, gifX, gifY, null);
            }
        }
    }

    // CHARACTER SELECTION SCREEN
    public void drawCharacterSelection() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        drawBG();

        drawCharacterSelectPreview();

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        String text = "(Esc to Exit)";
        g2.drawString(text,10,30);
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
            if (gp.keyH.spacePressed){
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.DIALOGUE_STATE){
                    npc.dialogueIndex++;
                    gp.keyH.spacePressed = false;
                } else if (gp.gameState == gp.CUTSCENE_STATE) {
                    npc.dialogueIndex++;
                    gp.keyH.spacePressed = false;
                }
            }
        } else {
            npc.dialogueIndex = 0;
            if (gp.gameState == gp.DIALOGUE_STATE) {
                gp.gameState = gp.PLAY_STATE;
            } else if (gp.gameState == gp.CUTSCENE_STATE) {
                gp.csManager.scenePhase++;
            }
        }

        for(String line : currentDialog.split("\n")){ // breaks long dialogues // for up to use
            g2.drawString(line,dialogX,dialogY);
            dialogY += 40;
        }
    }

    public void mapSelect() {
        try {
            InputStream is = new FileInputStream("ARCADE_N.TTF");
            Font arcade = Font.createFont(Font.TRUETYPE_FONT, is);
            arcade = arcade.deriveFont(Font.PLAIN, 16);
            int frameX = (gp.TILE_SIZE * 6) - 24;
            int frameY = gp.TILE_SIZE * 3;
            int frameWidth = (gp.TILE_SIZE * 6);
            int frameHeight= (gp.TILE_SIZE * 6);
            int x = gp.TILE_SIZE * 6 - 24;
            int y = gp.TILE_SIZE * 3;
            g2.setFont(arcade);

            g2.setColor(Color.BLACK);
            g2.fillRoundRect(frameX,frameY,frameWidth,frameHeight,0,0);

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke((5)));
            g2.drawRoundRect(frameX,frameY,frameWidth,frameHeight,0,0);

            g2.setColor(Color.WHITE);

            x += gp.TILE_SIZE;
            y += gp.TILE_SIZE;
            String text = "Map Selection";
            g2.drawString(text,x,y);
            y += gp.TILE_SIZE;
            g2.drawString("Map 1",x,y);
            if (commandNum == 0) {
                g2.drawString(">", x-24,y);
            }
            y += gp.TILE_SIZE;
            g2.drawString("Map 2",x,y);
            if (commandNum == 1) {
                g2.drawString(">", x-24,y);
            }
            y += gp.TILE_SIZE;
            g2.drawString("Exit",x,y);
            if (commandNum == 2) {
                g2.drawString(">", x-24,y);
            }

        } catch (FontFormatException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void dialogueDifficultySelect() {
        drawDialogScreen();

        // DRAW WINDOW
        int x = gp.TILE_SIZE * 12;
        int y = gp.TILE_SIZE * 4;
        int width = gp.TILE_SIZE * 4;
        int height = (int)(gp.TILE_SIZE * 4.5);
        drawSubWindow(x,y,width,height);

        // DRAW TEXT
        x += gp.TILE_SIZE;
        y += gp.TILE_SIZE;
        g2.drawString("Easy",x,y);
        if (commandNum == 0) {
            g2.drawString(">", x-24,y);
        }
        y += gp.TILE_SIZE;
        g2.drawString("Medium",x,y);
        if (commandNum == 1) {
            g2.drawString(">", x-24,y);
        }
        y += gp.TILE_SIZE;
        g2.drawString("Hard",x,y);
        if (commandNum == 2) {
            g2.drawString(">", x-24,y);
        }
        y += gp.TILE_SIZE;
        g2.drawString("No",x,y);
        if (commandNum == 3) {
            g2.drawString(">", x-24,y);
        }
    }
    public void dialogueBlackSmithSelect() {
        drawDialogScreen();

        // DRAW WINDOW
        int x = gp.TILE_SIZE * 12;
        int y = gp.TILE_SIZE * 4;
        int width = gp.TILE_SIZE * 4;
        int height = (gp.TILE_SIZE * 4);
        drawSubWindow(x,y,width,height);

        x += gp.TILE_SIZE;
        y += gp.TILE_SIZE;
        g2.drawString("Buy Items",x,y);
        if (commandNum == 0) {
            g2.drawString(">", x-24,y);
        }
        y += gp.TILE_SIZE;
        g2.drawString("Talk",x,y);
        if (commandNum == 1) {
            g2.drawString(">", x-24,y);
        }
        y += gp.TILE_SIZE;
        g2.drawString("Exit",x,y);
        if (commandNum == 2) {
            g2.drawString(">", x-24,y);
        }
    }

    public void drawDeathScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0,0, gp.SCREEN_WIDTH , gp.SCREEN_HEIGHT);
        int x, y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text = "YOU HAVE DIED";

        // SHADOW
        g2.setColor(Color.BLACK);
        x = getXForCenteredText(text);
        y = gp.TILE_SIZE*4;
        g2.drawString(text,x,y);
        // MAIN MESSAGE
        g2.setColor(Color.white);
        g2.drawString(text, x-4,y-4);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(40f));
        text = "PRESS E TO RETRY";
        x = getXForCenteredText(text);
        y += gp.TILE_SIZE*4;
        g2.drawString(text,x,y);

        if (gp.keyH.ePressed) drawDeathTransition();
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

    public int getXForCenteredText(String text) {
        int length = (int) this.g2.getFontMetrics().getStringBounds(text, this.g2).getWidth();
        return gp.SCREEN_WIDTH/2 - length/2;
    }
    //  DRAWS TRANSITION EVENT WHEN ENTERING OR EXITING
    public void drawTransition() {
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0,0,gp.SCREEN_WIDTH,gp.SCREEN_HEIGHT);

        if (counter == 50) {
            counter = 0;
            gp.currentMap = gp.eHandler.tempMap;
            gp.setMapColor();
            gp.player.worldX = gp.TILE_SIZE * gp.eHandler.tempCol;
            gp.player.worldY = gp.TILE_SIZE * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.loadLevel();
        }
    }

    public void drawAllMobHP() {
        for(int i = 0; i < gp.mobArr[1].length; i++){
            Entity mob = gp.mobArr[gp.currentMap][i]; // LOCAL ENTITY FOR SHORTER CODE
            if (mob != null && mob.inCamera()){
                if (mob.hpBarVisible && !mob.boss) {
                    double oneScale = (double) gp.TILE_SIZE / mob.maxLife;
                    double hpBarValue = oneScale * mob.currentLife;
                    // DRAW HP BAR BOX
                    g2.setColor(new Color(35, 35, 35));
                    switch (mob.mobNum) {
                        // SLIME
                        case 1: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, gp.TILE_SIZE, 10); break;
                        // SKELLINGTON
                        case 2: g2.fillRect(mob.getScreenX() + 70, mob.getScreenY() + 160, gp.TILE_SIZE, 10); break;
                        // ROBOT GUARDIAN
                        case 3: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, gp.TILE_SIZE, 10); break;
                        // RAMSES
                        case 4: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, gp.TILE_SIZE, 10); break;
                        // GOBLIN & SKELETON KNIGHT
                        case 5, 6: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, gp.TILE_SIZE, 10); break;
                        // ARMORED GUARDIAN
                        case 7: g2.fillRect(mob.getScreenX() + 70, mob.getScreenY() + 140, gp.TILE_SIZE, 10); break;
                        // FLYING EYE
                        case 8: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 130, gp.TILE_SIZE, 10); break;
                        // MUSHROOM
                        case 9: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, gp.TILE_SIZE, 10); break;
                        // CANINE
                        case 10: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, gp.TILE_SIZE, 10);
                    }

                    // FILL CURRENT HP
                    g2.setColor(new Color(255, 0, 30));
                    switch (mob.mobNum) {
                        // SLIME
                        case 1: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, (int) hpBarValue, 9); break;
                        // SKELLINGTON
                        case 2: g2.fillRect(mob.getScreenX() + 70, mob.getScreenY() + 160, (int) hpBarValue, 9); break;
                        // ROBOT GUARDIAN
                        case 3: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, (int) hpBarValue, 9); break;
                        // RAMSES
                        case 4: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, (int) hpBarValue, 9); break;
                        // GOBLIN & SKELETON KNIGHT
                        case 5, 6: g2.fillRect(mob.getScreenX() + 75, mob.getScreenY() + 140, (int) hpBarValue, 9); break;
                        // ARMORED GUARDIAN
                        case 7: g2.fillRect(mob.getScreenX() + 70, mob.getScreenY() + 140, (int) hpBarValue, 9); break;
                        // FLYING EYE
                        case 8: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 130, (int) hpBarValue, 9); break;
                        // MUSHROOM
                        case 9: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 160, (int) hpBarValue, 9); break;
                        // CANINE
                        case 10: g2.fillRect(mob.getScreenX() + 80, mob.getScreenY() + 140, (int) hpBarValue, 9);
                    }

                    mob.hpBarCounter++;
                    if (mob.hpBarCounter > 600) {
                        mob.hpBarCounter = 0;
                        mob.hpBarVisible = false;
                    }
                } else if (mob.boss && mob.onPath) {
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

        if (counter == 50){
            counter = 0;
            gp.gameState = gp.PLAY_STATE;
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

        if (gp.gameState == gp.OPTIONS_MENU_STATE) {
            subState = 0;
        } else if (gp.gameState == gp.OPTIONS_DIALOGUE_STATE) {
            subState = 1;
        }

        switch (subState) {
            case 0: drawSubWindow(frameX, frameY, frameWidth, frameHeight); options_PLAY_STATE(frameX, frameY);  break;
            case 1: options_startMenu(frameY); break;
            case 2: break;
        }
    }

    private void drawVolumeBar(int volumeScale, int x, int y) {
        int barWidth = 240;
        int barHeight = 20;
        if (gp.gameState == gp.OPTIONS_MENU_STATE) {
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

    public void options_PLAY_STATE(int frameX, int frameY) {
        int x, y;
        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        String text = "Options";
        x = getXForCenteredText(text);
        y = frameY + gp.TILE_SIZE;
        g2.drawString(text, x, y);

        // TITLE UNDERLINE
        g2.drawLine(x, y + 5, x + g2.getFontMetrics().stringWidth(text), y + 5);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));

        text = "Music";
        x = frameX + gp.TILE_SIZE;
        y += (int) (gp.TILE_SIZE * 1.2);
        g2.setColor(gp.ui.commandNum == 0? Color.YELLOW : Color.WHITE);
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 0) {
            g2.drawString(">", x - 25, y);
        }

        // SOUND EFFECTS
        text = "SFX";
        y += (int) (gp.TILE_SIZE * 1.2);
        g2.setColor(gp.ui.commandNum == 1? Color.YELLOW : Color.WHITE);
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 1) {
            g2.drawString(">", x - 25, y);
        }

        // CONTROL
        text = "Controls";
        y += (int) (gp.TILE_SIZE * 1.2);
        g2.setColor(gp.ui.commandNum == 2? Color.YELLOW : Color.WHITE);
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 2) {
            g2.drawString(">", x - 25, y);
        }

        text = "Back to Lobby";
        y += (int) (gp.TILE_SIZE * 1.2);
        g2.setColor(gp.ui.commandNum == 3 ? Color.YELLOW : Color.WHITE);
        if (gp.currentMap == 0)
            g2.setColor(Color.GRAY);
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 3) {
            g2.drawString(">", x - 25, y);
        }

        // END GAME
        text = "End Game";
        y += (int) (gp.TILE_SIZE * 1.2);
        g2.setColor(gp.ui.commandNum == 4? Color.YELLOW : Color.WHITE);
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 4) {
            g2.drawString(">", x - 25, y);
        }

        // BACK
        text = "Back";
        y += (gp.TILE_SIZE * 2);
        g2.setColor(gp.ui.commandNum == 5? Color.YELLOW : Color.WHITE);
        g2.drawString(text, x, y);
        if (gp.ui.commandNum == 5) {
            g2.drawString(">", x - 25, y);
        }

        // VOLUME BAR
        x = (int) (frameX + gp.TILE_SIZE*3.75);
        y = (int) (frameY + gp.TILE_SIZE*1.65f + 2);
        drawVolumeBar(gp.music.volumeScale, x, y);

        // SFX BAR
        y += (int) (gp.TILE_SIZE*1.2);
        drawVolumeBar(gp.effect.volumeScale, x, y);
    }

    public void options_startMenu(int frameY) {
        int x, y;
        drawBG2();
        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        String text = "Start Menu Options";
        x = getXForCenteredText(text);
        y = frameY + gp.TILE_SIZE;
        g2.drawString(text,x,y);

        // UNDERLINE TITLE
        g2.drawLine(x, y + 5, x + g2.getFontMetrics().stringWidth(text), y + 5);

        // MUSIC
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));
        text = "Music";
        x = getXForCenteredText(text);
        y += (int) (gp.TILE_SIZE*1.2);
        g2.setColor(gp.ui.commandNum == 0? Color.YELLOW : Color.WHITE);
        g2.drawString(text,x,y);


        // SOUND EFFECTS
        text = "SFX";
        x = getXForCenteredText(text);
        y += (int) (gp.TILE_SIZE*2.4);
        g2.setColor(gp.ui.commandNum == 1? Color.YELLOW : Color.WHITE);
        g2.drawString(text,x,y);

        // CONTROL
        text = "Controls";
        x = getXForCenteredText(text);
        y += (int) (gp.TILE_SIZE*2.4);
        g2.setColor(gp.ui.commandNum == 2? Color.YELLOW : Color.WHITE);
        g2.drawString(text,x,y);

        // BACK
        text = "Back";
        x = getXForCenteredText(text);
        y += gp.TILE_SIZE*3;
        g2.setColor(gp.ui.commandNum == 5? Color.YELLOW : Color.WHITE);
        g2.drawString(text,x,y);

        // VOLUME BAR
        x = gp.SCREEN_WIDTH/2 - 120;
        y = frameY + gp.TILE_SIZE*3;
        drawVolumeBar(gp.music.volumeScale, x, y);

        // SFX BAR
        y += frameY + gp.TILE_SIZE;
        drawVolumeBar(gp.effect.volumeScale, x, y);
    }

    public void drawControls() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        String text = "(Esc to Exit)";
        g2.drawString(text,10,30);

        int frameX = 0;
        int frameY = gp.TILE_SIZE*2;
        int frameWidth = gp.SCREEN_WIDTH;
        int frameHeight = gp.SCREEN_HEIGHT - gp.TILE_SIZE*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    }

    public void drawDifficultySelect() {
        drawBG();
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50f));
        String text;
        int x;
        int y;

        // DIFFICULTY
        text = "Difficulty Selection";
        x = getXForCenteredText(text);
        y = gp.TILE_SIZE*2;
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        //UNDERLINE
        g2.drawLine(x, y + 5, x + g2.getFontMetrics().stringWidth(text), y + 5);

        // MODES
        text = "Easy";
        x = getXForCenteredText(text);
        y += gp.TILE_SIZE*2;
        g2.setColor(gp.ui.commandNum == 0? Color.YELLOW : Color.WHITE);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 0) {
            g2.drawString(">", x - 25, y);
        }

        text = "Medium";
        x = getXForCenteredText(text);
        y += gp.TILE_SIZE;
        g2.setColor(gp.ui.commandNum == 1? Color.BLUE : Color.WHITE);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 1) {
            g2.drawString(">", x - 25, y);
        }

        text = "Hard";
        x = getXForCenteredText(text);
        y += gp.TILE_SIZE;
        g2.setColor(gp.ui.commandNum == 2? Color.RED : Color.WHITE);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 2) {
            g2.drawString(">", x - 25, y);
        }

        y += gp.TILE_SIZE*3;
        switch (gp.ui.commandNum) {
            case 0: {
                text = "For newborns.";
                x = getXForCenteredText(text);
                g2.setColor(gp.ui.commandNum == 0? Color.YELLOW : Color.WHITE);
                g2.drawString(text, x, y);
                break;
            }
            case 1: {
                text = "For regular people.";
                x = getXForCenteredText(text);
                g2.setColor(gp.ui.commandNum == 1? Color.BLUE : Color.WHITE);
                g2.drawString(text, x, y);
                break;
            }
            case 2: {
                text = "For renowned legends.";
                x = getXForCenteredText(text);
                g2.setColor(gp.ui.commandNum == 2? Color.RED : Color.WHITE);
                g2.drawString(text, x, y);
                break;
            }
        }

        text = "Back";
        x = getXForCenteredText(text);
        y += gp.TILE_SIZE*3;
        g2.setColor(gp.ui.commandNum == 3? Color.YELLOW : Color.WHITE);
        g2.drawString(text,x,y);
        if (gp.ui.commandNum == 3) {
            g2.drawString(">", x - 25, y);
        }
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(gameFont);

        // TITLE
        if (gp.gameState == gp.TITLE_STATE) drawTitleScreen();
        // LOGIN MENU
        if (gp.gameState == gp.LOGIN_STATE) drawLoginScreen();
        // START MENU
        if (gp.gameState == gp.MAIN_MENU_STATE) drawStartMenu();
        // CREDITS
        if (gp.gameState == gp.CREDITS_STATE) drawCredits(g2);
        // CHAR SELECTION
        if (gp.gameState == gp.CHAR_SELECT_STATE) drawCharacterSelection();
        // DIFFICULTY SELECTION
        if (gp.gameState == gp.DIFF_MENU_STATE) drawDifficultySelect();
        // MENU OPTION & GAMEPLAY OPTION
        if (gp.gameState == gp.OPTIONS_MENU_STATE) drawOptions();
        if (gp.gameState == gp.OPTIONS_DIALOGUE_STATE) {
            drawBG();
            drawOptions();
        }
        // GAMEPLAY
        if (gp.gameState == gp.PLAY_STATE) {
            drawPlayerMoney();
            drawHotbar();
            drawPlayerLife();
            drawAllMobHP();
        }
        // PAUSE
        if (gp.gameState == gp.PAUSE_STATE) drawPauseScreen();
        // DIALOGUE
        if(gp.gameState == gp.DIALOGUE_STATE) drawDialogScreen();
        // SHOP
        if (gp.gameState == gp.SHOP_STATE) drawShop();
        // DEATH
        if (gp.gameState == gp.DEATH_STATE) drawDeathScreen();
        // TRANSITION
        if (gp.gameState == gp.TRANSITION_STATE) drawTransition();
        // NPC DIALOGUE DIFFICULTY SELECT
        if (gp.gameState == gp.DIFF_DIALOGUE_STATE) dialogueDifficultySelect();
        // MAP SELECTION
        if (gp.gameState == gp.MAP_SELECTION) mapSelect();
        // BLACK SMITH DIALOGUE SELECTION
        if (gp.gameState == gp.BLACKSMITH_DIALOGUE_STATE) dialogueBlackSmithSelect();
    }
}
