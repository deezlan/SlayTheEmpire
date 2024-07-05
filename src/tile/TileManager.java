
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
    public int[][][] mapTileNum; // to check which tile the player is currently hitting
    String dir;

    public TileManager(GamePanel gp) {
        gp.currentMap = 0;
        this.gp = gp;
        gp.MAX_WORLD_COL = 30;
        gp.MAX_WORLD_ROW = 20;

        mapTileNum = new int[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        getTileImage();
        loadMap("/mapTextFiles/LobbyTest.txt",0);
        loadMap("/mapTextFiles/TestLevel.txt",1);
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

                switch (gp.currentMap) {
                    case 0, 1:
                        int screenX = worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = worldY - gp.player.worldY + gp.player.screenY;

                        if (worldX + gp.TILE_SIZE  > gp.player.worldX - gp.player.screenX - 48*4 &&
                                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX + 48*4 &&
                                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY) {
                            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                        }
                        break;
//                    gp.setBackground(Color.decode("#222034")); // NO LOCK ON PLAYER
//                    g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null);
//                    break;
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
            dir = "/tiles/";
            tile = new Tile[71];
            for (int i = 0; i <= 70; i++) {
                tile[i] = new Tile();
                if (i < 13 || i > 25 ) tile[i].collision = true;
            }
            // LOBBY WALLS
            tile[0].image = UtilityTool.loadSprite(dir + "lobby/00.png", "Missing Top Left Wall");
            tile[1].image = UtilityTool.loadSprite(dir + "lobby/01.png", "Missing Top Middle Wall");
            tile[2].image = UtilityTool.loadSprite(dir + "lobby/02.png", "Missing Hole Wall");
            tile[3].image = UtilityTool.loadSprite(dir + "lobby/03.png", "Missing Door Wall");
            tile[4].image = UtilityTool.loadSprite(dir + "lobby/04.png", "Missing Old Wall");
            tile[5].image = UtilityTool.loadSprite(dir + "lobby/05.png", "Missing Top Right Wall");
            tile[6].image = UtilityTool.loadSprite(dir + "lobby/06.png", "Missing Middle Left Wall");
            tile[7].image = UtilityTool.loadSprite(dir + "lobby/07.png", "Missing Middle Right Wall");
            tile[8].image = UtilityTool.loadSprite(dir + "lobby/08.png", "Missing Bottom Left Wall");
            tile[9].image = UtilityTool.loadSprite(dir + "lobby/09.png", "Missing Bottom Right Wall");
            tile[10].image = UtilityTool.loadSprite(dir + "lobby/10.png", "Missing Bottom Middle Wall");
            tile[11].image = UtilityTool.loadSprite(dir + "lobby/11.png", "Missing Left Stairs Wall tile");
            tile[12].image = UtilityTool.loadSprite(dir + "lobby/12.png", "Missing Right Stairs Wall tile");

            // LOBBY FLOOR
            tile[13].image = UtilityTool.loadSprite(dir + "lobby/13.png", "Missing Blank Floor");
            tile[14].image = UtilityTool.loadSprite(dir + "lobby/14.png", "Missing Patterned Floor");
            tile[15].image = UtilityTool.loadSprite(dir + "lobby/15.png", "Missing Checkered Floor");
            tile[16].image = UtilityTool.loadSprite(dir + "lobby/16.png", "Missing Blank Bordered Floor");
            tile[17].image = UtilityTool.loadSprite(dir + "lobby/17.png", "Missing Middle Stairs");
            tile[18].image = UtilityTool.loadSprite(dir + "lobby/18.png", "Missing Left Stair");
            tile[19].image = UtilityTool.loadSprite(dir + "lobby/19.png", "Missing Right Stair");
            tile[20].image = UtilityTool.loadSprite(dir + "lobby/20.png", "Missing Top Right Floor");
            tile[21].image = UtilityTool.loadSprite(dir + "lobby/21.png", "Missing Top Left Floor");
            tile[22].image = UtilityTool.loadSprite(dir + "lobby/22.png", "Missing Top Middle Floor");
            tile[23].image = UtilityTool.loadSprite(dir + "lobby/23.png", "Missing Middle Left Floor");
            tile[24].image = UtilityTool.loadSprite(dir + "lobby/24.png", "Missing Middle Right");
            tile[25].image = UtilityTool.loadSprite(dir + "lobby/25.png", "Missing Blank Cracked");

            // LOBBY WATER
            tile[26].image = UtilityTool.loadSprite(dir + "lobby/26.png", "");
            tile[27].image = UtilityTool.loadSprite(dir + "lobby/27.png", "");
            tile[28].image = UtilityTool.loadSprite(dir + "lobby/28.png", "");
            tile[29].image = UtilityTool.loadSprite(dir + "lobby/29.png", "");
            tile[30].image = UtilityTool.loadSprite(dir + "lobby/30.png", "");
            tile[31].image = UtilityTool.loadSprite(dir + "lobby/31.png", "");
            tile[32].image = UtilityTool.loadSprite(dir + "lobby/32.png", "");
            tile[33].image = UtilityTool.loadSprite(dir + "lobby/33.png", "");
            tile[34].image = UtilityTool.loadSprite(dir + "lobby/34.png", "");
            tile[35].image = UtilityTool.loadSprite(dir + "lobby/35.png", "");
            tile[36].image = UtilityTool.loadSprite(dir + "lobby/36.png", "");

            // LOBBY OUT OF BOUNDS
            tile[37].image = UtilityTool.loadSprite(dir + "lobby/37.png", "Missing Outside");

            // COLLISION CORRECTION
            tile[26].collision = false;
            tile[33].collision = false;
            tile[34].collision = false;
            tile[35].collision = false;

            // FIRST LEVEL WALL
            tile[38].image = UtilityTool.loadSprite(dir + "firstLevel/00.png", "Out of Bounds");
            tile[39].image = UtilityTool.loadSprite(dir + "firstLevel/01.png", "First Top Left");
            tile[40].image = UtilityTool.loadSprite(dir + "firstLevel/02.png", "First Top Middle");
            tile[41].image = UtilityTool.loadSprite(dir + "firstLevel/03.png", "First Top Right");
            tile[42].image = UtilityTool.loadSprite(dir + "firstLevel/04.png", "First Left Middle");
            tile[43].image = UtilityTool.loadSprite(dir + "firstLevel/05.png", "First Top Layer Shadow Brick");
            tile[44].image = UtilityTool.loadSprite(dir + "firstLevel/06.png", "First Right Middle");
            tile[45].image = UtilityTool.loadSprite(dir + "firstLevel/07.png", "First Bottom Layer Shadow Brick");
            tile[46].image = UtilityTool.loadSprite(dir + "firstLevel/08.png", "First Bottom Left");
            tile[47].image = UtilityTool.loadSprite(dir + "firstLevel/09.png", "Bottom Middle");
            tile[48].image = UtilityTool.loadSprite(dir + "firstLevel/10.png", "Bottom Right");
            tile[49].image = UtilityTool.loadSprite(dir + "firstLevel/11.png", "Bottom Left L");
            tile[50].image = UtilityTool.loadSprite(dir + "firstLevel/12.png", "Bottom Right L");
            tile[51].image = UtilityTool.loadSprite(dir + "firstLevel/13.png", "Top Left L");
            tile[52].image = UtilityTool.loadSprite(dir + "firstLevel/14.png", "Top Right L");
            tile[53].image = UtilityTool.loadSprite(dir + "firstLevel/15.png", "Top Left Long Right short");
            tile[54].image = UtilityTool.loadSprite(dir + "firstLevel/16.png", "U shape");
            tile[55].image = UtilityTool.loadSprite(dir + "firstLevel/17.png", "Top Left short Right Long");
            tile[56].image = UtilityTool.loadSprite(dir + "firstLevel/18.png", "Inverse U Shape");
            tile[57].image = UtilityTool.loadSprite(dir + "firstLevel/19.png", "Bottom Left Long Right short");
            tile[58].image = UtilityTool.loadSprite(dir + "firstLevel/20.png", "Bottom Left short Right long");
            tile[59].image = UtilityTool.loadSprite(dir + "firstLevel/21.png", "");
            tile[60].image = UtilityTool.loadSprite(dir + "firstLevel/22.png", "Left U");
            tile[61].image = UtilityTool.loadSprite(dir + "firstLevel/23.png", "Right U");
            tile[62].image = UtilityTool.loadSprite(dir + "firstLevel/24.png", "");
            tile[63].image = UtilityTool.loadSprite(dir + "firstLevel/25.png", "Parrellel Line");

            // FIRST LEVEL FLOOR
            tile[64].image = UtilityTool.loadSprite(dir + "firstLevel/26.png", "Chisel Tile");
            tile[65].image = UtilityTool.loadSprite(dir + "firstLevel/27.png", "Blank Space");
            tile[66].image = UtilityTool.loadSprite(dir + "firstLevel/28.png", "Grey Tile");
            tile[67].image = UtilityTool.loadSprite(dir + "firstLevel/29.png", "Cracked tiles");
            tile[68].image = UtilityTool.loadSprite(dir + "firstLevel/30.png", "Chisel Cracked Tile");

            tile[38].collision = false;
//                case 1:
//                    dir = "/tiles/firstLevel/";
//                    tile = new Tile[31];
//                    for (int i = 0; i <= 30; i++) {
//                        tile[i] = new Tile();
//                        if (i < 25) tile[i].collision = true;
//                    }
//
//                    // Walls
//                    tile[0].image = UtilityTool.loadSprite(dir + "00.png", "");
//                    tile[1].image = UtilityTool.loadSprite(dir + "01.png", "");
//                    tile[2].image = UtilityTool.loadSprite(dir + "02.png", "");
//                    tile[3].image = UtilityTool.loadSprite(dir + "03.png", "");
//                    tile[4].image = UtilityTool.loadSprite(dir + "04.png", "");
//                    tile[5].image = UtilityTool.loadSprite(dir + "05.png", "");
//                    tile[6].image = UtilityTool.loadSprite(dir + "06.png", "");
//                    tile[7].image = UtilityTool.loadSprite(dir + "07.png", "");
//                    tile[8].image = UtilityTool.loadSprite(dir + "08.png", "");
//                    tile[9].image = UtilityTool.loadSprite(dir + "09.png", "");
//                    tile[10].image = UtilityTool.loadSprite(dir + "10.png", "");
//                    tile[11].image = UtilityTool.loadSprite(dir + "11.png", "");
//                    tile[12].image = UtilityTool.loadSprite(dir + "12.png", "");
//                    tile[13].image = UtilityTool.loadSprite(dir + "13.png", "");
//                    tile[14].image = UtilityTool.loadSprite(dir + "14.png", "");
//                    tile[15].image = UtilityTool.loadSprite(dir + "15.png", "");
//                    tile[16].image = UtilityTool.loadSprite(dir + "16.png", "");
//                    tile[17].image = UtilityTool.loadSprite(dir + "17.png", "");
//                    tile[18].image = UtilityTool.loadSprite(dir + "18.png", "");
//                    tile[19].image = UtilityTool.loadSprite(dir + "19.png", "");
//                    tile[20].image = UtilityTool.loadSprite(dir + "20.png", "");
//                    tile[21].image = UtilityTool.loadSprite(dir + "21.png", "");
//                    tile[22].image = UtilityTool.loadSprite(dir + "22.png", "");
//                    tile[23].image = UtilityTool.loadSprite(dir + "23.png", "");
//                    tile[24].image = UtilityTool.loadSprite(dir + "24.png", "");
//                    tile[25].image = UtilityTool.loadSprite(dir + "25.png", "");
//
//                    // Floor
//                    tile[26].image = UtilityTool.loadSprite(dir + "26.png", "");
//                    tile[27].image = UtilityTool.loadSprite(dir + "27.png", "");
//                    tile[28].image = UtilityTool.loadSprite(dir + "28.png", "");
//                    tile[29].image = UtilityTool.loadSprite(dir + "29.png", "");
//                    tile[30].image = UtilityTool.loadSprite(dir + "30.png", "");
//                    System.out.println("Case 1 for getTileImage");
//                    break;
//                default:
//                    dir = "/tiles/firstLevel/";
//                    tile = new Tile[31];
//                    for (int i = 0; i <= 30; i++) {
//                        tile[i] = new Tile();
//                        if (i < 26) tile[i].collision = true;
//                    }
//
//                    tile[0].image = UtilityTool.loadSprite(dir + "00.png", "");
//                    tile[1].image = UtilityTool.loadSprite(dir + "01.png", "");
//                    tile[2].image = UtilityTool.loadSprite(dir + "02.png", "");
//                    tile[3].image = UtilityTool.loadSprite(dir + "03.png", "");
//                    tile[4].image = UtilityTool.loadSprite(dir + "04.png", "");
//                    tile[5].image = UtilityTool.loadSprite(dir + "05.png", "");
//                    tile[6].image = UtilityTool.loadSprite(dir + "06.png", "");
//                    tile[7].image = UtilityTool.loadSprite(dir + "07.png", "");
//                    tile[8].image = UtilityTool.loadSprite(dir + "08.png", "");
//                    tile[9].image = UtilityTool.loadSprite(dir + "09.png", "");
//                    tile[10].image = UtilityTool.loadSprite(dir + "10.png", "");
//                    tile[11].image = UtilityTool.loadSprite(dir + "11.png", "");
//                    tile[12].image = UtilityTool.loadSprite(dir + "12.png", "");
//                    tile[13].image = UtilityTool.loadSprite(dir + "13.png", "");
//                    tile[14].image = UtilityTool.loadSprite(dir + "14.png", "");
//                    tile[15].image = UtilityTool.loadSprite(dir + "15.png", "");
//                    tile[16].image = UtilityTool.loadSprite(dir + "16.png", "");
//                    tile[17].image = UtilityTool.loadSprite(dir + "17.png", "");
//                    tile[18].image = UtilityTool.loadSprite(dir + "18.png", "");
//                    tile[19].image = UtilityTool.loadSprite(dir + "19.png", "");
//                    tile[20].image = UtilityTool.loadSprite(dir + "20.png", "");
//                    tile[21].image = UtilityTool.loadSprite(dir + "21.png", "");
//                    tile[22].image = UtilityTool.loadSprite(dir + "22.png", "");
//                    tile[23].image = UtilityTool.loadSprite(dir + "23.png", "");
//                    tile[24].image = UtilityTool.loadSprite(dir + "24.png", "");
//                    tile[25].image = UtilityTool.loadSprite(dir + "25.png", "");
//
//                    // Floor
//                    tile[26].image = UtilityTool.loadSprite(dir + "26.png", "");
//                    tile[27].image = UtilityTool.loadSprite(dir + "27.png", "");
//                    tile[28].image = UtilityTool.loadSprite(dir + "28.png", "");
//                    tile[29].image = UtilityTool.loadSprite(dir + "29.png", "");
//                    tile[30].image = UtilityTool.loadSprite(dir + "30.png", "");
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
