package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {
    GamePanel gp;
    Tile[] tile;

    public TileManager (GamePanel gp) {
        this.gp = gp;
        tile = new Tile[11];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/topLeft.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/topMiddle.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/hole.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/door.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/old.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/topRight.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/old.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/old.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/old.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/old.png"));

            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lobby/walls/old.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
//        int col = 0;
//        int row = 0;
        int x = 0;
        int y = 0;

//        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
//            g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
//            col++;
//            x += gp.tileSize;
//
//            if (col == gp.maxScreenCol) {
//                col = 0;
//                x = 0;
//                row++;
//                y += gp.tileSize;
//            }
//        }

        g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 48, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[2].image, 96, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 144, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 192, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[3].image, 240, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 288, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[4].image, 336, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 384, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[1].image, 432, y, gp.tileSize, gp.tileSize, null);
        g2.drawImage(tile[5].image, 480, y, gp.tileSize, gp.tileSize, null);
    }
}
