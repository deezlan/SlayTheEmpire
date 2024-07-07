package entity;

import main.UtilityTool;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cursor {
    private int mouseX, mouseY;
    private double angle;
    private BufferedImage crosshairImage;
    public int deltaX, deltaY, arrowX, arrowY;

    public Cursor( ) {
        try {
            crosshairImage = UtilityTool.loadSprite("/crosshair/crosshair.png", "Missing crosshair");
        } catch(IOException e) {
            e.printStackTrace(System.out);
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

    public void drawCrosshair(Graphics2D g2) {
        if (crosshairImage != null) {
            int crosshairWidth = (int)(crosshairImage.getWidth() * 1.75);
            int crosshairHeight = (int)(crosshairImage.getHeight() * 1.75);

            g2.drawImage(crosshairImage, mouseX - crosshairWidth/ 2, mouseY - crosshairHeight/2, crosshairWidth, crosshairHeight, null);
        }
    }

    public void drawArrow(Graphics2D g2, int playerX, int playerY) {
        AffineTransform old = g2.getTransform();

        int xOffset = 0;
        int yOffset = 0;
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
//
//    public double getAngle() {
//        return angle;
//    }
}
