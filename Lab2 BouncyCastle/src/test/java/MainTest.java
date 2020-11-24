import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testEncryptionAndDecryption() throws IOException {
        String[] args = new String[2];
        args[0] = "-e";
        args[1] = "test_file_1.txt";
        File file = createFile(args[1]);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write("Hello");
        }
        Main.main(args);
        args[0] = "-d";
        Main.main(args);
        List<String> strings = Files.readAllLines(Paths.get(args[1]));
        String str = String.join("", strings);
        assertEquals("Hello", str);
        deleteFile(file);
    }

    private File createFile(String filename) throws IOException {
        File myObj = new File(filename);
        myObj.createNewFile();
        return myObj;
    }

    private void deleteFile(File file) {
        file.delete();
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

}