package threads.semaphor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        Connection connection = Connection.getConnection();

        for (int i = 0; i < 200; i++) {
            executorService.submit(connection::workWithSemaphore);
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 * Пользователь будет пользоваться одним объектом Connection (применение паттерна Singleton)
 */
class Connection {
    private static Connection connection = new Connection();
    private int connectionsCount;
    private Semaphore semaphore = new Semaphore(10);//больше 10 потоков не сможет выполнить задачу connection

    private Connection() {

    }

    public static Connection getConnection() {
        return connection;
    }

    public void workWithSemaphore() {
        try {
            semaphore.acquire();//нужен для получения разрешения
            doWork();//работаем с ресурсом Connection
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();//освобождаем ресурс (всегда должен быть в блоке finally)
        }
    }

    private void doWork() {
        synchronized (this) {
            connectionsCount++;
            System.out.println(connectionsCount);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (this) {
            connectionsCount--;
        }
    }
}