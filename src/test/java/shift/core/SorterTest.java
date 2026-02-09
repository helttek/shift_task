package shift.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import shift.config.Config;
import shift.config.DefaultConfigValues;
import shift.statistics.FullStatistics;
import shift.statistics.ShortStatistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SorterTest {

    @TempDir
    Path tempDir;

    private Path inputFile;
    private Path intOutputFile;
    private Path floatOutputFile;
    private Path stringOutputFile;

    @BeforeEach
    void setUp() {
        inputFile = tempDir.resolve("input.txt");
        intOutputFile = tempDir.resolve(DefaultConfigValues.INT_FILE);
        floatOutputFile = tempDir.resolve(DefaultConfigValues.FLOAT_FILE);
        stringOutputFile = tempDir.resolve(DefaultConfigValues.STR_FILE);
    }

    @Test
    void testSortIntegersOnly() throws IOException {
        Files.writeString(inputFile, "10\n20\n30\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(3, intLines.size());
        assertEquals("10", intLines.get(0));
        assertEquals("20", intLines.get(1));
        assertEquals("30", intLines.get(2));
        assertFalse(Files.exists(floatOutputFile));
        assertFalse(Files.exists(stringOutputFile));
    }

    @Test
    void testSortFloatsOnly() throws IOException {
        Files.writeString(inputFile, "1.5\n2.5\n3.5\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> floatLines = Files.readAllLines(floatOutputFile);
        assertEquals(3, floatLines.size());
        assertEquals("1.5", floatLines.get(0));
        assertEquals("2.5", floatLines.get(1));
        assertEquals("3.5", floatLines.get(2));
        assertFalse(Files.exists(intOutputFile));
        assertFalse(Files.exists(stringOutputFile));
    }

    @Test
    void testSortStringsOnly() throws IOException {
        Files.writeString(inputFile, "hello\nworld\ntest\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> stringLines = Files.readAllLines(stringOutputFile);
        assertEquals(3, stringLines.size());
        assertEquals("hello", stringLines.get(0));
        assertEquals("world", stringLines.get(1));
        assertEquals("test", stringLines.get(2));
        assertFalse(Files.exists(intOutputFile));
        assertFalse(Files.exists(floatOutputFile));
    }

    @Test
    void testSortMixedContent() throws IOException {
        Files.writeString(inputFile, "42\nhello\n3.14\nworld\n100\n2.5\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(2, intLines.size());
        assertEquals("42", intLines.get(0));
        assertEquals("100", intLines.get(1));

        List<String> floatLines = Files.readAllLines(floatOutputFile);
        assertEquals(2, floatLines.size());
        assertEquals("3.14", floatLines.get(0));
        assertEquals("2.5", floatLines.get(1));

        List<String> stringLines = Files.readAllLines(stringOutputFile);
        assertEquals(2, stringLines.size());
        assertEquals("hello", stringLines.get(0));
        assertEquals("world", stringLines.get(1));
    }

    @Test
    void testSortWithNegativeNumbers() throws IOException {
        Files.writeString(inputFile, "-10\n-5.5\n-100\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(2, intLines.size());
        assertEquals("-10", intLines.get(0));
        assertEquals("-100", intLines.get(1));

        List<String> floatLines = Files.readAllLines(floatOutputFile);
        assertEquals(1, floatLines.size());
        assertEquals("-5.5", floatLines.get(0));
    }

    @Test
    void testSortWithAppendMode() throws IOException {
        Files.writeString(intOutputFile, "existing\n");
        Files.writeString(inputFile, "42\n");

        Config config = new Config(
                true, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(2, intLines.size());
        assertEquals("existing", intLines.get(0));
        assertEquals("42", intLines.get(1));
    }

    @Test
    void testSortWithOverwriteMode() throws IOException {
        Files.writeString(intOutputFile, "old content\n");
        Files.writeString(inputFile, "42\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(1, intLines.size());
        assertEquals("42", intLines.get(0));
    }

    @Test
    void testSortMultipleFiles() throws IOException {
        Path inputFile2 = tempDir.resolve("input2.txt");
        Files.writeString(inputFile, "10\nhello\n");
        Files.writeString(inputFile2, "20\nworld\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString(), inputFile2.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(2, intLines.size());
        assertEquals("10", intLines.get(0));
        assertEquals("20", intLines.get(1));

        List<String> stringLines = Files.readAllLines(stringOutputFile);
        assertEquals(2, stringLines.size());
        assertEquals("hello", stringLines.get(0));
        assertEquals("world", stringLines.get(1));
    }

    @Test
    void testSortEmptyFile() throws IOException {
        Files.writeString(inputFile, "");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        assertFalse(Files.exists(intOutputFile));
        assertFalse(Files.exists(floatOutputFile));
        assertFalse(Files.exists(stringOutputFile));
    }

    @Test
    void testSortWithZero() throws IOException {
        Files.writeString(inputFile, "0\n0.0\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(1, intLines.size());
        assertEquals("0", intLines.get(0));

        List<String> floatLines = Files.readAllLines(floatOutputFile);
        assertEquals(1, floatLines.size());
        assertEquals("0.0", floatLines.get(0));
    }

    @Test
    void testSortWithEmptyLines() throws IOException {
        Files.writeString(inputFile, "10\n\n20\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(2, intLines.size());

        List<String> stringLines = Files.readAllLines(stringOutputFile);
        assertEquals(1, stringLines.size());
        assertEquals("", stringLines.get(0));
    }

    @Test
    void testSortWithShortStatistics() throws IOException {
        Files.writeString(inputFile, "10\nhello\n3.14\n");

        ShortStatistics stats = new ShortStatistics();
        Config config = new Config(
                false, true, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, stats)) {
            sorter.sort();
        }

        assertTrue(Files.exists(intOutputFile));
        assertTrue(Files.exists(floatOutputFile));
        assertTrue(Files.exists(stringOutputFile));
    }

    @Test
    void testSortWithFullStatistics() throws IOException {
        Files.writeString(inputFile, "10\n20\nhello\nworld\n1.5\n2.5\n");

        FullStatistics stats = new FullStatistics();
        Config config = new Config(
                false, false, true,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, stats)) {
            sorter.sort();
        }

        assertTrue(Files.exists(intOutputFile));
        assertTrue(Files.exists(floatOutputFile));
        assertTrue(Files.exists(stringOutputFile));
    }

    @Test
    void testSortWithNonExistentFile() {
        Config config = new Config(
                false, false, false,
                List.of("nonexistent.txt"),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        assertDoesNotThrow(() -> {
            try (Sorter sorter = new Sorter(config, null)) {
                sorter.sort();
            }
        });
    }

    @Test
    void testAutoCloseableInterface() throws IOException {
        Files.writeString(inputFile, "42\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        assertDoesNotThrow(() -> {
            try (Sorter sorter = new Sorter(config, null)) {
                sorter.sort();
            }
        });
    }

    @Test
    void testSortWithSpecialCharacters() throws IOException {
        Files.writeString(inputFile, "hello!\n@test\n#special\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> stringLines = Files.readAllLines(stringOutputFile);
        assertEquals(3, stringLines.size());
        assertEquals("hello!", stringLines.get(0));
        assertEquals("@test", stringLines.get(1));
        assertEquals("#special", stringLines.get(2));
    }

    @Test
    void testSortWithMaxIntegerValue() throws IOException {
        Files.writeString(inputFile, String.valueOf(Integer.MAX_VALUE) + "\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> intLines = Files.readAllLines(intOutputFile);
        assertEquals(1, intLines.size());
        assertEquals(String.valueOf(Integer.MAX_VALUE), intLines.get(0));
    }

    @Test
    void testSortWithScientificNotation() throws IOException {
        Files.writeString(inputFile, "1.5E10\n2.5E-5\n");

        Config config = new Config(
                false, false, false,
                List.of(inputFile.toString()),
                intOutputFile, floatOutputFile, stringOutputFile
        );

        try (Sorter sorter = new Sorter(config, null)) {
            sorter.sort();
        }

        List<String> floatLines = Files.readAllLines(floatOutputFile);
        assertEquals(2, floatLines.size());
    }
}
