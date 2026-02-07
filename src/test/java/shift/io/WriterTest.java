package shift.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import shift.exceptions.io.WriterException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WriterTest {

    @TempDir
    Path tempDir;

    private Path testFile;

    @BeforeEach
    void setUp() {
        testFile = tempDir.resolve("output.txt");
    }

    @Test
    void testWriteInteger() throws IOException {
        Writer<Integer> writer = new Writer<>(testFile, false);
        writer.write(123);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("123", lines.get(0));
    }

    @Test
    void testWriteFloat() throws IOException {
        Writer<Float> writer = new Writer<>(testFile, false);
        writer.write(45.67f);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("45.67", lines.get(0));
    }

    @Test
    void testWriteString() throws IOException {
        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("Hello World");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("Hello World", lines.get(0));
    }

    @Test
    void testWriteMultipleValues() throws IOException {
        Writer<Integer> writer = new Writer<>(testFile, false);
        writer.write(1);
        writer.write(2);
        writer.write(3);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(3, lines.size());
        assertEquals("1", lines.get(0));
        assertEquals("2", lines.get(1));
        assertEquals("3", lines.get(2));
    }

    @Test
    void testAppendMode() throws IOException {
        Files.writeString(testFile, "existing\n");

        Writer<String> writer = new Writer<>(testFile, true);
        writer.write("appended");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("existing", lines.get(0));
        assertEquals("appended", lines.get(1));
    }

    @Test
    void testOverwriteMode() throws IOException {
        Files.writeString(testFile, "old content\n");

        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("new content");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("new content", lines.get(0));
    }

    @Test
    void testCloseWithoutWrite() {
        Writer<String> writer = new Writer<>(testFile, false);
        assertDoesNotThrow(() -> writer.close());
    }

    @Test
    void testMultipleClose() throws IOException {
        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("test");
        writer.close();

        assertDoesNotThrow(() -> writer.close());
    }

    @Test
    void testWriteAfterClose() throws IOException {
        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("test");
        writer.close();

        assertThrows(IOException.class, () -> writer.write("after close"));
    }

    @Test
    void testWriteMixedTypes() throws IOException {
        Writer<Object> writer = new Writer<>(testFile, false);
        writer.write(42);
        writer.write(3.14);
        writer.write("text");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(3, lines.size());
        assertEquals("42", lines.get(0));
        assertEquals("3.14", lines.get(1));
        assertEquals("text", lines.get(2));
    }

    @Test
    void testWriteEmptyString() throws IOException {
        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("", lines.get(0));
    }

    @Test
    void testWriteNegativeNumber() throws IOException {
        Writer<Integer> writer = new Writer<>(testFile, false);
        writer.write(-999);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("-999", lines.get(0));
    }

    @Test
    void testWriteZero() throws IOException {
        Writer<Integer> writer = new Writer<>(testFile, false);
        writer.write(0);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("0", lines.get(0));
    }

    @Test
    void testWriteSpecialCharacters() throws IOException {
        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("!@#$%^&*()");
        writer.write("Unicode: \u00E9\u00F1\u00FC");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("!@#$%^&*()", lines.get(0));
        assertEquals("Unicode: \u00E9\u00F1\u00FC", lines.get(1));
    }

    @Test
    void testWriteLargeNumber() throws IOException {
        Writer<Long> writer = new Writer<>(testFile, false);
        writer.write(Long.MAX_VALUE);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals(String.valueOf(Long.MAX_VALUE), lines.get(0));
    }

    @Test
    void testAutoCloseableInterface() throws IOException {
        assertDoesNotThrow(() -> {
            try (Writer<String> writer = new Writer<>(testFile, false)) {
                writer.write("test");
            }
        });

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("test", lines.get(0));
    }

    @Test
    void testWriteToInvalidPath() {
        Path invalidPath = Path.of("\0invalid");
        Writer<String> writer = new Writer<>(invalidPath, false);

        assertThrows(WriterException.class, () -> writer.write("test"));
    }

    @Test
    void testWriteWithWhitespace() throws IOException {
        Writer<String> writer = new Writer<>(testFile, false);
        writer.write("  spaces  ");
        writer.write("\ttabs\t");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("  spaces  ", lines.get(0));
        assertEquals("\ttabs\t", lines.get(1));
    }

    @Test
    void testWriteMultipleSessionsAppend() throws IOException {
        try (Writer<String> writer1 = new Writer<>(testFile, false)) {
            writer1.write("first");
        }

        try (Writer<String> writer2 = new Writer<>(testFile, true)) {
            writer2.write("second");
        }

        try (Writer<String> writer3 = new Writer<>(testFile, true)) {
            writer3.write("third");
        }

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(3, lines.size());
        assertEquals("first", lines.get(0));
        assertEquals("second", lines.get(1));
        assertEquals("third", lines.get(2));
    }
}
