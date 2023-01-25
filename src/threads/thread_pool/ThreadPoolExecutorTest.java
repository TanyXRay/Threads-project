package threads.thread_pool;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                1, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new MyReject());
/*
Если поставить одновременно выполняемое кол-во задач (i < 2) меньше параметра corePoolSize,
 то пул расширяется до макс. параметра и в работу включается доп. поток.
 Если наоборот равно или больше, чем corePoolSize, то задачи ставятся в очередь (new LinkedBlockingQueue<>(capacity))
 и, если очередь накапливает задачи, то она не будет задействовать доп. потоки из maximumPoolSize.
 А если очередь забита, тогда используются доп. потоки.
 */
        for (int i = 0; i < 7; i++) {
            MyCallable myCallable = new MyCallable();
            threadPoolExecutor.submit(myCallable);
        }

        threadPoolExecutor.shutdown();
    }
}

/**
 * RejectedExecutionHandler используется в случае, если задач много, а потоков,
 * которые их должны выполнять в очереди, меньше
 */
class MyReject implements RejectedExecutionHandler{

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Rejected task");
    }
}

class MyCallable implements Callable<Long> {
    @Override
    public Long call() {
        try {
            System.out.println("Thread started: " + Thread.currentThread().getId());
            Thread.sleep(2000);
            System.out.println("Thread finished: " + Thread.currentThread().getId());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return Thread.currentThread().getId();
    }
}