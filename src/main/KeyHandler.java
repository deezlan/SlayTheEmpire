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
            ePressed;
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // PLaystate
        if (gp.gameState == gp.playState){
            if (code == KeyEvent.VK_W) { wPressed = true; }
            if (code == KeyEvent.VK_S) { sPressed = true; }
            if (code == KeyEvent.VK_A) { aPressed = true; }
            if (code == KeyEvent.VK_D) { dPressed = true; }
            if (code == KeyEvent.VK_E) { ePressed = true; }
        } else if (code == KeyEvent.VK_P) {  // Pause State
            if (gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        } else if (code == KeyEvent.VK_E){  //Dialogue State
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState;
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
    }
}
