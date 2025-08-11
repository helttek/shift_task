package shift;

public class Main {
    public static void main(String[] args) {
        try {
            ArgsParser parser = new ArgsParser();
            Config cfg = parser.parse(args);

            ConfigValidator validator = new ConfigValidator();
            validator.validate(cfg);

            Statistics stats = null;
            if (cfg.IsShortStatistics()) {
                stats = new ShortStatistics();
            }
            if (cfg.IsFullStatistics()) {
                stats = new FullStatistics();
            }

            Sorter sorter = new Sorter(cfg, stats);

            FileContentSorter fcs = new FileContentSorter(cfg, sorter, stats);

            fcs.run();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}