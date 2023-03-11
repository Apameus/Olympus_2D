package object;

import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends Entity {

    public Key() {
        name = "KEY";
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
