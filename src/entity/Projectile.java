package entity;

import main.GamePanel;

public class Projectile extends Entity{
    Entity user;
    double dx, dy, speedX, speedY;

    public Projectile(GamePanel gp) {
        super(gp);
    }
//    String direction;
    int delta;

    public void set(int worldX, int worldY, String action, boolean alive, Entity user, int mouseX, int mouseY) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.action = action;
        this.alive = alive;
        this.user = user;
        this.currentLife = this.maxLife;
        this.dx = mouseX;
        this.dy = mouseY;
    }

    public void update() {
        speedX = (int) (dx / speed);
        speedY = (int) (dy / speed);

        if (user == gp.player) {
            if (gp.player.currentWeapon.name.equalsIgnoreCase("fireball cannon")) {

                upCollisionOn = false;
                downCollisionOn = false;
                leftCollisionOn = false;
                rightCollisionOn = false;
                gp.cChecker.checkTile(this);
                if (upCollisionOn || downCollisionOn || leftCollisionOn || rightCollisionOn) {
                    alive = false;
                    System.out.println("collision detected");
                }
                delta++;
                if (Math.sqrt(dx * dx + dy * dy) < 200) {
                    dx *= 3;
                    dy *= 3;
                }
                int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                if (monsterIndex != 999) {
                    System.out.println("HIT!");
                    gp.player.damageMonster(monsterIndex, damage, this);
                    alive = false;
                }
                if (delta >= 4) {
                    worldX += (int) (dx / 10);
                    worldY += (int) (dy / 10);
                    delta = 0;
                }
            } else if (gp.player.currentWeapon.name.equalsIgnoreCase("electric blaster")) {
                int offset = 50;
                worldX = (int) (gp.player.worldX + 36 + (offset * Math.cos(gp.cursor.getAngle())));
                worldY = (int) (gp.player.worldY - 12 + (offset * Math.sin(gp.cursor.getAngle())));
                int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                if (monsterIndex != 999) {
                    System.out.println("HIT!");
                    gp.player.damageMonster(monsterIndex, damage, this);
                }
            } else if (gp.player.currentWeapon.name.equalsIgnoreCase("stickler")) {

                upCollisionOn = false;
                downCollisionOn = false;
                leftCollisionOn = false;
                rightCollisionOn = false;
                gp.cChecker.checkTile(this);
                if (upCollisionOn || downCollisionOn || leftCollisionOn || rightCollisionOn) {
                    alive = false;
                }
                delta++;
                if (Math.sqrt(dx * dx + dy * dy) < 200) {
                    dx *= 3;
                    dy *= 3;
                }
                int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                if (monsterIndex != 999) {
                    dy = 0;
                    dx = 0;

                    System.out.println("HIT!");
                    gp.player.damageMonster(monsterIndex, damage, this);
                }
                if (delta >= 4) {
                    worldX += (int) ((dx) / 10);
                    worldY += (int) ((dy) / 10);
                    delta = 0;
                }
            } else if (gp.player.currentWeapon.name.equalsIgnoreCase("hammer")) {
                int monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                if (monsterIndex != 999) {
                    System.out.println("HIT!");
                    gp.player.damageMonster(monsterIndex, damage, this);
                }
                dx = gp.cursor.deltaX;
                dy = gp.cursor.deltaY;
                monsterIndex = gp.cChecker.checkEntityCollision(this, gp.mobArr);
                if (monsterIndex != 999) {
                    System.out.println("HIT!");
                    gp.player.damageMonster(monsterIndex, damage, this);
                }
            }
        }
        if (user != gp.player) { // FIXED WAS IN PLAYER BRACKET
            boolean contactPlayer = gp.cChecker.checkPLayer(this);

            switch (action) {
                case "moveUp":
                    worldY -= speed;
                    currentList = projectileUp;
                    break;
                case "moveDown":
                    worldY += speed;
                    currentList = projectileDown;
                    break;
                case "moveLeft":
                    worldX -= speed;
                    currentList = projectileLeft;
                    break;
                case "moveRight":
                    worldX += speed;
                    currentList = projectileRight;
                    break;
            }

            if (name.equalsIgnoreCase("demon blast") & !gp.player.iframe & contactPlayer){
                System.out.println("HIT PLAYER!");
                damagePlayer(damage);
            }else if (!gp.player.iframe && contactPlayer) {
                System.out.println("HIT PLAYER!");
                damagePlayer(damage);
                alive = false;
            }
        }

        currentLife--;
        if (currentLife <= 0) {
            alive = false;
            spriteNum = 0;
        }

        spriteCounter++;
        if (currentList.size() > 14) {
            if (spriteCounter > 4) runCurrentListAnimation();
        } else if (currentList.size() > 7) {
            if (spriteCounter > 6) runCurrentListAnimation();
        } else {
            if (spriteCounter > 9) runCurrentListAnimation();
        }
    }
}
