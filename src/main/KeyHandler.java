package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean
            wPressed,
            sPressed,
            aPressed,
            dPressed,
            ePressed,
            pPressed,
            enterPressed;


    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

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



        // Start Menu State
        if (gp.gameState == gp.startMenuState){
            if (code == KeyEvent.VK_W){
                gp.ui.commandNum--;
            }
            if (code == KeyEvent.VK_S){
                gp.ui.commandNum++;
            }

            if (code == KeyEvent.VK_SPACE) {
                if (gp.ui.commandNum == 0) {
                    // GO TO CHAR SELECTION
                    gp.gameState = gp.characterSelectionState;
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


        // Play State
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W) {
                wPressed = true;
                if (gp.ui.slotRowMove != 0){
                    gp.ui.slotRowMove -= 2;
                    gp.ui.slotRow--;
                }
            }
            if (code == KeyEvent.VK_S) {
                sPressed = true;
                if (gp.ui.slotRowMove != 6){
                    gp.ui.slotRowMove += 2;
                    gp.ui.slotRow++;
                }
            }
            if (code == KeyEvent.VK_A) {
                aPressed = true;
                if (gp.ui.slotColMove != 0){
                    gp.ui.slotColMove -= 2;
                    gp.ui.slotCol--;
                }
            }
            if (code == KeyEvent.VK_D) {
                dPressed = true;
                if (gp.ui.slotColMove != 2){
                    gp.ui.slotColMove += 2;
                    gp.ui.slotCol++;
                }
            }
        }

        // PAUSING
        if (code == KeyEvent.VK_P) {
            pPressed = true;
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState){
                    gp.gameState = gp.playState;
            }
        }

        // MOVING THROUGH DIALOGUE
        if (code == KeyEvent.VK_E) {
            ePressed = true;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
            }  else if (gp.gameState == gp.shopState) {
                gp.gameState = gp.playState;
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { wPressed = false; }
        if (code == KeyEvent.VK_S) { sPressed = false; }
        if (code == KeyEvent.VK_A) { aPressed = false; }
        if (code == KeyEvent.VK_D) { dPressed = false; }
        if (code == KeyEvent.VK_ENTER) { enterPressed = false; }
    }
}
