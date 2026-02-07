package shift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import shift.exceptions.FileContentSorterException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileContentSorterTest {

    @TempDir
    Path tempDir;

    private Path inputFile;

    @BeforeEach
    void setUp() throws IOException {
        inputFile = tempDir.resolve("input.txt");
        Files.writeString(inputFile, "42\nhello\n3.14\n");
    }

    @Test
    void testConstructorWithValidArgs() {
        String[] args = {inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithNoInputFiles() {
        String[] args = {};
        assertThrows(FileContentSorterException.class, () -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithOutputDirectory() {
        String[] args = {"-o", tempDir.toString(), inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithPrefix() {
        String[] args = {"-p", "test_", inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithAppendOption() {
        String[] args = {"-a", inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithShortStatistics() {
        String[] args = {"-s", inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithFullStatistics() {
        String[] args = {"-f", inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithAllOptions() {
        String[] args = {
            "-o", tempDir.toString(),
            "-p", "result_",
            "-a",
            "-f",
            inputFile.toString()
        };
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithMultipleInputFiles() throws IOException {
        Path inputFile2 = tempDir.resolve("input2.txt");
        Files.writeString(inputFile2, "test\n");

        String[] args = {inputFile.toString(), inputFile2.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testConstructorWithInvalidOption() {
        String[] args = {"-z", inputFile.toString()};
        assertThrows(FileContentSorterException.class, () -> new FileContentSorter(args));
    }

    @Test
    void testStartCreatesOutputFiles() throws FileContentSorterException, IOException {
        Path intFile = tempDir.resolve("integers.txt");
        Path floatFile = tempDir.resolve("floats.txt");
        Path stringFile = tempDir.resolve("strings.txt");

        String[] args = {"-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        assertTrue(Files.exists(intFile));
        assertTrue(Files.exists(floatFile));
        assertTrue(Files.exists(stringFile));
    }

    @Test
    void testStartSortsContentCorrectly() throws FileContentSorterException, IOException {
        Path intFile = tempDir.resolve("integers.txt");
        Path floatFile = tempDir.resolve("floats.txt");
        Path stringFile = tempDir.resolve("strings.txt");

        String[] args = {"-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(1, intLines.size());
        assertEquals("42", intLines.get(0));

        List<String> floatLines = Files.readAllLines(floatFile);
        assertEquals(1, floatLines.size());
        assertEquals("3.14", floatLines.get(0));

        List<String> stringLines = Files.readAllLines(stringFile);
        assertEquals(1, stringLines.size());
        assertEquals("hello", stringLines.get(0));
    }

    @Test
    void testStartWithShortStatistics() throws FileContentSorterException {
        String[] args = {"-s", "-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        assertDoesNotThrow(() -> sorter.start());
    }

    @Test
    void testStartWithFullStatistics() throws FileContentSorterException {
        String[] args = {"-f", "-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        assertDoesNotThrow(() -> sorter.start());
    }

    @Test
    void testStartWithBothStatistics() throws FileContentSorterException {
        String[] args = {"-s", "-f", "-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        assertDoesNotThrow(() -> sorter.start());
    }

    @Test
    void testStartWithAppendMode() throws FileContentSorterException, IOException {
        Path intFile = tempDir.resolve("integers.txt");
        Files.writeString(intFile, "10\n");

        String[] args = {"-a", "-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(2, intLines.size());
        assertEquals("10", intLines.get(0));
        assertEquals("42", intLines.get(1));
    }

    @Test
    void testStartWithPrefix() throws FileContentSorterException, IOException {
        Path intFile = tempDir.resolve("prefix_integers.txt");

        String[] args = {"-p", "prefix_", "-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        assertTrue(Files.exists(intFile));
        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(1, intLines.size());
        assertEquals("42", intLines.get(0));
    }

    @Test
    void testStartWithMultipleFiles() throws FileContentSorterException, IOException {
        Path inputFile2 = tempDir.resolve("input2.txt");
        Files.writeString(inputFile2, "100\nworld\n5.5\n");

        Path intFile = tempDir.resolve("integers.txt");

        String[] args = {"-o", tempDir.toString(), inputFile.toString(), inputFile2.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(2, intLines.size());
        assertEquals("42", intLines.get(0));
        assertEquals("100", intLines.get(1));
    }

    @Test
    void testConstructorWithEmptyInputFiles() throws IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.writeString(emptyFile, "");

        String[] args = {emptyFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testStartWithEmptyFile() throws FileContentSorterException, IOException {
        Path emptyFile = tempDir.resolve("empty.txt");
        Files.writeString(emptyFile, "");

        String[] args = {"-o", tempDir.toString(), emptyFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        Path intFile = tempDir.resolve("integers.txt");
        Path floatFile = tempDir.resolve("floats.txt");
        Path stringFile = tempDir.resolve("strings.txt");

        assertFalse(Files.exists(intFile));
        assertFalse(Files.exists(floatFile));
        assertFalse(Files.exists(stringFile));
    }

    @Test
    void testStartWithMixedValidAndInvalidFiles() throws FileContentSorterException, IOException {
        String[] args = {"-o", tempDir.toString(), inputFile.toString(), "nonexistent.txt"};
        FileContentSorter sorter = new FileContentSorter(args);

        assertDoesNotThrow(() -> sorter.start());

        Path intFile = tempDir.resolve("integers.txt");
        assertTrue(Files.exists(intFile));
    }

    @Test
    void testConstructorWithOnlyOptions() {
        String[] args = {"-s", "-f", "-a"};
        assertThrows(FileContentSorterException.class, () -> new FileContentSorter(args));
    }

    @Test
    void testStartCreatesCorrectOutputStructure() throws FileContentSorterException, IOException {
        Files.writeString(inputFile, "1\n2\n3\nfour\nfive\n1.1\n2.2\n");

        String[] args = {"-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        Path intFile = tempDir.resolve("integers.txt");
        Path floatFile = tempDir.resolve("floats.txt");
        Path stringFile = tempDir.resolve("strings.txt");

        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(3, intLines.size());

        List<String> floatLines = Files.readAllLines(floatFile);
        assertEquals(2, floatLines.size());

        List<String> stringLines = Files.readAllLines(stringFile);
        assertEquals(2, stringLines.size());
    }

    @Test
    void testConstructorWithDuplicateInputFiles() throws IOException {
        String[] args = {inputFile.toString(), inputFile.toString()};
        assertDoesNotThrow(() -> new FileContentSorter(args));
    }

    @Test
    void testStartWithNegativeNumbers() throws FileContentSorterException, IOException {
        Files.writeString(inputFile, "-10\n-20\n-3.14\n");

        String[] args = {"-o", tempDir.toString(), inputFile.toString()};
        FileContentSorter sorter = new FileContentSorter(args);
        sorter.start();

        Path intFile = tempDir.resolve("integers.txt");
        Path floatFile = tempDir.resolve("floats.txt");

        List<String> intLines = Files.readAllLines(intFile);
        assertEquals(2, intLines.size());

        List<String> floatLines = Files.readAllLines(floatFile);
        assertEquals(1, floatLines.size());
    }
}
