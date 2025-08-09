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
            System.out.println("Warning: invalid result files path, files will be created in " + cfg.GetDefaultPath()
                    + " directory.");
        }
        if (!IsVaildPrefix(cfg.GetPrefix())) {
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

    private boolean IsVaildPrefix(String prefix) {
        if (prefix == null) {
            return true;
        }
        if (prefix.indexOf("/") != -1) {
            return false;
        }
        return true;
    }
}
