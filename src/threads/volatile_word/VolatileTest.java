package threads.volatile_word;

import java.util.Scanner;

public class VolatileTest {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start(); // поток myThread читает переменную на каждой итерации в методе run

        Scanner scan = new Scanner(System.in);
        scan.nextLine();

        myThread.shutdown(); // поток main меняет переменную
    }

    public static class MyThread extends Thread {
        /*
        На данный момент в программе при запуске работает два потока - main и myThread, и, вполне вероятно,
        что JVM распределила эти потоки на 2 разных ядра. У myThread есть переменная running,
        которую он себе закэширует в кэш, если переменная, используемая 2-мя потоками будет без ключевого слова volatile,
        то она всегда будет равняться true.
         */
        private volatile boolean running = true;

        public void run() {
            while (running) {
                System.out.println("Hello multithreading");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        public void shutdown() {
            this.running = false;
        }
    }
}
