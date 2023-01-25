package threads.producer_consumer;

import java.util.Scanner;

/**
 * Класс-тест ключевых слов wait и notify.
 * wait и notify вызываются только в пределах synchronized блока и на том объекте,
 * на котором явно вызываем эти методы.
 */
public class WaitNotifyThreadTest {

    public static void main(String[] args) {
        WaitAndNotify objectTest = new WaitAndNotify();

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

class WaitAndNotify {
    private Scanner scanner = new Scanner(System.in);

    public void produce() {
        synchronized (this) {
            try {
                System.out.println("Producer thread started...");
                wait();// 1)- отдаем intrinsic lock, 2)- ждем пока будет вызван notify
                System.out.println("Producer thread resumed...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void consume() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (this){
            System.out.println("Waiting for return key pressed");
            scanner.nextLine();
            notify();
        }
    }
}
