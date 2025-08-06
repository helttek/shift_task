package shift;

public class Main {
    public static void main(String[] args) {
        try {
            ArgsParser parser = new ArgsParser();
            Config cfg = parser.parse(args);

            ConfigValidator validator = new ConfigValidator();
            validator.validate(cfg);

            Statistics stats = new Statistics();
            Sorter sorter = new Sorter();

            FileContentSorter fcs = new FileContentSorter(cfg, sorter, stats);

            fcs.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}