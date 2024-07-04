package tile;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Graphics2D;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum; // to check which tile the player is currently hitting
    String dir;

    public TileManager(GamePanel gp) {
        gp.gameArea = 1;
        this.gp = gp;

        switch (gp.gameArea) {
            case 0:
                gp.MAX_WORLD_COL = 17;
                gp.MAX_WORLD_ROW = 13;
                gp.setBackground(Color.decode("#222034"));
                break;
            case 1:
                gp.MAX_WORLD_COL = 31;
                gp.MAX_WORLD_ROW = 21;
                break;
        }

        mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();

        switch (gp.gameArea) {
            case 0:
                loadMap("/mapTextFiles/lobby.txt");
                break;
            case 1:
                loadMap("/mapTextFiles/firstLevel.txt");
                break;
            default:
                System.out.println("Default Case TileM Cons");
        }
    }

    public void loadMap(String filePath) {
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
                        gp.setBackground(Color.decode("#222034"));
                        g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                        break;
                    case 1:
                        gp.setBackground(Color.decode("#42393A"));
                        int screenX = worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = worldY - gp.player.worldY + gp.player.screenY;

                        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.screenX &&
                                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX &&
                                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
                            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                        }
                        break;
                    default:
                        System.out.println("Wrong case for TileManager draw");
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
        try {
            switch (gp.gameArea) {
                case 0:
                    dir = "/tiles/lobby/";
                    tile = new Tile[38];
                    for (int i = 0; i <= 37; i++) {
                        tile[i] = new Tile();
                        if (i < 13 || i > 25 ) tile[i].collision = true;
                    }
                    System.out.println("Case 0 for getTileImage");

                    // Walls
                    tile[0].image = UtilityTool.loadSprite(dir + "00.png", "Missing Top Left Wall");
                    tile[1].image = UtilityTool.loadSprite(dir + "01.png", "Missing Top Middle Wall");
                    tile[2].image = UtilityTool.loadSprite(dir + "02.png", "Missing Hole Wall");
                    tile[3].image = UtilityTool.loadSprite(dir + "03.png", "Missing Door Wall");
                    tile[4].image = UtilityTool.loadSprite(dir + "04.png", "Missing Old Wall");
                    tile[5].image = UtilityTool.loadSprite(dir + "05.png", "Missing Top Right Wall");
                    tile[6].image = UtilityTool.loadSprite(dir + "06.png", "Missing Middle Left Wall");
                    tile[7].image = UtilityTool.loadSprite(dir + "07.png", "Missing Middle Right Wall");
                    tile[8].image = UtilityTool.loadSprite(dir + "08.png", "Missing Bottom Left Wall");
                    tile[9].image = UtilityTool.loadSprite(dir + "09.png", "Missing Bottom Right Wall");
                    tile[10].image = UtilityTool.loadSprite(dir + "10.png", "Missing Bottom Middle Wall");
                    tile[11].image = UtilityTool.loadSprite(dir + "11.png", "Missing Left Stairs Wall tile");
                    tile[12].image = UtilityTool.loadSprite(dir + "12.png", "Missing Right Stairs Wall tile");

                    // Floor
                    tile[13].image = UtilityTool.loadSprite(dir + "13.png", "Missing Blank Floor");
                    tile[14].image = UtilityTool.loadSprite(dir + "14.png", "Missing Patterned Floor");
                    tile[15].image = UtilityTool.loadSprite(dir + "15.png", "Missing Checkered Floor");
                    tile[16].image = UtilityTool.loadSprite(dir + "16.png", "Missing Blank Bordered Floor");
                    tile[17].image = UtilityTool.loadSprite(dir + "17.png", "Missing Middle Stairs");
                    tile[18].image = UtilityTool.loadSprite(dir + "18.png", "Missing Left Stair");
                    tile[19].image = UtilityTool.loadSprite(dir + "19.png", "Missing Right Stair");
                    tile[20].image = UtilityTool.loadSprite(dir + "20.png", "Missing Top Right Floor");
                    tile[21].image = UtilityTool.loadSprite(dir + "21.png", "Missing Top Left Floor");
                    tile[22].image = UtilityTool.loadSprite(dir + "22.png", "Missing Top Middle Floor");
                    tile[23].image = UtilityTool.loadSprite(dir + "23.png", "Missing Middle Left Floor");
                    tile[24].image = UtilityTool.loadSprite(dir + "24.png", "Missing Middle Right");
                    tile[25].image = UtilityTool.loadSprite(dir + "25.png", "Missing Blank Cracked");

                    // Water
                    tile[26].image = UtilityTool.loadSprite(dir + "26.png", "");
                    tile[27].image = UtilityTool.loadSprite(dir + "27.png", "");
                    tile[28].image = UtilityTool.loadSprite(dir + "28.png", "");
                    tile[29].image = UtilityTool.loadSprite(dir + "29.png", "");
                    tile[30].image = UtilityTool.loadSprite(dir + "30.png", "");
                    tile[31].image = UtilityTool.loadSprite(dir + "31.png", "");
                    tile[32].image = UtilityTool.loadSprite(dir + "32.png", "");
                    tile[33].image = UtilityTool.loadSprite(dir + "33.png", "");
                    tile[34].image = UtilityTool.loadSprite(dir + "34.png", "");
                    tile[35].image = UtilityTool.loadSprite(dir + "35.png", "");
                    tile[36].image = UtilityTool.loadSprite(dir + "36.png", "");

                    // Out of bounds
                    tile[37].image = UtilityTool.loadSprite(dir + "37.png", "Missing Outside");

                    tile[26].collision = false;
                    tile[33].collision = false;
                    tile[34].collision = false;
                    tile[35].collision = false;
                    break;
                case 1:
                    dir = "/tiles/firstLevel/";
                    tile = new Tile[31];
                    for (int i = 0; i <= 30; i++) {
                        tile[i] = new Tile();
                        if (i < 25) tile[i].collision = true;
                    }

                    // Walls
                    tile[0].image = UtilityTool.loadSprite(dir + "00.png", "");
                    tile[1].image = UtilityTool.loadSprite(dir + "01.png", "");
                    tile[2].image = UtilityTool.loadSprite(dir + "02.png", "");
                    tile[3].image = UtilityTool.loadSprite(dir + "03.png", "");
                    tile[4].image = UtilityTool.loadSprite(dir + "04.png", "");
                    tile[5].image = UtilityTool.loadSprite(dir + "05.png", "");
                    tile[6].image = UtilityTool.loadSprite(dir + "06.png", "");
                    tile[7].image = UtilityTool.loadSprite(dir + "07.png", "");
                    tile[8].image = UtilityTool.loadSprite(dir + "08.png", "");
                    tile[9].image = UtilityTool.loadSprite(dir + "09.png", "");
                    tile[10].image = UtilityTool.loadSprite(dir + "10.png", "");
                    tile[11].image = UtilityTool.loadSprite(dir + "11.png", "");
                    tile[12].image = UtilityTool.loadSprite(dir + "12.png", "");
                    tile[13].image = UtilityTool.loadSprite(dir + "13.png", "");
                    tile[14].image = UtilityTool.loadSprite(dir + "14.png", "");
                    tile[15].image = UtilityTool.loadSprite(dir + "15.png", "");
                    tile[16].image = UtilityTool.loadSprite(dir + "16.png", "");
                    tile[17].image = UtilityTool.loadSprite(dir + "17.png", "");
                    tile[18].image = UtilityTool.loadSprite(dir + "18.png", "");
                    tile[19].image = UtilityTool.loadSprite(dir + "19.png", "");
                    tile[20].image = UtilityTool.loadSprite(dir + "20.png", "");
                    tile[21].image = UtilityTool.loadSprite(dir + "21.png", "");
                    tile[22].image = UtilityTool.loadSprite(dir + "22.png", "");
                    tile[23].image = UtilityTool.loadSprite(dir + "23.png", "");
                    tile[24].image = UtilityTool.loadSprite(dir + "24.png", "");
                    tile[25].image = UtilityTool.loadSprite(dir + "25.png", "");

                    // Floor
                    tile[26].image = UtilityTool.loadSprite(dir + "26.png", "");
                    tile[27].image = UtilityTool.loadSprite(dir + "27.png", "");
                    tile[28].image = UtilityTool.loadSprite(dir + "28.png", "");
                    tile[29].image = UtilityTool.loadSprite(dir + "29.png", "");
                    tile[30].image = UtilityTool.loadSprite(dir + "30.png", "");
                    System.out.println("Case 1 for getTileImage");
                    break;
                case 2: // TESTING
                    dir = "/tiles/lobby/";
                    tile = new Tile[38];
                    for (int i = 0; i <= 37; i++) {
                        tile[i] = new Tile();
                        if (i < 13 || i > 25 ) tile[i].collision = true;
                    }
                    System.out.println("Case 0 for getTileImage");

                    // Walls
                    tile[0].image = UtilityTool.loadSprite(dir + "00.png", "Missing Top Left Wall");
                    tile[1].image = UtilityTool.loadSprite(dir + "01.png", "Missing Top Middle Wall");
                    tile[2].image = UtilityTool.loadSprite(dir + "02.png", "Missing Hole Wall");
                    tile[3].image = UtilityTool.loadSprite(dir + "03.png", "Missing Door Wall");
                    tile[4].image = UtilityTool.loadSprite(dir + "04.png", "Missing Old Wall");
                    tile[5].image = UtilityTool.loadSprite(dir + "05.png", "Missing Top Right Wall");
                    tile[6].image = UtilityTool.loadSprite(dir + "06.png", "Missing Middle Left Wall");
                    tile[7].image = UtilityTool.loadSprite(dir + "07.png", "Missing Middle Right Wall");
                    tile[8].image = UtilityTool.loadSprite(dir + "08.png", "Missing Bottom Left Wall");
                    tile[9].image = UtilityTool.loadSprite(dir + "09.png", "Missing Bottom Right Wall");
                    tile[10].image = UtilityTool.loadSprite(dir + "10.png", "Missing Bottom Middle Wall");
                    tile[11].image = UtilityTool.loadSprite(dir + "11.png", "Missing Left Stairs Wall tile");
                    tile[12].image = UtilityTool.loadSprite(dir + "12.png", "Missing Right Stairs Wall tile");

                    // Floor
                    tile[13].image = UtilityTool.loadSprite(dir + "13.png", "Missing Blank Floor");
                    tile[14].image = UtilityTool.loadSprite(dir + "14.png", "Missing Patterned Floor");
                    tile[15].image = UtilityTool.loadSprite(dir + "15.png", "Missing Checkered Floor");
                    tile[16].image = UtilityTool.loadSprite(dir + "16.png", "Missing Blank Bordered Floor");
                    tile[17].image = UtilityTool.loadSprite(dir + "17.png", "Missing Middle Stairs");
                    tile[18].image = UtilityTool.loadSprite(dir + "18.png", "Missing Left Stair");
                    tile[19].image = UtilityTool.loadSprite(dir + "19.png", "Missing Right Stair");
                    tile[20].image = UtilityTool.loadSprite(dir + "20.png", "Missing Top Right Floor");
                    tile[21].image = UtilityTool.loadSprite(dir + "21.png", "Missing Top Left Floor");
                    tile[22].image = UtilityTool.loadSprite(dir + "22.png", "Missing Top Middle Floor");
                    tile[23].image = UtilityTool.loadSprite(dir + "23.png", "Missing Middle Left Floor");
                    tile[24].image = UtilityTool.loadSprite(dir + "24.png", "Missing Middle Right");
                    tile[25].image = UtilityTool.loadSprite(dir + "25.png", "Missing Blank Cracked");

                    // Water
                    tile[26].image = UtilityTool.loadSprite(dir + "26.png", "");
                    tile[27].image = UtilityTool.loadSprite(dir + "27.png", "");
                    tile[28].image = UtilityTool.loadSprite(dir + "28.png", "");
                    tile[29].image = UtilityTool.loadSprite(dir + "29.png", "");
                    tile[30].image = UtilityTool.loadSprite(dir + "30.png", "");
                    tile[31].image = UtilityTool.loadSprite(dir + "31.png", "");
                    tile[32].image = UtilityTool.loadSprite(dir + "32.png", "");
                    tile[33].image = UtilityTool.loadSprite(dir + "33.png", "");
                    tile[34].image = UtilityTool.loadSprite(dir + "34.png", "");
                    tile[35].image = UtilityTool.loadSprite(dir + "35.png", "");
                    tile[36].image = UtilityTool.loadSprite(dir + "36.png", "");

                    // Out of bounds
                    tile[37].image = UtilityTool.loadSprite(dir + "37.png", "Missing Outside");

                    tile[26].collision = false;
                    tile[33].collision = false;
                    tile[34].collision = false;
                    tile[35].collision = false;
                    break;
                default:
                    dir = "/tiles/firstLevel/";
                    tile = new Tile[31];
                    for (int i = 0; i <= 30; i++) {
                        tile[i] = new Tile();
                        if (i < 26) tile[i].collision = true;
                    }

                    tile[0].image = UtilityTool.loadSprite(dir + "00.png", "");
                    tile[1].image = UtilityTool.loadSprite(dir + "01.png", "");
                    tile[2].image = UtilityTool.loadSprite(dir + "02.png", "");
                    tile[3].image = UtilityTool.loadSprite(dir + "03.png", "");
                    tile[4].image = UtilityTool.loadSprite(dir + "04.png", "");
                    tile[5].image = UtilityTool.loadSprite(dir + "05.png", "");
                    tile[6].image = UtilityTool.loadSprite(dir + "06.png", "");
                    tile[7].image = UtilityTool.loadSprite(dir + "07.png", "");
                    tile[8].image = UtilityTool.loadSprite(dir + "08.png", "");
                    tile[9].image = UtilityTool.loadSprite(dir + "09.png", "");
                    tile[10].image = UtilityTool.loadSprite(dir + "10.png", "");
                    tile[11].image = UtilityTool.loadSprite(dir + "11.png", "");
                    tile[12].image = UtilityTool.loadSprite(dir + "12.png", "");
                    tile[13].image = UtilityTool.loadSprite(dir + "13.png", "");
                    tile[14].image = UtilityTool.loadSprite(dir + "14.png", "");
                    tile[15].image = UtilityTool.loadSprite(dir + "15.png", "");
                    tile[16].image = UtilityTool.loadSprite(dir + "16.png", "");
                    tile[17].image = UtilityTool.loadSprite(dir + "17.png", "");
                    tile[18].image = UtilityTool.loadSprite(dir + "18.png", "");
                    tile[19].image = UtilityTool.loadSprite(dir + "19.png", "");
                    tile[20].image = UtilityTool.loadSprite(dir + "20.png", "");
                    tile[21].image = UtilityTool.loadSprite(dir + "21.png", "");
                    tile[22].image = UtilityTool.loadSprite(dir + "22.png", "");
                    tile[23].image = UtilityTool.loadSprite(dir + "23.png", "");
                    tile[24].image = UtilityTool.loadSprite(dir + "24.png", "");
                    tile[25].image = UtilityTool.loadSprite(dir + "25.png", "");

                    // Floor
                    tile[26].image = UtilityTool.loadSprite(dir + "26.png", "");
                    tile[27].image = UtilityTool.loadSprite(dir + "27.png", "");
                    tile[28].image = UtilityTool.loadSprite(dir + "28.png", "");
                    tile[29].image = UtilityTool.loadSprite(dir + "29.png", "");
                    tile[30].image = UtilityTool.loadSprite(dir + "30.png", "");
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
