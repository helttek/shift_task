package shift.Core;

import java.util.ArrayList;
import java.util.Objects;

import shift.Args.Args;

public final class Config {
    private static final String defaultPath = ".";
    private static final String defaultPrefix = "";

    private final Boolean append;
    private final Boolean shortStats;
    private final Boolean fullStats;

    private final ArrayList<String> inputFiles;
    private final String intFile;
    private final String floatFile;
    private final String stringFile;

    public Config(Args validArgs) {
        String path = Objects.requireNonNullElse(validArgs.GetOption("-o"), defaultPath);
        String prefix = Objects.requireNonNullElse(validArgs.GetOption("-p"), defaultPrefix);
        this.fullStats = validArgs.GetOption("-f") != null;
        this.append = validArgs.GetOption("-a") != null;
        this.shortStats = validArgs.GetOption("-s") != null;

        this.inputFiles = validArgs.GetFilesCopy();
        this.intFile = path + "/" + prefix + "integers.txt";
        this.floatFile = path + "/" + prefix + "floats.txt";
        this.stringFile = path + "/" + prefix + "strings.txt";
    }

    public ArrayList<String> GetInputFilesCopy() {
        return new ArrayList<>(inputFiles);
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

    public static String GetDefaultPath() {
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
}
