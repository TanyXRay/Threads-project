package threads.example.example2;

public class Main {

    public static void main(String[] args) {
        // Запись массивов в разные файлы в разных потоках
        // 1. Создаем три целочисленных массива
        int[] arrayTest1 = {2, 4, 3, 8, 9, 11, 7};
        int[] arrayTest2 = {1, 8, 7, 6, 3};
        int[] arrayTest3 = {7, 7, 9, 9, 4, 2};

        // 2. Создаем три потока
        SaveAsThread t1 = new SaveAsThread(arrayTest1, "arrayTest1.txt", "t1");
        SaveAsThread t2 = new SaveAsThread(arrayTest2, "arrayTest2.txt", "t2");
        SaveAsThread t3 = new SaveAsThread(arrayTest3, "arrayTest3.txt", "t3");

        // 3. Запускаем потоки на выполнение
        t1.start();
        t2.start();
        t3.start();
    }
}
