package threads.atomic.atomic_integer;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Input two count: \n");
        String[] counts = scan.nextLine().trim().split(" ");
        int i = Integer.parseInt(counts[0]);
        int j = Integer.parseInt(counts[1]);

        AtomicIntegerTest test = new AtomicIntegerTest(i, j);

        Thread firstThread = new Thread(() -> {
            test.getI().incrementAndGet();
            test.getJ().incrementAndGet();
            System.out.println("First thread completed: " + test.getI() + " " + test.getJ());
        });

        Thread secondThread = new Thread(() -> {
            test.getI().decrementAndGet();
            test.getJ().decrementAndGet();
            System.out.println("Second thread completed: " + test.getI() + " " + test.getJ());
        });

        Thread thirdThread = new Thread(() -> {
            test.getI().accumulateAndGet(i, (n, m) -> n + m);
            test.getJ().accumulateAndGet(j, (n, m) -> n + m);
            System.out.println("Third thread completed: " + test.getI() + " " + test.getJ());
        });

        firstThread.start();
        sleep(1000);
        secondThread.start();
        sleep(1000);
        thirdThread.start();
    }
}
