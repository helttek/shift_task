package shift;

public class FileContentSorter {
    private Config cfg;
    private Statistics stats;
    private Sorter sorter;

    public FileContentSorter(Config cfg, Sorter sorter, Statistics stats) {
        this.cfg = cfg;
        this.stats = stats;
        this.sorter = sorter;
    }

    public void run() {
        sorter.sort(cfg);
        stats.print();
    }
}
