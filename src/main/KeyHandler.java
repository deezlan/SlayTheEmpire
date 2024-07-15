package main;

import entity.NPC_Blacksmith;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Player;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    boolean musicPlaying = false; // check if music is already playing

    public boolean
            wPressed,
            sPressed,
            aPressed,
            dPressed,
            ePressed,
            pPressed,
            enterPressed,
            showDebug, // DEBUG
            shotKeyPressed,
            onePressed,
            twoPressed,
            threePressed,
            godModeOn,
            escapePressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // **GAME STATES**
        if (gp.gameState == gp.titleState) {
            titleState(code);
        } else if (gp.gameState == gp.loginState) {
            loginState(code);
        } else if (gp.gameState == gp.startMenuState) {
            startMenuState(code);
        } else if (gp.gameState == gp.characterSelectionState) {
            characterSelectionState(code);
        } else if (gp.gameState == gp.playState) {
            playState(code);
        } else if (gp.gameState == gp.optionState || gp.gameState == gp.optionState2) {
            optionState(code);
        } else if (gp.gameState == gp.shopState) {
            shopState(code);
        } else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } else if (gp.gameState == gp.dialogueState || gp.gameState == gp.cutsceneState) {
            dialogState(code);
        } else if (gp.gameState == gp.deathState) {
            deathState(code);
        } else if (gp.gameState == gp.creditsState) {
            creditsState(code);
        } else if (gp.gameState == gp.controlsState) {
            controlsState(code);
        }

        if (code == KeyEvent.VK_T){
            showDebug = !showDebug;
        }

        // !!!!! LOGIN+START MENU !!!!!
        if (code == KeyEvent.VK_SPACE) {
            if (gp.gameState == gp.titleState){
                gp.playSE(2);
                gp.gameState = gp.startMenuState;
            }
        }
    }

    // GAME STATES METHODS
    public void titleState(int code) {
        if (code == KeyEvent.VK_SPACE) {
            if (gp.gameState == gp.titleState){
                gp.playSE(2);
                gp.gameState = gp.loginState;
            }
        }
    }

    public void startMenuState(int code) {
        if (gp.gameState == gp.startMenuState){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_S) {
                gp.ui.commandNum += (code == KeyEvent.VK_W) ? -1 : 1;
                gp.playSE(1);
                gp.ui.commandNum = Math.max(0, Math.min(gp.ui.commandNum, 4));
            }

            // SELECT OPTION
            if (code == KeyEvent.VK_SPACE) {
                //SOUND EFFECT
                gp.playSE(2);
                switch (gp.ui.commandNum) {
                    case 0 -> gp.gameState = gp.characterSelectionState; // GO TO LOGIN
                    case 1 -> System.out.println("My name is Yoshikage Kira. I'm 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married. I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest. \n I don't smoke, but I occasionally drink. I'm in bed by 11 PM, and make sure I get eight hours of sleep, no matter what. After having a glass of warm milk and doing about twenty minutes of stretches before going to bed, I usually have no problems sleeping until morning. \n Just like a baby, I wake up without any fatigue or stress in the morning. I was told there were no issues at my last check-up. I'm trying to explain that I'm a person who wishes to live a very quiet life. I take care not to trouble myself with any enemies, like winning and losing, that would cause me to lose sleep at night. \n That is how I deal with society, and I know that is what brings me happiness. Although, if I were to fight I wouldn't lose to anyone."); // LOAD GAME
                    case 2 -> gp.gameState = gp.creditsState;
                    case 3 -> {
                        gp.gameState = gp.optionState2;
                        // DEFAULT TO FIRST OPTION
                        gp.ui.commandNum = 0;
                    }
                    case 4 -> System.exit(0); // EXIT GAME
                }
            }
        }
    }

    public void creditsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.startMenuState;
            gp.ui.typingUsername = false;
            gp.ui.typingPassword = false;
            gp.ui.inpUser = "";
            gp.ui.inpPass = "";
            gp.ui.inpPassHidden = "";
        }
    }

    public void controlsState(int code) {

    }

    public void loginState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.titleState;
        }

        if (gp.ui.typingUsername) {
            if (code == KeyEvent.VK_BACK_SPACE) {
                System.out.println("Test backspace");
                if (!gp.ui.inpUser.isEmpty()) {
                    gp.ui.inpUser = gp.ui.inpUser.substring(0, gp.ui.inpUser.length() - 1);
                }
            } else if (code!= KeyEvent.VK_SHIFT && code!= KeyEvent.VK_SPACE && code!= KeyEvent.VK_CAPS_LOCK) {
                System.out.println("Test any key");

                System.out.println(gp.ui.inpUser);
                gp.ui.inpUser = gp.ui.inpUser.concat(String.valueOf((char) code));
                System.out.println("Username is: " + gp.ui.inpUser);
            }
        }

        if (gp.ui.typingPassword) {
            if (code == KeyEvent.VK_BACK_SPACE) {
                System.out.println("Test backspace");
                if (!gp.ui.inpPass.isEmpty()) {
                    gp.ui.inpPass = gp.ui.inpPass.substring(0, gp.ui.inpPass.length() - 1);
                    gp.ui.inpPassHidden = gp.ui.inpPassHidden.substring(0, gp.ui.inpPassHidden.length() - 1);
                }
            } else if (code!= KeyEvent.VK_SHIFT && code!= KeyEvent.VK_SPACE && code!= KeyEvent.VK_CAPS_LOCK) {
                System.out.println("Test any key");

                System.out.println(gp.ui.inpPass);
                gp.ui.inpPass = gp.ui.inpPass.concat(String.valueOf((char) code));
                gp.ui.inpPassHidden = gp.ui.inpPassHidden.concat("*");

                System.out.println("Password is: " + gp.ui.inpPass);
                System.out.println("Password Hidden is: " + gp.ui.inpPassHidden);
            }
        }
    }

    public void characterSelectionState(int code) {
        // SELECT CLASS
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
            gp.ui.commandNum += (code == KeyEvent.VK_A) ? -1 : 1;
            gp.playSE(1);
            gp.ui.commandNum = Math.max(0, Math.min(gp.ui.commandNum, 2));
        }

        // CHOOSE CLASS
        if (code == KeyEvent.VK_SPACE) {
            // SELECT SOUND EFFECT
            gp.playSE(2);
            switch (gp.ui.commandNum) {
                case 0:
                    // CHANGE CLASS TO WARRIOR
                    gp.player = new Player(gp, gp.keyH, gp.cursor, 0);
                    gp.gameState = gp.playState;
                    break;
                case 1:
                    // CHANGE CLASS TO KNIGHT
                    gp.player = new Player(gp, gp.keyH, gp.cursor, 1);
                    gp.gameState = gp.playState;
                    break;
                case 2:
                    // CHANGE CLASS TO ASSASSIN
                    gp.player = new Player(gp, gp.keyH, gp.cursor, 2);
                    gp.gameState = gp.playState;
                    break;
            }
            gp.loadLevel();
        }
    }

    public void playState(int code) {
        if (gp.gameState == gp.playState){
            if (!musicPlaying) {
                gp.music.stopAll();
                gp.playMusic(4);
                musicPlaying = true;
            }
            // GO TO OPTIONS
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.optionState;
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
            if (code == KeyEvent.VK_G){
                godModeOn = !godModeOn;
            }
        }
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            pPressed = true;
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }
        }
    }

    public void dialogState(int code) {
        if (code == KeyEvent.VK_E) {
            ePressed = true;
            if (gp.gameState == gp.shopState) {
                gp.gameState = gp.playState;
            }
        }
    }

    public void deathState(int code) {
        if (code == KeyEvent.VK_E){
            ePressed = true;
        }
    }

    private void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = (gp.gameState == gp.playState) ? gp.optionState : (gp.gameState == gp.optionState) ? gp.playState : gp.gameState;
        }

        // OPTION SELECT
        if (gp.gameState == gp.optionState || gp.gameState == gp.optionState2) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_S) {
                gp.ui.commandNum += (code == KeyEvent.VK_W) ? -1 : 1;
                gp.playSE(1);

                // skip through empty button for option 2
                if (gp.gameState == gp.optionState2) {
                    if (gp.ui.commandNum == 3) {
                        gp.ui.commandNum += (code == KeyEvent.VK_W) ? -1 : 1;
                    }
                }
            }

            // MUSIC AND SFX CONTROL
            if (gp.ui.commandNum == 0 || gp.ui.commandNum == 1) {
                if (code == KeyEvent.VK_A || code == KeyEvent.VK_D) {
                    int adjustment = (code == KeyEvent.VK_A) ? -1 : 1;
                    gp.playSE(1);

                    switch (gp.ui.commandNum) {
                        case 0: {
                            // MUSIC
                            gp.music.volumeScale = Math.max(0, Math.min(gp.music.volumeScale + adjustment, 5));
                            gp.music.checkVolume();
                            System.out.println((adjustment == -1 ? "minus'ing" : "plus'ing") + "music vol");
                            break;
                        }
                        case 1: {
                            // SFX
                            gp.effect.volumeScale = Math.max(0, Math.min(gp.effect.volumeScale + adjustment, 5));
                            gp.effect.checkVolume();
                            System.out.println((adjustment == -1 ? "minus'ing" : "plus'ing") + "sfx vol");
                            break;
                        }
                    }
                }
            }

            if (code == KeyEvent.VK_SPACE) {
                gp.playSE(1);
                switch (gp.ui.commandNum) {
                    // CONTROLS
                    case 2: {
                        break;
                    }
                    // END GAME
                    case 3: {
                        if (gp.gameState == gp.optionState) {
                            System.exit(0);
                        }
                        break;
                    }
                    // BACK
                    case 4: {
                        gp.gameState = (gp.gameState == gp.optionState) ? gp.playState : gp.startMenuState;
                        gp.ui.commandNum = 0;
                        break;
                    }
                }
            }
        }
    }

    public void shopState(int code) {
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
        if (code == KeyEvent.VK_ESCAPE) {
            escapePressed = false;
        }
    }
}
