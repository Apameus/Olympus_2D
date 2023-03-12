package entity;

import UI.UI;
import engine.GameEngine;
import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static engine.GameEngine.colChecker;
import static engine.GameEngine.obj;

public class Entity {
    public int worldX, worldY;
    public int speed;
    public Direction direction = Direction.DOWN;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    //
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter;
    //
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    //
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    // CHARACTER STATS
    public double maxLife;
    public double life;


    public void render(Graphics2D g2){
        BufferedImage image = null;

        int screenX = worldX - GameEngine.player.worldX + GameEngine.player.screenX;
        int screenY = worldY - GameEngine.player.worldY + GameEngine.player.screenY;

        // Magical performer increase
        if (    worldX + GameEngine.tileSize > GameEngine.player.worldX - GameEngine.player.screenX &&
                worldX - GameEngine.tileSize < GameEngine.player.worldX + GameEngine.player.screenX &&
                worldY + GameEngine.tileSize > GameEngine.player.worldY - GameEngine.player.screenY &&
                worldY - GameEngine.tileSize < GameEngine.player.worldY + GameEngine.player.screenY){

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

            g2.drawImage(image, screenX, screenY, GameEngine.tileSize, GameEngine.tileSize, null);

        }
    }

    public void update(){
        setAction();
        collisionOn = false;
        colChecker.checkTile(this);
        colChecker.checkEntity(this, obj);
        colChecker.checkEntity(this, GameEngine.npc);
        colChecker.checkEntity(this, GameEngine.monster);
        colChecker.checkPlayer(this);

        // IF COLLISION is FALSE, ENTITY CAN MOVE
        if (!collisionOn) {
            direction.move(this);
        }
    }
    protected void speak() {
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        UI.currentDialogue = dialogues[dialogueIndex++];

        switch (GameEngine.player.direction){
            case UP -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.UP;
            case LEFT -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.LEFT;
        }
    }

    public void setAction(){}

    protected BufferedImage getImage(String name) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
