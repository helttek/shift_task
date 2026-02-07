package shift.config;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void testConfigCreation() {
        List<String> inputFiles = Arrays.asList("file1.txt", "file2.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(false, false, false, inputFiles, intFile, floatFile, stringFile);

        assertNotNull(config);
        assertFalse(config.append());
        assertFalse(config.shortStatistics());
        assertFalse(config.fullStatistics());
        assertEquals(inputFiles, config.inputFiles());
        assertEquals(intFile, config.intFile());
        assertEquals(floatFile, config.floatFile());
        assertEquals(stringFile, config.stringFile());
    }

    @Test
    void testConfigWithAppendTrue() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(true, false, false, inputFiles, intFile, floatFile, stringFile);

        assertTrue(config.append());
    }

    @Test
    void testConfigWithShortStatistics() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(false, true, false, inputFiles, intFile, floatFile, stringFile);

        assertTrue(config.shortStatistics());
        assertFalse(config.fullStatistics());
    }

    @Test
    void testConfigWithFullStatistics() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(false, false, true, inputFiles, intFile, floatFile, stringFile);

        assertFalse(config.shortStatistics());
        assertTrue(config.fullStatistics());
    }

    @Test
    void testConfigWithBothStatistics() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(false, true, true, inputFiles, intFile, floatFile, stringFile);

        assertTrue(config.shortStatistics());
        assertTrue(config.fullStatistics());
    }

    @Test
    void testConfigWithAllOptionsEnabled() {
        List<String> inputFiles = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        Path intFile = Paths.get("output", "integers.txt");
        Path floatFile = Paths.get("output", "floats.txt");
        Path stringFile = Paths.get("output", "strings.txt");

        Config config = new Config(true, true, true, inputFiles, intFile, floatFile, stringFile);

        assertTrue(config.append());
        assertTrue(config.shortStatistics());
        assertTrue(config.fullStatistics());
        assertEquals(3, config.inputFiles().size());
        assertEquals(Paths.get("output", "integers.txt"), config.intFile());
        assertEquals(Paths.get("output", "floats.txt"), config.floatFile());
        assertEquals(Paths.get("output", "strings.txt"), config.stringFile());
    }

    @Test
    void testConfigWithMultipleInputFiles() {
        List<String> inputFiles = Arrays.asList("in1.txt", "in2.txt", "in3.txt", "in4.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(false, false, false, inputFiles, intFile, floatFile, stringFile);

        assertEquals(4, config.inputFiles().size());
        assertEquals("in1.txt", config.inputFiles().get(0));
        assertEquals("in4.txt", config.inputFiles().get(3));
    }

    @Test
    void testConfigWithCustomPaths() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("custom", "output", "my_integers.txt");
        Path floatFile = Paths.get("custom", "output", "my_floats.txt");
        Path stringFile = Paths.get("custom", "output", "my_strings.txt");

        Config config = new Config(false, false, false, inputFiles, intFile, floatFile, stringFile);

        assertEquals(Paths.get("custom", "output", "my_integers.txt"), config.intFile());
        assertEquals(Paths.get("custom", "output", "my_floats.txt"), config.floatFile());
        assertEquals(Paths.get("custom", "output", "my_strings.txt"), config.stringFile());
    }

    @Test
    void testConfigEquality() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config1 = new Config(true, true, false, inputFiles, intFile, floatFile, stringFile);
        Config config2 = new Config(true, true, false, inputFiles, intFile, floatFile, stringFile);

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    void testConfigInequality() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config1 = new Config(true, true, false, inputFiles, intFile, floatFile, stringFile);
        Config config2 = new Config(false, true, false, inputFiles, intFile, floatFile, stringFile);

        assertNotEquals(config1, config2);
    }

    @Test
    void testConfigToString() {
        List<String> inputFiles = List.of("input.txt");
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(true, false, true, inputFiles, intFile, floatFile, stringFile);

        String toString = config.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("true"));
        assertTrue(toString.contains("input.txt"));
    }

    @Test
    void testConfigWithEmptyInputFiles() {
        List<String> inputFiles = List.of();
        Path intFile = Paths.get("integers.txt");
        Path floatFile = Paths.get("floats.txt");
        Path stringFile = Paths.get("strings.txt");

        Config config = new Config(false, false, false, inputFiles, intFile, floatFile, stringFile);

        assertTrue(config.inputFiles().isEmpty());
    }
}
