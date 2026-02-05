package shift.statistics;

public class ShortStatistics extends Statistics {

    public ShortStatistics() {
        intNum = 0;
        floatNum = 0;
        strNum = 0;
    }

    @Override
    public void print() {
        System.out.println("---------------STATISTICS-----------------");
        System.out.println("Number of integers: " + intNum);
        System.out.println("Number of floats: " + floatNum);
        System.out.println("Number of strings: " + strNum);
    }

    @Override
    public void collect(String s) {
        strNum++;
    }

    @Override
    public void collect(int i) {
        intNum++;
    }

    @Override
    public void collect(float f) {
        floatNum++;
    }
}
