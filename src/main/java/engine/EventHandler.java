package engine;

import UI.UI;
import entity.Direction;
import graphics.State;

import java.awt.*;

import static engine.GameEngine.*;

public final class EventHandler {

    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;


    public EventHandler() {
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public void checkEvent(){
        if (hit(24,16,Direction.RIGHT)){damagePit(State.DIALOGUE);}
        if (hit(23,12,Direction.UP)){healingPool(State.DIALOGUE);}
        if (hit(20,12,Direction.LEFT)){teleport();}
    }

    private boolean hit(int eventCol, int eventRow, Direction direction){
        boolean hit = false;
        player.solidArea.x += player.worldX;
        player.solidArea.y += player.worldY;
        eventRect.x += eventCol * tileSize;
        eventRect.y += eventRow * tileSize;

        if (player.solidArea.intersects(eventRect)){
            if (player.direction.equals(direction)){
                hit = true;
            }
        }

        player.solidArea.x = player.solidAreaDefaultX;
        player.solidArea.y = player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

    private void damagePit(State state) {
        gameState = state;
        ui.currentDialogue = "Πάτησες σκατά!";
        player.life -= 0.5;
    }

    private void healingPool(State state){
        if (GameEngine.keyH.enterPressed == true){
            gameState = state;
            ui.currentDialogue = "Ξέπλυνες καλα τα πόδια σου,/nη ζωή σου γέμισε";
            player.life = player.maxLife;
        }
        keyH.enterPressed = false;
    }

    private void teleport(){
        gameState = State.DIALOGUE;
        ui.currentDialogue = "Ιντά'γινε μωρέ επαέ?";
        player.worldX = tileSize * 37;
    }
}
