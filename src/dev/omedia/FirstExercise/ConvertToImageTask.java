package dev.omedia.FirstExercise;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class ConvertToImageTask implements Callable<int[]> {
    private static final int PIXEL_AVERAGE = 128;
    private final BufferedImage bufferedImage;
    private final int readFromLine;

    ConvertToImageTask(BufferedImage bufferedImage,  int readFromLine) {
        this.bufferedImage = bufferedImage;
        this.readFromLine = readFromLine;
    }

    @Override
    public int[] call() {
        int imageWidth = bufferedImage.getWidth();
        int[] RGBArray = new int[imageWidth];
        for (int x = 0; x < imageWidth; x++) {
            Color oldColor = new Color(bufferedImage.getRGB(x, readFromLine));
            if ((oldColor.getBlue() + oldColor.getGreen() + oldColor.getRed()) >= 3*PIXEL_AVERAGE) {
                RGBArray[x] = new Color(255, 255, 255).getRGB();
            } else {
                RGBArray[x] = new Color(0, 0, 0).getRGB();
            }
        }
        return RGBArray;
    }
}