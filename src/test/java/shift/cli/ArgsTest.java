package shift.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArgsTest {
    private Args args;
    private final List<String> argsList = Arrays.asList("file1.txt", "file2.txt", "file3.txt");

    @BeforeEach
    void setUp() {
        args = new Args(argsList);
    }

    @Test
    void testGetInputFilesReturnsUnmodifiableList() {
        List<String> inputFiles = args.getInputFiles();
        assertThrows(UnsupportedOperationException.class, () -> inputFiles.add("newfile.txt"));
    }
}
