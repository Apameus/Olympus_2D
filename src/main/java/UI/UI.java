package UI;

import graphics.State;
import graphics.MenuOption;
import object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import static engine.GameEngine.*;

public class UI{
    //
    Font font_arial_40, font_arial_80B;
//    Font maruMonica = getFont("font/src/main/resources/font/x12y16pxMaruMonica.ttf");
//
//    Font purisa = getFont("font/Purisa Bold.ttf");

    //
    int messageCounter = 0;
    public String message = "";
    public boolean messageOn = false;
    //
    public boolean gameFinished = false;
    //
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    //
    public static String currentDialogue = "";
    Graphics2D g2;
    //
    public static MenuOption option = MenuOption.NEW_GAME;
    //
    BufferedImage heart_blank, heart_half, heart_full;


    public UI() {
//        option = option.chooseOption();
        font_arial_40 = new Font("Ink Free", Font.PLAIN, 40);
        font_arial_80B = new Font("Arial", Font.BOLD, 80);

        Heart heart = new Heart();
        heart_blank = heart.image;
        heart_half = ((Heart) heart).image2;
        heart_full = ((Heart) heart).image3;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;

    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(font_arial_40);
        g2.setColor(Color.white);

        // title state
        if (gameState.equals(State.TITLE)){
            drawTitleScreen();
        }

        // play state
        if (gameState.equals(State.PLAY)){
            drawPlayerLife();
        }

        // pause state
        else if (gameState.equals(State.PAUSE)) {
            drawPlayerLife();

            String text = "PAUSED";
            int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            int x = screenWidth / 2 - length / 2;
            int y = screenHeight /2;
            g2.drawString(text, x, y);
        }

        // dialogue state
        if (gameState.equals(State.DIALOGUE)){
            drawPlayerLife();
            drawDialogueScreen();
        }

    }

    private void drawTitleScreen() {

        g2.setColor(new Color(40,30,30));
        g2.fillRect(0,0, screenWidth, screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
        String text = "Action Game 2D";
        int x = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth() /10;
        int y = tileSize * 3;

        // SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        // MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // CHARACTER IMAGE
        x = screenWidth / 2 - tileSize ;
        y = tileSize * 5;
        g2.drawImage(player.down1, x, y, tileSize*2, tileSize*2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        option = option.chooseOption();

        text = "NEW GAME";
        x = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        y += tileSize * 3.5;
        g2.drawString(text, x, y);
        if (option == MenuOption.NEW_GAME){
            g2.drawString(">", x - tileSize, y);
        }

        text = "LOAD GAME";
        x = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        y += tileSize * 1.4;
        g2.drawString(text, x, y);
        if (option == MenuOption.LOAD_GAME){
            g2.drawString(">", x - tileSize, y);
        }

        text = "EXIT";
        x = (int) (g2.getFontMetrics().getStringBounds(text,g2).getWidth() * 3);
        y += tileSize * 1.4;
        g2.drawString(text, x - tileSize, y);
        if (option == MenuOption.EXIT){
            g2.drawString(">", x - tileSize*2, y);
        }
    }

    private void drawDialogueScreen() {
        // WINDOW
        int x = tileSize * 2;
        int y = tileSize / 2;
        int width = screenWidth - tileSize * 4;
        int height = tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += tileSize;
        y += tileSize;

        // super stupid thing
        for(String line : currentDialogue.split("/n")){
            g2.drawString(line, x, y);
            y += 40;
        }
        //
    }

    private void drawSubWindow(int x, int y, int width, int height) {

        Color color = new Color(0,0,0,210);
        g2.setColor(color);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(new Color(255,255,255));
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height -10, 25, 25);
    }


    private void drawPlayerLife() {
        int x = tileSize / 2;
        int y = tileSize / 2;
        double i = 0;

        // draw max life
        while (i < player.maxLife){
            g2.drawImage(heart_blank, x, y, null);
            i += 1;
            x += tileSize;
        }

        //
        x = tileSize / 2;
        i = 0;

        // draw current life
        while (i < player.life){
            g2.drawImage(heart_half, x, y, null);
            i += 0.5;
            if (i < player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i += 0.5;
            x += tileSize;
        }
    }


    // DOESN'T WORK..
//    private Font getFont(String path)  {
//        try {
//            InputStream is = getClass().getResourceAsStream(path);
//            return Font.createFont(Font.TRUETYPE_FONT, is);
//        } catch (FontFormatException e) {
//            throw new IllegalArgumentException("Font" + e);
//        } catch (IOException e) {
//            throw new IllegalArgumentException("IO" + e);
//        }
//    }
}
