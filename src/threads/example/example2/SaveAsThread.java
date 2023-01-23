package threads.example.example2;

import java.io.*;

/**
 * Класс, представляющий поток записи массива целых чисел в файл
 */
class SaveAsThread implements Runnable {
    private int[] array;
    private String filename;
    private String threadName;
    private Thread thread;

    /**
     * Конструктор - получает 3 параметра:
     * array - массив, который нужно записать в файл;
     * filename - имя файла, в который записывается массив array;
     * threadName - имя потока.
     */
    public SaveAsThread(int[] array, String filename, String threadName) {
        this.array = array;
        this.filename = filename;
        this.threadName = threadName;
        thread = new Thread(this, "SaveThread");
    }

    public void start() {
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        synchronized (this) {
            System.out.println("Begin thread: " + threadName);
            try (FileOutputStream out = new FileOutputStream(filename);
                 PrintStream printStream = new PrintStream(out)) {

                printStream.println(array.length);
                for (int i = 0; i < array.length; i++) {
                    printStream.println(array[i]);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("End thread: " + threadName);
        }
    }
}

