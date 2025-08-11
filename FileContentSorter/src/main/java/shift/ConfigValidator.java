package shift;

import java.io.File;
import java.util.Iterator;

public class ConfigValidator {
    public ConfigValidator() {

    }

    public void validate(Config cfg) {
        if (cfg.GetInputFiles().isEmpty()) {
            throw new RuntimeException("no input files provided");
        }
        if (!IsValidPath(cfg.GetPath())) {
            cfg.SetDefaultPath();
            System.out.println("Warning: invalid result files path, files will be created in " + cfg.GetDefaultPath()
                    + " directory.");
        }
        if (!IsValidPrefix(cfg.GetPrefix())) {
            cfg.SetEmptyPrefix();
            System.out.println("Warning: invalid prefix name.");
        }

        Iterator<String> iterator = cfg.GetInputFiles().iterator();
        while (iterator.hasNext()) {
            String filePath = iterator.next();
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Unknown option or invalid file \"" + filePath + "\".");
            }
        }
    }

    private boolean IsValidPath(String path) {
        if (path == null) {
            return true;
        }
        if (!(new File(path)).exists()) {
            System.err.println("Invalid path \"" + path + "\".");
            return false;
        }
        return true;
    }

    private boolean IsValidPrefix(String prefix) {
        if (prefix == null) {
            return true;
        }
        return !prefix.contains("/");
    }
}
