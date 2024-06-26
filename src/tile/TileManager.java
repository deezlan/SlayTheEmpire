package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum; //to check which tile the player is currently hitting

    public TileManager (GamePanel gp) {
        this.gp = gp;
        tile = new Tile[11];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image
                    = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
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

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                            "/tiles/lobby/walls/old.png"), "Missing old wall tile"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/tiles/lobby/walls/topRight.png"), "Missing top right wall tile"));
            tile[5].collision = true;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void draw(Graphics2D g2) {
//        int col = 0;
//        int row = 0;
        int x = 0;
        int y = 0;

//        AUTOMATIC TILE PLACER
//        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
//            g2.drawImage(tile[0].image, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
//            col++;
//            x += gp.TILE_SIZE;
//
//            if (col == gp.maxScreenCol) {
//                col = 0;
//                x = 0;
//                row++;
//                y += gp.TILE_SIZE;
//            }
//        }

        g2.drawImage(tile[0].image, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[1].image, 48, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[2].image, 96, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[1].image, 144, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[1].image, 192, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[3].image, 240, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[1].image, 288, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[4].image, 336, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[1].image, 384, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[1].image, 432, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
        g2.drawImage(tile[5].image, 480, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
    }
}
