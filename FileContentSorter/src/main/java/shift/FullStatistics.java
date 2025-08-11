package shift;

/*
NOTE: long overflow case is not covered.
(though shouldn't matter as it wouldn't fit in the address space of a process anyway)
*/

//TODO: finish statistics coverage and printing

public class FullStatistics extends Statistics {
    private long intNum;
    private long floatNum;
    private long strNum;

    private long maxInt;
    private double maxFloat;
    private long intSum;
    private double floatSum;
    private long minInt;
    private double minFloat;
    private double intAvg;
    private double floatAvg;

    private long shortestStr;
    private long longestStr;

    public FullStatistics() {
        intNum = 0;
        floatNum = 0;
        strNum = 0;
    }

    @Override
    public void print() {
        System.out.println("---------------STATISTICS-----------------");
        System.out.println("Number of integers: " + this.intNum);
        System.out.println("Number of floats: " + this.floatNum);
        System.out.println("Number of strings: " + this.strNum);
    }

    @Override
    public void collect(String s) {
        this.strNum++;
    }

    @Override
    public void collect(int i) {
        this.intNum++;
    }

    @Override
    public void collect(float f) {
        this.floatNum++;
    }
}
