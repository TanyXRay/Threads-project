package threads.example.example5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс, реализующий самоблокировку с обратным отсчетом.
 * Объект этого класса изначально создается с количеством событий,
 * которые должны произойти до того момента, как будет снята самоблокировка.
 * Всякий раз, когда происходит событие, значение счетчика уменьшается.
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);//в аргументах конструктора указываем счетчик-число

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.submit(new Process(i, countDownLatch));
        }

        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();//уменьшение счетчика на единицу
    }

    static class Process implements Runnable {
        private CountDownLatch countDownLatch;
        private int id;

        public Process(int id, CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            this.id = id;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();//пока есть число в счетчике != 0, то main не работает
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Thread with id: " + id + " worked");
        }
    }
}
