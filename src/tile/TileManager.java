
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
        gp.MAX_WORLD_COL = 30;
        gp.MAX_WORLD_ROW = 20;

        mapTileNum = new int[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        getTileImage();
        loadMap("/mapTextFiles/LobbyTest.txt",0);
        loadMap("/mapTextFiles/smalRoom.txt",1);
//        loadMap("/mapTextFiles/secondLevel.txt", 2);
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

//                switch (gp.currentMap) {
//                    case 0, 1:
                        int screenX = worldX - gp.player.worldX + gp.player.screenX;
                        int screenY = worldY - gp.player.worldY + gp.player.screenY;

                        if (worldX + gp.TILE_SIZE  > gp.player.worldX - gp.player.screenX - 48*4 &&
                                worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.screenX + 48*4 &&
                                worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.screenY &&
                                worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.screenY + 48*2) {
                            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                        }
//                        break;
////                    gp.setBackground(Color.decode("#222034")); // NO LOCK ON PLAYER
////                    g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null);
////                    break;
//                    default:
//                        System.out.println("Wrong case for TileManager draw");
//                }
            } else {
                System.err.println("Tile or tile image is null for tile number: " + tileNum);
            }

            WORLD_COL++;
            if (WORLD_COL == gp.MAX_WORLD_COL) {
                WORLD_COL = 0;
                WORLD_ROW++;
            }
        }
        if(drawPath){ // for viewing the move path of entities
            g2.setColor(new Color(255,0,0,70));

            for(int i = 0; i < gp.pFinder.pathList.size(); i++){
                int worldX = gp.pFinder.pathList.get(i).col * gp.TILE_SIZE;
                int worldY = gp.pFinder.pathList.get(i).row * gp.TILE_SIZE;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX,screenY,gp.TILE_SIZE,gp.TILE_SIZE);
            }
        }
    }

    public void getTileImage() {
        try {
            dir = "/tiles/";
            tile = new Tile[200];
            for (int i = 0; i <= 149; i++) {
                tile[i] = new Tile();
                if (i < 13 || i > 25 ) tile[i].collision = true;
            }

            // LOBBY WALLS
            tile[0].image = UtilityTool.loadSprite(dir + "lobby/00.png", "Top Left Wall");
            tile[1].image = UtilityTool.loadSprite(dir + "lobby/01.png", "Top Middle Wall");
            tile[2].image = UtilityTool.loadSprite(dir + "lobby/02.png", "Hole Wall");
            tile[3].image = UtilityTool.loadSprite(dir + "lobby/03.png", "Door Wall");
            tile[4].image = UtilityTool.loadSprite(dir + "lobby/04.png", "Old Wall");
            tile[5].image = UtilityTool.loadSprite(dir + "lobby/05.png", "Top Right Wall");
            tile[6].image = UtilityTool.loadSprite(dir + "lobby/06.png", "Middle Left Wall");
            tile[7].image = UtilityTool.loadSprite(dir + "lobby/07.png", "Middle Right Wall");
            tile[8].image = UtilityTool.loadSprite(dir + "lobby/08.png", "Bottom Left Wall");
            tile[9].image = UtilityTool.loadSprite(dir + "lobby/09.png", "Bottom Right Wall");
            tile[10].image = UtilityTool.loadSprite(dir + "lobby/10.png", "Bottom Middle Wall");
            tile[11].image = UtilityTool.loadSprite(dir + "lobby/11.png", "Left Stairs Wall tile");
            tile[12].image = UtilityTool.loadSprite(dir + "lobby/12.png", "Right Stairs Wall tile");

            // LOBBY FLOOR
            tile[13].image = UtilityTool.loadSprite(dir + "lobby/13.png", "Blank Floor");
            tile[14].image = UtilityTool.loadSprite(dir + "lobby/14.png", "Patterned Floor");
            tile[15].image = UtilityTool.loadSprite(dir + "lobby/15.png", "Checkered Floor");
            tile[16].image = UtilityTool.loadSprite(dir + "lobby/16.png", "Blank Bordered Floor");
            tile[17].image = UtilityTool.loadSprite(dir + "lobby/17.png", "Middle Stairs");
            tile[18].image = UtilityTool.loadSprite(dir + "lobby/18.png", "Left Stair");
            tile[19].image = UtilityTool.loadSprite(dir + "lobby/19.png", "Right Stair");
            tile[20].image = UtilityTool.loadSprite(dir + "lobby/20.png", "Top Right Floor");
            tile[21].image = UtilityTool.loadSprite(dir + "lobby/21.png", "Top Left Floor");
            tile[22].image = UtilityTool.loadSprite(dir + "lobby/22.png", "Top Middle Floor");
            tile[23].image = UtilityTool.loadSprite(dir + "lobby/23.png", "Middle Left Floor");
            tile[24].image = UtilityTool.loadSprite(dir + "lobby/24.png", "Middle Right");
            tile[25].image = UtilityTool.loadSprite(dir + "lobby/25.png", "Blank Cracked");

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
            tile[37].image = UtilityTool.loadSprite(dir + "lobby/37.png", "Lobby Out of Bounds");

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
            tile[63].image = UtilityTool.loadSprite(dir + "firstLevel/25.png", "Parallel Line");

            // FIRST LEVEL FLOOR
            tile[64].image = UtilityTool.loadSprite(dir + "firstLevel/26.png", "Chisel Tile");
            tile[65].image = UtilityTool.loadSprite(dir + "firstLevel/27.png", "Blank Space");
            tile[66].image = UtilityTool.loadSprite(dir + "firstLevel/28.png", "Grey Tile");
            tile[67].image = UtilityTool.loadSprite(dir + "firstLevel/29.png", "Cracked tiles");
            tile[68].image = UtilityTool.loadSprite(dir + "firstLevel/30.png", "Chisel Cracked Tile");

            // COLLISION CORRECTION
            tile[38].collision = false;
            tile[64].collision = false;
            tile[65].collision = false;
            tile[66].collision = false;
            tile[67].collision = false;
            tile[68].collision = false;

            // SECOND LEVEL WALL
            tile[69].image = UtilityTool.loadSprite(dir + "secondLevel/00.png", "Out of Bounds");
            tile[70].image = UtilityTool.loadSprite(dir + "secondLevel/01.png", "Top Left Wall");
            tile[71].image = UtilityTool.loadSprite(dir + "secondLevel/02.png", "Top Wall Shadowed");
            tile[72].image = UtilityTool.loadSprite(dir + "secondLevel/03.png", "Top Wall");
            tile[73].image = UtilityTool.loadSprite(dir + "secondLevel/04.png", "Top Wall 2");
            tile[74].image = UtilityTool.loadSprite(dir + "secondLevel/05.png", "Top Right Wall");
            tile[75].image = UtilityTool.loadSprite(dir + "secondLevel/06.png", "Left Wall");
            tile[76].image = UtilityTool.loadSprite(dir + "secondLevel/07.png", "Right Wall");
            tile[77].image = UtilityTool.loadSprite(dir + "secondLevel/08.png", "Top Wall Bottom Shadowed");
            tile[78].image = UtilityTool.loadSprite(dir + "secondLevel/09.png", "Top Wall Bottom");
            tile[79].image = UtilityTool.loadSprite(dir + "secondLevel/10.png", "Top Left Corner Wall");
            tile[80].image = UtilityTool.loadSprite(dir + "secondLevel/11.png", "Bottom Left Corner Wall");
            tile[81].image = UtilityTool.loadSprite(dir + "secondLevel/12.png", "Bottom Wall");
            tile[82].image = UtilityTool.loadSprite(dir + "secondLevel/13.png", "Bottom Wall 2");
            tile[83].image = UtilityTool.loadSprite(dir + "secondLevel/14.png", "Bottom Wall 3");
            tile[84].image = UtilityTool.loadSprite(dir + "secondLevel/15.png", "Bottom Wall 4");
            tile[85].image = UtilityTool.loadSprite(dir + "secondLevel/16.png", "Left Wall 2");
            tile[86].image = UtilityTool.loadSprite(dir + "secondLevel/17.png", "Right Wall 2");
            tile[87].image = UtilityTool.loadSprite(dir + "secondLevel/18.png", "Bottom Right Corner Wall");
            tile[88].image = UtilityTool.loadSprite(dir + "secondLevel/19.png", "Window Wall 1");
            tile[89].image = UtilityTool.loadSprite(dir + "secondLevel/20.png", "Window Wall 2");
            tile[90].image = UtilityTool.loadSprite(dir + "secondLevel/21.png", "L Wall");
            tile[91].image = UtilityTool.loadSprite(dir + "secondLevel/22.png", "L Wall 2");
            tile[92].image = UtilityTool.loadSprite(dir + "secondLevel/23.png", "Left Wall 3");
            tile[93].image = UtilityTool.loadSprite(dir + "secondLevel/24.png", "Right Wall 3");
            tile[94].image = UtilityTool.loadSprite(dir + "secondLevel/25.png", "L Wall 3");
            tile[95].image = UtilityTool.loadSprite(dir + "secondLevel/26.png", "L Wall 4");
            tile[96].image = UtilityTool.loadSprite(dir + "secondLevel/27.png", "Top Border");
            tile[97].image = UtilityTool.loadSprite(dir + "secondLevel/28.png", "Top Border 2");
            tile[98].image = UtilityTool.loadSprite(dir + "secondLevel/29.png", "Top Border 3");

            // SECOND LEVEL FLOOR
            tile[99].image = UtilityTool.loadSprite(dir + "secondLevel/30.png", "Missing ");
            tile[100].image = UtilityTool.loadSprite(dir + "secondLevel/31.png", "Missing ");
            tile[101].image = UtilityTool.loadSprite(dir + "secondLevel/32.png", "Missing ");
            tile[102].image = UtilityTool.loadSprite(dir + "secondLevel/33.png", "Missing ");
            tile[103].image = UtilityTool.loadSprite(dir + "secondLevel/34.png", "Missing ");
            tile[104].image = UtilityTool.loadSprite(dir + "secondLevel/35.png", "Missing ");
            tile[105].image = UtilityTool.loadSprite(dir + "secondLevel/36.png", "Missing ");
            tile[106].image = UtilityTool.loadSprite(dir + "secondLevel/37.png", "Missing ");
            tile[107].image = UtilityTool.loadSprite(dir + "secondLevel/38.png", "Missing ");
            tile[108].image = UtilityTool.loadSprite(dir + "secondLevel/39.png", "Missing ");
            tile[109].image = UtilityTool.loadSprite(dir + "secondLevel/40.png", "Missing ");
            tile[110].image = UtilityTool.loadSprite(dir + "secondLevel/41.png", "Missing ");
            tile[111].image = UtilityTool.loadSprite(dir + "secondLevel/42.png", "Missing ");
            tile[112].image = UtilityTool.loadSprite(dir + "secondLevel/43.png", "Missing ");
            tile[113].image = UtilityTool.loadSprite(dir + "secondLevel/44.png", "Missing ");
            tile[114].image = UtilityTool.loadSprite(dir + "secondLevel/45.png", "Missing ");
            tile[115].image = UtilityTool.loadSprite(dir + "secondLevel/46.png", "Missing ");
            tile[116].image = UtilityTool.loadSprite(dir + "secondLevel/47.png", "Missing ");
            tile[117].image = UtilityTool.loadSprite(dir + "secondLevel/48.png", "Missing ");
            tile[118].image = UtilityTool.loadSprite(dir + "secondLevel/49.png", "Missing ");
            tile[119].image = UtilityTool.loadSprite(dir + "secondLevel/50.png", "Missing ");
            tile[120].image = UtilityTool.loadSprite(dir + "secondLevel/51.png", "Missing ");
            tile[121].image = UtilityTool.loadSprite(dir + "secondLevel/52.png", "Missing ");
            tile[122].image = UtilityTool.loadSprite(dir + "secondLevel/53.png", "Missing ");
            tile[123].image = UtilityTool.loadSprite(dir + "secondLevel/54.png", "Missing ");
            tile[124].image = UtilityTool.loadSprite(dir + "secondLevel/55.png", "Missing ");
            tile[125].image = UtilityTool.loadSprite(dir + "secondLevel/56.png", "Missing ");
            tile[126].image = UtilityTool.loadSprite(dir + "secondLevel/57.png", "Missing ");
            tile[127].image = UtilityTool.loadSprite(dir + "secondLevel/58.png", "Missing ");
            tile[128].image = UtilityTool.loadSprite(dir + "secondLevel/59.png", "Missing ");
            tile[129].image = UtilityTool.loadSprite(dir + "secondLevel/60.png", "Missing ");
            tile[130].image = UtilityTool.loadSprite(dir + "secondLevel/61.png", "Missing ");
            tile[131].image = UtilityTool.loadSprite(dir + "secondLevel/62.png", "Missing ");
            tile[132].image = UtilityTool.loadSprite(dir + "secondLevel/63.png", "Missing ");
            tile[133].image = UtilityTool.loadSprite(dir + "secondLevel/64.png", "Missing ");
            tile[134].image = UtilityTool.loadSprite(dir + "secondLevel/65.png", "Missing ");
            tile[135].image = UtilityTool.loadSprite(dir + "secondLevel/66.png", "Missing ");
            tile[136].image = UtilityTool.loadSprite(dir + "secondLevel/67.png", "Missing ");
            tile[137].image = UtilityTool.loadSprite(dir + "secondLevel/68.png", "Missing ");
            tile[138].image = UtilityTool.loadSprite(dir + "secondLevel/69.png", "Missing ");
            tile[139].image = UtilityTool.loadSprite(dir + "secondLevel/70.png", "Missing ");
            tile[140].image = UtilityTool.loadSprite(dir + "secondLevel/71.png", "Missing ");
            tile[141].image = UtilityTool.loadSprite(dir + "secondLevel/72.png", "Missing ");
            tile[142].image = UtilityTool.loadSprite(dir + "secondLevel/73.png", "Missing ");
            tile[143].image = UtilityTool.loadSprite(dir + "secondLevel/74.png", "Missing ");
            tile[144].image = UtilityTool.loadSprite(dir + "secondLevel/75.png", "Missing ");
            tile[145].image = UtilityTool.loadSprite(dir + "secondLevel/76.png", "Missing ");
            tile[146].image = UtilityTool.loadSprite(dir + "secondLevel/77.png", "Missing ");
            tile[147].image = UtilityTool.loadSprite(dir + "secondLevel/78.png", "Missing ");
            tile[148].image = UtilityTool.loadSprite(dir + "secondLevel/79.png", "Missing ");

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
