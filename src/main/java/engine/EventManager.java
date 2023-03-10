package engine;

import graphics.State;

import static engine.GameEngine.*;
import static tile.TileManager.maxWorldCol;
import static tile.TileManager.maxWorldRaw;

public final class EventManager {

    EventRect[][] eventRect;


    public EventManager() {
        eventRect = new EventRect[maxWorldCol][maxWorldRaw];
        int col = 0;
        int row = 0;
        while (col < maxWorldCol && row < maxWorldRaw){

            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;

            if (col == maxWorldCol){
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent(){
        damagePit(24, 16);
        healingPool(23,12);
        teleport(20,12);
    }

    private boolean hit(int col, int row){
        canToucheActivationAfterOneBlock(col, row);
        if (!eventRect[col][row].canTouch){return false;}

        boolean hit = false;
        player.solidArea.x += player.worldX;
        player.solidArea.y += player.worldY;
        eventRect[col][row].x += col * tileSize;
        eventRect[col][row].y += row * tileSize;

        if (player.solidArea.intersects(eventRect[col][row])){
//            if (player.direction.equals(direction)){
                hit = true;

                eventRect[col][row].previousEventX = player.worldX;
                eventRect[col][row].previousEventY = player.worldY;
//            }
        }

        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    private void canToucheActivationAfterOneBlock(int col, int row) {
        int xDistance = Math.abs(player.worldX - eventRect[col][row].previousEventX);
        int yDistance = Math.abs(player.worldY - eventRect[col][row].previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > tileSize){
            eventRect[col][row].canTouch = true;
        }
    }

    private void damagePit(int col, int row) {
        if (hit(col,row)){
            gameState = State.DIALOGUE;
            ui.currentDialogue = "Πάτησες σκατά!";
            player.life -= 0.5;

            eventRect[col][row].canTouch = false;
        }
    }

    private void healingPool(int col, int row){
        if (hit(col,row)){
            if (GameEngine.keyM.enterPressed){
                gameState = State.DIALOGUE;
                ui.currentDialogue = "Ξέπλυνες καλα τα πόδια σου,/nη ζωή σου γέμισε";
                player.life = player.maxLife;
                eventRect[col][row].canTouch = false;
            }
            keyM.enterPressed = false;
        }
    }

    private void teleport(int col, int row){
        if (hit(col,row)){
            gameState = State.DIALOGUE;
            ui.currentDialogue = "Ιντά'γινε μωρέ επαέ?";
            player.worldX = tileSize * 37;
        }
    }
}
