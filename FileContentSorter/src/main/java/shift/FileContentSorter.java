package shift;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileContentSorter {
    private Config cfg;
    private Statistics stats;
    private Sorter sorter;

//    private final int QUEUE_SIZE = 10;
//    private final int CONSUMERS_NUM = 3;
//    private final int PRODUCER_NUM = 1;
//    private BlockingQueue<String> queue;

    public FileContentSorter(Config cfg, Sorter sorter, Statistics stats) {
        this.cfg = cfg;
        this.stats = stats;
        this.sorter = sorter;

//        this.queue = new ArrayBlockingQueue<>(QUEUE_SIZE);
//        for (int i = 0; i < PRODUCER_NUM; ++i) {
//            Thread producer = new Thread(new Producer(queue));
//            producer.start();
//        }
//        for (int i = 0; i < CONSUMERS_NUM; i++) {
//            Thread consumer = new Thread(new Consumer(queue));
//            consumer.start();
//        }
    }

    public void run() {
        sorter.sort();

        stats.print();
    }
}
