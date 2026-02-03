package shift.cli;

import org.apache.commons.cli.*;

public class ArgsParser {
    private final Options options;
    private final String[] args;

    public ArgsParser(String[] args) {
        this.args = args;
        options = new Options();

        Option outputDirectoryPathOption = Option
                .builder(String.valueOf(OptionsNamesEnum.OUTPUT_DIRECTORY_PATH_OPTION_NAME))
                .hasArg()
                .desc("Sets a path for the output files")
                .get();
        Option outputFilesPrefixOption = Option
                .builder(String.valueOf(OptionsNamesEnum.OUTPUT_FILES_NAME_PREFIX_OPTION_NAME))
                .hasArg()
                .desc("Adds prefix to the output files")
                .get();
        Option fileAppendOption = Option
                .builder(String.valueOf(OptionsNamesEnum.OUTPUT_FILES_APPEND_OPTION_NAME))
                .desc("Appends to the existing files")
                .get();
        Option shortStatisticsOption = Option
                .builder(String.valueOf(OptionsNamesEnum.SHORT_STATISTICS_OPTION_NAME))
                .desc("Outputs short statistics for the parsed files")
                .get();
        Option fullStatisticsOption = Option
                .builder(String.valueOf(OptionsNamesEnum.FULL_STATISTICS_OPTION_NAME))
                .desc("Outputs full statistics for the parsed files")
                .get();

        options.addOption(outputDirectoryPathOption);
        options.addOption(outputFilesPrefixOption);
        options.addOption(fileAppendOption);
        options.addOption(shortStatisticsOption);
        options.addOption(fullStatisticsOption);
    }

    public Args parse() {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to parse cmd args: " + e.getMessage());
        }

        Args ret = new Args(cmd.getArgList());
        for (var option : cmd.getOptions()) {
            ret.addOption(option.getArgName(), option.getValuesList());
        }
        return ret;
    }

}
