package shift.core;

import lombok.extern.java.Log;
import shift.exceptions.core.FileSortingException;
import shift.exceptions.core.LineSortingException;
import shift.exceptions.io.ReaderException;
import shift.io.IWriter;
import shift.io.Reader;
import shift.io.Writer;
import shift.statistics.ShortStatistics;
import shift.statistics.Statistics;
import shift.config.Config;

import java.io.IOException;
import java.util.Objects;

@Log
public class Sorter implements AutoCloseable {
    private final Config cfg;
    private final Statistics stats;
    private final IWriter<Integer> intWriter;
    private final IWriter<Float> floatWriter;
    private final IWriter<String> stringWriter;

    public Sorter(Config cfg, Statistics stats) {
        this.cfg = cfg;
        this.stats = Objects.requireNonNullElseGet(stats, ShortStatistics::new);
        this.intWriter = new Writer<>(cfg.intFile(), cfg.append());
        this.floatWriter = new Writer<>(cfg.floatFile(), cfg.append());
        this.stringWriter = new Writer<>(cfg.stringFile(), cfg.append());
    }

    public void sort() {
        for (var file : cfg.inputFiles()) {
            try {
                sortFile(file);
                log.info("File \"" + file + "\" sorted successfully.");
            } catch (FileSortingException e) {
                log.warning("Failed to sort file \"" + file + "\": " + e.getMessage());
            }
        }
    }

    private void sortFile(String file) throws FileSortingException {
        try (Reader reader = new Reader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sortLine(line);
            }
        } catch (ReaderException | IOException e) {
            throw new FileSortingException("Error processing file " + file + ": " + e.getMessage());
        }
    }

    private void sortLine(String line) {
        try {
            if (tryParseInt(line)) return;
            if (tryParseFloat(line)) return;

            stringWriter.write(line);
            stats.collect(line);
        } catch (IOException e) {
            throw new LineSortingException("Write error: " + e.getMessage());
        }
    }

    private boolean tryParseInt(String line) throws IOException {
        try {
            int i = Integer.parseInt(line);
            intWriter.write(i);
            stats.collect(i);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean tryParseFloat(String line) throws IOException {
        try {
            float f = Float.parseFloat(line);
            floatWriter.write(f);
            stats.collect(f);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void close() {
        try {
            intWriter.close();
            floatWriter.close();
            stringWriter.close();
        } catch (Exception e) {
            log.severe("Failed to safely close output writers: " + e.getMessage());
        }
    }
}