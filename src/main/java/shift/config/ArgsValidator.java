package shift.config;

import shift.cli.Args;
import shift.cli.OptionsNamesEnum;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class ArgsValidator {
    private final Args args;
    private static final String DEFAULT_OUTPUT_DIRECTORY_PATH = ".";
    private static final String DEFAULT_OUTPUT_FILES_NAME_PREFIX = "";

    public ArgsValidator(Args args) {
        this.args = args;
    }

    private String validateOutputDirectoryPathOption() {
        if (!isValidPath(args.GetOption(String.valueOf(OptionsNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME)).getFirst())) {
            System.out.println("Warning: invalid result files path, files will be created in " + shift.Core.Config.GetDefaultPath()
                    + " directory.");
            return DEFAULT_OUTPUT_DIRECTORY_PATH;
        }
        return args.GetOption("-o").getFirst();
    }

    private String validateOutputFilesNamePrefixOption() {
        if (!isValidPrefix(args.GetOption(String.valueOf(OptionsNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME)).getFirst())) {
            System.out.println("Warning: invalid prefix name. No prefix will be used.");
            return DEFAULT_OUTPUT_FILES_NAME_PREFIX;
        }
        return args.GetOption(String.valueOf(OptionsNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME)).getFirst();
    }

    private boolean validateOutputFilesAppendOption() {
        return args.GetOption(String.valueOf(OptionsNamesEnum.OUTPUT_FILES_APPEND_OPTION_NAME)) != null;
    }

    private boolean validateShortStatisticsOption() {
        return args.GetOption(String.valueOf(OptionsNamesEnum.SHORT_STATISTICS_OPTION_NAME)) != null;
    }

    private boolean validateFullStatisticsOption() {
        return args.GetOption(String.valueOf(OptionsNamesEnum.FULL_STATISTICS_OPTION_NAME)) != null;
    }

    private List<String> validateInputFiles() {
        if (args.noInputFiles()) {
            throw new RuntimeException("no input files provided");
        }
        Iterator<String> iterator = args.GetFilesIterator();
        while (iterator.hasNext()) {
            String filePath = iterator.next();
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File \"" + filePath + "\" doesn't exist.");
            }
        }
        return args.getInputFiles();
    }

    public Config getConfig() {
        String outputFilesNamePrefix = validateOutputFilesNamePrefixOption();
        String outputDirectoryPath = validateOutputDirectoryPathOption();

        return new Config(
                validateOutputFilesAppendOption(),
                validateShortStatisticsOption(),
                validateFullStatisticsOption(),
                validateInputFiles(),
                outputDirectoryPath + "/",
                outputDirectoryPath + "/",
                outputDirectoryPath + "/"
        );
    }

    private static boolean isValidPath(String path) {
        if (path == null) {
            return true;
        }
        if (!(new File(path)).exists()) {
            System.err.println("Invalid path \"" + path + "\".");
            return false;
        }
        return true;
    }

    private static boolean isValidPrefix(String prefix) {
        if (prefix == null) {
            return true;
        }
        return !prefix.contains("/");
    }
}
