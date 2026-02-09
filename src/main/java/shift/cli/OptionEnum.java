package shift.cli;

import lombok.Getter;

@Getter
public enum OptionEnum {
    OUTPUT_DIRECTORY("o", "Sets a path for the output files", true),
    PREFIX("p", "Adds prefix to the output files", true),
    APPEND("a", "Appends to the existing files", false),
    SHORT_STATS("s", "Outputs short statistics for the parsed files", false),
    FULL_STATS("f", "Outputs full statistics for the parsed files", false);

    private final String shortName;
    private final String description;
    private final boolean hasArgs;

    OptionEnum(String shortName, String description, boolean hasArgs) {
        this.shortName = shortName;
        this.description = description;
        this.hasArgs = hasArgs;
    }
}
