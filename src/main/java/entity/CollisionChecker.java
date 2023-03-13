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

    // NPC
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // GET ENTITY SOLID AREA POSITION
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                // GET OBJECT SOLID AREA POSITION
                target[i].solidArea.x += target[i].worldX;
                target[i].solidArea.y += target[i].worldY;

                switch (entity.direction) {
                    case UP -> entity.solidArea.y -= entity.speed;
                    case DOWN -> entity.solidArea.y += entity.speed;
                    case LEFT -> entity.solidArea.x -= entity.speed;
                    case RIGHT -> entity.solidArea.x += entity.speed;
                }
                index = setEntityCollision(entity, target, i, index);

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity){
        boolean contactPlayer = false;
        // GET ENTITY SOLID AREA POSITION
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        // GET OBJECT SOLID AREA POSITION
        player.solidArea.x = player.worldX + player.solidArea.x;
        player.solidArea.y = player.worldY + player.solidArea.y;
        switch (entity.direction) {
            case UP -> entity.solidArea.y -= entity.speed;
            case DOWN -> entity.solidArea.y += entity.speed;
            case LEFT -> entity.solidArea.x -= entity.speed;
            case RIGHT -> entity.solidArea.x += entity.speed;
        }
        contactPlayer = setEntityToPlayerCollision(entity, contactPlayer);
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;

         return contactPlayer;
    }

    ////
    private static boolean setEntityToPlayerCollision(Entity entity, boolean contactPlayer) {
        if (entity.solidArea.intersects(player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }
        return contactPlayer;
    }
    ////

    ////
    private int setEntityCollision(Entity entity, int i) {
        // sets collision
        if (entity.solidArea.intersects(obj[i].solidArea)) {
            if (obj[i].collision && obj[i] != entity) {
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
            if (target[i] != entity){
                entity.collisionOn = true;
                index = i;
            }
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

