package shift;

import shift.Core.Sorter;
import shift.IO.Writer;
import shift.Statistics.FullStatistics;
import shift.Statistics.ShortStatistics;
import shift.Statistics.Statistics;
import shift.cli.ArgsParser;
import shift.config.ArgsValidator;
import shift.config.Config;

public class FileContentSorter {
    private Statistics stats;
    private final Sorter sorter;

    //TODO:
    // - add custom expressions for errors in config parsing, validating
    // - add comments wherever it's necessary
    // - work out how do enums work, with string values especially

    public FileContentSorter(String[] args) {
        ArgsParser argsParser = new ArgsParser(args);
        ArgsValidator argsValidator = new ArgsValidator(argsParser.parse());
        Config cfg = argsValidator.getConfig();

        stats = null;
        if (cfg.shortStatistics()) {
            stats = new ShortStatistics();
        }
        if (cfg.fullStatistics()) {
            stats = new FullStatistics();
        }

        sorter = new Sorter(cfg, stats, new Writer(cfg.getWriterConfig()));
    }

    public void start() {
        sorter.sort();
        if (stats != null) {
            stats.print();
        }
    }
}
