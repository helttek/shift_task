package shift;

public class ArgsParser {
    public ArgsParser() {
    }

    public Config parse(String[] args) {
        return new Config(args);
    }
}
