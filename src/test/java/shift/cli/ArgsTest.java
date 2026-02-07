package shift.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {
    private Args args;

    @BeforeEach
    void setUp() {
        List<String> files = Arrays.asList("file1.txt", "file2.txt", "file3.txt");
        args = new Args(files);
    }

    @Test
    void testConstructor() {
        List<String> files = Arrays.asList("test1.txt", "test2.txt");
        Args newArgs = new Args(files);
        assertNotNull(newArgs);
        assertEquals(2, newArgs.getInputFiles().size());
    }

    @Test
    void testGetInputFiles() {
        List<String> inputFiles = args.getInputFiles();
        assertEquals(3, inputFiles.size());
        assertEquals("file1.txt", inputFiles.get(0));
        assertEquals("file2.txt", inputFiles.get(1));
        assertEquals("file3.txt", inputFiles.get(2));
    }

    @Test
    void testGetInputFilesReturnsUnmodifiableList() {
        List<String> inputFiles = args.getInputFiles();
        assertThrows(UnsupportedOperationException.class, () -> inputFiles.add("newfile.txt"));
    }

    @Test
    void testAddOption() {
        List<String> optionValues = Arrays.asList("value1", "value2");
        args.addOption("testOption", optionValues);

        List<String> retrievedValues = args.GetOption("testOption");
        assertNotNull(retrievedValues);
        assertEquals(2, retrievedValues.size());
        assertEquals("value1", retrievedValues.get(0));
        assertEquals("value2", retrievedValues.get(1));
    }

    @Test
    void testGetOption_NonExistent() {
        List<String> result = args.GetOption("nonExistentOption");
        assertNull(result);
    }

    @Test
    void testAddMultipleOptions() {
        args.addOption("option1", List.of("val1"));
        args.addOption("option2", List.of("val2", "val3"));
        args.addOption("option3", List.of());

        assertEquals(List.of("val1"), args.GetOption("option1"));
        assertEquals(List.of("val2", "val3"), args.GetOption("option2"));
        assertEquals(List.of(), args.GetOption("option3"));
    }

    @Test
    void testOverwriteOption() {
        List<String> firstValues = List.of("first");
        List<String> secondValues = List.of("second", "third");

        args.addOption("option", firstValues);
        args.addOption("option", secondValues);

        List<String> result = args.GetOption("option");
        assertEquals(2, result.size());
        assertEquals("second", result.get(0));
        assertEquals("third", result.get(1));
    }

    @Test
    void testEmptyFileList() {
        Args emptyArgs = new Args(List.of());
        assertTrue(emptyArgs.getInputFiles().isEmpty());
    }

    @Test
    void testAddOptionWithEmptyValues() {
        args.addOption("emptyOption", List.of());
        List<String> result = args.GetOption("emptyOption");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
