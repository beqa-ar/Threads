package dev.omedia;

import dev.omedia.FirstExercise.FileType;
import dev.omedia.FirstExercise.ImageFileConverter;
import dev.omedia.FirstExercise.ValidationException;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter File Path. format: fileName.fileExtension   example: text.txt");
        String path = scanner.nextLine();
        checkPathValidation(path);


        System.out.println("Enter File Type-> TXT/IMAGE");
        String convertToType = scanner.nextLine();
        checkFileTypeValidation(convertToType);

        System.out.println("Enter Number of Threads!");
        int nThreads =Integer.parseInt(scanner.nextLine());

        FileType fileType;
        if (convertToType.equals("TXT")) {
            fileType = FileType.TXT;
        } else {
            fileType = FileType.IMAGE;
        }


        ImageFileConverter imageFileConverter = new ImageFileConverter();
        try {
            imageFileConverter.convert(ImageIO.read(new File(path)), fileType,nThreads);
        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new IOException(e);
        }
    }

    private static void checkPathValidation(String path) throws ValidationException {
        Pattern pathPattern = Pattern.compile("[a-zA-Z0-9]+[.][a-zA-z]+");
        Matcher pathMatcher = pathPattern.matcher(path);
        if (!pathMatcher.matches()) {
            throw new ValidationException(
                    "Wrong input! your input: " + path + "! required format: fileName.fileExtension   example: text.txt");
        }
    }

    private static void checkFileTypeValidation(String convertToType) throws ValidationException {
        Pattern fileTypePattern = Pattern.compile("TXT|IMAGE");
        Matcher fileTypeMatcher = fileTypePattern.matcher(convertToType);
        if (!fileTypeMatcher.matches()) {
            throw new ValidationException(
                    "Wrong input! your input: " + convertToType + " required -> TXT/IMAGE");
        }
    }
}
