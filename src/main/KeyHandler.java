package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        if (code == KeyEvent.VK_P) {
            pPressed = true;
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState){
                    gp.gameState = gp.playState;
            }
        }
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
            } else if (gp.gameState == gp.startMenuState) {
                gp.gameState = gp.playState;
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
