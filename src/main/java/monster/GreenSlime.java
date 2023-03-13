package monster;

import engine.GameEngine;
import entity.Direction;
import entity.Entity;
import entity.Type;

import java.util.Random;

public final class GreenSlime extends Entity {

    public GreenSlime() {
        name = "GREEN SLIME";
        type = Type.MONSTER;
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setEntityImage();

    }

    public void setEntityImage(){
        up1 = getImage("/monster/greenslime_down_1.png");
        up2 = getImage("/monster/greenslime_down_2.png");
        down1 = getImage("/monster/greenslime_down_1.png");
        down2 = getImage("/monster/greenslime_down_2.png");
        left1 = getImage("/monster/greenslime_down_1.png");
        left2 = getImage("/monster/greenslime_down_2.png");
        right1 = getImage("/monster/greenslime_down_1.png");
        right2 = getImage("/monster/greenslime_down_2.png");
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
}
