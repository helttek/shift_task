package shift.IO;

import java.nio.file.Path;

public record WriterConfig(
        Path intFile,
        Path floatFile,
        Path stringFile,
        boolean append
) {
}
