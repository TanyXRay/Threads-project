package threads.example.example1;

/**
 * Класс, реализующий дочерний поток в задаче
 */
public class ThreadMinMax implements Runnable {
    private int minimum;
    private int maximum;
    private Thread thread;
    private int[] array;

    public ThreadMinMax(int[] array) {
        this.array = array;
        thread = new Thread(this);
        thread.start();
    }

    public int getMinimum() {
        return minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
            if (min > array[i]) {
                min = array[i];
            }
        }
        maximum = max;
        minimum = min;
    }
}


