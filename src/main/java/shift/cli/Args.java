package shift.cli;

import java.util.HashMap;
import java.util.Iterator;
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

    public boolean noInputFiles() {
        return this.files.isEmpty();
    }

    public Iterator<String> GetFilesIterator() {
        return this.files.iterator();
    }

    public List<String> getInputFiles() {
        return files;
    }

    public List<String> GetOption(String key) {
        return this.options.get(key);
    }
}