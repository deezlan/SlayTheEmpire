package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    public BufferedImage
            idleRight1, idleRight2, idleRight3, idleRight4, idleRight5, idleRight6,
            idleLeft1, idleLeft2, idleLeft3, idleLeft4, idleLeft5, idleLeft6,
            left1, left2, left3, left4, left5, left6, left7, left8,
            right1, right2, right3, right4, right5, right6, right7, right8;
    public String action;
    public boolean lookingRight;

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
