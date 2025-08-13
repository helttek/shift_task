package shift;

import shift.Args.ArgsParser;
import shift.Args.ArgsValidator;
import shift.Core.*;
import shift.IO.Writer;
import shift.Statistics.*;

public class Main {
    public static void main(String[] args) {
        try {
            ArgsParser parser = new ArgsParser();
            ArgsValidator validator = new ArgsValidator();
            Config cfg = new Config(validator.validate(parser.parse(args)));

            Statistics stats = null;
            if (cfg.IsShortStatistics()) {
                stats = new ShortStatistics();
            }
            if (cfg.IsFullStatistics()) {
                stats = new FullStatistics();
            }

            Writer writer = new Writer(cfg.GetIntFile(), cfg.GetFloatFile(), cfg.GetStringFile(), cfg.DoAppend());
            Sorter sorter = new Sorter(cfg, stats, writer);

            sorter.sort();
            if (stats != null) {
                stats.print();
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}