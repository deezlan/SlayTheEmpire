package entity;

import main.GamePanel;

public class Projectile extends Entity{

    Entity user;
    double dx;
    double dy;
    double speedX;
    double speedY;
    public Projectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX+30;
        this.worldY = worldY+30;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
        this.action = direction;
    }

    public void update(){
        dx = gp.cursor.deltaX;
        dy = gp.cursor.deltaY;

        speedX = (int) (dx / speed);
        speedY = (int) (dy / speed);

        if (user == gp.player){
            int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
            if (monsterIndex != 999) {
                System.out.println("HIT!");
                alive = false;
            }
        }
//        if (user != gp.player){
//
//        }
//        switch (direction){
//            case "moveUp": worldY -= speed;
//                System.out.println("working"); break;
//            case "moveDown": worldY += speed; break;
//            case "moveLeft": worldX -= speed; break;
//            case "moveRight": worldX += speed; break;
//        }
        worldY += (int) (speedY/5);
        worldX += (int) (speedX/5);

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
