package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean
            wPressed,
            sPressed,
            aPressed,
            dPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { wPressed = true; }
        if (code == KeyEvent.VK_S) { sPressed = true; }
        if (code == KeyEvent.VK_A) { aPressed = true; }
        if (code == KeyEvent.VK_D) { dPressed = true; }
        if (code == KeyEvent.VK_P) {
            if(gp.gameState == gp.playState) {
               gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }

        // !!!!! TEMPORARY TITLE+START MENU TEST !!!!!
        if (code == KeyEvent.VK_T) {
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.titleState;
            } else if (gp.gameState == gp.titleState){
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_Y) {
            if(gp.gameState == gp.playState) {
                gp.gameState = gp.startMenuState;
            } else if (gp.gameState == gp.startMenuState){
                gp.gameState = gp.playState;
            }
        }
        // !!!!! TEMPORARY TITLE+START MENU TEST !!!!!

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { wPressed = false; }
        if (code == KeyEvent.VK_S) { sPressed = false; }
        if (code == KeyEvent.VK_A) { aPressed = false; }
        if (code == KeyEvent.VK_D) { dPressed = false; }
    }
}
