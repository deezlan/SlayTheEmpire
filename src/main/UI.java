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
    private final LoginPanel loginPanel;

    public String currentDialog = "";
    public int slotCol = 0;
    public int slotRow = 0;
    public int slotColMove = 0;
    public int slotRowMove = 0;
    public int commandNum = 0;
    public UI(GamePanel gp) {
        this.gp = gp;

        loginPanel = new LoginPanel(gp);

        //HUD Components
        SuperObject heart = new OBJ_Heart(gp);
        fullHeart = heart.defaultList.get(2);
        halfHeart = heart.defaultList.get(1);
        emptyHeart = heart.defaultList.get(0);

        // INITIALIZE TITLE VIDEO
        ImageIcon icon = new ImageIcon("res/UI/Title.gif");
        titleGif = icon.getImage();

        // INITIALIZE TITLE
        ImageIcon title = new ImageIcon("res/UI/Title.png");
        titleImage = title.getImage();

        // INITIALIZE START MENU UI
        ImageIcon newgame = new ImageIcon("res/UI/newgame.png");
        newGame = newgame.getImage();

        ImageIcon load = new ImageIcon("res/UI/loadgame.png");
        loadGame = load.getImage();

        ImageIcon optionsImage = new ImageIcon("res/UI/options.png");
        options = optionsImage.getImage();

        ImageIcon quitImage = new ImageIcon("res/UI/quit.png");
        quit = quitImage.getImage();
    }

    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 28));
        g2.setColor(Color.white);
        drawPlayerLife();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // LOGIN MENU
        if (gp.gameState == gp.loginState) {
            drawLogin();
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
        int x = getXforCenteredText(text);
        int y = gp.SCREEN_HEIGHT/2;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+2, y+2);
        // Main PauseScreen
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

    }

    // Calculate Image Height
    public int calculateHeight(Image img) {
        return img.getHeight(null);
    }

    public int calculateWidth(Image img) {
        return img.getWidth(null);
    }

    // Draw Title GIF
    public void drawTitleScreen() {
        g2.drawImage(titleGif, 0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, null);
    }

    // DRAW TITLE IMAGE
    public void drawTitleImage() {
        int imageWidth = (int)(gp.SCREEN_WIDTH/1.3);
        int imageHeight = (int) (calculateHeight(titleImage) * ((double) imageWidth / titleImage.getWidth(null)));
        int x = (gp.SCREEN_WIDTH - imageWidth) / 2;
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 4;

        g2.drawImage(titleImage, x, y, imageWidth, imageHeight, null);
    }

    // Draw LOGIN_OR_REGISTER SCREEN
//    public void drawLoginOrRegisterScreen() {
//
//    }

    // Draw Login Screen
    public void drawLogin() {

        // black background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // Draw login
        gp.loginPanel.setVisible(true);

        // draw title
        drawTitleImage();
    }

    public void drawNewGame() {
        int imageWidth = calculateWidth(newGame);
        int imageHeight = calculateHeight(newGame);
        int x = (gp.SCREEN_WIDTH - imageWidth) / 2;
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 2;
        g2.drawImage(newGame, x, y, imageWidth, imageHeight, null);
        if (commandNum == 0) {
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.TILE_SIZE, y);
        }
    }

    public void drawLoadGame() {
        int imageWidth = calculateWidth(loadGame);
        int imageHeight = calculateHeight(loadGame);
        int x = (gp.SCREEN_WIDTH - imageWidth) / 2;
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 2 + 60;
        g2.drawImage(loadGame, x, y, imageWidth, imageHeight, null);
        if (commandNum == 1) {
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.TILE_SIZE, y);
        }
    }

    public void drawOptions() {
        int imageWidth = calculateWidth(options);
        int imageHeight = calculateHeight(options);
        int x = (gp.SCREEN_WIDTH - imageWidth) / 3;
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 2 + 130;
        g2.drawImage(options, x, y, imageWidth, imageHeight, null);
        if (commandNum == 2) {
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.TILE_SIZE, y);
        }
    }

    public void drawQuit() {
        int imageWidth = calculateWidth(quit);
        int imageHeight = calculateHeight(quit);
        int x = ((gp.SCREEN_WIDTH - imageWidth) / 2 + 110);
        int y = (gp.SCREEN_HEIGHT - imageHeight) / 2 + 130;
        g2.drawImage(quit, x, y, imageWidth, imageHeight, null);
        if (commandNum == 3) {
            g2.setColor(Color.WHITE);
            g2.drawString(">", x - gp.TILE_SIZE, y);
        }
    }

    // Draw Start Menu
    public void drawStartMenu() {
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        // Draw Title and Buttons
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

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.SCREEN_WIDTH/2 - length/2;
    }
}
