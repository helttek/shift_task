package shift.statistics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ShortStatisticsTest {

    private ShortStatistics stats;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        stats = new ShortStatistics();
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

        assertTrue(output.contains("Number of integers: 0"));
        assertTrue(output.contains("Number of floats: 0"));
        assertTrue(output.contains("Number of strings: 0"));
    }

    @Test
    void testCollectInteger() {
        stats.collect(42);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 1"));
        assertTrue(output.contains("Number of floats: 0"));
        assertTrue(output.contains("Number of strings: 0"));
    }

    @Test
    void testCollectFloat() {
        stats.collect(3.14f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 0"));
        assertTrue(output.contains("Number of floats: 1"));
        assertTrue(output.contains("Number of strings: 0"));
    }

    @Test
    void testCollectString() {
        stats.collect("test");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 0"));
        assertTrue(output.contains("Number of floats: 0"));
        assertTrue(output.contains("Number of strings: 1"));
    }

    @Test
    void testCollectMultipleIntegers() {
        stats.collect(1);
        stats.collect(2);
        stats.collect(3);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 3"));
    }

    @Test
    void testCollectMultipleFloats() {
        stats.collect(1.1f);
        stats.collect(2.2f);
        stats.collect(3.3f);
        stats.collect(4.4f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of floats: 4"));
    }

    @Test
    void testCollectMultipleStrings() {
        stats.collect("one");
        stats.collect("two");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of strings: 2"));
    }

    @Test
    void testCollectMixedTypes() {
        stats.collect(10);
        stats.collect("text");
        stats.collect(5.5f);
        stats.collect(20);
        stats.collect("another");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 2"));
        assertTrue(output.contains("Number of floats: 1"));
        assertTrue(output.contains("Number of strings: 2"));
    }

    @Test
    void testCollectNegativeInteger() {
        stats.collect(-100);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 1"));
    }

    @Test
    void testCollectNegativeFloat() {
        stats.collect(-99.9f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of floats: 1"));
    }

    @Test
    void testCollectZeroInteger() {
        stats.collect(0);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 1"));
    }

    @Test
    void testCollectZeroFloat() {
        stats.collect(0.0f);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of floats: 1"));
    }

    @Test
    void testCollectEmptyString() {
        stats.collect("");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of strings: 1"));
    }

    @Test
    void testPrintContainsStatisticsHeader() {
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("STATISTICS"));
    }

    @Test
    void testLargeNumberOfIntegers() {
        for (int i = 0; i < 1000; i++) {
            stats.collect(i);
        }
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 1000"));
    }

    @Test
    void testLargeNumberOfFloats() {
        for (int i = 0; i < 500; i++) {
            stats.collect((float) i);
        }
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of floats: 500"));
    }

    @Test
    void testLargeNumberOfStrings() {
        for (int i = 0; i < 250; i++) {
            stats.collect("string" + i);
        }
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of strings: 250"));
    }

    @Test
    void testCollectStringWithSpecialCharacters() {
        stats.collect("!@#$%^&*()");
        stats.collect("Unicode: \u00E9\u00F1\u00FC");
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of strings: 2"));
    }

    @Test
    void testMaxIntegerValue() {
        stats.collect(Integer.MAX_VALUE);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 1"));
    }

    @Test
    void testMinIntegerValue() {
        stats.collect(Integer.MIN_VALUE);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of integers: 1"));
    }

    @Test
    void testMaxFloatValue() {
        stats.collect(Float.MAX_VALUE);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of floats: 1"));
    }

    @Test
    void testMinFloatValue() {
        stats.collect(Float.MIN_VALUE);
        stats.print();
        String output = outContent.toString();

        assertTrue(output.contains("Number of floats: 1"));
    }
}
