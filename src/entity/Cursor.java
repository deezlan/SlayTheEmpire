package entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Cursor {
    private int mouseX, mouseY;
    public double angle;
    private BufferedImage crosshairImage;
    public int deltaX;
    public int deltaY;
    //a random line

    public Cursor( ) {
        try {
            crosshairImage = ImageIO.read(getClass().getResourceAsStream("/crosshair/crosshair.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void updateMousePosition(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void calculateAngle(int playerX, int playerY) {
        deltaX = mouseX - playerX;
        deltaY = mouseY - playerY;
        angle = Math.atan2(deltaY, deltaX);
    }

    public void draw(Graphics2D g2, int playerX, int playerY) {
        drawArrow(g2, playerX, playerY);
        drawCrosshair(g2);
    }

    public void
    drawCrosshair(Graphics2D g2) {
        if (crosshairImage != null) {
            int crosshairWidth = crosshairImage.getWidth();
            int crosshairHeight = crosshairImage.getHeight();

            g2.drawImage(crosshairImage, mouseX - crosshairWidth/ 2, mouseY - crosshairHeight/2, null);
        }
    }

    public void drawArrow(Graphics2D g2, int playerX, int playerY) {
        AffineTransform old = g2.getTransform();

        int xOffset = 0;
        int yOffset = 10;
        g2.translate(playerX + xOffset, playerY + yOffset);

        g2.rotate(angle);
        g2.setColor(Color.CYAN);
        int[] xPoints = {55, 48, 48};
        int[] yPoints = {0, -10, 10};
        g2.fillPolygon(xPoints, yPoints, 3); // Arrowhead
        g2.setTransform(old);
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
