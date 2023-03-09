package object;

import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public final class Heart extends SuperObject{

    public BufferedImage image2;
    public BufferedImage image3;


    public Heart() {
        name = "HEART";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_blank.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
