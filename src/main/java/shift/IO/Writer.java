package shift.IO;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private final boolean append;
    private final String intFileName;
    private final String floatFileName;
    private final String stringFileName;
    private FileWriter intWriter;
    private FileWriter floatWriter;
    private FileWriter stringWriter;

    public Writer(String intFile, String floatFile, String stringFile, boolean append) {
        this.intFileName = intFile;
        this.floatFileName = floatFile;
        this.stringFileName = stringFile;
        this.append = append;
        this.intWriter = null;
        this.floatWriter = null;
        this.stringWriter = null;
    }

    /**
     * 
     * @return True if write was successful, false otherwise.
     */
    public boolean Write(int i) {
        if (this.intWriter == null) {
            try {
                this.intWriter = new FileWriter(intFileName, append);
            } catch (IOException e) {
                System.err.println("Failed to create buffered writer for output file: " + e.getMessage());
                return false;
            }
        }
        return WriteImpl(i, this.intWriter);
    }

    public boolean Write(float f) {
        if (this.floatWriter == null) {
            try {
                this.floatWriter = new FileWriter(floatFileName, append);
            } catch (IOException e) {
                System.err.println("Failed to create buffered writer for output file: " + e.getMessage());
                return false;
            }
        }
        return WriteImpl(f, this.floatWriter);
    }

    public boolean Write(String s) {
        if (this.stringWriter == null) {
            try {
                this.stringWriter = new FileWriter(stringFileName, append);
            } catch (IOException e) {
                System.err.println("Failed to create buffered writer for output file: " + e.getMessage());
                return false;
            }
        }
        return WriteImpl(s, this.stringWriter);
    }

    private <T> boolean WriteImpl(T t, FileWriter writer) {
        try {
            writer.write(t + System.lineSeparator());
            return true;
        } catch (IOException e) {
            System.err.println("Failed to write to output file: " + e.getMessage());
            return false;
        }
    }

    public void Close() {
        try {
            if (intWriter != null) {
                intWriter.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            try {
                intWriter.flush();
            } catch (IOException ex) {
                System.err.println(e.getMessage());
            }
        }
        try {
            if (floatWriter != null) {
                floatWriter.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            try {
                floatWriter.flush();
            } catch (IOException ex) {
                System.err.println(e.getMessage());
            }
        }
        try {
            if (stringWriter != null) {
                stringWriter.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            try {
                stringWriter.flush();
            } catch (IOException ex) {
                System.err.println(e.getMessage());
            }
        }
    }
}
