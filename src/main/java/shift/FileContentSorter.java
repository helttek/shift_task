package shift;

import shift.Core.Sorter;
import shift.IO.Writer;
import shift.Statistics.FullStatistics;
import shift.Statistics.ShortStatistics;
import shift.Statistics.Statistics;
import shift.cli.ArgsParser;
import shift.cli.ArgsValidator;
import shift.config.Config;
import shift.exceptions.ArgsParsingErrorException;
import shift.exceptions.ConfigCreationErrorException;
import shift.exceptions.FileContentSorterCreationErrorException;

public class FileContentSorter {
    private Statistics stats;
    private final Sorter sorter;

    //TODO:
    // - add custom expressions for errors in config parsing, validating
    // - add comments wherever it's necessary

    public FileContentSorter(String[] args) {
        ArgsParser argsParser = new ArgsParser(args);
        ArgsValidator argsValidator;
        try {
            argsValidator = new ArgsValidator(argsParser.parse());
        } catch (ArgsParsingErrorException e) {
            throw new FileContentSorterCreationErrorException("Failed to parse command line arguments: " + e.getMessage());
        }
        Config cfg;
        try {
            cfg = argsValidator.getConfig();
        } catch (ConfigCreationErrorException e) {
            throw new FileContentSorterCreationErrorException("Failed to create a config: " + e.getMessage());
        }

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
