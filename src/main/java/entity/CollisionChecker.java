package entity;

import static engine.GameEngine.*;

public class CollisionChecker {

    // in dire need for refactor !!!

    public int index;

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftRow = entityLeftWorldX / tileSize;
        int entityRightRow = entityRightWorldX / tileSize;
        int entityTopCol = entityTopWorldY / tileSize;
        int entityBottomCol = entityBottomWorldY / tileSize;

        int tileNum1, tileNum2;


        switch (entity.direction) {
            case UP -> {
                entityTopCol = (entityTopWorldY - entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityLeftRow][entityTopCol];
                tileNum2 = tileManager.mapTileNumber[entityRightRow][entityTopCol];
                setEntityCollision(entity, tileNum1, tileNum2);
            }
            case DOWN -> {
                entityBottomCol = (entityBottomWorldY + entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityLeftRow][entityBottomCol];
                tileNum2 = tileManager.mapTileNumber[entityRightRow][entityBottomCol];
                setEntityCollision(entity, tileNum1, tileNum2);
            }
            case LEFT -> {
                entityLeftRow = (entityLeftWorldX - entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityLeftRow][entityTopCol];
                tileNum2 = tileManager.mapTileNumber[entityLeftRow][entityBottomCol];
                setEntityCollision(entity, tileNum1, tileNum2);
            }
            case RIGHT -> {
                entityRightRow = (entityRightWorldX + entity.speed) / tileSize;
                tileNum1 = tileManager.mapTileNumber[entityRightRow][entityTopCol];
                tileNum2 = tileManager.mapTileNumber[entityRightRow][entityBottomCol];
                setEntityCollision(entity, tileNum1, tileNum2);
            }
        }
    }

    public int checkObject(Entity entity) {

        index = 999;

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                // GET ENTITY SOLID AREA POSITION
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // GET OBJECT SOLID AREA POSITION
                obj[i].solidArea.x = obj[i].worldX + obj[i].solidArea.x;
                obj[i].solidArea.y = obj[i].worldY + obj[i].solidArea.y;
                switch (entity.direction) {
                    case UP -> {
                        entity.solidArea.y -= entity.speed;
                        index = setEntityCollision(entity, i);
                    }
                    case DOWN -> {
                        entity.solidArea.y += entity.speed;
                        index = setEntityCollision(entity, i);
                    }
                    case LEFT -> {
                        entity.solidArea.x -= entity.speed;
                        index = setEntityCollision(entity, i);
                    }
                    case RIGHT -> {
                        entity.solidArea.x += entity.speed;
                        index = setEntityCollision(entity, i);
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                obj[i].solidArea.x = obj[i].solidAreaDefaultX;
                obj[i].solidArea.y = obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }


    // NPC
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // GET ENTITY SOLID AREA POSITION
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                // GET OBJECT SOLID AREA POSITION
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
                switch (entity.direction) {
                    case UP -> {
                        entity.solidArea.y -= entity.speed;
                        index = setEntityCollision(entity, target, i, index);
                    }
                    case DOWN -> {
                        entity.solidArea.y += entity.speed;
                        index = setEntityCollision(entity, target, i, index);
                    }
                    case LEFT -> {
                        entity.solidArea.x -= entity.speed;
                        index = setEntityCollision(entity,target, i, index);
                    }
                    case RIGHT -> {
                        entity.solidArea.x += entity.speed;
                        index = setEntityCollision(entity, target, i, index);
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkPlayer(Entity entity){
        // GET ENTITY SOLID AREA POSITION
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // GET OBJECT SOLID AREA POSITION
        player.solidArea.x = player.worldX + player.solidArea.x;
        player.solidArea.y = player.worldY + player.solidArea.y;
        switch (entity.direction) {
            case UP -> {
                entity.solidArea.y -= entity.speed;
                setEntityToPlayerCollision(entity);
            }
            case DOWN -> {
                entity.solidArea.y += entity.speed;
                setEntityToPlayerCollision(entity);
            }
            case LEFT -> {
                entity.solidArea.x -= entity.speed;
                setEntityToPlayerCollision(entity);
            }
            case RIGHT -> {
                entity.solidArea.x += entity.speed;
                setEntityToPlayerCollision(entity);
            }
        }
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
    }

    ////
    private static void setEntityToPlayerCollision(Entity entity) {
        if (entity.solidArea.intersects(player.solidArea)) {
            entity.collisionOn = true;
        }
    }
    ////

    ////
    private int setEntityCollision(Entity entity, int i) {
        // sets collision
        if (entity.solidArea.intersects(obj[i].solidArea)) {
            if (obj[i].collision) {
                entity.collisionOn = true;
            }
        }
        // returns the index
        return i;
    }
    ////

    ////
    private <V extends Entity> int setEntityCollision(Entity entity, V[] target, int i, int index) {
        // sets collision
        if (entity.solidArea.intersects(target[i].solidArea)) {
            entity.collisionOn = true;
            index = i;
        }
        // returns index
        return index;
    }
    ////

    ////
    private void setEntityCollision(Entity entity, int tileNum1, int tileNum2) {
        if (tileManager.tile[tileNum1].collision || tileManager.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }
    ////

    public int getIndex() {
        return index;
    }
}
