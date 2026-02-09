package shift.cli;

import lombok.extern.java.Log;
import shift.config.Config;
import shift.config.DefaultConfigValues;
import shift.exceptions.config.ConfigCreationException;
import shift.exceptions.cli.NoInputException;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

@Log
public record ArgsValidator(Args args) {

    private String validateOutputDirectoryPathOption() {
        try {
            if (!isValidPath(args.GetOptionValues(OptionEnum.OUTPUT_DIRECTORY.getShortName()).getFirst())) {
                log.log(Level.WARNING, "Warning: invalid result files path, files will be created in " + DefaultConfigValues.OUT_DIR + " directory.");
                return DefaultConfigValues.OUT_DIR;
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return DefaultConfigValues.OUT_DIR;
        }

        return args.GetOptionValues(OptionEnum.OUTPUT_DIRECTORY.getShortName()).getFirst();
    }

    private String validateOutputFilesNamePrefixOption() {
        try {
            if (!isValidPrefix(args.GetOptionValues(OptionEnum.PREFIX.getShortName()).getFirst())) {
                log.log(Level.WARNING, "Warning: invalid prefix name. Default prefix will be used: \"" + DefaultConfigValues.PREFIX + "\".");
                return DefaultConfigValues.PREFIX;
            }
        } catch (NullPointerException | NoSuchElementException e) {
            return DefaultConfigValues.PREFIX;
        }
        return args.GetOptionValues(OptionEnum.PREFIX.getShortName()).getFirst();
    }

    private boolean validateOutputFilesAppendOption() {
        return args.GetOptionValues(OptionEnum.APPEND.getShortName()) != null;
    }

    private boolean validateShortStatisticsOption() {
        return args.GetOptionValues(OptionEnum.SHORT_STATS.getShortName()) != null;
    }

    private boolean validateFullStatisticsOption() {
        return args.GetOptionValues(OptionEnum.FULL_STATS.getShortName()) != null;
    }

    private List<String> validateInputFiles() throws NoInputException {
        List<String> validFiles = args.getInputFiles().stream()
                .filter(this::isValidPath)
                .toList();

        if (validFiles.isEmpty()) {
            throw new NoInputException("No valid input files provided. Check your file paths.");
        }
        return validFiles;
    }

    public Config getConfig() throws ConfigCreationException {
        String outputFilesNamePrefix = validateOutputFilesNamePrefixOption();
        String outputDirectoryPath = validateOutputDirectoryPathOption();

        Path intFile = constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DefaultConfigValues.INT_FILE);
        Path floatFile = constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DefaultConfigValues.FLOAT_FILE);
        Path stringFile = constructOutputFilePath(outputDirectoryPath, outputFilesNamePrefix, DefaultConfigValues.STR_FILE);

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
        } catch (NoInputException e) {
            throw new ConfigCreationException("Failed to get input files: " + e.getMessage());
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
            Paths.get(prefix + DefaultConfigValues.INT_FILE);
            return true;
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
    }

    private Path constructOutputFilePath(String pathToFileName, String fileNamePrefix, String fileName) {
        try {
            return Paths.get(pathToFileName, fileNamePrefix + fileName);
        } catch (InvalidPathException | NullPointerException ex) {
            return Paths.get(DefaultConfigValues.OUT_DIR, DefaultConfigValues.PREFIX + fileName);
        }
    }
}
