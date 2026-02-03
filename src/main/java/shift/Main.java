package shift;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            FileContentSorter fcs = new FileContentSorter(args);
            fcs.start();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}