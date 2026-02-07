package shift.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FullStatisticsTest {

    private FullStatistics stats;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        stats = new FullStatistics();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testInitialState() {
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("INTEGERS"));
        assertTrue(output.contains("Amount: 0"));
        assertTrue(output.contains("FLOATS"));
        assertTrue(output.contains("STRINGS"));
    }

    @Test
    void testCollectSingleInteger() {
        stats.collect(42);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("INTEGERS"));
        assertTrue(output.contains("Amount: 1"));
        assertTrue(output.contains("Smallest: 42"));
        assertTrue(output.contains("Biggest: 42"));
        assertTrue(output.contains("Sum: 42"));
        assertTrue(output.contains("Average: 42"));
    }

    @Test
    void testCollectMultipleIntegers() {
        stats.collect(10);
        stats.collect(20);
        stats.collect(30);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Amount: 3"));
        assertTrue(output.contains("Smallest: 10"));
        assertTrue(output.contains("Biggest: 30"));
        assertTrue(output.contains("Sum: 60"));
        assertTrue(output.contains("Average: 20.0"));
    }

    @Test
    void testCollectNegativeIntegers() {
        stats.collect(-10);
        stats.collect(-5);
        stats.collect(-15);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Smallest: -15"));
        assertTrue(output.contains("Biggest: -5"));
        assertTrue(output.contains("Sum: -30"));
    }

    @Test
    void testCollectSingleFloat() {
        stats.collect(3.14f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("FLOATS"));
        assertTrue(output.contains("Amount: 1"));
        assertTrue(output.contains("Smallest: 3.14"));
        assertTrue(output.contains("Biggest: 3.14"));
    }

    @Test
    void testCollectMultipleFloats() {
        stats.collect(1.5f);
        stats.collect(2.5f);
        stats.collect(3.5f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("FLOATS"));
        assertTrue(output.contains("Amount: 3"));
        assertTrue(output.contains("Smallest: 1.5"));
        assertTrue(output.contains("Biggest: 3.5"));
        assertTrue(output.contains("Sum: 7.5"));
        assertTrue(output.contains("Average: 2.5"));
    }

    @Test
    void testCollectNegativeFloats() {
        stats.collect(-1.5f);
        stats.collect(-2.5f);
        stats.collect(-3.5f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Smallest: -3.5"));
        assertTrue(output.contains("Biggest: -1.5"));
    }

    @Test
    void testCollectSingleString() {
        stats.collect("hello");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("STRINGS"));
        assertTrue(output.contains("Amount: 1"));
        assertTrue(output.contains("Shortest: 5"));
        assertTrue(output.contains("Longest: 5"));
    }

    @Test
    void testCollectMultipleStrings() {
        stats.collect("a");
        stats.collect("hello");
        stats.collect("world");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("STRINGS"));
        assertTrue(output.contains("Amount: 3"));
        assertTrue(output.contains("Shortest: 1"));
        assertTrue(output.contains("Longest: 5"));
    }

    @Test
    void testCollectEmptyString() {
        stats.collect("");
        stats.collect("test");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Shortest: 0"));
        assertTrue(output.contains("Longest: 4"));
    }

    @Test
    void testCollectMixedTypes() {
        stats.collect(100);
        stats.collect(50);
        stats.collect(2.5f);
        stats.collect(7.5f);
        stats.collect("short");
        stats.collect("a very long string");
        stats.print();
        String output = outContent.toString();

        // Integers
        assertTrue(output.contains("INTEGERS"));
        assertTrue(output.contains("Amount: 2"));
        assertTrue(output.contains("Smallest: 50"));
        assertTrue(output.contains("Biggest: 100"));
        assertTrue(output.contains("Sum: 150"));

        // Floats
        assertTrue(output.contains("FLOATS"));
        assertTrue(output.contains("Amount: 2"));
        assertTrue(output.contains("Smallest: 2.5"));
        assertTrue(output.contains("Biggest: 7.5"));

        // Strings
        assertTrue(output.contains("STRINGS"));
        assertTrue(output.contains("Amount: 2"));
        assertTrue(output.contains("Shortest: 5"));
        assertTrue(output.contains("Longest: 18"));
    }

    @Test
    void testIntegerAverage() {
        stats.collect(10);
        stats.collect(20);
        stats.collect(30);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Average: 20.0"));
    }

    @Test
    void testFloatAverage() {
        stats.collect(1.0f);
        stats.collect(2.0f);
        stats.collect(3.0f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Average: 2.0"));
    }

    @Test
    void testZeroInteger() {
        stats.collect(0);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Smallest: 0"));
        assertTrue(output.contains("Biggest: 0"));
        assertTrue(output.contains("Sum: 0"));
        assertTrue(output.contains("Average: 0"));
    }

    @Test
    void testZeroFloat() {
        stats.collect(0.0f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Smallest: 0.0"));
        assertTrue(output.contains("Biggest: 0.0"));
    }

    @Test
    void testMaxMinInteger() {
        stats.collect(Integer.MAX_VALUE);
        stats.collect(Integer.MIN_VALUE);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Smallest: " + Integer.MIN_VALUE));
        assertTrue(output.contains("Biggest: " + Integer.MAX_VALUE));
    }

    @Test
    void testLargeNumberOfIntegers() {
        for (int i = 1; i <= 100; i++) {
            stats.collect(i);
        }
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Amount: 100"));
        assertTrue(output.contains("Smallest: 1"));
        assertTrue(output.contains("Biggest: 100"));
        assertTrue(output.contains("Sum: 5050"));
        assertTrue(output.contains("Average: 50.5"));
    }

    @Test
    void testLargeNumberOfFloats() {
        for (int i = 1; i <= 10; i++) {
            stats.collect((float) i);
        }
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("FLOATS"));
        assertTrue(output.contains("Amount: 10"));
        assertTrue(output.contains("Smallest: 1.0"));
        assertTrue(output.contains("Biggest: 10.0"));
    }

    @Test
    void testStringWithSpecialCharacters() {
        stats.collect("!@#");
        stats.collect("Unicode: \u00E9\u00F1\u00FC");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Amount: 2"));
        assertTrue(output.contains("Shortest: 3"));
    }

    @Test
    void testPrintWithNoData() {
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("INTEGERS"));
        assertTrue(output.contains("Amount: 0"));
        assertTrue(output.contains("FLOATS"));
        assertTrue(output.contains("Amount: 0"));
        assertTrue(output.contains("STRINGS"));
        assertTrue(output.contains("Amount: 0"));

        assertFalse(output.contains("Smallest:"));
        assertFalse(output.contains("Biggest:"));
    }

    @Test
    void testPrintContainsStatisticsHeader() {
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("STATISTICS"));
    }

    @Test
    void testIntegerSumOverflow() {
        stats.collect(Integer.MAX_VALUE);
        stats.collect(1);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("INTEGERS"));
        assertTrue(output.contains("Amount: 2"));
    }

    @Test
    void testVeryLongString() {
        String longString = "a".repeat(1000);
        stats.collect(longString);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Longest: 1000"));
    }

    @Test
    void testSameValuesMultipleTimes() {
        stats.collect(5);
        stats.collect(5);
        stats.collect(5);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Amount: 3"));
        assertTrue(output.contains("Smallest: 5"));
        assertTrue(output.contains("Biggest: 5"));
        assertTrue(output.contains("Sum: 15"));
        assertTrue(output.contains("Average: 5.0"));
    }
}
