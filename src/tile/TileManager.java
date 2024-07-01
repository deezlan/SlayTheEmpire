package tile;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum; // to check which tile the player is currently hitting

    public TileManager(GamePanel gp) {
        gp.gameArea = 0;
        this.gp = gp;
        tile = new Tile[30];
        mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();

        loadMap("/mapTextFiles/map.txt",0);
//        loadMap("/mapTextFiles/test.txt",1);
//
//        switch (gp.gameArea) {
//            case 0:
//                loadMap("/mapTextFiles/test.txt");
//                break;
//            case 1:
//                loadMap("/mapTextFiles/map.txt");
//            default:
//                loadMap("/tiles/lobby/map.txt");
        }


    public void loadMap(String filePath, int mapNo) {
        try {
            InputStream map = getClass().getResourceAsStream(filePath);
            BufferedReader mapScanner = new BufferedReader(new InputStreamReader(Objects.requireNonNull(map)));

            int col = 0;
            int row = 0;

            while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
                String line = mapScanner.readLine();

                while (col < gp.MAX_WORLD_COL) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col], 2); // changes string to integer

                    mapTileNum[col][row] = num;
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
            int tileNum = mapTileNum[WORLD_COL][WORLD_ROW];

            if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null && tile[tileNum].image != null) {
                int worldX = WORLD_COL * gp.TILE_SIZE;
                int worldY = WORLD_ROW * gp.TILE_SIZE;

                switch (gp.gameArea) {
                    case 0:
                        g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                        break;
                    case 1:
                        g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                        break;
                    default:
                        int screenX = worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = worldY - gp.player.worldY + gp.player.screenY;

                        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
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
    }

    public void getTileImage() {
        String dir = "/tiles/lobby/";
        try {
            switch (gp.gameArea) {
                case 0:
                case 1:
                default:
                    for (int i = 0; i <= 25; i++) {
                        tile[i] = new Tile();
                        if (i < 13) tile[i].collision = true;
                    }

                    // Walls
                    tile[0].image = UtilityTool.loadSprite(dir + "walls/topLeft.png", "Missing Top Left Wall");
                    tile[1].image = UtilityTool.loadSprite(dir + "walls/topMiddle.png", "Missing Top Middle Wall");
                    tile[2].image = UtilityTool.loadSprite(dir + "walls/hole.png", "Missing Hole Wall");
                    tile[3].image = UtilityTool.loadSprite(dir + "walls/door.png", "Missing Door Wall");
                    tile[4].image = UtilityTool.loadSprite(dir + "walls/old.png", "Missing Old Wall");
                    tile[5].image = UtilityTool.loadSprite(dir + "walls/topRight.png", "Missing Top Right Wall");
                    tile[6].image = UtilityTool.loadSprite(dir + "walls/middleLeft.png", "Missing Middle Left Wall");
                    tile[7].image = UtilityTool.loadSprite(dir + "walls/middleRight.png", "Missing Middle Right Wall");
                    tile[8].image = UtilityTool.loadSprite(dir + "walls/bottomLeft.png", "Missing Bottom Left Wall");
                    tile[9].image = UtilityTool.loadSprite(dir + "walls/bottomRight.png", "Missing Bottom Right Wall");
                    tile[10].image = UtilityTool.loadSprite(dir + "walls/bottomMiddle.png", "Missing Bottom Middle Wall");
                    tile[11].image = UtilityTool.loadSprite(dir + "walls/stairsLeft.png", "Missing Left Stairs Wall tile");
                    tile[12].image = UtilityTool.loadSprite(dir + "walls/stairsRight.png", "Missing Right Stairs Wall tile");

                    // Floor
                    tile[13].image = UtilityTool.loadSprite(dir + "floor/blank.png", "Missing Blank Floor");
                    tile[14].image = UtilityTool.loadSprite(dir + "floor/patterned.png", "Missing Patterned Floor");
                    tile[15].image = UtilityTool.loadSprite(dir + "floor/checkered.png", "Missing Checkered Floor");
                    tile[16].image = UtilityTool.loadSprite(dir + "floor/blankBordered.png", "Missing Blank Bordered Floor");
                    tile[17].image = UtilityTool.loadSprite(dir + "floor/middleStairs.png", "Missing Middle Stairs");
                    tile[18].image = UtilityTool.loadSprite(dir + "floor/leftStairs.png", "Missing Left Stair");
                    tile[19].image = UtilityTool.loadSprite(dir + "floor/rightStairs.png", "Missing Right Stair");
                    tile[20].image = UtilityTool.loadSprite(dir + "floor/topRight.png", "Missing Top Right Floor");
                    tile[21].image = UtilityTool.loadSprite(dir + "floor/topLeft.png", "Missing Top Left Floor");
                    tile[22].image = UtilityTool.loadSprite(dir + "floor/topMiddle.png", "Missing Top Middle Floor");
                    tile[23].image = UtilityTool.loadSprite(dir + "floor/middleLeft.png", "Missing Middle Left Floor");
                    tile[24].image = UtilityTool.loadSprite(dir + "floor/middleRight.png", "Missing Middle Right");

                    // Out of bounds
                    tile[25].image = UtilityTool.loadSprite(dir + "outside.png", "Missing Outside");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
