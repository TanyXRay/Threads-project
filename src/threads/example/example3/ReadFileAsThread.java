package threads.example.example3;

import java.util.*;
import java.io.*;

/**
 * Класс, инкапсулирующий поток чтения массива чисел из файла.
 */
class ReadFileAsThread extends Thread {
    private int[] array;
    private String filename;
    private Thread thread;

    public ReadFileAsThread(String filename, String threadName) {
        this.filename = filename;
        thread = new Thread(this, threadName);
    }

    public int[] get() {
        return array;
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
            System.out.println("Begin thread: " + thread.getName());
            try (FileInputStream input = new FileInputStream(filename);
                 Scanner scanner = new Scanner(input)) {

                int count = scanner.nextInt();
                array = new int[count];
                for (int i = 0; i < array.length; i++) {
                    array[i] = scanner.nextInt();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("End thread: " + thread.getName());
    }
}

