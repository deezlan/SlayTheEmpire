package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends Entity {
    Entity user;
    public ArrayList <BufferedImage> spriteList = new ArrayList<>();
    int selectedProjectile;
    GamePanel gp;

    public Projectile(GamePanel gp) {
        super(gp);
        selectedProjectile = 0; //update this to link with the weapons class, for now left to this
        projectileSelect(selectedProjectile);
    }

    public void projectileSelect (int selectedProjectile){
        switch(selectedProjectile){
            case 0:
                selectedProjectile = 0;
                break;
        }
    }

    public void update(){
        collisionOn = false;
        worldY += speed;

    }
    public void set(int worldX, int worldY, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.user = user;
    }

}

