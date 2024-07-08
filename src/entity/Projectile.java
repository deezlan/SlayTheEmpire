package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;
    double dx;
    double dy;
    public Projectile(GamePanel gp) {
        super(gp);
    }
    String direction;
    int delta;

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX+80;
        this.worldY = worldY+30;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
        this.action = direction;
    }

    public void update(){
        delta++;
        dx = gp.cursor.deltaX;
        dy = gp.cursor.deltaY;
        if (Math.sqrt(dx*dx + dy*dy) < 200){
            dx *= 3;
            dy *= 3;
        }

        if (user == gp.player){
            int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
            if (monsterIndex != 999) {
                System.out.println("HIT!");
                alive = false;
            }
        }
        if (delta >= 10){
            worldX += (int) (dx/20);
            worldY += (int) (dy/20);
            delta = 0;
        }

        life--;
        if (life <= 0){
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
}
