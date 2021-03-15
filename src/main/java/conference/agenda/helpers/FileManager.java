package conference.agenda.helpers;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

    public List<String> readFile(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.skip(1).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void writeToFile(String text, String pathName) {
        Path path = Paths.get(pathName);
        File file = new File(pathName);
        if (!file.isFile()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Cannot create file " + pathName);
                e.printStackTrace();
            }
        }
        try {
            Files.write(path, text.getBytes());
        } catch (IOException e) {
            System.out.println("Cannot write to file " + pathName);
            e.printStackTrace();
        }
    }
}
