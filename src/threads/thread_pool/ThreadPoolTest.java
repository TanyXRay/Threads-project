package threads.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {
         /*
         С помощью сервиса по выполнению создаем 2 потока, которые будут работать параллельно в цикле,
         выводя на экран по 2 строки одновременно.
         Submit - способ подачи задачи в пул потоков.
         Передаем в аргументы объект реализующий интерфейс Runnable, т.к. в объекте указаны действия (в методе run)
         */
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            executorService.submit(new Work(i));
        }
        executorService.shutdown();/*инициирует упорядоченное отключение, при котором ранее отправленные задачи выполняются,
                                    но новые задачи не принимаются.
                                   */
        System.out.println("All tasks submitted");

        /*
        awaitTermination - блокируется до тех пор, пока все задачи не завершат выполнение после запроса на завершение работы,
        или не истечет время ожидания, или текущий поток не будет прерван, в зависимости от того, что произойдет раньше.
         */
        try {
            executorService.awaitTermination(2, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Work implements Runnable {
    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Work " + id + " was completed");
    }
}
