package entity;

import UI.UI;
import engine.GameEngine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static engine.GameEngine.colChecker;
import static engine.GameEngine.obj;

public class Entity {
    public BufferedImage image;
    public boolean collision = false;
    public boolean attacking = false;
    String[] dialogues = new String[20];
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    // STATE.
    int dialogueIndex = 0;
    public int worldX, worldY;
    public int spriteNumber = 1;
    public boolean invisible = false;
    public boolean collisionOn = false;
    public Direction direction = Direction.DOWN;

    // TIMER
    public int spriteTimer = 0;
    public int attackTimer = 0;
    public int invisibleTimer = 0;
    //
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int actionLockCounter;
    //

    // CHARACTER STATS
    public String name;
    public Type type;
    public int speed;
    public double life;
    public double maxLife;



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

            // INVISIBLE VISUAL EFFECT
            if (invisible) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
            }
            g2.drawImage(image, screenX, screenY, GameEngine.tileSize, GameEngine.tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void update(){
        setAction();
        collisionOn = false;
        colChecker.checkTile(this);
        colChecker.checkEntity(this, obj);
        colChecker.checkEntity(this, GameEngine.npc);
        colChecker.checkEntity(this, GameEngine.monster);
        boolean contactPlayer = colChecker.checkPlayer(this);

        //
        if (this.type.equals(Type.MONSTER) && contactPlayer){
            GameEngine.player.receiveDamage(0.5);
        }

        // IF COLLISION is FALSE, ENTITY CAN MOVE
        if (!collisionOn) {
            direction.move(this);
        }

        if (invisible) {
            invisibleTimer++;
            if (invisibleTimer > 40) {
                invisible = false;
                invisibleTimer = 0;
            }
        }
    }
    protected void speak() {
        if (dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        UI.currentDialogue = dialogues[dialogueIndex++];

        // TURNS IN YOUR DIRECTION
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

    protected BufferedImage getImage(String name, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(name)));
            return scaleImage(image, width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }


    protected void receiveDamage(double damage) {
        this.life -= damage;
        this.invisible = true;
    }
}
