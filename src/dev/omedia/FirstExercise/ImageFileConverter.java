package dev.omedia.FirstExercise;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ImageFileConverter {


    public void convert(BufferedImage bufferedImage, FileType convertToType, int nThreads) throws IOException, ExecutionException, InterruptedException {
        String UUID = java.util.UUID.randomUUID().toString();
        if (convertToType.equals(FileType.IMAGE)) {
            convertIfImage(bufferedImage, "me_" + UUID + ".jpeg", nThreads);
        } else {
            covertIfTXT(bufferedImage, "me_" + UUID + ".txt", nThreads);
        }
    }


    private void covertIfTXT(BufferedImage bufferedImage, String savePath, int nThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            ConvertToTextTask task = new ConvertToTextTask(bufferedImage, i);
            Future<String> result = executor.submit(task);
            futureList.add(result);
            System.out.println(Thread.activeCount());
        }
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(savePath), StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            futureList.stream().map(f -> {
                try {
                    return f.get().getBytes();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(b -> {
                try {
                    outputStream.write(b);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private void convertIfImage(BufferedImage bufferedImage, String savePath, int nThreads) throws ExecutionException, InterruptedException {
        int imageWidth = bufferedImage.getWidth();
        int imageHeight = bufferedImage.getHeight();
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        BufferedImage bufferedImageWrite = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < imageHeight; y++) {
            ConvertToImageTask task = new ConvertToImageTask(bufferedImage, y);
            Future<int[]> result = executor.submit(task);
            bufferedImageWrite.setRGB(0, y, imageWidth, 1, result.get(), 0, imageWidth);
        }
        executor.shutdown();
        try {
            File convertedFile = new File(savePath);
            ImageIO.write(bufferedImageWrite, "jpeg", convertedFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
