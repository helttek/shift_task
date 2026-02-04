package shift.config;

import shift.cli.Args;
import shift.cli.OptionNamesEnum;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ArgsValidator {
    private final Args args;
    private static final String DEFAULT_OUTPUT_DIRECTORY_PATH = ".";
    private static final String DEFAULT_OUTPUT_FILES_NAME_PREFIX = "";
    private static final String DEFAULT_INT_OUTPUT_FILENAME_WITHOUT_PREFIX = "integers.txt";
    private static final String DEFAULT_STRING_OUTPUT_FILENAME_WITHOUT_PREFIX = "strings.txt";
    private static final String DEFAULT_FLOAT_OUTPUT_FILENAME_WITHOUT_PREFIX = "floats.txt";

    public ArgsValidator(Args args) {
        this.args = args;
    }

    private String validateOutputDirectoryPathOption() {
        try {
            if (!isValidPath(args.GetOption(String.valueOf(OptionNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME)).getFirst())) {
                System.out.println("Warning: invalid result files path, files will be created in " + DEFAULT_OUTPUT_DIRECTORY_PATH + " directory.");
                return DEFAULT_OUTPUT_DIRECTORY_PATH;
            }
        } catch (NullPointerException ignored) {
            return DEFAULT_OUTPUT_DIRECTORY_PATH;
        }
        return args.GetOption(String.valueOf(OptionNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME)).getFirst();
    }

    private String validateOutputFilesNamePrefixOption() {
        try {
            if (!isValidPrefix(args.GetOption(String.valueOf(OptionNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME)).getFirst())) {
                System.out.println("Warning: invalid prefix name. No prefix will be used.");
                return DEFAULT_OUTPUT_FILES_NAME_PREFIX;
            }
        } catch (NullPointerException ignored) {
            return DEFAULT_OUTPUT_FILES_NAME_PREFIX;
        }
        return args.GetOption(String.valueOf(OptionNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME)).getFirst();
    }

    private boolean validateOutputFilesAppendOption() {
        return args.GetOption(String.valueOf(OptionNamesEnum.OUTPUT_FILES_APPEND_OPTION_NAME)) != null;
    }

    private boolean validateShortStatisticsOption() {
        return args.GetOption(String.valueOf(OptionNamesEnum.SHORT_STATISTICS_OPTION_NAME)) != null;
    }

    private boolean validateFullStatisticsOption() {
        return args.GetOption(String.valueOf(OptionNamesEnum.FULL_STATISTICS_OPTION_NAME)) != null;
    }

    private List<String> validateInputFiles() {
        List<String> validFiles = args.getInputFiles().stream()
                .filter(this::isValidPath)
                .toList();

        if (validFiles.isEmpty()) {
            throw new RuntimeException("No valid input files provided. Please check your file paths.");
        }
        return validFiles;
    }

    public Config getConfig() {
        String outputFilesNamePrefix = validateOutputFilesNamePrefixOption();
        String outputDirectoryPath = validateOutputDirectoryPathOption();

        return new Config(
                validateOutputFilesAppendOption(),
                validateShortStatisticsOption(),
                validateFullStatisticsOption(),
                validateInputFiles(),
                constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DEFAULT_INT_OUTPUT_FILENAME_WITHOUT_PREFIX),
                constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DEFAULT_FLOAT_OUTPUT_FILENAME_WITHOUT_PREFIX),
                constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DEFAULT_STRING_OUTPUT_FILENAME_WITHOUT_PREFIX)
        );
    }

    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
            return true;
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
    }

    private static boolean isValidPrefix(String prefix) {
        try {
            Paths.get(prefix + DEFAULT_INT_OUTPUT_FILENAME_WITHOUT_PREFIX);
            return true;
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
    }

    private Path constructOutputFilePath(String pathToFileName, String fileNamePrefix, String fileName) {
        try {
            return Paths.get(pathToFileName, fileNamePrefix + fileName);
        } catch (InvalidPathException | NullPointerException ex) {
            return Paths.get(DEFAULT_OUTPUT_DIRECTORY_PATH, DEFAULT_OUTPUT_FILES_NAME_PREFIX + fileName);
        }
    }
}
