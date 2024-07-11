package main;

import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect ;
    Entity eventMaster;
    int previousEventX, previousEventY; // prevent event from happening again immediately
    int tempMap, tempCol, tempRow;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventMaster = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = -10;
            eventRect[map][col][row].width = 80;
            eventRect[map][col][row].height = 40;
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
    public void checkEvent() { // check tile for event;
        // check tile if player i one tile away from the tile
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.TILE_SIZE){
            canTouchEvent = true;
        }
            if(canTouchEvent) {// use else if to add more events
                if (hit(0,8, 12, "any")) { // FIRST LEVEL
                    enterDungeon(1,1,40);
                } else if (hit(1,0, 7, "any")) { // SECOND LEVEL
                    enterDungeon(2,9,11);
                } else if (hit(0,4, 10, "any")) {
                    drinkWater(gp.dialogueState);
                } else if (hit(0,3, 10, "any")) {
                    drinkWater(gp.dialogueState);
                } else if (hit(0,2, 10, "any")) {
                    drinkWater(gp.dialogueState);
                } else if (hit(0,13, 10, "any")) {
                    drinkWater(gp.dialogueState);
                } else if (hit(1,11, 16, "any")) {
                    FrostGiant();
                }
            }
        }

    public boolean hit (int map,int col, int row, String reqAction) {
        boolean hit = false;

        if(map == gp.currentMap) {
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

    public void enterDungeon(int map, int col, int row){
        gp.gameState = gp.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
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

