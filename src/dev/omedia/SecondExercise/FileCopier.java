package dev.omedia.SecondExercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileCopier {


    public static void  copy(Path sourcePath, Path savePath, int nThreads) {
        List<String> pathList;
        try (Stream<Path> stream = Files.walk(sourcePath)) {
            pathList = stream.map(Path::normalize)
                    .filter(Files::isRegularFile)
                    .map(p -> p.toString().substring(sourcePath.toString().length() + 1))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        pathList.stream().map(p -> new CopierRunnable(Paths.get(sourcePath + "/" + p), Paths.get(savePath + "/" + p.replace("\\", "_"))))
                .forEach(executor::execute);
        executor.shutdown();
    }
}
