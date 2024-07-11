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
        eventRect = new EventRect[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

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

        if (distance > gp.TILE_SIZE){
            canTouchEvent = true;
        }
        if(canTouchEvent) {// use else if to add more events
            switch (gp.currentMap) {
                case 0:
                    if (hit(0,8, 13, "any")) {
                        changeMap();
                    } else if (
                            hit(0,2, 9, "any")
                            || hit(0,3, 10, "any")
                            || hit(0,4, 10, "moveDown")
                            || hit(0,13, 10, "any")
                    ) {
                        drinkWater(gp.dialogueState);
                    } else if (hit(0,8, 4, "any")) {
                        gp.objArr[0][6].locking = true;
//                eventRect[0][8][4].eventDone = true;
                    } else if (hit(0,11, 4, "any")) {
                        gp.objArr[0][6].unlocking = true;
//                eventRect[0][8][4].eventDone = true;
                    }
                    break;
                case 1:
                    if ( // TRIGGER 1ST ROOM GATES
                            hit(1,7, 40, "any")
                            || hit(1,8, 41, "any")
                            || hit(1,8, 42, "any")
                            || hit(1,7, 43, "any")
                    ) {
                        for (int i = 1; i <= 5; i++)
                            gp.objArr[1][i].locking = true;

                        eventRect[1][7][40].eventDone = true;
                        eventRect[1][8][41].eventDone = true;
                        eventRect[1][8][42].eventDone = true;
                        eventRect[1][7][43].eventDone = true;
                    } else if ( // TRIGGER LEFT PATH GATES
                            hit(1,8, 30, "any")
                            || hit(1,8, 31, "any")
                    ) {
                        for (int i = 6; i <= 7; i++)
                            gp.objArr[1][i].locking = true;

                        eventRect[1][8][30].eventDone = true;
                        eventRect[1][8][31].eventDone = true;
                    } else if ( // TRIGGER RIGHT PATH GATES
                            hit(1,16, 30, "any")
                            || hit(1,16, 31, "any")
                    ) {
                        for (int i = 8; i <= 9; i++)
                            gp.objArr[1][i].locking = true;

                        eventRect[1][16][30].eventDone = true;
                        eventRect[1][16][31].eventDone = true;
                    } else if ( // TRIGGER MINI-BOSS ROOM GATES
                            hit(1,7, 22, "any")
                            || hit(1,8, 23, "any")
                            || hit(1,8, 24, "any")
                            || hit(1,7, 25, "any")

                            || hit(1,17, 22, "any")
                            || hit(1,16, 23, "any")
                            || hit(1,16, 24, "any")
                            || hit(1,17, 25, "any")
                    ) {
//                        for (int i = 10; i <= 16; i++)
//                            gp.objArr[1][i].locking = true;

                        eventRect[1][7][22].eventDone = true;
                        eventRect[1][8][23].eventDone = true;
                        eventRect[1][8][24].eventDone = true;
                        eventRect[1][7][25].eventDone = true;
                        eventRect[1][17][22].eventDone = true;
                        eventRect[1][16][23].eventDone = true;
                        eventRect[1][16][24].eventDone = true;
                        eventRect[1][17][25].eventDone = true;
                    } else if ( // TRIGGER FINAL BOSS ROOM GATES
                            hit(1,10, 13, "any")
                            || hit(1,11, 12, "any")
                            || hit(1,12, 12, "any")
                            || hit(1,13, 12, "any")
                            || hit(1,14, 13, "any")
                    ) {
//                        for (int i = 17; i <= 20; i++)
//                            gp.objArr[1][i].locking = true;

                        eventRect[1][10][13].eventDone = true;
                        eventRect[1][11][12].eventDone = true;
                        eventRect[1][12][12].eventDone = true;
                        eventRect[1][13][12].eventDone = true;
                        eventRect[1][14][13].eventDone = true;
                    }

                    // UNLOCK FIRST ROOM GATES ONCE EVERY MOB SLAIN
                    if (gp.mobArr[1][0] == null && gp.mobArr[1][1] == null) {
                        for (int i = 1; i <= 5; i++)
                            gp.objArr[1][i].unlocking = true;
                    }
            }
        }

    public boolean hit (int map,int col, int row, String reqAction) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.TILE_SIZE + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.TILE_SIZE + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) { // second half of if statement for 1 time events
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

    public void changeMap(){
        gp.gameState = gp.transitionState;
        System.out.println(gp.currentMap);
        switch (gp.currentMap) {
            case 0:
                tempMap = 1;
                tempCol = 2;
                tempRow = 41;
                break;
            case 1:
                tempMap = 0;
                tempCol = 8;
                tempRow = 3;
        }

        canTouchEvent = false;

        //if one time only event enable this
//        eventRect[col][row].eventDone = true;
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
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.frostGiant;
        }
    }
}

