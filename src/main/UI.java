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

    // IMAGE AND GIFS
    private final Image titleGif;
    private final Image titleImage;
    private final Image newGame;
    private final Image loadGame;
    private final Image options;
    private final Image quit;
    private final Image warriorGif, knightGif, assassinGif;
    private final Image loginDefault, typeUsername, typePassword,
            blankErr, usernameErr, loginErr, usernameTakenErr;

    public String currentDialog = "";
    public int slotRow = 0;
    public int slotRowMove = 0;
    int counter = 0;
    int charIndex = 0;
    String combinedText = "";
    public int commandNum = 0;
    int subState = 0;

    // Login variables
    public String username = "", password = "", passwordHidden = "";
    public boolean hasBlankField = false,
            isInvalidUsername = false,
            isInvalidLogin = false,
            usernameTaken = false,
            validLogin = false;

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
            Font gameFont = loadFont("res/font/x12y16pxMaruMonica.ttf");
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
        loginDefault = new ImageIcon("res/UI/loginDefault.png").getImage();
        typeUsername = new ImageIcon("res/UI/loginUsername.png").getImage();
        typePassword = new ImageIcon("res/UI/loginPassword.png").getImage();
        blankErr = new ImageIcon("res/UI/blankField.png").getImage();
        loginErr = new ImageIcon("res/UI/invalidLogin.png").getImage();
        usernameErr = new ImageIcon("res/UI/invalidUsername.png").getImage();
        usernameTakenErr = new ImageIcon("res/UI/usernameTaken.png").getImage();

        // INITIALIZE START MENU UI
        newGame = new ImageIcon("res/UI/newGame.png").getImage();
        loadGame = new ImageIcon("res/UI/loadGame.png").getImage();
        options = new ImageIcon("res/UI/options.png").getImage();
        quit = new ImageIcon("res/UI/quit.png").getImage();

        // INITIALIZE PLAYER PREVIEW
        ImageIcon warrior = new ImageIcon("res/player/WarriorIdle.gif");
        warriorGif = warrior.getImage();
        ImageIcon knight = new ImageIcon("res/player/KnightIdle.gif");
        knightGif = knight.getImage();
        ImageIcon assassin = new ImageIcon("res/player/AssassinIdle.gif");
        assassinGif = assassin.getImage();
    }

    private Font loadFont(String fontPath) throws FontFormatException, IOException {
        File fontFile = new File(fontPath);
        return Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fontFile));
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

        if (gp.gameState == gp.optionState) {
            drawOptions();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerMoney();
            drawHotbar();
            drawPlayerLife();
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
        if (coin.spriteCounter > 4) coin.loopThroughSprites();
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

        if (gp.onUsername) {
            g2.drawImage(typeUsername, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        } else if (gp.onPassword) {
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
        g2.drawString(username, 275, 285);
//        g2.drawString(password, 275, 351);  // ENABLE TO VIEW PASSWORD DATA
        g2.drawString(passwordHidden, 275, 355); // ENABLE TO HIDE PASSWORD DATA

        if (gp.mouseH.leftClicked) {
            if (nameRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
                System.out.println("I am within username.");
                gp.onUsername = true;
                gp.onPassword = false;
            } else if (passRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
                System.out.println("I am within password.");
                gp.onUsername = false;
                gp.onPassword = true;
            } else {
                gp.onUsername = false;
                gp.onPassword = false;
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
        if (isInvalidUsername && !username.isEmpty())
            g2.drawImage(usernameErr, 320, 480, 250, 20, null);
        if (isInvalidLogin)
            g2.drawImage(loginErr, 320, 480, 250, 20, null);
        if (usernameTaken)
            g2.drawImage(usernameTakenErr, 320, 480, 250, 20, null);
        if (validLogin)
            System.out.println("Login successful! JACOB'S LADDER");

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3f));
        if (registerRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
            g2.drawRect(355, 410, 110, 0);
        } else if (loginRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
            g2.drawRect(375, 455, 72, 0);
        }
    }

    public void checkLogin () {
        if (username.isEmpty() || password.isEmpty()) {
            hasBlankField = true;
        } else if (!username.matches("[a-zA-Z0-9.\\-_]{0,}")) {
            System.out.println("Has illegal");
            isInvalidUsername = true;
        } else if (!password.equals("123")) {
            isInvalidLogin = true;
        } else if (username.equals("123")) {


            validLogin = true;
            gp.gameState = gp.characterSelectionState;
        }
    }

    public void checkRegister () {
        if (username.isEmpty() || password.isEmpty()) {
            hasBlankField = true;
        } else if (!username.matches("[a-zA-Z0-9.\\-_]{0,}")) {
            isInvalidUsername = true;
        } else if (username.equals("Zaky")) {
            usernameTaken = true;
        }
    }

    public void drawNewGameButton() {
        int x = (gp.SCREEN_WIDTH - newGame.getWidth(null)) / 2;
        int y = (gp.SCREEN_HEIGHT - newGame.getHeight(null)) / 2;
        g2.drawImage(newGame, x, y, newGame.getWidth(null), newGame.getHeight(null), null);


        if (commandNum == 0) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));

            g2.drawString(">", (int) (x - gp.TILE_SIZE*0.4), y + 40);
            g2.drawString("<", (x + newGame.getWidth(null)), y + 40);

        }
    }

    public void drawLoadButton() {
        int x = (gp.SCREEN_WIDTH - loadGame.getWidth(null)) / 2;
        int y = (gp.SCREEN_HEIGHT - loadGame.getHeight(null)) / 2 + 60;
        g2.drawImage(loadGame, x, y, loadGame.getWidth(null), loadGame.getHeight(null), null);
        if (commandNum == 1) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));


            g2.drawString(">", (int) (x - gp.TILE_SIZE*0.4), y + 42);
            g2.drawString("<", x + (loadGame.getWidth(null)), y + 42);

        }
    }

    public void drawOptionButton() {
        int x = (gp.SCREEN_WIDTH - options.getWidth(null)) / 3;
        int y = (gp.SCREEN_HEIGHT - options.getHeight(null)) / 2 + 130;
        g2.drawImage(options, x, y, options.getWidth(null), options.getHeight(null), null);
        if (commandNum == 2) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));


            g2.drawString(">", (int) (x - gp.TILE_SIZE*0.4), y + 36);
            g2.drawString("<", x + (options.getWidth(null)), y + 36);

        }
    }

    public void drawQuitButton() {
        int x = ((gp.SCREEN_WIDTH - quit.getWidth(null)) / 2 + 110);
        int y = (gp.SCREEN_HEIGHT - quit.getHeight(null)) / 2 + 130;
        g2.drawImage(quit, x, y, quit.getWidth(null), quit.getHeight(null), null);
        if (commandNum == 3) {
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));

            g2.drawString(">", (int) (x - gp.TILE_SIZE*0.4), y + 36);
            g2.drawString("<", x + (quit.getWidth(null)), y + 36);

        }
    }

    // Draw Start Menu
    public void drawStartMenu() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        // Draw Title and Buttons
        drawTitleImage();
        drawNewGameButton();
        drawLoadButton();
        drawOptionButton();
        drawQuitButton();
    }

    // CHARACTER SELECTION SCREEN
    public void drawCharacterSelection() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        String text = "Select Your Class";
        int x = getXForCenteredText(text);
        int y = gp.TILE_SIZE*3;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        String text1 = "Warrior";
        x = getXForCenteredText(text)/3;
        y = gp.TILE_SIZE*12;

        int gifX = (int) (gp.SCREEN_WIDTH/1.5);
        int gifY = gp.SCREEN_HEIGHT/2;
        if (commandNum == 0) {
            g2.setColor(Color.WHITE);
            g2.drawString(text1, x, y);
            g2.drawImage(warriorGif, (gp.SCREEN_WIDTH - gifX)/2, (gp.SCREEN_HEIGHT - gifY)/2 + 30, gifX, gifY, null);
        } else {
            g2.setColor(Color.GRAY);
            g2.drawString(text1, x, y);
        }

        String text2 = "Knight";
        x += (int) (getXForCenteredText(text)/1.1);
        if (commandNum == 1) {
            g2.setColor(Color.WHITE);
            g2.drawString(text2, x, y);
            g2.drawImage(knightGif, (gp.SCREEN_WIDTH - gifX)/2, (gp.SCREEN_HEIGHT - gifY)/2  + 30, gifX, gifY, null);
        } else {
            g2.setColor(Color.GRAY);
            g2.drawString(text2, x, y);
        }

        String text3 = "Assassin";
        x += (int) (getXForCenteredText(text) / 1.1);
        if (commandNum == 2) {
            g2.setColor(Color.WHITE);
            g2.drawString(text3, x, y);
            g2.drawImage(assassinGif, (gp.SCREEN_WIDTH - gifX)/2, (gp.SCREEN_HEIGHT - gifY)/2  + 30, gifX, gifY, null);

        } else {
            g2.setColor(Color.GRAY);
            g2.drawString(text3, x, y);
        }
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
                }
            }
        } else {
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
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

    public int getXForCenteredText(String text) {
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

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: break;
            case 2: break;

        }
    }

    public void options_top(int frameX, int frameY) {
        int x, y;

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
        String text = "Options";
        x = getXForCenteredText(text);
        y = frameY + gp.TILE_SIZE;
        g2.drawString(text,x,y);


        // VOLUME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40f));
        text = "Volume";
        x = frameX + gp.TILE_SIZE;
        y += (int) (gp.TILE_SIZE*1.2);
        g2.drawString(text,x,y);
        if (commandNum == 0) {
            g2.drawString(">", x-25, y);
        }

        // CONTROL
        text = "Controls";
        y += (int) (gp.TILE_SIZE*1.2);
        g2.drawString(text,x,y);
        if (commandNum == 1) {
            g2.drawString(">", x-25, y);
        }

        // END GAME
        text = "End Game";
        y += (int) (gp.TILE_SIZE*1.2);
        g2.drawString(text,x,y);
        if (commandNum == 2) {
            g2.drawString(">", x-25, y);
        }

        // BACK
        text = "Back";
        y += (int) (gp.TILE_SIZE*3.5);
        g2.drawString(text,x,y);
        if (commandNum == 3) {
            g2.drawString(">", x-25, y);
        }

        // VOLUME BAR
        x = frameX + gp.TILE_SIZE*6;
        y = (int) (frameY + gp.TILE_SIZE*2.2f);
        switch (gp.sound.volumeScale) {
            case 0: g2.drawString("0%", x, y); break;
            case 1: g2.drawString("20%", x, y);break;
            case 2: g2.drawString("40%", x, y);break;
            case 3: g2.drawString("60%", x, y);break;
            case 4: g2.drawString("80%", x, y);break;
            case 5: g2.drawString("100%", x, y);break;
        }
    }
}
