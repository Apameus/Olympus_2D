package object;

import entity.Entity;
import jdk.jshell.execution.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static engine.GameEngine.tileSize;

public final class Heart extends Entity {

    public BufferedImage image2;
    public BufferedImage image3;


    public Heart() {
        name = "HEART";
            image = getImage("/objects/heart_blank.png", tileSize, tileSize);
            image2 = getImage("/objects/heart_half.png", tileSize, tileSize);
            image3 = getImage("/objects/heart_full.png", tileSize, tileSize);

    }
}
