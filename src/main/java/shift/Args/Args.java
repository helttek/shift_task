package shift.Args;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Args {
    private final ArrayList<String> files;
    private final HashMap<String, String> options;

    public Args(ArrayList<String> files, HashMap<String, String> options) {
        this.files = files;
        this.options = options;
    }

    public String GetOption(String key) {
        return this.options.get(key);
    }

    public boolean NoFiles() {
        return this.files.isEmpty();
    }

    public Iterator<String> GetFilesIterator() {
        return this.files.iterator();
    }

    public void RemoveOption(String key) {
        this.options.remove(key);
    }

    public ArrayList<String> GetFilesCopy() {
        return new ArrayList<>(this.files);
    }
}
