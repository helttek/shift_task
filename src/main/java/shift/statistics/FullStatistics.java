package shift.statistics;

public class FullStatistics extends Statistics {
    private long maxInt;
    private long intSum;
    private long minInt;
    private double intAvg;

    private double maxFloat;
    private double floatSum;
    private double minFloat;
    private double floatAvg;

    private long shortestStr;
    private long longestStr;

    public FullStatistics() {
        intNum = 0;
        floatNum = 0;
        strNum = 0;

        maxInt = Long.MIN_VALUE;
        intSum = 0;
        minInt = Long.MAX_VALUE;
        intAvg = 0;

        maxFloat = Double.MIN_VALUE;
        floatSum = 0;
        minFloat = Double.MAX_VALUE;
        floatAvg = 0;

        shortestStr = Long.MAX_VALUE;
        longestStr = 0;
    }

    @Override
    public void print() {
        System.out.println("---------------STATISTICS-----------------");

        printInt();
        System.out.println();

        printFloat();
        System.out.println();

        printStrings();
    }

    private void printInt() {
        System.out.println("INTEGERS");
        System.out.println("Amount: " + intNum);
        if (intNum > 0) {
            System.out.println("Smallest: " + minInt);
            System.out.println("Biggest: " + maxInt);
            System.out.println("Sum: " + intSum);
            System.out.println("Average: " + intAvg);
        }
    }

    private void printFloat() {
        System.out.println("FLOATS");
        System.out.println("Amount: " + floatNum);
        if (floatNum > 0) {
            System.out.println("Smallest: " + minFloat);
            System.out.println("Biggest: " + maxFloat);
            System.out.println("Sum: " + floatSum);
            System.out.println("Average: " + floatAvg);
        }
    }

    private void printStrings() {
        System.out.println("STRINGS");
        System.out.println("Amount: " + strNum);
        if (strNum > 0) {
            System.out.println("Shortest: " + shortestStr);
            System.out.println("Longest: " + longestStr);
        }
    }

    @Override
    public void collect(String s) {
        shortestStr = Math.min(shortestStr, s.length());
        longestStr = Math.max(longestStr, s.length());
        strNum++;
    }

    @Override
    public void collect(int i) {
        minInt = Math.min(minInt, i);
        maxInt = Math.max(maxInt, i);
        intSum += i;
        intNum++;
        intAvg = (double) intSum / intNum;
    }

    @Override
    public void collect(float f) {
        minFloat = Math.min(minFloat, f);
        maxFloat = Math.max(maxFloat, f);
        floatSum += f;
        floatNum++;
        floatAvg = floatSum / floatNum;
    }
}
