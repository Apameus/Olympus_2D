package entity;

import UI.UI;
import engine.GameEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public final class NPC_OldMan extends Entity{

    public NPC_OldMan() {
        name = "OLD_MAN";
        type = Type.NPC;
        direction = Direction.DOWN;
        speed = 1;
        getPlayerImage();
        setDialogues();
    }


    public void getPlayerImage(){
        down1 = getImage("/npc/oldman_down_1.png");
        down2 = getImage("/npc/oldman_down_2.png");
        up1 = getImage("/npc/oldman_up_1.png");
        up2 = getImage("/npc/oldman_up_2.png");
        left1 = getImage("/npc/oldman_left_1.png");
        left2 = getImage("/npc/oldman_left_2.png");
        right1 = getImage("/npc/oldman_right_1.png");
        right2 = getImage("/npc/oldman_right_2.png");
    }


    @Override
    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 60){

            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25){
                direction = Direction.UP;
            } else if (i <= 50) {
                direction = Direction.DOWN;
            } else if (i <=75) {
                direction = Direction.LEFT;
            } else {
                direction = Direction.RIGHT;
            }

            actionLockCounter = 0;
        }
    }

    private void setDialogues(){
        dialogues[0] = "Γειά σου σύντεκνε.";
        dialogues[1] = "Ήρθες επαέ για να γυρέψεις μαγλατά?";
        dialogues[2] = "Εγώ στην ηλικία σου έπνιγα αλιγάτορες,/nέχε χάρη που γέρασα.";
        dialogues[3] = "Άμε να βρείς κάνα στο μπόι σου.";
    }
    @Override
    protected void speak() {
      super.speak();
    }
}
