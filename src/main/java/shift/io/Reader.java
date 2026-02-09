package shift.io;

import shift.exceptions.io.ReaderException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader implements IReader {
    private final BufferedReader reader;

    public Reader(String fileName) throws ReaderException {
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            throw new ReaderException(e.getMessage());
        }
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws ReaderException {
        try {
            reader.close();
        } catch (IOException e) {
            throw new ReaderException(e.getMessage());
        }
    }
}
