package object;

import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends Entity {

    public Chest() {
        name = "CHEST";
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
