package shift.cli;

import lombok.extern.java.Log;
import shift.config.Config;
import shift.exceptions.ConfigCreationErrorException;
import shift.exceptions.NoInputFilesException;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

@Log
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
            if (!isValidPath(args.GetOption(OptionNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME.getOptionName()).getFirst())) {
                log.log(Level.WARNING, "Warning: invalid result files path, files will be created in " + DEFAULT_OUTPUT_DIRECTORY_PATH + " directory.");
                return DEFAULT_OUTPUT_DIRECTORY_PATH;
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return DEFAULT_OUTPUT_DIRECTORY_PATH;
        }

        return args.GetOption(OptionNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME.getOptionName()).getFirst();
    }

    private String validateOutputFilesNamePrefixOption() {
        try {
            if (!isValidPrefix(args.GetOption(OptionNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME.getOptionName()).getFirst())) {
                log.log(Level.WARNING, "Warning: invalid prefix name. Default prefix will be used: \"" + DEFAULT_OUTPUT_FILES_NAME_PREFIX + "\".");
                return DEFAULT_OUTPUT_FILES_NAME_PREFIX;
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return DEFAULT_OUTPUT_FILES_NAME_PREFIX;
        }
        return args.GetOption(OptionNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME.getOptionName()).getFirst();
    }

    private boolean validateOutputFilesAppendOption() {
        return args.GetOption(OptionNamesEnum.OUTPUT_FILES_APPEND_OPTION_NAME.getOptionName()) != null;
    }

    private boolean validateShortStatisticsOption() {
        return args.GetOption(OptionNamesEnum.SHORT_STATISTICS_OPTION_NAME.getOptionName()) != null;
    }

    private boolean validateFullStatisticsOption() {
        return args.GetOption(OptionNamesEnum.FULL_STATISTICS_OPTION_NAME.getOptionName()) != null;
    }

    private List<String> validateInputFiles() {
        List<String> validFiles = args.getInputFiles().stream()
                .filter(this::isValidPath)
                .toList();

        if (validFiles.isEmpty()) {
            throw new NoInputFilesException("No valid input files provided. Check your file paths.");
        }
        return validFiles;
    }

    public Config getConfig() {
        String outputFilesNamePrefix = validateOutputFilesNamePrefixOption();
        String outputDirectoryPath = validateOutputDirectoryPathOption();

        Path intFile = constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DEFAULT_INT_OUTPUT_FILENAME_WITHOUT_PREFIX);
        Path floatFile = constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DEFAULT_FLOAT_OUTPUT_FILENAME_WITHOUT_PREFIX);
        Path stringFile = constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DEFAULT_STRING_OUTPUT_FILENAME_WITHOUT_PREFIX);

        try {
            return new Config(
                    validateOutputFilesAppendOption(),
                    validateShortStatisticsOption(),
                    validateFullStatisticsOption(),
                    validateInputFiles(),
                    intFile,
                    floatFile,
                    stringFile
            );
        } catch (NoInputFilesException e) {
            throw new ConfigCreationErrorException("Failed to get input files: " + e.getMessage());
        }
    }

    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
            return true;
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
    }

    private boolean isValidPrefix(String prefix) {
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
