package shift.Statistics;

/**
 * Abstract class to provide universal interface for statistics actions.
 *
 */
public abstract class Statistics {
    protected long intNum;
    protected long floatNum;
    protected long strNum;

    public Statistics() {
    }

    public void print() {
    }

    public void collect(String s) {
    }

    public void collect(int i) {
    }


    public void collect(float i) {
    }
}
