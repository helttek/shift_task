package shift;

import shift.exceptions.FileContentSorterCreationErrorException;

public class Main {
    public static void main(String[] args) {
        try {
            FileContentSorter fcs = new FileContentSorter(args);
            fcs.start();
        } catch (FileContentSorterCreationErrorException e) {
            System.err.println("Failed to create file content sorter: " + e.getMessage());
        }
    }
}