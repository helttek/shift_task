package shift.cli;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Args {
    private final List<String> files;
    private final HashMap<String, List<String>> options;

    public Args(List<String> files) {
        this.files = files;
        this.options = new HashMap<>();
    }

    public void addOption(String name, List<String> values) {
        options.put(name, values);
    }

    public List<String> getInputFiles() {
        return Collections.unmodifiableList(files);
    }

    public List<String> GetOption(String key) {
        return options.get(key);
    }

}