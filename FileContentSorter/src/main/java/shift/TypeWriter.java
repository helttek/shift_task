package shift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TypeWriter {
    private final Config cfg;

    public TypeWriter(Config cfg) {
        this.cfg = cfg;
    }

    public void Write(int i) {
        WriteImpl(i, cfg.GetIntFile());
    }

    public void Write(float f) {
        WriteImpl(f, cfg.GetFloatFile());
    }

    public void Write(String s) {
        WriteImpl(s, cfg.GetStringFile());
    }

    private <T> void WriteImpl(T t, String fileName) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, cfg.DoAppend()));
            writer.write(String.valueOf(t)); // TODO: why does it not work?
        } catch (IOException e) {
            System.err.println("Failed to write to output file: " + e.getMessage());
        }
    }
}
