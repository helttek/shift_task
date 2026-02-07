package shift.io;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import shift.exceptions.io.ReaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReaderTest {

    @TempDir
    Path tempDir;

    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("test.txt");
    }

    @Test
    void testReadLineFromFile() throws IOException, ReaderException {
        Files.writeString(testFile, "line1\nline2\nline3");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("line1", reader.readLine());
            assertEquals("line2", reader.readLine());
            assertEquals("line3", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void testReadEmptyFile() throws IOException, ReaderException {
        Files.writeString(testFile, "");

        try (Reader reader = new Reader(testFile.toString())) {
            assertNull(reader.readLine());
        }
    }

    @Test
    void testReadSingleLine() throws IOException, ReaderException {
        Files.writeString(testFile, "single line");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("single line", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void testReadFileWithEmptyLines() throws IOException, ReaderException {
        Files.writeString(testFile, "line1\n\nline3\n");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("line1", reader.readLine());
            assertEquals("", reader.readLine());
            assertEquals("line3", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void testConstructorThrowsExceptionForNonExistentFile() {
        assertThrows(ReaderException.class, () -> new Reader("nonexistent.txt"));
    }

    @Test
    void testReadLineAfterClose() throws IOException, ReaderException {
        Files.writeString(testFile, "test");

        Reader reader = new Reader(testFile.toString());
        reader.close();

        assertThrows(IOException.class, () -> reader.readLine());
    }

    @Test
    void testCloseMultipleTimes() throws IOException, ReaderException {
        Files.writeString(testFile, "test");

        Reader reader = new Reader(testFile.toString());
        reader.close();

        assertDoesNotThrow(() -> reader.close());
    }

    @Test
    void testReadLongLines() throws IOException, ReaderException {
        String longLine = "a".repeat(10000);
        Files.writeString(testFile, longLine);

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals(longLine, reader.readLine());
        }
    }

    @Test
    void testReadFileWithSpecialCharacters() throws IOException, ReaderException {
        Files.writeString(testFile, "Special: !@#$%^&*()_+\nUnicode: \u00E9\u00F1\u00FC");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("Special: !@#$%^&*()_+", reader.readLine());
            assertEquals("Unicode: \u00E9\u00F1\u00FC", reader.readLine());
        }
    }

    @Test
    void testReadFileWithOnlyNewlines() throws IOException, ReaderException {
        Files.writeString(testFile, "\n\n\n");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("", reader.readLine());
            assertEquals("", reader.readLine());
            assertEquals("", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void testReadFileWithNumbers() throws IOException, ReaderException {
        Files.writeString(testFile, "123\n456.789\n-999");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("123", reader.readLine());
            assertEquals("456.789", reader.readLine());
            assertEquals("-999", reader.readLine());
        }
    }

    @Test
    void testAutoCloseableInterface() throws IOException, ReaderException {
        Files.writeString(testFile, "test line");

        assertDoesNotThrow(() -> {
            try (Reader reader = new Reader(testFile.toString())) {
                reader.readLine();
            }
        });
    }

    @Test
    void testReadFileWithMixedContent() throws IOException, ReaderException {
        Files.writeString(testFile, "Text\n123\n45.67\nMore text\n");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("Text", reader.readLine());
            assertEquals("123", reader.readLine());
            assertEquals("45.67", reader.readLine());
            assertEquals("More text", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    void testReadFileWithWhitespace() throws IOException, ReaderException {
        Files.writeString(testFile, "  spaces  \n\ttabs\t\n  \t  ");

        try (Reader reader = new Reader(testFile.toString())) {
            assertEquals("  spaces  ", reader.readLine());
            assertEquals("\ttabs\t", reader.readLine());
            assertEquals("  \t  ", reader.readLine());
        }
    }
}
