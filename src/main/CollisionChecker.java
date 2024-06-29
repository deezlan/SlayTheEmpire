package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.TILE_SIZE;
        int entityRightCol = entityRightWorldX/gp.TILE_SIZE;
        int entityTopRow = entityTopWorldY/gp.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY/gp.TILE_SIZE;

        int tileNum1, tileNum2, tileNum3; //only need to check three tiles

        switch (entity.action){
            case "moveUp": // check if top corners of the square hitting any tiles
                entityTopRow = (entityTopWorldY - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.upCollisionOn = true;
                }
                break;
            case "moveDown": // check both corners at the bottom of the square hitting a tile
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.downCollisionOn = true;
                }
                break;
            case "moveLeft": // check both corners on the left side if hitting any tile
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.leftCollisionOn = true;
                }
                break;
            case "moveRight": //check both corners on the right if hitting any tile
                entityRightCol = (entityRightWorldX + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision){
                    entity.rightCollisionOn = true;
                }
                break;
            case "moveUpRight": //check both corners on the right if hitting any tile
                entityTopRow = (entityTopWorldY - entity.speed)/gp.TILE_SIZE;
                entityRightCol = (entityRightWorldX + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum3 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                        entity.upCollisionOn = true;
                        entity.rightCollisionOn = true;
                }
                else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                        entity.rightCollisionOn = true;
                }
                else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision
                ){
                        entity.upCollisionOn = true;
                }
                break;
            case "moveDownRight": //check both corners on the right if hitting any tile
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.TILE_SIZE;
                entityRightCol = (entityRightWorldX + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision){
                        entity.downCollisionOn = true;
                        entity.rightCollisionOn = true;
                }
                else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                        entity.downCollisionOn = true;
                } else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision
                ){
                        entity.rightCollisionOn = true;
                }
                break;
            case "moveUpLeft": //check both corners on the right if hitting any tile
                entityTopRow = (entityTopWorldY - entity.speed)/gp.TILE_SIZE;
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum3 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                        entity.upCollisionOn = true;
                        entity.leftCollisionOn = true;
                } else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                        entity.leftCollisionOn = true;
                } else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision
                ){
                        entity.upCollisionOn = true;
                }
                break;
            case "moveDownLeft": //check both corners on the right if hitting any tile
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.TILE_SIZE;
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum3 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                    entity.downCollisionOn = true;
                    entity.leftCollisionOn = true;
                } else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision
                ){
                        entity.downCollisionOn = true;
                } else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision
                ){
                        entity.leftCollisionOn = true;
                }
                break;
        }
    }

    public int checkObject (Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.objArr.length; i++) {
            if (gp.objArr[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gp.objArr[i].solidArea.x = gp.objArr[i].worldX + gp.objArr[i].solidArea.x;
                gp.objArr[i].solidArea.y = gp.objArr[i].worldY + gp.objArr[i].solidArea.y;

                switch (entity.action) {
                    case "moveUp":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) entity.upCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveDown":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) entity.downCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveLeft":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) entity.leftCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveRight":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) entity.rightCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveUpRight":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) {
                                entity.rightCollisionOn = true;
                                entity.upCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                    case "moveDownRight":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) {
                                entity.rightCollisionOn = true;
                                entity.downCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                    case "moveUpLeft":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) {
                                entity.leftCollisionOn = true;
                                entity.upCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                    case "moveDownLeft":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[i].solidArea)) {
                            if (gp.objArr[i].collision) {
                                entity.downCollisionOn = true;
                                entity.leftCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objArr[i].solidArea.x = gp.objArr[i].solidAreaDefaultX;
                gp.objArr[i].solidArea.y = gp.objArr[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public int checkEntityCollision(Entity entity, Entity[] target){
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.action) {
                    case "moveUp":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.upCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveDown":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.downCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveLeft":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.leftCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveRight":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.rightCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveUpRight":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.rightCollisionOn = true;
                                entity.upCollisionOn = true;
                                index = i;
                        }
                        break;
                    case "moveDownRight":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.rightCollisionOn = true;
                                entity.downCollisionOn = true;
                                index = i;
                        }
                        break;
                    case "moveUpLeft":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.leftCollisionOn = true;
                                entity.upCollisionOn = true;
                                index = i;
                        }
                        break;
                    case "moveDownLeft":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                                entity.downCollisionOn = true;
                                entity.leftCollisionOn = true;
                                index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPLayer(Entity entity){
        boolean contactPlayer  = false;
        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        switch (entity.action) {
            case "moveUp":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.upCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveDown":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.downCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveLeft":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.leftCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveRight":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.rightCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveUpRight":
                entity.solidArea.y -= entity.speed;
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.rightCollisionOn = true;
                    entity.upCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveDownRight":
                entity.solidArea.y += entity.speed;
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.rightCollisionOn = true;
                    entity.downCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveUpLeft":
                entity.solidArea.y -= entity.speed;
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.leftCollisionOn = true;
                    entity.upCollisionOn = true;
                    contactPlayer = true;

                }
                break;
            case "moveDownLeft":
                entity.solidArea.y += entity.speed;
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.solidArea)) {
                    entity.downCollisionOn = true;
                    entity.leftCollisionOn = true;
                    contactPlayer = true;
                }
                break;

        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }
}


