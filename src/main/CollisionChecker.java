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

        // Temp Direction for knockback
        String action = entity.action;
        if (entity.knockBack) {
            action = entity.knockBackDirection;
        }

        switch (action) {
            case "moveUp": // check if top corners of the square hitting any tiles
                entityTopRow = (entityTopWorldY - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
                    entity.upCollisionOn = true;
                break;
            case "moveDown": // check both corners at the bottom of the square hitting a tile
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
                    entity.downCollisionOn = true;
                break;
            case "moveLeft": // check both corners on the left side if hitting any tile
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
                    entity.leftCollisionOn = true;
                break;
            case "moveRight": // check both corners on the right if hitting any tile
                entityRightCol = (entityRightWorldX + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision)
                    entity.rightCollisionOn = true;
                break;
            case "moveUpRight": // check both corners on the right if hitting any tile
                entityTopRow = (entityTopWorldY - entity.speed)/gp.TILE_SIZE;
                entityRightCol = (entityRightWorldX + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.upCollisionOn = true;
                        entity.rightCollisionOn = true;
                }
                else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.rightCollisionOn = true;
                }
                else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision)
                {
                        entity.upCollisionOn = true;
                }
                break;
            case "moveDownRight": //check both corners on the right if hitting any tile
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.TILE_SIZE;
                entityRightCol = (entityRightWorldX + entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.downCollisionOn = true;
                        entity.rightCollisionOn = true;
                }
                else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.downCollisionOn = true;
                } else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision)
                {
                        entity.rightCollisionOn = true;
                }
                break;
            case "moveUpLeft": //check both corners on the right if hitting any tile
                entityTopRow = (entityTopWorldY - entity.speed)/gp.TILE_SIZE;
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.upCollisionOn = true;
                        entity.leftCollisionOn = true;
                } else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.leftCollisionOn = true;
                } else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision)
                {
                        entity.upCollisionOn = true;
                }
                break;
            case "moveDownLeft": //check both corners on the right if hitting any tile
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.TILE_SIZE;
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum3 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                    entity.downCollisionOn = true;
                    entity.leftCollisionOn = true;
                } else if (!gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && gp.tileM.tile[tileNum3].collision)
                {
                        entity.downCollisionOn = true;
                } else if (gp.tileM.tile[tileNum1].collision
                    && gp.tileM.tile[tileNum2].collision
                    && !gp.tileM.tile[tileNum3].collision)
                {
                        entity.leftCollisionOn = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.objArr[1].length; i++) {
            if (gp.objArr[gp.currentMap][i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gp.objArr[gp.currentMap][i].solidArea.x = gp.objArr[gp.currentMap][i].worldX + gp.objArr[gp.currentMap][i].solidArea.x;
                gp.objArr[gp.currentMap][i].solidArea.y = gp.objArr[gp.currentMap][i].worldY + gp.objArr[gp.currentMap][i].solidArea.y;

                switch (entity.action) {
                    case "moveUp":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) entity.upCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveDown":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) entity.downCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveLeft":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) entity.leftCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveRight":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) entity.rightCollisionOn = true;
                            if (player) index = i;
                        }
                        break;
                    case "moveUpRight":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) {
                                entity.rightCollisionOn = true;
                                entity.upCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                    case "moveDownRight":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) {
                                entity.rightCollisionOn = true;
                                entity.downCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                    case "moveUpLeft":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) {
                                entity.leftCollisionOn = true;
                                entity.upCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                        break;
                    case "moveDownLeft":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.objArr[gp.currentMap][i].solidArea)) {
                            if (gp.objArr[gp.currentMap][i].collision) {
                                entity.downCollisionOn = true;
                                entity.leftCollisionOn = true;
                            }
                            if (player) index = i;
                        }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objArr[gp.currentMap][i].solidArea.x = gp.objArr[gp.currentMap][i].solidAreaDefaultX;
                gp.objArr[gp.currentMap][i].solidArea.y = gp.objArr[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkGate(Entity entity, boolean player) {
        for (int i = 0; i < gp.gateArr[1].length; i++) {
            if (gp.gateArr[gp.currentMap][i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the gate's solid area position
                gp.gateArr[gp.currentMap][i].solidArea.x = gp.gateArr[gp.currentMap][i].worldX + gp.gateArr[gp.currentMap][i].solidArea.x;
                gp.gateArr[gp.currentMap][i].solidArea.y = gp.gateArr[gp.currentMap][i].worldY + gp.gateArr[gp.currentMap][i].solidArea.y;

                switch (entity.action) {
                    case "moveUp":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) entity.upCollisionOn = true;
                        }
                        break;
                    case "moveDown":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) entity.downCollisionOn = true;
                        }
                        break;
                    case "moveLeft":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) entity.leftCollisionOn = true;
                        }
                        break;
                    case "moveRight":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) entity.rightCollisionOn = true;
                        }
                        break;
                    case "moveUpRight":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) {
                                entity.rightCollisionOn = true;
                                entity.upCollisionOn = true;
                            }
                        }
                        break;
                    case "moveDownRight":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) {
                                entity.rightCollisionOn = true;
                                entity.downCollisionOn = true;
                            }
                        }
                        break;
                    case "moveUpLeft":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) {
                                entity.leftCollisionOn = true;
                                entity.upCollisionOn = true;
                            }
                        }
                        break;
                    case "moveDownLeft":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.gateArr[gp.currentMap][i].solidArea)) {
                            if (gp.gateArr[gp.currentMap][i].collision || !player) {
                                entity.downCollisionOn = true;
                                entity.leftCollisionOn = true;
                            }
                        }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.gateArr[gp.currentMap][i].solidArea.x = gp.gateArr[gp.currentMap][i].solidAreaDefaultX;
                gp.gateArr[gp.currentMap][i].solidArea.y = gp.gateArr[gp.currentMap][i].solidAreaDefaultY;
            }
        }
    }

    public int checkEntityCollision(Entity entity, Entity[][] target){
        int index = 999;

        String action = entity.action;
        if(entity.knockBack){
            action = entity.knockBackDirection;
        }

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;

                switch (action) {
                    case "moveUp":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.upCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveDown":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.downCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveLeft":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.leftCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveRight":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                            entity.rightCollisionOn = true;
                            index = i;
                        }
                        break;
                    case "moveUpRight":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.rightCollisionOn = true;
                                entity.upCollisionOn = true;
                                index = i;
                        }
                        break;
                    case "moveDownRight":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.rightCollisionOn = true;
                                entity.downCollisionOn = true;
                                index = i;
                        }
                        break;
                    case "moveUpLeft":
                        entity.solidArea.y -= entity.speed;
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.leftCollisionOn = true;
                                entity.upCollisionOn = true;
                                index = i;
                        }
                        break;
                    case "moveDownLeft", "idleRight", "idleLeft":
                        entity.solidArea.y += entity.speed;
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                                entity.downCollisionOn = true;
                                entity.leftCollisionOn = true;
                                index = i;
                        }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
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
//        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
//        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        gp.player.hitboxArea.x = gp.player.worldX + gp.player.hitboxArea.x;
        gp.player.hitboxArea.y = gp.player.worldY + gp.player.hitboxArea.y;

        switch (entity.action) {
            case "moveUp":
                entity.solidArea.y -= entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.upCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveDown":
                entity.solidArea.y += entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.downCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveLeft":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.leftCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveRight":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.rightCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveUpRight":
                entity.solidArea.y -= entity.speed;
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.rightCollisionOn = true;
                    entity.upCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveDownRight":
                entity.solidArea.y += entity.speed;
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.rightCollisionOn = true;
                    entity.downCollisionOn = true;
                    contactPlayer = true;
                }
                break;
            case "moveUpLeft":
                entity.solidArea.y -= entity.speed;
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.leftCollisionOn = true;
                    entity.upCollisionOn = true;
                    contactPlayer = true;

                }
                break;
            case "moveDownLeft":
                entity.solidArea.y += entity.speed;
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gp.player.hitboxArea)) {
                    entity.downCollisionOn = true;
                    entity.leftCollisionOn = true;
                    contactPlayer = true;
                }
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.hitboxArea.x = gp.player.hitboxAreaDefaultX;
        gp.player.hitboxArea.y = gp.player.hitboxAreaDefaultY;
        return contactPlayer;
    }
}