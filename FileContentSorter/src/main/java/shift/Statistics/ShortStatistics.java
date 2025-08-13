package shift.Statistics;

public class ShortStatistics extends Statistics {
    private long intNum;
    private long floatNum;
    private long strNum;

    public ShortStatistics() {
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
