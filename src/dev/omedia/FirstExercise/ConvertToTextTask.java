package dev.omedia.FirstExercise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class ConvertToTextTask implements Callable<String> {
    private static final int PIXEL_AVERAGE = 128;
    private final BufferedImage bufferedImage;
    private final int readFromLine;

    ConvertToTextTask(BufferedImage bufferedImage, int readFromLine) {
        this.bufferedImage = bufferedImage;
        this.readFromLine = readFromLine;
    }

    @Override
    public String call() {
        String line = "";
        int imageWidth = bufferedImage.getWidth();
        for (int x = 0; x < imageWidth; x++) {
            Color oldColor = new Color(bufferedImage.getRGB(x, readFromLine));
            if ((oldColor.getBlue() + oldColor.getGreen() + oldColor.getRed()) >= 3 * PIXEL_AVERAGE) {
                line = line.concat(".");
            } else {
                line = line.concat(";");
            }
        }
        line = line.concat("\n");
        return line;
    }


}
