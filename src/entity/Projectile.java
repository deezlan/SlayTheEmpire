package entity;

import main.GamePanel;
import object.OBJ_Gun_SnowBallCannon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends Entity {

    /* Arraylist for images of sprites,
    not used in this class specifically, but used in projectile's subclasses
     */
    public ArrayList<BufferedImage> spriteList = new ArrayList<>();
    public BufferedImage image;
    private Graphics2D g2;

    // Projectile Info
    int selectedProjectile;
    boolean disappear;

    // Initialise projectiles
    OBJ_Gun_SnowBallCannon snc;

    // Projectile movement variables
    public int proX;
    public int proY;
    public int proNum = 1;
    public int proCounter = 0;
    public int proSpeed = 70;
    public Timer timer;
    double dx;
    double dy;



    public Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
        selectedProjectile = 0;
        speed = 5;
        timer = new Timer(20, e -> updatePosition());
        timer.start();
    }

    public void update() {
        if (selectedProjectile < 3) {
            if (gp.mouseH.shortGet) {
                proX = getPlayerX();
                proY = getPlayerY();
            }
            switch (selectedProjectile) {
                case 0:
                    if (snc == null) {
                        snc = new OBJ_Gun_SnowBallCannon(gp);
                    }
                    snc.update();
                    checkProjectileCollision();
                    break;
            }
        }else {
            //PUT CODE FOR BOSS PROJECTILES
        }

        System.out.println(getProjectileFace());

        if(!disappear){
            updatePosition();
        }

    }

    private void updatePosition() {
        dx = gp.cursor.deltaX;
        dy = gp.cursor.deltaY;

        if (Math.abs(dx) > 1) {
            proX += (int) (dx / proSpeed);
        }
        if (Math.abs(dy) > 1) {
            proY += (int) (dy / proSpeed);
        }

        if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1) {
            timer.stop();
        }
    }

    public void draw(Graphics2D g2) {
        if (snc == null) {
            snc = new OBJ_Gun_SnowBallCannon(gp);

        }
        if (proNum > 0 && proNum <= snc.spriteList.size()) {
            BufferedImage projectiles = snc.spriteList.get(snc.proNum - 1);
            g2.drawImage(projectiles, proX + 22, proY, gp.TILE_SIZE*3/2, gp.TILE_SIZE*3/2, null);
        }
    }

    public void checkProjectileCollision(){
        if (!gp.mouseH.shortGet) {
            action = getProjectileAction();
            gp.cChecker.checkTile(this);

//            FOR DANIAL: remove the line below to see how when collision checker is off
            checkCollideWall();
        }
    }

//  boolean to see whether it has collided with tile wall or not, disappear = true if collided
    public void checkCollideWall(){
        if(action.equals("moveDown")){
            disappear = downCollisionOn;
        } else if (action.equals("moveUp")) {
            disappear = upCollisionOn;
        }
    }

    //projectile action for CollisionChecker class
    public String getProjectileAction(){
        if (getProjectileAngle() > 0){
            return "moveDown";
        }
        return "moveUp";
    }

    public double getProjectileAngle(){
        return Math.atan2(dy, dx);
    }

    //will be used to tell if i should take the image of projectile facing right or left
    public String getProjectileFace(){
        if (dx > 0) {
            return "right";
        } else {
            return "left";
        }
    }

    public int getPlayerX() {
        return gp.player.worldX;
    }

    public int getPlayerY() {
        return gp.player.worldY;
    }






}



