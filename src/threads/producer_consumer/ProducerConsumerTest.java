package threads.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс-тест по использованию паттерна producer-consumer без вспомогательных библиотек из пакета concurrent.
 */
public class ProducerConsumerTest {

    public static void main(String[] args) {
        ProducerConsumer objectTest = new ProducerConsumer();

        Thread thread1 = new Thread(objectTest::produce);
        Thread thread2 = new Thread(objectTest::consume);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ProducerConsumer {
    private Queue<Integer> queue = new LinkedList(); //используем простую не потокобезопасную очередь
    private final static int LIMIT = 10;//максимальное кол-во элементов для очереди
    private final Object lock = new Object();//будем вызывать wait и notify на этом объекте синхронизации

    public void produce() {
        int value = 0;
        while (true) {

            synchronized (lock) {
                try {
                    while (queue.size() == LIMIT) {
                        lock.wait();
                    }
                    queue.offer(value++);
                    System.out.println(value);
                    lock.notify();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public void consume() {
        while (true) {

            synchronized (lock) {
                try {
                    while (queue.size() == 0) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                queue.poll();
                System.out.println("Queue size is: " + queue.size());
                lock.notify();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
