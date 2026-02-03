package shift.config;

import java.util.List;

public record Config(
        boolean append,
        boolean shortStatistics,
        boolean fullStatistics,
        List<String> inputFiles,
        String intFile,
        String floatFile,
        String stringFile
) {
}
