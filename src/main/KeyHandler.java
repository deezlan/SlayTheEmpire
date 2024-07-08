package main;

import entity.NPC_Blacksmith;

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

        // PLayState
        if (gp.gameState == gp.playState){
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
        //Pause State
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

        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

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
