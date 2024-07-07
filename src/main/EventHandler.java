package main;


public class EventHandler {
    GamePanel gp;
    EventRect[][] eventRect ;
    int previousEventX, previousEventY; // prevent event from happening again immediately
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.MAX_WORLD_COL) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() { // check tile for event;
        // check tile if player i one tile away from the tile
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.TILE_SIZE){
            canTouchEvent = true;
        }
        //testing damage fall pit
            if(canTouchEvent) {// use else if to add more events
                if (hit(8, 12, "moveDown")) {
                    enterDungeon(8, 12, gp.dialogueState);
                } else if (hit(4, 9, "moveDown")) {
                    drinkWater(4, 9, gp.dialogueState);
                } else if (hit(3, 9, "moveDown")) {
                    drinkWater(3, 9, gp.dialogueState);
                } else if (hit(2, 9, "moveDown")) {
                    drinkWater(2, 9, gp.dialogueState);
                }
            }
        }

    public boolean hit (int col, int row, String reqAction) {
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.TILE_SIZE + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.TILE_SIZE + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){ // second half of if statement for 1 time events
            if(gp.player.action.contentEquals(reqAction) || reqAction.contentEquals("any")){
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void enterDungeon(int col, int row ,int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialog = "Placeholder : Enter Dungeon";
        canTouchEvent = false;
        //if one time only event enable this
//        eventRect[col][row].eventDone = true;
    }

    public void drinkWater(int col, int row ,int gameState){
        if(gp.keyH.ePressed){
            gp.gameState = gameState;
            gp.ui.currentDialog = "Drank Possibly Toilet Water";
            gp.player.life = gp.player.maxLife;
            canTouchEvent = false;
        }
    }
}

