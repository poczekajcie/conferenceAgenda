import conference.agenda.helpers.FileManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileManagerTest {

    private FileManager fileManager;

    @BeforeAll
    void beforeAll() {
        fileManager = new FileManager();
    }

    @Test
    public void shouldReadLines() {
        assertThat(fileManager.readFile("src/test/java/resources/input.txt").size()).isEqualTo(19);
    }

    @Test
    public void shouldWriteToExistingFile() throws IOException {
        String text = "line1\nline2";
        String path = "src/test/java/resources/output.txt";
        fileManager.writeToFile(text, path);

        List<String> lines = Files.lines(Paths.get(path)).collect(Collectors.toList());

        assertThat(lines).contains("line1");
        assertThat(lines).contains("line2");
    }

    @Test
    public void shouldCreateFileAndWriteToIt() throws IOException {
        String text = "line1\nline2";
        String path = "src/test/java/resources/output2.txt";
        fileManager.writeToFile(text, path);

        assertThat(new File(path).isFile()).isTrue();

        List<String> lines = Files.lines(Paths.get(path)).collect(Collectors.toList());

        assertThat(lines).contains("line1");
        assertThat(lines).contains("line2");
    }
}
