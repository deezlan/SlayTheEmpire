package main;

import entity.Player;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect ;
    Player eventMaster;
    int previousEventX, previousEventY; // prevent event from happening again immediately
    int tempMap, tempCol, tempRow;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventMaster = new Player (gp, gp.keyH, gp.cursor, gp.playerClass);
        eventRect = new EventRect[gp.MAX_MAP][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.MAX_MAP && col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 0;
            eventRect[map][col][row].y = -24;
            eventRect[map][col][row].width = 48;
            eventRect[map][col][row].height = 48;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;
            if(col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;

                if(row == gp.MAX_WORLD_ROW) {
                    row = 0;
                    map++;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue() {
        eventMaster.dialogs[0][0] = "Drank Possibly Toilet Water";
        eventMaster.dialogs[0][1] = "Why does it taste like pee";
    }

    // check tile for event;
    public void checkEvent() {
        // check tile if player i one tile away from the tile
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.TILE_SIZE) canTouchEvent = true;

        if (canTouchEvent) { // use else if to add more events
            switch (gp.currentMap) {
                // LOBBY
                case 0:
                    if (hit(0, 8, 13, "any")) {
                        changeMap();
                    } else if (
                            hit(0, 2, 9, "any")
                                    || hit(gp.currentMap, 3, 10, "any")
                                    || hit(gp.currentMap, 4, 10, "moveDown")
                                    || hit(gp.currentMap, 13, 10, "any")
                    ) {
                        drinkWater(gp.DIALOGUE_STATE);
                    }
                    break;

                // LEVEL ONE
                case 1:
                    if (hit(gp.currentMap,29, 16, "any")) {
                        FrostGiant();
                    }
                    if ( // TRIGGER 1ST ROOM GATES
                            hit(gp.currentMap, 24, 40, "any")
                                    || hit(gp.currentMap, 25, 41, "any")
                                    || hit(gp.currentMap, 25, 42, "any")
                                    || hit(gp.currentMap, 25, 43, "any")
                                    || hit(gp.currentMap, 24, 44, "any")
                    ) {
//                        for (int i = 0; i <= 4; i++)
//                            gp.gateArr[gp.currentMap][i].locking = true;
                        triggerGates(true, 0, 4);

                        eventRect[1][24][40].eventDone = true;
                        eventRect[1][25][41].eventDone = true;
                        eventRect[1][25][42].eventDone = true;
                        eventRect[1][25][43].eventDone = true;
                        eventRect[1][24][44].eventDone = true;
                    }
                    if ( // TRIGGER LEFT PATH GATES
                            hit(gp.currentMap, 25, 30, "any")
                                    || hit(gp.currentMap, 25, 31, "any")
                    ) {
                        for (int i = 5; i <= 6; i++)
                            gp.gateArr[gp.currentMap][i].locking = true;

                        eventRect[1][25][30].eventDone = true;
                        eventRect[1][25][31].eventDone = true;
                    }
                    if ( // TRIGGER RIGHT PATH GATES
                            hit(gp.currentMap, 33, 30, "any")
                                    || hit(1, 33, 31, "any")
                    ) {
                        for (int i = 7; i <= 8; i++)
                            gp.gateArr[gp.currentMap][i].locking = true;

                        eventRect[1][33][30].eventDone = true;
                        eventRect[1][33][31].eventDone = true;
                    }
                    if ( // TRIGGER MINI-BOSS ROOM GATES
                            hit(1, 24, 22, "any")
                                    || hit(1, 25, 23, "any")
                                    || hit(1, 25, 24, "any")
                                    || hit(1, 25, 25, "any")
                                    || hit(1, 24, 26, "any")

                                    || hit(1, 34, 22, "any")
                                    || hit(1, 33, 23, "any")
                                    || hit(1, 33, 24, "any")
                                    || hit(1, 33, 25, "any")
                                    || hit(1, 34, 26, "any")
                    ) {
                        triggerGates(true, 9, 15);

                        eventRect[1][24][22].eventDone = true;
                        eventRect[1][25][23].eventDone = true;
                        eventRect[1][25][24].eventDone = true;
                        eventRect[1][25][25].eventDone = true;
                        eventRect[1][24][26].eventDone = true;
                        eventRect[1][34][22].eventDone = true;
                        eventRect[1][33][23].eventDone = true;
                        eventRect[1][33][24].eventDone = true;
                        eventRect[1][33][25].eventDone = true;
                        eventRect[1][34][26].eventDone = true;
                    }
                    if ( // TRIGGER FINAL BOSS ROOM GATES
                            hit(1, 27, 13, "any")
                                    || hit(1, 28, 12, "any")
                                    || hit(1, 29, 12, "any")
                                    || hit(1, 30, 12, "any")
                                    || hit(1, 31, 13, "any")
                    ) {
                        for (int i = 16; i <= 19; i++)
                            gp.gateArr[gp.currentMap][i].locking = true;

//                        eventRect[1][27][13].eventDone = true;
//                        eventRect[1][28][12].eventDone = true;
//                        eventRect[1][29][12].eventDone = true;
//                        eventRect[1][30][12].eventDone = true;
//                        eventRect[1][31][13].eventDone = true;
                    }

                    // UNLOCK FIRST ROOM GATES ONCE CLEARED
                    if (roomCleared(0, 3))
                        triggerGates(false, 0, 4);

                    // UNLOCK MINI-BOSS ROOM GATES ONCE CLEARED
                    if (roomCleared(4, 6))
                        triggerGates(false, 5, 15);

                    // UNLOCK FINAL BOSS ROOM GATES ONCE CLEARED
                    if (roomCleared(7, 11))
                        triggerGates(false, 16, 19);

                    break;

                // LEVEL TWO
                case 2:
                    if ( // TRIGGER 1ST ROOM GATES
                            hit(gp.currentMap, 25, 35, "any")
                                    || hit(gp.currentMap, 24, 36, "any")
                                    || hit(gp.currentMap, 24, 37, "any")
                                    || hit(gp.currentMap, 24, 38, "any")
                                    || hit(gp.currentMap, 25, 39, "any")
                    ) {
//                        for (int i = 0; i <= 3; i++)
//                            gp.gateArr[gp.currentMap][i].locking = true;
                    }
                    if ( // TRIGGER MINI-BOSS ROOM GATES
                            hit(gp.currentMap, 12, 23, "any")
                                    || hit(gp.currentMap, 11, 24, "any")
                                    || hit(gp.currentMap, 11, 25, "any")
                                    || hit(gp.currentMap, 11, 26, "any")
                                    || hit(gp.currentMap, 12, 27, "any")
                    ) {
//                        for (int i = 4; i <= 8; i++)
//                            gp.gateArr[gp.currentMap][i].locking = true;
                    }
                    if ( // TRIGGER FINAL BOSS ROOM GATES
                            hit(gp.currentMap, 5, 10, "any")
                                    || hit(gp.currentMap, 6, 9, "any")
                                    || hit(gp.currentMap, 7, 9, "any")
                                    || hit(gp.currentMap, 8, 9, "any")
                                    || hit(gp.currentMap, 9, 10, "any")
                    ) {
//                        for (int i = 9; i <= 14; i++)
//                            gp.gateArr[gp.currentMap][i].locking = true;
                    }
                    if ( // TRIGGER PLOT TWIST FINAL BOSS ROOM GATES
                            hit(gp.currentMap, 26, 4, "any")
                                    || hit(gp.currentMap, 27, 5, "any")
                                    || hit(gp.currentMap, 27, 6, "any")
                                    || hit(gp.currentMap, 27, 7, "any")
                                    || hit(gp.currentMap, 27, 8, "any")
                                    || hit(gp.currentMap, 26, 9, "any")
                    ) {
//                        for (int i = 15; i <= 20; i++)
//                            gp.gateArr[gp.currentMap][i].locking = true;
                    }

                    // UNLOCK FIRST ROOM GATES ONCE EVERY MOB SLAIN
                    if (gp.mobArr[gp.currentMap][0] == null && gp.mobArr[gp.currentMap][1] == null) {
//                        for (int i = 0; i <= 4; i++)
//                            gp.gateArr[gp.currentMap][i].unlocking = true;
                    }

                    // UNLOCK MINI BOSS GATE AFTER SLAIN
                    if (gp.mobArr[1][3] == null) {
//                    for (int i = 9; i <= 15; i++)
//                        gp.gateArr[gp.currentMap][i].unlocking = true;
                    }

                    // UNLOCK BOSS ROOM GATES ONCE BOSS SLAIN
                    if (gp.mobArr[gp.currentMap][2] == null) {
//                        for (int i = 16; i <= 19; i++)
//                            gp.gateArr[gp.currentMap][i].unlocking = true;
                    }
            }
        }
    }

    public boolean hit (int map, int col, int row, String reqAction) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.TILE_SIZE + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.TILE_SIZE + eventRect[map][col][row].y;

            // second half of if statement for 1 time events
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.action.contentEquals(reqAction) || reqAction.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }

    public void triggerGates(boolean lock, int firstGate, int lastGate) {
        for (int i = firstGate; i <= lastGate; i++) {
            if (lock)
                gp.gateArr[gp.currentMap][i].locking = true;
            else
                gp.gateArr[gp.currentMap][i].unlocking = true;
        }
    }

    public boolean roomCleared(int firstMob, int lastMob) {
        boolean cleared = true;

        for (int i = firstMob; i <= lastMob; i++) {
            if (gp.mobArr[gp.currentMap][i] != null) {
                cleared = false;
                break;
            }
        }

        return cleared;
    }

    public void changeMap(){
        gp.gameState = gp.TRANSITION_STATE;

        System.out.println("Changing map - loadLevel() to " + gp.currentMap);
        switch (gp.currentMap) {
            case 0:
//                tempMap = 1; // FIRST LEVEL CORRECT VALUES
//                tempCol = 19;
//                tempRow = 41;
                tempMap = 1; // FIRST LEVEL TESTING VALUES
                tempCol = 22;
                tempRow = 7;
//                tempMap = 2; // SECOND LEVEL CORRECT VALUES
//                tempCol = 31;
//                tempRow = 36;
//                gp.player.lookingRight = false;
                break;
            case 1, 2:
                tempMap = 0;
                tempCol = 6;
                tempRow = 2;
        }

        canTouchEvent = false;
    }

    public void drinkWater(int gameState){
        if(gp.keyH.ePressed){
            gp.gameState = gameState;
            gp.ui.currentDialog = "Drank Possibly Toilet Water";
            eventMaster.startDialogue(eventMaster,0);
            gp.player.currentLife = gp.player.maxLife;
            canTouchEvent = false;
        }
    }

    public void FrostGiant() {
        if(!gp.bossBattleOn) {
            gp.gameState = gp.CUTSCENE_STATE;
            gp.csManager.sceneNum = gp.csManager.frostGiant;
        }
    }
}

