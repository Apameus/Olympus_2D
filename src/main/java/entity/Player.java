package entity;

import graphics.State;
import graphics.Control;
import engine.GameEngine;
import desktop.KeyManager;
import graphics.Input;

import java.awt.*;
import java.awt.image.BufferedImage;

import static entity.Direction.*;
import static engine.GameEngine.*;
import static graphics.Control.CONTROLS;

public class Player extends Entity{
    GameEngine gp;
    KeyManager keyM;
    public final int screenX;
    public final int screenY;
//    public int playerKeys = 0;
    public boolean invisible = false;
    public int invisibleTimer = 0;

    public Player(GameEngine gp, KeyManager keyM) {
        this.gp = gp;
        this.keyM = keyM;

        type = Type.PLAYER;

        screenX = screenWidth / 2 - (tileSize / 2);
        screenY = screenHeight / 2 - (tileSize / 2);

        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        setPlayerImage();
    }

    public void setDefaultValues() {
        worldX = tileSize * 23;
        worldY = tileSize * 21;
        speed = 4;
        direction = DOWN;

        // PLAYER STATUS
        maxLife = 3;
        life = maxLife;
    }

    public void setPlayerImage(){
            up1 = getImage("/player/boy_up_1.png");
            up2 = getImage("/player/boy_up_2.png");
            down1 = getImage("/player/boy_down_1.png");
            down2 = getImage("/player/boy_down_2.png");
            left1 = getImage("/player/boy_left_1.png");
            left2 = getImage("/player/boy_left_2.png");
            right1 = getImage("/player/boy_right_1.png");
            right2 = getImage("/player/boy_right_2.png");
    }


    public void update(Input input){

        for (Control control : CONTROLS) {
            if (input.isActive(control)) {

                direction = control.direction();

                // CHECK TILE COLLISION
                collisionOn = false;
                colChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = colChecker.checkEntity(this, obj);
                pickUpObjects(objIndex);

                // CHECK NPC COLLISION
                //int npcIndex = colChecker.getIndex();
                int npcIndex = colChecker.checkEntity(this, npc);  // PROBLEM HERE !!!!!!
                interactNPC(npcIndex);

                // CHECK MONSTER COLLISION
                int monsterIndex = colChecker.checkEntity(this,monster);
                contactMonster(monsterIndex);

                // CHECK EVENT
                eventManager.checkEvent();

                //
                KeyManager.enterPressed = false;
                //

                // IF COLLISION is FALSE, PLAYER CAN MOVE
                if (!collisionOn) {
                    control.direction().move(this);
                }

                // MOVING MOTION COUNTER
                spriteTimer++;
                if (spriteTimer > 12) {
                    if (spriteNumber == 1) {
                        spriteNumber = 2;
                    } else if (spriteNumber == 2) {
                        spriteNumber = 1;
                    }
                    spriteTimer = 0;
                }
            }
        }
        // INVISIBLE COUNTER
        if (invisible) {
            invisibleTimer++;
            if (invisibleTimer > 60) {
                invisible = false;
                invisibleTimer = 0;
            }
        }
    }

    private void contactMonster(int monsterIndex) {

        if (monsterIndex != 999){
            receiveDamage(0.5);


        }
    }

    public void receiveDamage(double damage) {
        if (!invisible){
            life -= damage;
            invisible = true;
        }
    }

    private void interactNPC(int npcIndex) {
        if (npcIndex != 999){
           if (KeyManager.enterPressed) {
               gameState = State.DIALOGUE;
               npc[npcIndex].speak();
           }
        }
    }

    // MOVING MOTION
    public void render(Graphics2D g2){

        BufferedImage image = null;
        switch (direction){
            case UP -> {
                if (spriteNumber == 1){ image = up1; }
                else if (spriteNumber == 2) { image = up2; }
            }
            case DOWN -> {
                if (spriteNumber == 1){ image = down1; }
                else if (spriteNumber == 2) { image = down2; }
            }
            case LEFT -> {
                if (spriteNumber == 1){ image = left1; }
                else if (spriteNumber == 2) { image = left2; }
            }
            case RIGHT -> {
                if (spriteNumber == 1){ image = right1; }
                else if (spriteNumber == 2) { image = right2; }
            }
        }
        // INVISIBLE VISUAL EFFECT
        if (invisible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
        // RESET TO NORMAL
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void pickUpObjects(int i){
    }
}
