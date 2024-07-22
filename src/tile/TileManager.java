
package tile;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum; // to check which tile the player is currently hitting
    boolean drawPath = true;
    String dir;

    public TileManager(GamePanel gp) {
        gp.currentMap = 0;
        this.gp = gp;

        mapTileNum = new int[gp.MAX_MAP][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        getTileImage();
        loadMap("/mapTextFiles/lobby.bin", 0);
        loadMap("/mapTextFiles/levelOne.bin", 1);
        loadMap("/mapTextFiles/levelTwo.bin", 2);
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader mapScanner = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));

            int col = 0;
            int row = 0;

            while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
                String line = mapScanner.readLine();

                while (col < gp.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col], 2); // changes string to integer

                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
            mapScanner.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void draw(Graphics2D g2) {
        int WORLD_COL = 0;
        int WORLD_ROW = 0;

        while (WORLD_COL < gp.MAX_WORLD_COL && WORLD_ROW < gp.MAX_WORLD_ROW) {
            int tileNum = mapTileNum[gp.currentMap][WORLD_COL][WORLD_ROW];

            if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null && tile[tileNum].image != null) {
                int worldX = WORLD_COL * gp.TILE_SIZE;
                int worldY = WORLD_ROW * gp.TILE_SIZE;
                if (gp.currentMap == 0){
                    g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                } else {
                    int screenX = worldX - gp.player.worldX + gp.player.screenX;
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if (worldX + gp.TILE_SIZE  > gp.player.worldX - gp.player.screenX - 48*4 &&
                            worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX + 48*4 &&
                            worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY + 48*2) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                    }
                }
            } else {
                System.err.println("Tile or tile image is null for tile number: " + tileNum);
            }

            WORLD_COL++;
            if (WORLD_COL == gp.MAX_WORLD_COL) {
                WORLD_COL = 0;
                WORLD_ROW++;
            }
        }
//        if(drawPath){ // for viewing the move path of entities
//            g2.setColor(new Color(255,0,0,70));
//
//            for(int i = 0; i < gp.pFinder.pathList.size(); i++){
//                int worldX = gp.pFinder.pathList.get(i).col * gp.TILE_SIZE;
//                int worldY = gp.pFinder.pathList.get(i).row * gp.TILE_SIZE;
//                int screenX = worldX - gp.player.worldX + gp.player.screenX;
//                int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//                g2.fillRect(screenX,screenY,gp.TILE_SIZE,gp.TILE_SIZE);
//            }
//        }
    }

    public void getTileImage() {
        try {
            dir = "/tiles/";
            tile = new Tile[200];
            for (int i = 0; i <= 150; i++) {
                tile[i] = new Tile();
                if (i < 13 || i > 25 ) tile[i].collision = true;
            }

            // INITIALIZE LOBBY TILES
            dir = "/tiles/lobby/";
            for (int i = 0; i <= 37; i++) {
                tile[i].image = UtilityTool.loadSprite(dir + i + ".png", "Missing Tile " + i + " in " + dir);
            }

            // LOBBY TILES COLLISION CORRECTION
            tile[26].collision = false;
            tile[33].collision = false;
            tile[34].collision = false;
            tile[35].collision = false;

            // INITIALIZE FIRST LEVEL TILES
            dir = "/tiles/firstLevel/";
            for (int i = 38; i <= 68; i++) {
                tile[i].image = UtilityTool.loadSprite(dir + i + ".png", "Missing Tile " + i + " in " + dir);
            }

            // FIRST LEVEL TILES COLLISION CORRECTION
            tile[38].collision = false;
            tile[64].collision = false;
            tile[65].collision = false;
            tile[66].collision = false;
            tile[67].collision = false;
            tile[68].collision = false;

            // INITIALIZE SECOND LEVEL TILES
            dir = "/tiles/secondLevel/";
            for (int i = 69; i <= 150; i++) {
                tile[i].image = UtilityTool.loadSprite(dir + i + ".png", "Missing Tile " + i + " in " + dir);
            }

            // SECOND LEVEL TILES COLLISION CORRECTION
            tile[69].collision = false;
            tile[85].collision = false;
            tile[86].collision = false;
            tile[92].collision = false;
            tile[93].collision = false;

            for (int i = 99; i <= 148; i++) {
                tile[i].collision = false;
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
