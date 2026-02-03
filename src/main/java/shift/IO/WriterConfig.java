package shift.IO;

public record WriterConfig(
        String intFile,
        String floatFile,
        String stringFile,
        boolean append
) {
}
