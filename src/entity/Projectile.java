package entity;

import main.GamePanel;

import java.awt.*;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void update() {
    }

    public void set(int worldX, int worldY, Entity user){
        this.worldX = worldX;
        this.worldY = worldY;
        this.user = user;
    }
}

