package shift.Statistics;

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
        System.out.println("INTEGERS");
        System.out.println("Amount: " + this.intNum);
        System.out.println("Smallest: " + this.minInt);
        System.out.println("Biggest: " + this.maxInt);
        System.out.println("Sum: " + this.intSum);
        System.out.println("Average: " + this.intAvg);
        System.out.println();
        System.out.println("FLOATS");
        System.out.println("Amount: " + this.floatNum);
        System.out.println("Smallest: " + this.minFloat);
        System.out.println("Biggest: " + this.maxFloat);
        System.out.println("Sum: " + this.floatSum);
        System.out.println("Average: " + this.floatAvg);
        System.out.println();
        System.out.println("STRINGS");
        System.out.println("Amount: " + this.strNum);
        System.out.println("Shortest: " + this.shortestStr);
        System.out.println("Longest: " + this.longestStr);
    }

    @Override
    public void collect(String s) {
        this.shortestStr = Math.min(this.shortestStr, s.length());
        this.longestStr = Math.max(this.longestStr, s.length());
        this.strNum++;
    }

    @Override
    public void collect(int i) {
        this.minInt = Math.min(this.minInt, i);
        this.maxInt = Math.max(this.maxInt, i);
        this.intSum += i;
        this.intNum++;
        this.intAvg = (double) this.intSum / this.intNum;
    }

    @Override
    public void collect(float f) {
        this.minFloat = Math.min(this.minFloat, f);
        this.maxFloat = Math.max(this.maxFloat, f);
        this.floatSum += f;
        this.floatNum++;
        this.floatAvg = this.floatSum / this.floatNum;
    }
}
