package shift;

import java.util.ArrayList;
import java.util.Objects;

public final class Config {
    private final String defaultPath;
    private final String defaultPrefix;

    private String path;
    private String prefix;
    private final Boolean append;
    private final Boolean shortStats;
    private final Boolean fullStats;

    private final ArrayList<String> inputFiles;
    private final String intFile;
    private final String floatFile;
    private final String stringFile;

    public Config(String path, String prefix, Boolean append, Boolean shortStats, Boolean fullStats,
                  ArrayList<String> files) {
        this.defaultPath = ".";
        this.defaultPrefix = "";

        this.path = Objects.requireNonNullElse(path, this.defaultPath);
        this.fullStats = fullStats;
        this.append = append;
        this.shortStats = shortStats;
        this.prefix = Objects.requireNonNullElse(prefix, this.defaultPrefix);

        this.inputFiles = files;
        this.intFile = this.path + "/" + this.prefix + "integers.txt";
        this.floatFile = this.path + "/" + this.prefix + "floats.txt";
        this.stringFile = this.path + "/" + this.prefix + "strings.txt";
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

    public Boolean DoAppend() {
        return append;
    }

    public String GetDefaultPath() {
        return defaultPath;
    }

    public String GetIntFile() {
        return intFile;
    }

    public String GetFloatFile() {
        return floatFile;
    }

    public String GetStringFile() {
        return stringFile;
    }

    public void SetEmptyPrefix() {
        this.prefix = this.defaultPrefix;
    }

    public void SetDefaultPath() {
        this.path = this.defaultPath;
    }
}
