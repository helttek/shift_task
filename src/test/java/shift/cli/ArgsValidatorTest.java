package shift.cli;

import org.junit.jupiter.api.Test;
import shift.config.Config;
import shift.exceptions.config.ConfigCreationException;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArgsValidatorTest {

    @Test
    void testGetConfigWithMinimalValidArgs() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(1, config.inputFiles().size());
        assertEquals("input.txt", config.inputFiles().get(0));
        assertFalse(config.append());
        assertFalse(config.shortStatistics());
        assertFalse(config.fullStatistics());
        assertEquals(Paths.get(".", "integers.txt"), config.intFile());
        assertEquals(Paths.get(".", "floats.txt"), config.floatFile());
        assertEquals(Paths.get(".", "strings.txt"), config.stringFile());
    }

    @Test
    void testGetConfigWithMultipleInputFiles() throws ConfigCreationException {
        Args args = new Args(List.of("file1.txt", "file2.txt", "file3.txt"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(3, config.inputFiles().size());
        assertTrue(config.inputFiles().contains("file1.txt"));
        assertTrue(config.inputFiles().contains("file2.txt"));
        assertTrue(config.inputFiles().contains("file3.txt"));
    }

    @Test
    void testGetConfigWithOutputDirectory() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("o", List.of("output"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get("output", "integers.txt"), config.intFile());
        assertEquals(Paths.get("output", "floats.txt"), config.floatFile());
        assertEquals(Paths.get("output", "strings.txt"), config.stringFile());
    }

    @Test
    void testGetConfigWithPrefix() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("p", List.of("result_"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get(".", "result_integers.txt"), config.intFile());
        assertEquals(Paths.get(".", "result_floats.txt"), config.floatFile());
        assertEquals(Paths.get(".", "result_strings.txt"), config.stringFile());
    }

    @Test
    void testGetConfigWithOutputDirectoryAndPrefix() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("o", List.of("out"));
        args.addOption("p", List.of("test_"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get("out", "test_integers.txt"), config.intFile());
        assertEquals(Paths.get("out", "test_floats.txt"), config.floatFile());
        assertEquals(Paths.get("out", "test_strings.txt"), config.stringFile());
    }

    @Test
    void testGetConfigWithAppendOption() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("a", List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertTrue(config.append());
    }

    @Test
    void testGetConfigWithShortStatistics() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("s", List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertTrue(config.shortStatistics());
        assertFalse(config.fullStatistics());
    }

    @Test
    void testGetConfigWithFullStatistics() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("f", List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertFalse(config.shortStatistics());
        assertTrue(config.fullStatistics());
    }

    @Test
    void testGetConfigWithBothStatistics() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("s", List.of());
        args.addOption("f", List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertTrue(config.shortStatistics());
        assertTrue(config.fullStatistics());
    }

    @Test
    void testGetConfigWithAllOptions() throws ConfigCreationException {
        Args args = new Args(List.of("in1.txt", "in2.txt"));
        args.addOption("o", List.of("results"));
        args.addOption("p", List.of("output_"));
        args.addOption("a", List.of());
        args.addOption("f", List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(2, config.inputFiles().size());
        assertTrue(config.append());
        assertTrue(config.fullStatistics());
        assertEquals(Paths.get("results", "output_integers.txt"), config.intFile());
        assertEquals(Paths.get("results", "output_floats.txt"), config.floatFile());
        assertEquals(Paths.get("results", "output_strings.txt"), config.stringFile());
    }

    @Test
    void testGetConfigThrowsExceptionForNoInputFiles() {
        Args args = new Args(List.of());
        ArgsValidator validator = new ArgsValidator(args);

        assertThrows(ConfigCreationException.class, () -> validator.getConfig());
    }

    @Test
    void testGetConfigWithInvalidPath() throws ConfigCreationException {
        Args args = new Args(List.of("valid.txt"));
        args.addOption("o", List.of("\0invalid"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        // Should fallback to default directory
        assertEquals(Paths.get(".", "integers.txt"), config.intFile());
    }

    @Test
    void testGetConfigFiltersInvalidInputFiles() {
        Args args = new Args(List.of("\0invalid", "valid.txt"));
        ArgsValidator validator = new ArgsValidator(args);

        assertDoesNotThrow(() -> {
            Config config = validator.getConfig();
            assertEquals(1, config.inputFiles().size());
            assertEquals("valid.txt", config.inputFiles().get(0));
        });
    }

    @Test
    void testGetConfigWithEmptyPrefix() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption("p", List.of(""));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get(".", "integers.txt"), config.intFile());
    }

    @Test
    void testGetConfigWithoutAppendOption() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertFalse(config.append());
    }
}
