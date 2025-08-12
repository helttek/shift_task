package shift;

import java.io.File;
import java.util.Iterator;

public class ArgsValidator {
    public ArgsValidator() {

    }

    public Args validate(Args args) {
        if (args.NoFiles()) {
            throw new RuntimeException("no input files provided");
        }
        if (!IsValidPath(args.GetOption("-o"))) {
            args.RemoveOption("-o");
            System.out.println("Warning: invalid result files path, files will be created in " + Config.GetDefaultPath()
                    + " directory.");
        }
        if (!IsValidPrefix(args.GetOption("-p"))) {
            args.RemoveOption("-p");
            System.out.println("Warning: invalid prefix name. No prefix will be used.");
        }

        Iterator<String> iterator = args.GetFilesIterator();
        while (iterator.hasNext()) {
            String filePath = iterator.next();
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Unknown option or invalid file \"" + filePath + "\".");
            }
        }

        return args;
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
