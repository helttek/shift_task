package shift.cli;

import lombok.extern.java.Log;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import shift.exceptions.ArgsParsingErrorException;

import java.util.logging.Level;

@Log
public class ArgsParser {
    private final Options options;
    private final String[] args;
    private final CommandLineParser parser;

    public ArgsParser(String[] args) {
        this.args = args;
        options = new Options();
        setupOptions();
        parser = new DefaultParser();
    }

    private void setupOptions() {
        addOption(OptionNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME.getOptionName(), true, "Sets a path for the output files");
        addOption(OptionNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME.getOptionName(), true, "Adds prefix to the output files");
        addOption(OptionNamesEnum.OUTPUT_FILES_APPEND_OPTION_NAME.getOptionName(), false, "Appends to the existing files");
        addOption(OptionNamesEnum.SHORT_STATISTICS_OPTION_NAME.getOptionName(), false, "Outputs short statistics for the parsed files");
        addOption(OptionNamesEnum.FULL_STATISTICS_OPTION_NAME.getOptionName(), false, "Outputs full statistics for the parsed files");
    }

    private void addOption(String optionName, boolean hasArg, String description) {
        try {
            options.addOption(new Option(optionName, hasArg, description));
        } catch (IllegalArgumentException e) {
            log.log(Level.WARNING, "Failed to create option " + optionName + ". Proceeding without using the specified option.");
        }
    }

    public Args parse() throws ArgsParsingErrorException {
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw new ArgsParsingErrorException(e.getMessage());
        }

        Args ret = new Args(cmd.getArgList());
        for (var option : cmd.getOptions()) {
            ret.addOption(option.getOpt(), option.getValuesList());
        }
        return ret;
    }

}
