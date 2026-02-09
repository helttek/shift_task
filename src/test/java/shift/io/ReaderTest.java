package shift.io;

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
    void testConstructorThrowsExceptionForNonExistentFile() {
        assertThrows(ReaderException.class, () -> new Reader("nonexistent.txt"));
    }

    @Test
    void testReadSingleLine() throws Exception {
        Files.writeString(testFile, "Hello World");

        IReader reader = new Reader(testFile.toString());
        String line = reader.readLine();
        reader.close();

        assertEquals("Hello World", line);
    }

    @Test
    void testReadMultipleLines() throws Exception {
        Files.writeString(testFile, "Line 1\nLine 2\nLine 3\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("Line 1", reader.readLine());
        assertEquals("Line 2", reader.readLine());
        assertEquals("Line 3", reader.readLine());
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    void testReadEmptyFile() throws Exception {
        Files.writeString(testFile, "");

        IReader reader = new Reader(testFile.toString());
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    void testReadLineReturnsNullAtEndOfFile() throws Exception {
        Files.writeString(testFile, "Only one line");

        IReader reader = new Reader(testFile.toString());
        reader.readLine();
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    void testReadEmptyLines() throws Exception {
        Files.writeString(testFile, "\n\n\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("", reader.readLine());
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    void testReadMixedContent() throws Exception {
        Files.writeString(testFile, "42\nhello\n3.14\nworld\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("42", reader.readLine());
        assertEquals("hello", reader.readLine());
        assertEquals("3.14", reader.readLine());
        assertEquals("world", reader.readLine());
        assertNull(reader.readLine());
        reader.close();
    }

    @Test
    void testReadSpecialCharacters() throws Exception {
        Files.writeString(testFile, "!@#$%^&*()\nUnicode: éñü\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("!@#$%^&*()", reader.readLine());
        assertEquals("Unicode: éñü", reader.readLine());
        reader.close();
    }

    @Test
    void testReadWithWhitespace() throws Exception {
        Files.writeString(testFile, "  spaces  \n\ttabs\t\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("  spaces  ", reader.readLine());
        assertEquals("\ttabs\t", reader.readLine());
        reader.close();
    }

    @Test
    void testAutoCloseableInterface() throws IOException {
        Files.writeString(testFile, "test");

        assertDoesNotThrow(() -> {
            try (IReader reader = new Reader(testFile.toString())) {
                assertEquals("test", reader.readLine());
            }
        });
    }

    @Test
    void testCloseWithoutRead() throws IOException {
        Files.writeString(testFile, "test");

        IReader reader = new Reader(testFile.toString());
        assertDoesNotThrow(() -> reader.close());
    }

    @Test
    void testReadNegativeNumbers() throws Exception {
        Files.writeString(testFile, "-10\n-5.5\n-999\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("-10", reader.readLine());
        assertEquals("-5.5", reader.readLine());
        assertEquals("-999", reader.readLine());
        reader.close();
    }

    @Test
    void testReadZero() throws Exception {
        Files.writeString(testFile, "0\n0.0\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("0", reader.readLine());
        assertEquals("0.0", reader.readLine());
        reader.close();
    }

    @Test
    void testReadLongLine() throws Exception {
        String longLine = "a".repeat(10000);
        Files.writeString(testFile, longLine);

        IReader reader = new Reader(testFile.toString());
        assertEquals(longLine, reader.readLine());
        reader.close();
    }

    @Test
    void testReadScientificNotation() throws Exception {
        Files.writeString(testFile, "1.5E10\n2.5E-5\n");

        IReader reader = new Reader(testFile.toString());
        assertEquals("1.5E10", reader.readLine());
        assertEquals("2.5E-5", reader.readLine());
        reader.close();
    }

    @Test
    void testReadMaxIntegerValue() throws Exception {
        Files.writeString(testFile, String.valueOf(Integer.MAX_VALUE));

        IReader reader = new Reader(testFile.toString());
        assertEquals(String.valueOf(Integer.MAX_VALUE), reader.readLine());
        reader.close();
    }

    @Test
    void testReadWithMixedLineEndings() throws Exception {
        Files.writeString(testFile, "Line 1\nLine 2\rLine 3\r\nLine 4");

        IReader reader = new Reader(testFile.toString());
        assertEquals("Line 1", reader.readLine());
        assertEquals("Line 2", reader.readLine());
        assertEquals("Line 3", reader.readLine());
        assertEquals("Line 4", reader.readLine());
        reader.close();
    }
}
