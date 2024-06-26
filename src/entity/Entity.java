package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage
            idleRight1, idleRight2, idleRight3, idleRight4, idleRight5, idleRight6,
            idleLeft1, idleLeft2, idleLeft3, idleLeft4, idleLeft5, idleLeft6,
            moveLeft1, moveLeft2, moveLeft3, moveLeft4, moveLeft5, moveLeft6, moveLeft7, moveLeft8,
            moveRight1, moveRight2, moveRight3, moveRight4, moveRight5, moveRight6, moveRight7, moveRight8;

    public String action;
    public boolean lookingRight;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn = false;
}
