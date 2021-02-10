package hrd.basic;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Long> concurrentLinkedQueue =new ConcurrentLinkedQueue();
        Long poll = concurrentLinkedQueue.poll();
        boolean empty = concurrentLinkedQueue.isEmpty();
//        concurrentLinkedQueue.
        System.out.println(poll);
    }
}
