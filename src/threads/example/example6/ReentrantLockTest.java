package threads.example.example6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Класс, схожий с synchronized.
 * Простой пример.
 */
public class ReentrantLockTest {

    public static void main(String[] args) {
        Task task = new Task();

        Thread firstThread = new Thread(task::firstThreadIncrement);
        Thread secondThread = new Thread(task::secondThreadIncrement);

        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        task.showCounter();
    }
}

class Task {
    private int counter;
    private Lock lock = new ReentrantLock();//создаем объект лок для блокировки/разблокировки потоков

    private void increment() {
        for (int i = 0; i < 100000; i++) {
            counter++;
        }
    }

    public void firstThreadIncrement() {
        lock.lock();//метод блокировки
        increment();
        lock.unlock();//метод разблокировки
    }

    public void secondThreadIncrement() {
        lock.lock();
        increment();
        lock.unlock();
    }

    public void showCounter() {
        System.out.println(counter);
    }
}
