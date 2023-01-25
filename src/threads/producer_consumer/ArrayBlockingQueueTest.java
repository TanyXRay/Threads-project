package threads.producer_consumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Реализация паттерна producer-consumer с помощью класса ArrayBlockingQueue
 */
public class ArrayBlockingQueueTest {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);//не может быть более 10 элементов
    private static Random random = new Random();

    public static void main(String[] args) {
        Thread producerThread = new Thread(() -> produce());
        Thread consumerThread = new Thread(() -> consume());

        producerThread.start();//производитель сразу запускается и заполняет очередь из 10 элементов числами от 0 до 99
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void produce() {
        while (true) {
            try {
                queue.put(random.nextInt(100));//когда очередь полна, то производитель ждет начала работы потребителя
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void consume() {
        while (true) {
            try {
                Thread.sleep(100);
                if (random.nextInt(10) == 5) {
                    System.out.println(queue.take());//метод ждет элементы, если их нет
                    System.out.println("Queue size is: " + queue.size());
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

