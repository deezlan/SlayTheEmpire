package main;

import object.OBJ_Heart;
import object.SuperObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class  UI {

    GamePanel gp;
    BufferedImage fullHeart, halfHeart, emptyHeart;
    Graphics2D g2;
    private final Image titleGif;
    private final Image titleImage;
    private final Image newGame;
    private final Image loadGame;
    private final Image options;
    private final Image quit;
    private final Image loginDefault, loginUsername, loginPassword,
            blankField, invalidLogin, invalidUsername, usernameTaken;

    public String currentDialog = "";
    public int slotCol = 0;
    public int slotRow = 0;
    public int slotColMove = 0;
    public int slotRowMove = 0;

    // Login variables
    public String username = "", password = "", passwordHidden = "";
    public boolean hasBlankField = false,
            isInvalidUsername = false,
            isInvalidLogin = false,
            validLogin = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        // HUD Components
        SuperObject heart = new OBJ_Heart(gp);
        fullHeart = heart.defaultList.get(2);
        halfHeart = heart.defaultList.get(1);
        emptyHeart = heart.defaultList.get(0);

        // Login variables initializations

        // INITIALIZE TITLE VIDEO
        ImageIcon icon = new ImageIcon("res/UI/Title.gif");
        titleGif = icon.getImage();

        // INITIALIZE TITLE
        ImageIcon title = new ImageIcon("res/UI/Title.png");
        titleImage = title.getImage();

        // INITIALIZE LOGIN SCREEN
        loginDefault = new ImageIcon("res/UI/loginDefault.png").getImage();
        loginUsername = new ImageIcon("res/UI/loginUsername.png").getImage();
        loginPassword = new ImageIcon("res/UI/loginPassword.png").getImage();
        blankField = new ImageIcon("res/UI/blankField.png").getImage();
        invalidLogin = new ImageIcon("res/UI/invalidLogin.png").getImage();
        invalidUsername = new ImageIcon("res/UI/invalidUsername.png").getImage();
        usernameTaken = new ImageIcon("res/UI/usernameTaken.png").getImage();

        // INITIALIZE START MENU UI
        newGame = new ImageIcon("res/UI/newGame.png").getImage();

        loadGame = new ImageIcon("res/UI/loadGame.png").getImage();

        options = new ImageIcon("res/UI/options.png").getImage();

        quit = new ImageIcon("res/UI/quit.png").getImage();
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 26));
        g2.setColor(Color.white);
        drawPlayerLife();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // LOGIN MENU
        if (gp.gameState == gp.loginState) {
            g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
            drawLoginScreen();
        } else {
            gp.loginPanel.setVisible(false);
        }

        // START MENU STATE
        if (gp.gameState == gp.startMenuState) {
            drawStartMenu();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
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
            drawInventory();
        }
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
//        int slotX = slotXstart; temp commented
//        int slotY = slotYstart;
//
//        //DRAW PLAYER INVENTORY
//        for (int i = 0; i < gp.player.inventory.size(); i++){
//            //insert weapons
//        }

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
        // save composite
        Composite orgComposite = g2.getComposite();

        // set opacity of bg
        float opacity = 0.8f;
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
        if (gp.onUsername) {
            g2.drawImage(loginUsername, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        } else if (gp.onPassword) {
            g2.drawImage(loginPassword, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        } else {
            g2.drawImage(loginDefault, 0, 0, loginDefault.getWidth(null), loginDefault.getHeight(null), null);
        }
        if (hasBlankField)
            g2.drawImage(blankField, 320, 480, 250, 20, null);
        if (isInvalidUsername)
            g2.drawImage(invalidUsername, 320, 480, 250, 20, null);
        if (isInvalidLogin)
            g2.drawImage(invalidLogin, 320, 480, 250, 20, null);
        if (validLogin)
            System.out.println("Login successful! JACOB'S LADDER");

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

            // Execute user registration
            if (registerRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {

            }
            // Execute user login
            if (loginRect.contains(gp.cursor.getMouseX(), gp.cursor.getMouseY())) {
                System.out.println("Clicked on login.");
                checkLogin();
                System.out.println(isInvalidUsername);
            }

            gp.mouseH.clearMouseClick();
        }

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
        } else if (UtilityTool.containsIllegals(username)) {
            System.out.println("Has illegal");
            hasBlankField = false;
            isInvalidUsername = true;
        } else if (!username.isEmpty() && !password.equals("bunny")) {
            hasBlankField = false;
            isInvalidUsername = false;
            isInvalidLogin = true;
        } else if (username.equals("jacob") && password.equals("bunny")) {
            hasBlankField = false;
            isInvalidUsername = false;
            isInvalidLogin = false;
            validLogin = true;
        }
        System.out.println(username);
        System.out.println(!UtilityTool.containsIllegals(username));
    }

    public void checkRegister () {

    }

    public void drawNewGame() {
        int x = (gp.SCREEN_WIDTH - newGame.getWidth(null)) / 2;
        int y = (gp.SCREEN_HEIGHT - newGame.getHeight(null)) / 2;
        g2.drawImage(newGame, x, y, newGame.getWidth(null), newGame.getHeight(null), null);
    }

    public void drawLoadGame() {
        int x = (gp.SCREEN_WIDTH - loadGame.getWidth(null)) / 2;
        int y = (gp.SCREEN_HEIGHT - loadGame.getHeight(null)) / 2 + 60;
        g2.drawImage(loadGame, x, y, loadGame.getWidth(null), loadGame.getHeight(null), null);
    }

    public void drawOptions() {
        int x = (gp.SCREEN_WIDTH - options.getWidth(null)) / 3;
        int y = (gp.SCREEN_HEIGHT - options.getHeight(null)) / 2 + 130;
        g2.drawImage(options, x, y, options.getWidth(null), options.getHeight(null), null);
    }

    public void drawQuit() {
        int x = ((gp.SCREEN_WIDTH - quit.getWidth(null)) / 2 + 110);
        int y = (gp.SCREEN_HEIGHT - quit.getHeight(null)) / 2 + 130;
        g2.drawImage(quit, x, y, quit.getWidth(null), quit.getHeight(null), null);
    }

    // Draw Start Menu
    public void drawStartMenu() {

        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // title image
        drawTitleImage();
        drawNewGame();
        drawLoadGame();
        drawOptions();
        drawQuit();

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 55F));
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
}
