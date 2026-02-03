package shift;

import shift.Core.Config;
import shift.Core.Sorter;
import shift.IO.Writer;
import shift.Statistics.FullStatistics;
import shift.Statistics.ShortStatistics;
import shift.Statistics.Statistics;

public class FileContentSorter {
    private Statistics stats;
    private final Sorter sorter;

    //TODO:
    // - add custom expressions for errors in config parsing, validating
    // - use cli parsing library apache common cli
    // - add comments wherever it's necessary
    // - rewrite the pathing logic, so that it's cross platform and doesn't use raw strings

    public FileContentSorter(String[] args) {
        Config cfg = new Config(args);

        stats = null;
        if (cfg.IsShortStatistics()) {
            stats = new ShortStatistics();
        }
        if (cfg.IsFullStatistics()) {
            stats = new FullStatistics();
        }

        sorter = new Sorter(cfg, stats, new Writer(cfg.GetWriterConfig()));
    }

    public void start() {
        sorter.sort();
        if (stats != null) {
            stats.print();
        }
    }
}
