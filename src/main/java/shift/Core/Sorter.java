package shift.Core;

import shift.IO.Reader;
import shift.IO.Writer;
import shift.Statistics.Statistics;

import java.io.IOException;

public class Sorter {
    private final Config cfg;
    private final Statistics stats;
    private final Writer writer;

    public Sorter(Config cfg, Statistics stats, Writer writer) {
        this.cfg = cfg;
        this.stats = stats;
        this.writer = writer;
    }

    public void sort() {
        if (stats == null) {
            for (String file : cfg.GetInputFilesCopy()) {
                sortFile(new Reader(file));
            }
            writer.Close();
            return;
        }
        for (String file : cfg.GetInputFilesCopy()) {
            sortFileWithStats(new Reader(file));
        }
        writer.Close();
    }

    private void sortFile(Reader reader) {
        try {
            String line = reader.readLine();
            while (line != null) {
                SortAndWriteLine(line);

                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void sortFileWithStats(Reader reader) {
        try {
            String line = reader.readLine();
            while (line != null) {
                SortWithStatsAndWriteLine(line);

                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void SortAndWriteLine(String line) {
        try {
            writer.Write(Integer.parseInt(line));
            return;
        } catch (NumberFormatException ignored) {
        }
        try {
            writer.Write(Float.parseFloat(line));
            return;
        } catch (NumberFormatException ignored) {
        }
        writer.Write(line);
    }

    private void SortWithStatsAndWriteLine(String line) {
        try {
            int i = Integer.parseInt(line);
            if (writer.Write(i)) {
                stats.collect(i);
            }
            return;
        } catch (NumberFormatException ignored) {
        }
        try {
            float d = Float.parseFloat(line);
            if (writer.Write(d)) {
                stats.collect(d);
            }
            return;
        } catch (NumberFormatException ignored) {
        }
        if (writer.Write(line)) {
            stats.collect(line);
        }
    }
}
