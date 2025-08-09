package shift;

import java.util.ArrayList;

public class Config {
    String defaultPath;

    String path;
    String prefix;
    Boolean prepend;
    Boolean shortStats;
    Boolean fullStats;
    ArrayList<String> inputFiles;

    public Config(String path, String prefix, Boolean prepend, Boolean shortStats, Boolean fullStats,
            ArrayList<String> files) {
        this.path = path;
        this.fullStats = fullStats;
        this.inputFiles = files;
        this.prepend = prepend;
        this.shortStats = shortStats;
        this.prefix = prefix;
        this.defaultPath = new String(".");
    }

    public ArrayList<String> GetInputFiles() {
        return inputFiles;
    }

    public String GetPath() {
        return path;
    }

    public String GetPrefix() {
        return prefix;
    }

    public Boolean IsShortStatistics() {
        return shortStats;
    }

    public Boolean IsFullStatistics() {
        return fullStats;
    }

    public Boolean DoPrepend() {
        return prepend;
    }

    public String GetDefaultPath() {
        return defaultPath;
    }
}
