package shift;

import java.util.ArrayList;

public class ArgsParser {
    public ArgsParser() {
    }

    public Config parse(String[] args) {
        String curPath = null;
        String curPrefix = null;

        String path = null;
        String prefix = null;
        Boolean prepend = null;
        Boolean shortStats = null;
        Boolean fullStats = null;
        ArrayList<String> inFiles = new ArrayList<String>();

        if (args.length < 1) {
            throw new RuntimeException("no arguments provided.");
        }

        for (int i = 0; i < args.length; i++) {
            if (curPath != null) {
                path = curPath;
                curPath = null;
                continue;
            }
            if (curPrefix != null) {
                prefix = curPrefix;
                curPrefix = null;
                continue;
            }

            switch (args[i]) {
                case "-o":
                    if (i + 1 >= args.length) {
                        System.err.println("Warning: no argument provided for \"-o\" option.");
                        break;
                    }
                    curPath = args[i + 1];
                    break;

                case "-p":
                    if (i + 1 >= args.length) {
                        System.err.println("Warning: no argument provided for \"-p\" option.");
                        break;
                    }
                    curPrefix = args[i + 1];
                    break;

                case "-a":
                    prepend = true;
                    break;

                case "-s":
                    shortStats = true;
                    break;

                case "-f":
                    fullStats = true;
                    break;

                default:
                    inFiles.add(args[i]);
                    break;
            }
        }

        return new Config(path, prefix, prepend, shortStats, fullStats, inFiles);
    }
}
