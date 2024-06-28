package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
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
        gp.gameArea = 1;
        this.gp = gp;
        tile = new Tile[30];
        mapTileNum = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];
        getTileImage();

        switch (gp.gameArea) {
            case 0:
                loadMap("/tiles/lobby/test.txt");
                break;
            case 1:
                loadMap("/tiles/lobby/map.txt");
            default:
//                loadMap("/tiles/lobby/map.txt");
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

        // AUTOMATIC TILE PLACER
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
                        g2.drawImage(tile[tileNum].image, worldX, worldY, gp.TILE_SIZE, gp.TILE_SIZE, null); // here
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
        try {
            switch (gp.gameArea) {
                case 0:
                case 1:
                default:
                    // Walls
                    tile[0] = new Tile();
                    tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/topLeft.png"), "Missing top left wall tile"));
                    tile[0].collision = true;

                    tile[1] = new Tile();
                    tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/topMiddle.png"), "Missing top middle wall tile"));
                    tile[1].collision = true;

                    tile[2] = new Tile();
                    tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/hole.png"), "Missing hole wall tile"));
                    tile[2].collision = true;

                    tile[3] = new Tile();
                    tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/door.png"), "Missing top door wall tile"));
                    tile[3].collision = true;

                    tile[4] = new Tile();
                    tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/old.png"), "Missing old wall tile"));
                    tile[4].collision = true;

                    tile[5] = new Tile();
                    tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/topRight.png"), "Missing top right wall tile"));
                    tile[5].collision = true;

                    tile[18] = new Tile();
                    tile[18].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/middleLeft.png"), "Missing middle left wall tile"));
                    tile[18].collision = true;

                    tile[19] = new Tile();
                    tile[19].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/middleRight.png"), "Missing middle right wall tile"));
                    tile[19].collision = true;

                    tile[20] = new Tile();
                    tile[20].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/bottomLeft.png"), "Missing bottom left wall tile"));
                    tile[20].collision = true;

                    tile[21] = new Tile();
                    tile[21].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/bottomRight.png"), "Missing bottom right wall tile"));
                    tile[21].collision = true;

                    tile[22] = new Tile();
                    tile[22].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/bottomMiddle.png"), "Missing bottom middle wall tile"));
                    tile[22].collision = true;

                    tile[23] = new Tile();
                    tile[23].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/stairsLeft.png"), "Missing stair left wall tile"));
                    tile[23].collision = true;

                    tile[24] = new Tile();
                    tile[24].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/stairsRight.png"), "Missing stair right wall tile"));
                    tile[24].collision = true;

                    // Floor
                    tile[6] = new Tile();
                    tile[6].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/blank.png"), "Missing Blank Floor"));

                    tile[7] = new Tile();
                    tile[7].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/patterned.png"), "Missing Patterned Floor"));

                    tile[8] = new Tile();
                    tile[8].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/checkered.png"), "Missing Checkered Floor"));

                    tile[9] = new Tile();
                    tile[9].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/blankBordered.png"), "Missing Border floor"));

                    tile[10] = new Tile();
                    tile[10].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/middleStairs.png"), "Missing Middle Stair"));

                    tile[11] = new Tile();
                    tile[11].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/leftStairs.png"), "Missing Left Stair"));

                    tile[12] = new Tile();
                    tile[12].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/rightStairs.png"), "Missing Right Stair"));

                    tile[13] = new Tile();
                    tile[13].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/topRight.png"), "Missing top right floor"));

                    tile[14] = new Tile();
                    tile[14].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/topLeft.png"), "Missing top left floor"));

                    tile[15] = new Tile();
                    tile[15].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/topMiddle.png"), "Missing top middle floor"));

                    tile[16] = new Tile();
                    tile[16].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/middleLeft.png"), "Missing Left Middle floor"));

                    tile[17] = new Tile();
                    tile[17].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/floor/middleRight.png"), "Missing Right middle"));
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
