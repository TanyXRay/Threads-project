package threads.example.example4;

public class Main {

    public static void main(String[] args) {
        // Демонстрация сортировки массивов строк в разных потоках
        // 1. Объявляем три массива строк. Массивы имеют одинаковые значения
        String[] arrayStrings1 = {"Aleksa", "Dilan", "Billy", "Daria", "Zoye", "Jill", "Franky", "Erica"};
        String[] arrayStrings2 = {"Aleksa", "Dilan", "Billy", "Daria", "Zoye", "Jill", "Franky", "Erica"};
        String[] arrayStrings3 = {"Aleksa", "Dilan", "Billy", "Daria", "Zoye", "Jill", "Franky", "Erica"};

        // 2. Объявляем экземпляры классов потоков
        SelectionSortThread t1 = new SelectionSortThread(arrayStrings1, "t1:SelectionSort");
        InsertionSortThread t2 = new InsertionSortThread(arrayStrings2, "t2:InsertionSort");
        BubbleSortThread t3 = new BubbleSortThread(arrayStrings3, "t3:BubbleSort");

        // 3. Запуск потоков t1, t2, t3 параллельно
        t1.start();
        t2.start();
        t3.start();

        // 4. Дожидаемся завершения потоков t1, t2, t3
        try {
            t1.getThread().join();
            t2.getThread().join();
            t3.getThread().join();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        // 5. Вывод отсортированных массивов в консоль
        t1.print();
        t2.print();
        t3.print();
    }
}
