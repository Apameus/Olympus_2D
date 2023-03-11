package object;

import entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends Entity {

    public Boots() {
        name = "BOOTS";
        try {
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        collision = true;
    }
}
