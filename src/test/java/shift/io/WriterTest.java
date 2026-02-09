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
    void testWriteInteger() throws Exception {
        IWriter<Integer> writer = new Writer<>(testFile, false);
        writer.write(123);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("123", lines.get(0));
    }

    @Test
    void testWriteFloat() throws Exception {
        IWriter<Float> writer = new Writer<>(testFile, false);
        writer.write(45.67f);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("45.67", lines.get(0));
    }

    @Test
    void testWriteString() throws Exception {
        IWriter<String> writer = new Writer<>(testFile, false);
        writer.write("Hello World");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("Hello World", lines.get(0));
    }

    @Test
    void testAppendMode() throws Exception {
        Files.writeString(testFile, "existing\n");

        IWriter<String> writer = new Writer<>(testFile, true);
        writer.write("appended");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("existing", lines.get(0));
        assertEquals("appended", lines.get(1));
    }

    @Test
    void testOverwriteMode() throws Exception {
        Files.writeString(testFile, "old content\n");

        IWriter<String> writer = new Writer<>(testFile, false);
        writer.write("new content");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("new content", lines.get(0));
    }

    @Test
    void testCloseWithoutWrite() {
        IWriter<String> writer = new Writer<>(testFile, false);
        assertDoesNotThrow(() -> writer.close());
    }


    @Test
    void testWriteMixedTypes() throws Exception {
        IWriter<Object> writer = new Writer<>(testFile, false);
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
    void testWriteEmptyString() throws Exception {
        IWriter<String> writer = new Writer<>(testFile, false);
        writer.write("");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("", lines.get(0));
    }

    @Test
    void testWriteNegativeNumber() throws Exception {
        IWriter<Integer> writer = new Writer<>(testFile, false);
        writer.write(-999);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("-999", lines.get(0));
    }

    @Test
    void testWriteZero() throws Exception {
        IWriter<Integer> writer = new Writer<>(testFile, false);
        writer.write(0);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("0", lines.get(0));
    }

    @Test
    void testWriteSpecialCharacters() throws Exception {
        IWriter<String> writer = new Writer<>(testFile, false);
        writer.write("!@#$%^&*()");
        writer.write("Unicode: \u00E9\u00F1\u00FC");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("!@#$%^&*()", lines.get(0));
        assertEquals("Unicode: \u00E9\u00F1\u00FC", lines.get(1));
    }

    @Test
    void testWriteLargeNumber() throws Exception {
        IWriter<Long> writer = new Writer<>(testFile, false);
        writer.write(Long.MAX_VALUE);
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals(String.valueOf(Long.MAX_VALUE), lines.get(0));
    }

    @Test
    void testAutoCloseableInterface() throws IOException {
        assertDoesNotThrow(() -> {
            try (IWriter<String> writer = new Writer<>(testFile, false)) {
                writer.write("test");
            }
        });

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(1, lines.size());
        assertEquals("test", lines.get(0));
    }

    @Test
    void testWriteWithWhitespace() throws Exception {
        IWriter<String> writer = new Writer<>(testFile, false);
        writer.write("  spaces  ");
        writer.write("\ttabs\t");
        writer.close();

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(2, lines.size());
        assertEquals("  spaces  ", lines.get(0));
        assertEquals("\ttabs\t", lines.get(1));
    }

    @Test
    void testWriteMultipleSessionsAppend() throws Exception {
        try (IWriter<String> writer1 = new Writer<>(testFile, false)) {
            writer1.write("first");
        }

        try (IWriter<String> writer2 = new Writer<>(testFile, true)) {
            writer2.write("second");
        }

        try (IWriter<String> writer3 = new Writer<>(testFile, true)) {
            writer3.write("third");
        }

        List<String> lines = Files.readAllLines(testFile);
        assertEquals(3, lines.size());
        assertEquals("first", lines.get(0));
        assertEquals("second", lines.get(1));
        assertEquals("third", lines.get(2));
    }
}
