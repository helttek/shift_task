package shift;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        try {
            FileContentSorter fcs = new FileContentSorter(args);
            fcs.start();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}