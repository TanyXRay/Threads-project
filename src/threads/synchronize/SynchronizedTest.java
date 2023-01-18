package threads.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizedTest {

    public static void main(String[] args) {
        new Worker().main();
    }
}

class Worker {
    /*Создаем 2 объекта для параллельной синхронизации, чтобы при выполнении метода addToList1 потоком,
     который первый его начал выполнять, то 2-й поток не ждал бы, когда освободится первый поток,
      а начал бы параллельно выполнять второй метод addToList2.
      Все это благодаря синхронизации потоков на конкретных объектах в этих методах
     */
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    private Random random = new Random();

    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    private void addToList1() {
        /*
        Синхронизируемся на мониторе первого объекта потоком, который будет его вызывать монитор lock2 при этом свободен
        и может быть занят вторым потоком и наоборот.
        В результате нет состояния гонки, когда может произойти недетерминированный результат!
         */
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            list1.add(random.nextInt(100));
        }
    }

    private void addToList2() {
        /*
        Синхронизируемся на мониторе второго объекта потоком, который будет его вызывать
         */
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            list2.add(random.nextInt(100));
        }
    }

    private void work() {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() {
        long before = System.currentTimeMillis();

        Thread thread1 = new Thread(() -> work());
        Thread thread2 = new Thread(() -> work());

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long after = System.currentTimeMillis();
        System.out.println("Programm took " + (after - before) + " ms to run");
        System.out.println("list1 " + list1.size());
        System.out.println("list2 " + list2.size());
    }
}
