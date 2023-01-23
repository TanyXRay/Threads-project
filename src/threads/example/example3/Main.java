package threads.example.example3;

public class Main {

    public static void main(String[] args) {
        // Запись массивов в разные файлы в разных потоках
        // 1. Создаем три ссылки на целочисленные массивы
        int[] arrayTest1;
        int[] arrayTest2;
        int[] arrayTest3;

        // 2. Создаем три потока
        ReadFileAsThread t1 = new ReadFileAsThread("arrayTest1.txt", "t1");
        ReadFileAsThread t2 = new ReadFileAsThread("arrayTest2.txt", "t2");
        ReadFileAsThread t3 = new ReadFileAsThread("arrayTest3.txt", "t3");

        // 3. Запускаем потоки на выполнение
        t1.start();
        t2.start();
        t3.start();

        // 4. Дожидаемся завершения потоков с помощью метода join()
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // 5. Читаем массивы
        arrayTest1 = t1.get();
        arrayTest2 = t2.get();
        arrayTest3 = t3.get();

        // 6. Выводим массивы в консоль
        System.out.print("arrayTest1 = [");
        for (int d : arrayTest1)
            System.out.print(d + " ");
        System.out.println("]");

        System.out.print("arrayTest2 = [");
        for (int d : arrayTest2)
            System.out.print(d + " ");
        System.out.println("]");

        System.out.print("arrayTest3 = [");
        for (int d : arrayTest3)
            System.out.print(d + " ");
        System.out.println("]");
    }
}
