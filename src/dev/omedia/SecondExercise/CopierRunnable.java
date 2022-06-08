package dev.omedia.SecondExercise;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopierRunnable implements Runnable {
    private final Path sourcePath;
    private final Path savePath;

    public CopierRunnable(Path sourcePath, Path savePath) {
        this.sourcePath = sourcePath;
        this.savePath = savePath;
    }

    @Override
    public void run() {
        try {
            Files.copy(sourcePath, new File(String.valueOf(savePath)).toPath(), StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
