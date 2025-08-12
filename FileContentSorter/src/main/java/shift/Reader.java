package shift;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private BufferedReader reader;

    public Reader(String fileName) {
        try {
            this.reader = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            System.out.println("Failed to find file \"" + fileName + "\".");
        }
    }

    public String readLine() throws IOException {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new IOException("Failed to read from file: " + e.getMessage());
        }
    }
}
