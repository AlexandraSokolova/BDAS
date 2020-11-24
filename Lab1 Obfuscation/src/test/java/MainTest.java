import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testMainWithNormalFileWithoutKey() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("staff.xml"), UTF_8);
            String[] args = new String[2];
            args[0] = "-o";
            args[1] = "staff.xml";
            Main.main(args);
            args[0] = "-u";
            Main.main(args);
            List<String> newLines = Files.readAllLines(Paths.get("staff.xml"), UTF_8);
            assertEquals(lines, newLines);
            assertEquals(0, outContent.toString().length());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testMainWithNormalFileWithKey() {
        try {
            List<String> lines = Files.readAllLines(Paths.get("staff.xml"), UTF_8);
            String[] args = new String[3];
            args[0] = "-o";
            args[1] = "staff.xml";
            args[2] = "aaaaaaaa";
            Main.main(args);
            args[0] = "-u";
            Main.main(args);
            List<String> newLines = Files.readAllLines(Paths.get("staff.xml"), UTF_8);
            assertEquals(lines, newLines);
            assertEquals(0, outContent.toString().length());
        } catch (IOException e) {
            fail();
        }
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }
}