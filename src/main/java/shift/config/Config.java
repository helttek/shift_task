package shift.config;

import java.nio.file.Path;
import java.util.List;

public record Config(
        boolean append,
        boolean shortStatistics,
        boolean fullStatistics,
        List<String> inputFiles,
        Path intFile,
        Path floatFile,
        Path stringFile
) {
    public WriterConfig getWriterConfig() {
        return new WriterConfig(intFile, floatFile, stringFile, append);
    }
}
