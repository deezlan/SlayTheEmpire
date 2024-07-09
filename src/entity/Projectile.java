package entity;

import main.GamePanel;

public class Projectile extends Entity{
    Entity user;
    double dx, dy, speedX, speedY;
    public Projectile(GamePanel gp) {
        super(gp);
    }
    String direction;

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.worldX = worldX+80;
        this.worldY = worldY+30;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.currentLife = this.maxLife;
        this.action = direction;
    }

    public void update(){
        speedX = (int) (dx / speed);
        speedY = (int) (dy / speed);

        if (user == gp.player){
            dx = gp.cursor.deltaX;
            dy = gp.cursor.deltaY;
            int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
            if (monsterIndex != 999) {
                System.out.println("HIT!");
                gp.player.damageMonster(monsterIndex, damage,this);
                alive = false;
            }
        }
        if(user != gp.player) {
            boolean contactPlayer = gp.cChecker.checkPLayer(this);

            switch (action) {
                case "moveUp": worldY -= speed; currentList = projectileUp; break;
                case "moveDown": worldY += speed; currentList = projectileDown; break;
                case "moveLeft": worldX -= speed; currentList = projectileLeft; break;
                case "moveRight": worldX += speed; currentList = projectileRight; break;
            }

            if(!gp.player.iframe && contactPlayer){
                System.out.println("HIT PLAYER!");
                damagePlayer(damage);
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

        currentLife--;
        if (currentLife <= 0){
            alive = false;
        }

        spriteCounter++;
        if (currentList.size() > 14) {
            if (spriteCounter > 4) loopThroughSprites();
        } else if (currentList.size() > 7) {
            if (spriteCounter > 6) loopThroughSprites();
        } else {
            if (spriteCounter > 9) loopThroughSprites();
        }
    }
}
