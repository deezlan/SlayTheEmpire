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
        if (code == KeyEvent.VK_P) {
            if(gp.gameState == gp.playState) {
               gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
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
    }
}
