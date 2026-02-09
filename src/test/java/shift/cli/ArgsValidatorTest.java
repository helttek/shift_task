package shift.cli;

import org.junit.jupiter.api.Test;
import shift.config.Config;
import shift.config.DefaultConfigValues;
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

        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, DefaultConfigValues.INT_FILE), config.intFile());
        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, DefaultConfigValues.FLOAT_FILE), config.floatFile());
        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, DefaultConfigValues.STR_FILE), config.stringFile());
    }

    @Test
    void testGetConfigWithMultipleInputFiles() throws ConfigCreationException {
        Args args = new Args(List.of("file1.txt", "file2.txt", "file3.txt"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(3, config.inputFiles().size());
        assertTrue(config.inputFiles().containsAll(List.of("file1.txt", "file2.txt", "file3.txt")));
    }

    @Test
    void testGetConfigWithOutputDirectory() throws ConfigCreationException {
        String customDir = "output";
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.OUTPUT_DIRECTORY.getShortName(), List.of(customDir));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get(customDir, DefaultConfigValues.INT_FILE), config.intFile());
        assertEquals(Paths.get(customDir, DefaultConfigValues.FLOAT_FILE), config.floatFile());
        assertEquals(Paths.get(customDir, DefaultConfigValues.STR_FILE), config.stringFile());
    }

    @Test
    void testGetConfigWithPrefix() throws ConfigCreationException {
        String prefix = "result_";
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.PREFIX.getShortName(), List.of(prefix));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, prefix + DefaultConfigValues.INT_FILE), config.intFile());
        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, prefix + DefaultConfigValues.FLOAT_FILE), config.floatFile());
        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, prefix + DefaultConfigValues.STR_FILE), config.stringFile());
    }

    @Test
    void testGetConfigWithOutputDirectoryAndPrefix() throws ConfigCreationException {
        String customDir = "out";
        String prefix = "test_";
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.OUTPUT_DIRECTORY.getShortName(), List.of(customDir));
        args.addOption(OptionEnum.PREFIX.getShortName(), List.of(prefix));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get(customDir, prefix + DefaultConfigValues.INT_FILE), config.intFile());
    }

    @Test
    void testGetConfigWithAppendOption() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.APPEND.getShortName(), List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertTrue(config.append());
    }

    @Test
    void testGetConfigWithShortStatistics() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.SHORT_STATS.getShortName(), List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertTrue(config.shortStatistics());
        assertFalse(config.fullStatistics());
    }

    @Test
    void testGetConfigWithFullStatistics() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.FULL_STATS.getShortName(), List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertFalse(config.shortStatistics());
        assertTrue(config.fullStatistics());
    }

    @Test
    void testGetConfigWithAllOptions() throws ConfigCreationException {
        String customDir = "results";
        String prefix = "output_";
        Args args = new Args(List.of("in1.txt", "in2.txt"));
        args.addOption(OptionEnum.OUTPUT_DIRECTORY.getShortName(), List.of(customDir));
        args.addOption(OptionEnum.PREFIX.getShortName(), List.of(prefix));
        args.addOption(OptionEnum.APPEND.getShortName(), List.of());
        args.addOption(OptionEnum.FULL_STATS.getShortName(), List.of());
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(2, config.inputFiles().size());
        assertTrue(config.append());
        assertTrue(config.fullStatistics());
        assertEquals(Paths.get(customDir, prefix + DefaultConfigValues.INT_FILE), config.intFile());
    }

    @Test
    void testGetConfigThrowsExceptionForNoInputFiles() {
        Args args = new Args(List.of());
        ArgsValidator validator = new ArgsValidator(args);

        assertThrows(ConfigCreationException.class, validator::getConfig);
    }

    @Test
    void testGetConfigWithInvalidPath() throws ConfigCreationException {
        Args args = new Args(List.of("valid.txt"));
        args.addOption(OptionEnum.OUTPUT_DIRECTORY.getShortName(), List.of("\0invalid"));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, DefaultConfigValues.INT_FILE), config.intFile());
    }

    @Test
    void testGetConfigWithEmptyPrefix() throws ConfigCreationException {
        Args args = new Args(List.of("input.txt"));
        args.addOption(OptionEnum.PREFIX.getShortName(), List.of(""));
        ArgsValidator validator = new ArgsValidator(args);

        Config config = validator.getConfig();

        assertNotNull(config);
        assertEquals(Paths.get(DefaultConfigValues.OUT_DIR, DefaultConfigValues.INT_FILE), config.intFile());
    }
}