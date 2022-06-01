package io.ohmry.file;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static List<String> readAll (String filePath) throws Exception {
        return Files.readAllLines(new File(filePath).toPath(), StandardCharsets.UTF_8);
    }

    public static List<String> readMatches (String filePath, String regex) throws Exception {
        List<String> matchedLines = new ArrayList<>();
        List<String> allLine = FileReader.readAll(filePath);

        for (String line : allLine) {
            System.out.println(line);
            if (line.matches(regex)) {
                matchedLines.add(line);
            }
        }

        return matchedLines;
    }
}
