package shift.cli;

import org.junit.jupiter.api.Test;
import shift.exceptions.cli.ArgsParsingException;

import static org.junit.jupiter.api.Assertions.*;

class ArgsParserTest {

    @Test
    void testParseWithNoArguments() throws ArgsParsingException {
        String[] args = {};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertTrue(result.getInputFiles().isEmpty());
    }

    @Test
    void testParseWithOnlyFiles() throws ArgsParsingException {
        String[] args = {"file1.txt", "file2.txt", "file3.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertEquals(3, result.getInputFiles().size());
        assertEquals("file1.txt", result.getInputFiles().get(0));
        assertEquals("file2.txt", result.getInputFiles().get(1));
        assertEquals("file3.txt", result.getInputFiles().get(2));
    }

    @Test
    void testParseWithOutputDirectoryOption() throws ArgsParsingException {
        String[] args = {"-o", "/output/path", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertEquals(1, result.getInputFiles().size());
        assertNotNull(result.GetOption("o"));
        assertEquals("/output/path", result.GetOption("o").get(0));
    }

    @Test
    void testParseWithPrefixOption() throws ArgsParsingException {
        String[] args = {"-p", "prefix_", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOption("p"));
        assertEquals("prefix_", result.GetOption("p").get(0));
    }

    @Test
    void testParseWithAppendOption() throws ArgsParsingException {
        String[] args = {"-a", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOption("a"));
    }

    @Test
    void testParseWithShortStatisticsOption() throws ArgsParsingException {
        String[] args = {"-s", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOption("s"));
    }

    @Test
    void testParseWithFullStatisticsOption() throws ArgsParsingException {
        String[] args = {"-f", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOption("f"));
    }

    @Test
    void testParseWithMultipleOptions() throws ArgsParsingException {
        String[] args = {"-o", "/output", "-p", "test_", "-a", "-s", "file1.txt", "file2.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertEquals(2, result.getInputFiles().size());
        assertNotNull(result.GetOption("o"));
        assertNotNull(result.GetOption("p"));
        assertNotNull(result.GetOption("a"));
        assertNotNull(result.GetOption("s"));
        assertEquals("/output", result.GetOption("o").get(0));
        assertEquals("test_", result.GetOption("p").get(0));
    }

    @Test
    void testParseWithBothStatisticsOptions() throws ArgsParsingException {
        String[] args = {"-s", "-f", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOption("s"));
        assertNotNull(result.GetOption("f"));
    }

    @Test
    void testParseWithInvalidOption() {
        String[] args = {"-z", "file.txt"};
        ArgsParser parser = new ArgsParser(args);

        assertThrows(ArgsParsingException.class, () -> parser.parse());
    }

    @Test
    void testParseWithMissingRequiredArgument() {
        String[] args = {"-o"};
        ArgsParser parser = new ArgsParser(args);

        assertThrows(ArgsParsingException.class, () -> parser.parse());
    }

    @Test
    void testParseWithAllOptionsAndMultipleFiles() throws ArgsParsingException {
        String[] args = {
            "-o", "/custom/output",
            "-p", "result_",
            "-a",
            "-f",
            "input1.txt",
            "input2.txt",
            "input3.txt"
        };
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertEquals(3, result.getInputFiles().size());
        assertEquals("/custom/output", result.GetOption("o").get(0));
        assertEquals("result_", result.GetOption("p").get(0));
        assertNotNull(result.GetOption("a"));
        assertNotNull(result.GetOption("f"));
    }

    @Test
    void testParsePreservesFileOrder() throws ArgsParsingException {
        String[] args = {"zebra.txt", "apple.txt", "middle.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertEquals("zebra.txt", result.getInputFiles().get(0));
        assertEquals("apple.txt", result.getInputFiles().get(1));
        assertEquals("middle.txt", result.getInputFiles().get(2));
    }
}
