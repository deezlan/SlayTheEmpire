package main;

import entity.NPC_Blacksmith;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class KeyHandler implements KeyListener {
    GamePanel gp;
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
            threePressed,
            godModeOn;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    // GAME STATES METHODS
    public void titleState(int code) {
        // PUT TITLE STATE STUFF HERE
    }
    public void playState(int code) {
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
        if (code == KeyEvent.VK_E) {
            ePressed = true;
        }
        if (code == KeyEvent.VK_R) {
            switch (gp.currentMap) {
                case 0:
                    gp.tileM.loadMap("/mapTextFiles/firstLevel.txt", 0);
                    break;
                case 1:
                    gp.tileM.loadMap("/mapTextFiles/firstLevel.txt", 1);
                    break;
            }
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_1) {
            onePressed = true;
        }
        if (code == KeyEvent.VK_2) {
            twoPressed = true;
        }
        if (code == KeyEvent.VK_3) {
            threePressed = true;
        }
        if (code == KeyEvent.VK_T){
            if(!showDebug){
                showDebug = true;
            }
            else if (showDebug){
                showDebug = false;
            }
        }
        if (code == KeyEvent.VK_G){
            if(!godModeOn){
                godModeOn = true;
            }
            else if (godModeOn){
                godModeOn = false;
            }
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
    public void dialogState(int code) {
        if (code == KeyEvent.VK_E){
            gp.gameState = gp.playState;
        }
    }
    public void shopState(int code) {
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRowMove != 0) {
                gp.ui.slotRowMove -= 1;
                gp.ui.slotRow--;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            NPC_Blacksmith bs = (NPC_Blacksmith) gp.npcArr[gp.currentMap][1];
            if (gp.player.totalCoins < bs.getShopItems().get(gp.ui.slotRow).price) {
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
        if (code == KeyEvent.VK_E) {
            ePressed = true;
            if (gp.gameState == gp.shopState || gp.gameState == gp.cutsceneState) {
                gp.gameState = gp.playState;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            titleState(code);
        } else if (gp.gameState == gp.playState) {
            playState(code);
        } else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
            dialogState(code);
        } else if (gp.gameState == gp.shopState) {
            shopState(code);
        } // ADD MORE STATES HERE
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) { wPressed = false; }
        if (code == KeyEvent.VK_S) { sPressed = false; }
        if (code == KeyEvent.VK_A) { aPressed = false; }
        if (code == KeyEvent.VK_D) { dPressed = false; }
        if (code == KeyEvent.VK_P) { pPressed = false; }
        if (code == KeyEvent.VK_ENTER) { enterPressed = false; }
        if (code == KeyEvent.VK_F) { shotKeyPressed = false; }
        if (code == KeyEvent.VK_1){ onePressed = false; }
        if (code == KeyEvent.VK_2){ twoPressed = false; }
        if (code == KeyEvent.VK_3){ threePressed = false; }
    }
}
