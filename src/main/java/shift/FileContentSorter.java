package shift;

import shift.core.Sorter;
import shift.statistics.FullStatistics;
import shift.statistics.ShortStatistics;
import shift.statistics.Statistics;
import shift.cli.ArgsParser;
import shift.cli.ArgsValidator;
import shift.config.Config;
import shift.exceptions.cli.ArgsParsingException;
import shift.exceptions.config.ConfigCreationException;
import shift.exceptions.FileContentSorterException;

public class FileContentSorter {
    private Statistics stats;
    private final Sorter sorter;

    public FileContentSorter(String[] args) throws FileContentSorterException {
        ArgsParser argsParser = new ArgsParser(args);
        ArgsValidator argsValidator;
        try {
            argsValidator = new ArgsValidator(argsParser.parse());
        } catch (ArgsParsingException e) {
            throw new FileContentSorterException("Failed to parse command line arguments: " + e.getMessage());
        }
        Config cfg;
        try {
            cfg = argsValidator.getConfig();
        } catch (ConfigCreationException e) {
            throw new FileContentSorterException("Failed to create a config: " + e.getMessage());
        }

        stats = null;
        if (cfg.shortStatistics()) {
            stats = new ShortStatistics();
        }
        if (cfg.fullStatistics()) {
            stats = new FullStatistics();
        }

        sorter = new Sorter(cfg, stats);
    }

    public void start() {
        sorter.sort();
        if (stats != null) {
            stats.print();
        }
    }
}
