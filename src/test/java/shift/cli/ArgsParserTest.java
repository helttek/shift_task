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
        assertNotNull(result.GetOptionValues("o"));
        assertEquals("/output/path", result.GetOptionValues("o").get(0));
    }

    @Test
    void testParseWithPrefixOption() throws ArgsParsingException {
        String[] args = {"-" + OptionEnum.PREFIX.getShortName(), "prefix_", "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOptionValues(OptionEnum.PREFIX.getShortName()));
        assertEquals("prefix_", result.GetOptionValues(OptionEnum.PREFIX.getShortName()).get(0));
    }

    @Test
    void testParseWithAppendOption() throws ArgsParsingException {
        String[] args = {"-" + OptionEnum.APPEND.getShortName(), "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOptionValues(OptionEnum.APPEND.getShortName()));
    }

    @Test
    void testParseWithShortStatisticsOption() throws ArgsParsingException {
        String[] args = {"-" + OptionEnum.SHORT_STATS.getShortName(), "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOptionValues(OptionEnum.SHORT_STATS.getShortName()));
    }

    @Test
    void testParseWithFullStatisticsOption() throws ArgsParsingException {
        String[] args = {"-" + OptionEnum.FULL_STATS.getShortName(), "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOptionValues(OptionEnum.FULL_STATS.getShortName()));
    }

    @Test
    void testParseWithMultipleOptions() throws ArgsParsingException {
        String[] args = {"-" + OptionEnum.OUTPUT_DIRECTORY.getShortName(), "/output", "-" + OptionEnum.PREFIX.getShortName(), "test_", "-" + OptionEnum.APPEND.getShortName(), "-" + OptionEnum.SHORT_STATS.getShortName(), "file1.txt", "file2.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertEquals(2, result.getInputFiles().size());
        assertNotNull(result.GetOptionValues(OptionEnum.OUTPUT_DIRECTORY.getShortName()));
        assertNotNull(result.GetOptionValues(OptionEnum.PREFIX.getShortName()));
        assertNotNull(result.GetOptionValues(OptionEnum.APPEND.getShortName()));
        assertNotNull(result.GetOptionValues(OptionEnum.SHORT_STATS.getShortName()));
        assertEquals("/output", result.GetOptionValues(OptionEnum.OUTPUT_DIRECTORY.getShortName()).get(0));
        assertEquals("test_", result.GetOptionValues(OptionEnum.PREFIX.getShortName()).get(0));
    }

    @Test
    void testParseWithBothStatisticsOptions() throws ArgsParsingException {
        String[] args = {"-" + OptionEnum.SHORT_STATS.getShortName(), "-" + OptionEnum.FULL_STATS.getShortName(), "file.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertNotNull(result);
        assertNotNull(result.GetOptionValues(OptionEnum.SHORT_STATS.getShortName()));
        assertNotNull(result.GetOptionValues(OptionEnum.FULL_STATS.getShortName()));
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
    void testParsePreservesFileOrder() throws ArgsParsingException {
        String[] args = {"zebra.txt", "apple.txt", "middle.txt"};
        ArgsParser parser = new ArgsParser(args);
        Args result = parser.parse();

        assertEquals("zebra.txt", result.getInputFiles().get(0));
        assertEquals("apple.txt", result.getInputFiles().get(1));
        assertEquals("middle.txt", result.getInputFiles().get(2));
    }
}
