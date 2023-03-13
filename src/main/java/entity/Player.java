package entity;

import graphics.State;
import graphics.Control;
import engine.GameEngine;
import desktop.KeyManager;
import graphics.Input;

import java.awt.*;
import java.awt.image.BufferedImage;

import static desktop.KeyManager.enterPressed;
import static entity.Direction.*;
import static engine.GameEngine.*;
import static graphics.Control.CONTROLS;

public class Player extends Entity{
    GameEngine gp;
    KeyManager keyM;
    public final int screenX;
    public final int screenY;
//    public int playerKeys = 0;


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
        speed = 4;
        direction = DOWN;
        worldX = tileSize * 23;
        worldY = tileSize * 21;
        attackArea.width = 32;
        attackArea.height = 32;

        // PLAYER STATUS
        maxLife = 3;
        life = maxLife;
    }

    public void setPlayerImage(){
        up1 = getImage("/player/boy_up_1.png", tileSize, tileSize);
        up2 = getImage("/player/boy_up_2.png", tileSize, tileSize);
        down1 = getImage("/player/boy_down_1.png", tileSize, tileSize);
        down2 = getImage("/player/boy_down_2.png", tileSize, tileSize);
        left1 = getImage("/player/boy_left_1.png", tileSize, tileSize);
        left2 = getImage("/player/boy_left_2.png", tileSize, tileSize);
        right1 = getImage("/player/boy_right_1.png", tileSize, tileSize);
        right2 = getImage("/player/boy_right_2.png", tileSize, tileSize);


        // for some reason the resolution here is 48 x 96 (tileSize * 2 x tileSize * 3) !!!!
        attackUp1 = getImage("/player/attack/boy_attack_up_1.png", tileSize, tileSize * 2);
        attackUp2 = getImage("/player/attack/boy_attack_up_2.png", tileSize, tileSize * 2);
        attackDown1 = getImage("/player/attack/boy_attack_down_1.png", tileSize, tileSize * 2);
        attackDown2 = getImage("/player/attack/boy_attack_down_2.png", tileSize, tileSize * 2);
        attackLeft1 = getImage("/player/attack/boy_attack_left_1.png", tileSize * 2, tileSize);
        attackLeft2 = getImage("/player/attack/boy_attack_left_2.png", tileSize * 2, tileSize);
        attackRight1 = getImage("/player/attack/boy_attack_right_1.png", tileSize * 2, tileSize);
        attackRight2 = getImage("/player/attack/boy_attack_right_2.png", tileSize * 2, tileSize);

    }





    public void update(Input input){
        if (attacking){
            attack();
        }

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
                enterPressed = false;
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

    private void attack() {

        // PROBLEM !! sprite counter increases also while moving.

        attackTimer++;

        if (attackTimer <= 5){
            spriteNumber = 1;
        }
        else if (attackTimer < 25) {
            spriteNumber = 2;

            // Save current data.
            int currentWorldX = worldX;
            int currentWoldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player WorldX&Y for the attackArea.
            switch (direction){
                case UP -> worldY -= attackArea.height;
                case DOWN -> worldY += attackArea.height;
                case LEFT -> worldX -= attackArea.width;
                case RIGHT -> worldX += attackArea.width;
            }

            // attackArea becomes solidArea
            solidArea.height = attackArea.height;
            solidArea.width = attackArea.width;

            // check monsterCollision with the updated worldX & worldY, solidArea.
            int monsterIndex = colChecker.checkEntity(this, monster);
            dealDamageToMonster(monsterIndex);

            // Restore Data.
            worldX = currentWorldX;
            worldY = currentWoldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        else{
            spriteNumber = 1;
            attackTimer = 0;
            attacking = false;
        }
    }

    private void dealDamageToMonster(int monsterIndex) {
        if (monsterIndex != 999){
            if (!monster[monsterIndex].invisible){
                monster[monsterIndex].receiveDamage(1);
                if (monster[monsterIndex].life <= 0){
                    monster[monsterIndex] = null;
                }
            }
        }
    }

    private void contactMonster(int monsterIndex) {

        if (monsterIndex != 999){
            receiveDamage(0.5);


        }
    }

    @Override
    public void receiveDamage(double damage) {
        if (!invisible){
            life -= damage;
            invisible = true;
        }
    }

    private void interactNPC(int npcIndex) {
        if (npcIndex != 999){
           if (enterPressed) {
               gameState = State.DIALOGUE;
               npc[npcIndex].speak();
           }
        }
    }

    // MOVING MOTION
    @Override
    public void render(Graphics2D g2){

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction){
            case UP -> {
                if (attacking){
                    tempScreenY -= tileSize;
                    if (spriteNumber == 1) {image = attackUp1;}
                    else {image = attackUp2;}
                }
                else {
                    if (spriteNumber == 1) {image = up1;}
                    else {image = up2;}
                }
            }
            case DOWN -> {
                if (attacking){
                    if (spriteNumber == 1) {image = attackDown1;}
                    else {image = attackDown2;}
                }
                else {
                    if (spriteNumber == 1) {image = down1;}
                    else {image = down2;}
                }
            }
            case LEFT -> {
                if (attacking){
                    tempScreenX -= tileSize;
                    if (spriteNumber == 1) {image = attackLeft1;}
                    else {image = attackLeft2;}
                }
                else {
                    if (spriteNumber == 1) {image = left1;}
                    else {image = left2;}
                }
            }
            case RIGHT -> {
                if (attacking){
                    if (spriteNumber == 1) {image = attackRight1;}
                    else {image = attackRight2;}
                }
                else {
                    if (spriteNumber == 1) {image = right1;}
                    else {image = right2;}
                }
            }
        }
        // INVISIBLE VISUAL EFFECT
        if (invisible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY,null);
        // RESET TO NORMAL
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


    public void pickUpObjects(int i){
    }
}
