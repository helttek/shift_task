package shift;

import java.io.*;

public class Sorter {
    private Config cfg;
    private Statistics stats;

    public Sorter(Config cfg, Statistics stats) {
        this.cfg = cfg;
        this.stats = stats;
    }

    public void sort() {
        for (String file : cfg.GetInputFiles()) {
            sortFile(file);
        }
    }

    private void sortFile(String file) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            try {
                String line = reader.readLine();
                while (line != null) {
                    sortLine(line);
                    try {
                        line = reader.readLine();
                    } catch (IOException e) {
                        System.err.println("Failed to read from file \"" + file + "\": " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to read from file \"" + file + "\": " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to find file \"" + file + "\".");
        }
    }

    private void sortLine(String line) {
        TypeWriter writer = new TypeWriter(this.cfg);
        if (stats == null) {
            try {
                int i = Integer.parseInt(line);
                writer.Write(i);
                return;
            } catch (NumberFormatException ignored) {
            }
            try {
                float d = Float.parseFloat(line);
                writer.Write(d);
                return;
            } catch (NumberFormatException ignored) {
            }
        } else {
            try {
                int i = Integer.parseInt(line);
                stats.collect(i);
                writer.Write(i);
                return;
            } catch (NumberFormatException ignored) {
            }
            try {
                float d = Float.parseFloat(line);
                stats.collect(d);
                writer.Write(d);
                return;
            } catch (NumberFormatException ignored) {
            }
            stats.collect(line);
        }

        writer.Write(line);
    }
}
