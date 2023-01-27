package threads.example.example7;

import java.util.Random;

/**
 * Пример прерывания потоков.
 */
public class InterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();

                for (int i = 0; i < 1_000_000_000; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) { //исключение выбрасывается в том потоке, который был прерван
                        System.out.println("Thread was interrupted!");
                        break;
                    }

                    Math.sin(random.nextDouble());
                }
            }
        });

        System.out.println("Starting thread...");

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();//метод прерывания потока
        thread.join();

        System.out.println("Thread has finished");
    }
}
