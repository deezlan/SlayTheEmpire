package main;

import entity.NPC_Blacksmith;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import entity.Player;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    Sound sound = new Sound();
    boolean musicPlaying = false; // check if music is already playing

    // PLAY MUSIC
    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    // STOP MUSIC
    public void stopMusic() {
        sound.stop();
    }
    // PLAY SOUND EFFECT
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

    public boolean
            wPressed,
            sPressed,
            aPressed,
            dPressed,
            ePressed,
            pPressed,
            enterPressed,
            showDebug = false, // DEBUG
            shotKeyPressed,
            onePressed,
            twoPressed,
            threePressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // **GAME STATES**
        if (gp.gameState == gp.loginState) {
            if (gp.onUsername) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    System.out.println("Test backspace");
                    if (!gp.ui.username.isEmpty()) {
                        gp.ui.username = gp.ui.username.substring(0, gp.ui.username.length() - 1);
                    }
                } else if (e.getKeyCode() != KeyEvent.VK_SHIFT && e.getKeyCode() != KeyEvent.VK_SPACE && e.getKeyCode() != KeyEvent.VK_CAPS_LOCK) {
                    System.out.println("Test any key");

                    System.out.println(gp.ui.username);
                    gp.ui.username = gp.ui.username.concat(String.valueOf(e.getKeyChar()));
                    System.out.println("Username is: " + gp.ui.username);
                }
            }

            if (gp.onPassword) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    System.out.println("Test backspace");
                    if (!gp.ui.password.isEmpty()) {
                        gp.ui.password = gp.ui.password.substring(0, gp.ui.password.length() - 1);
                        gp.ui.passwordHidden = gp.ui.passwordHidden.substring(0, gp.ui.passwordHidden.length() - 1);
                    }
                } else if (e.getKeyCode() != KeyEvent.VK_SHIFT && e.getKeyCode() != KeyEvent.VK_SPACE && e.getKeyCode() != KeyEvent.VK_CAPS_LOCK) {
                    System.out.println("Test any key");

                    System.out.println(gp.ui.password);
                    gp.ui.password = gp.ui.password.concat(String.valueOf(e.getKeyChar()));
                    gp.ui.passwordHidden = gp.ui.passwordHidden.concat("*");

                    System.out.println("Password is: " + gp.ui.password);
                    System.out.println("Password Hidden is: " + gp.ui.passwordHidden);
                }
            }
        }

        // START MENU STATE
        if (gp.gameState == gp.startMenuState){
            if (code == KeyEvent.VK_W){
                gp.ui.commandNum--;
                playSE(1);
            }
            if (code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                playSE(1);
            }
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 0;
            } else if (gp.ui.commandNum > 3) {
                gp.ui.commandNum = 3;
            }

            if (code == KeyEvent.VK_SPACE) {
                // SELECT SOUND EFFECT
                playSE(2);
                if (gp.ui.commandNum == 0) {
                    // GO TO LOGIN
                    gp.gameState = gp.loginState;
                }
                if (gp.ui.commandNum == 1) {
                    // LOAD GAME
                    System.out.println("My name is Yoshikage Kira. I'm 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married. I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest. \n I don't smoke, but I occasionally drink. I'm in bed by 11 PM, and make sure I get eight hours of sleep, no matter what. After having a glass of warm milk and doing about twenty minutes of stretches before going to bed, I usually have no problems sleeping until morning. \n Just like a baby, I wake up without any fatigue or stress in the morning. I was told there were no issues at my last check-up. I'm trying to explain that I'm a person who wishes to live a very quiet life. I take care not to trouble myself with any enemies, like winning and losing, that would cause me to lose sleep at night. \n That is how I deal with society, and I know that is what brings me happiness. Although, if I were to fight I wouldn't lose to anyone.");
                }
                if (gp.ui.commandNum == 2) {
                    // GO TO OPTIONS
                    gp.gameState = gp.optionState;
                }
                if (gp.ui.commandNum == 3) {
                    // EXIT GAME
                    System.exit(0);
                }
            }
        }
        // LOGIN
        else if (gp.gameState == gp.loginState){
//            if (validLogin) {
//                System.out.println("Login Successful.");
//            }
        }
        // CHARACTER SELECTION
        else if (gp.gameState == gp.characterSelectionState) {
            if (code == KeyEvent.VK_SPACE) {
                // SELECT SOUND EFFECT
                playSE(2);
                if (gp.ui.commandNum == 0) {
                    // CHANGE CLASS TO WARRIOR
                    gp.player = new Player(gp, gp.keyH, gp.cursor, 0);
                    gp.gameState = gp.playState;
                } else if (gp.ui.commandNum == 1) {
                    // CHANGE CLASS TO KNIGHT
                    gp.player = new Player(gp, gp.keyH, gp.cursor, 1);
                    gp.gameState = gp.playState;
                } else if (gp.ui.commandNum == 2) {
                    // CHANGE CLASS TO ASSASSIN
                    gp.player = new Player(gp, gp.keyH, gp.cursor, 2);
                    gp.gameState = gp.playState;
                }
            }

            if (code == KeyEvent.VK_A){
                gp.ui.commandNum--;
                playSE(1);
            }
            if (code == KeyEvent.VK_D){
                gp.ui.commandNum++;
                playSE(1);
            }

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 0;
            } else if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 2;
            }
        }

        // PLAY STATE
        if (gp.gameState == gp.playState){
            if (!musicPlaying) {
                sound.stopAll();
                playMusic(4);
                musicPlaying = true;
            }
            if (code == KeyEvent.VK_W) {
                wPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                sPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                aPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                dPressed = true;
            }
            if (code == KeyEvent.VK_E){
                ePressed = true;
            }
            if (code == KeyEvent.VK_R){
                switch(gp.currentMap) {
                    case 0: gp.tileM.loadMap("/mapTextFiles/firstLevel.txt",0); break;
                    case 1: gp.tileM.loadMap("/mapTextFiles/firstLevel.txt",1); break;
                }
            }
            if (code == KeyEvent.VK_F) {
                shotKeyPressed = true;
            }
            if (code == KeyEvent.VK_1){
                onePressed = true;
            }
            if (code == KeyEvent.VK_2){
                twoPressed = true;
            }
            if (code == KeyEvent.VK_3){
                threePressed = true;
            }
        }

        // SHOP STATE
        if (gp.gameState == gp.shopState){
            if (code == KeyEvent.VK_W) {
                if (gp.ui.slotRowMove != 0){
                    gp.ui.slotRowMove -= 1;
                    gp.ui.slotRow--;
                }
            }
            if (code == KeyEvent.VK_ENTER){
                NPC_Blacksmith bs = (NPC_Blacksmith) gp.npcArr[gp.currentMap][1];
                if (gp.player.totalCoins < bs.getShopItems().get(gp.ui.slotRow).price){
                    //Do nothing
                } else {
                    bs.buy();
                }
                gp.gameState = gp.playState;
            }
            if (code == KeyEvent.VK_S) {
                if (gp.ui.slotRowMove != 3){
                    gp.ui.slotRowMove += 1;
                    gp.ui.slotRow++;
                }
            }
        }

        // PAUSE STATE
        if (code == KeyEvent.VK_P) {
            pPressed = true;
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState){
                    gp.gameState = gp.playState;
            }
        }

        // **KEY HANDLING**
        // MOVING THROUGH DIALOGUE
        if (code == KeyEvent.VK_E) {
            ePressed = true;
//            if(gp.gameState == gp.dialogueState){
//                gp.gameState = gp.playState;
            if (gp.gameState == gp.shopState) {
                gp.gameState = gp.playState;
            } else if (gp.gameState == gp.deathState){
                gp.gameState = gp.playState;
                gp.currentMap = 0;
                gp.setMapColor();
                gp.retry();
            }
        }

        // !!!!! TEMPORARY TITLE+LOGIN+START MENU TEST !!!!!
        if (code == KeyEvent.VK_SPACE) {
            if (gp.gameState == gp.titleState){
                gp.gameState = gp.startMenuState;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
            if (gp.gameState == gp.loginState) {
                gp.gameState = gp.startMenuState;
            }
        }
        // !!!!! TEMPORARY TITLE+LOGIN+START MENU TEST !!!!!

        // DEBUG
        if (code == KeyEvent.VK_T){
            if(!showDebug){
                showDebug = true;
            }
            else if (showDebug){
                showDebug = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { wPressed = false; }
        if (code == KeyEvent.VK_S) { sPressed = false; }
        if (code == KeyEvent.VK_A) { aPressed = false; }
        if (code == KeyEvent.VK_D) { dPressed = false; }
        if (code == KeyEvent.VK_ENTER) { enterPressed = false; }
        if (code == KeyEvent.VK_F) { shotKeyPressed = false; }
        if (code == KeyEvent.VK_1){ onePressed = false; }
        if (code == KeyEvent.VK_2){ twoPressed = false; }
        if (code == KeyEvent.VK_3){ threePressed = false; }
    }
}
