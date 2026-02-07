package shift.io;

import lombok.extern.java.Log;
import shift.exceptions.io.WriterException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

@Log
public class Writer<T> implements AutoCloseable {
    protected final boolean append;
    protected final Path fileName;
    protected FileWriter fileWriter;

    public Writer(Path file, boolean append) {
        fileName = file;
        this.append = append;
        fileWriter = null;
    }

    public void write(T t) throws IOException {
        prepareWriter();
        writeImpl(t);
    }

    private void prepareWriter() {
        if (fileWriter == null) {
            try {
                fileWriter = new FileWriter(fileName.toFile(), append);
            } catch (IOException e) {
                throw new WriterException(e.getMessage());
            }
        }
    }

    private void writeImpl(T t) throws IOException {
        try {
            fileWriter.write(t + System.lineSeparator());
        } catch (IOException e) {
            log.warning("Failed to write payload \"" + t + "\" to output file \"" + fileName + "\": " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            throw new WriterException(e.getMessage());
        }
    }
}
