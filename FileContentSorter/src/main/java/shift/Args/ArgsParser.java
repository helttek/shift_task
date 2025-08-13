package shift.Args;

import java.util.ArrayList;
import java.util.HashMap;

public class ArgsParser {
    public ArgsParser() {
    }

    public Args parse(String[] args) {
        String curPath = null;
        String curPrefix = null;

        ArrayList<String> inFiles = new ArrayList<>();
        HashMap<String, String> options = new HashMap<>();

        if (args.length < 1) {
            throw new RuntimeException("No arguments provided.");
        }

        for (int i = 0; i < args.length; i++) {
            if (curPath != null) {
                options.put("-o", curPath);
                curPath = null;
                continue;
            }
            if (curPrefix != null) {
                options.put("-p", curPrefix);
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
                    options.put("-a", "");
                    break;

                case "-s":
                    options.put("-s", "");
                    break;

                case "-f":
                    options.put("-f", "");
                    break;

                default:
                    inFiles.add(args[i]);
                    break;
            }
        }

        return new Args(inFiles, options);
    }
}
