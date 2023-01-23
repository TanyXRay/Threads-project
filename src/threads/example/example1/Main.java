package threads.example.example1;

public class Main {

    public static void main(String[] args) {
        //Параллельные целочисленные массивы в потоках
        // 1. Объявляем тестируемый массив
        int[] array = {2, 6, -6, 14, 78, 4, 55, -7, 10};

        // 2. Создаем два дочерних потока и получаем ссылки на них
        ThreadMinMax thread1 = new ThreadMinMax(array);
        ThreadMinMax thread2 = new ThreadMinMax(array);

        // 3. Чтение результата
        // Ожидание завершения потоков thread1, thread2- обязательно, иначе можно получить нулевые значения
        try {
            thread1.getThread().join();
            thread2.getThread().join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 4. Вывод результата в консоль
        System.out.println("max = " + thread1.getMaximum());
        System.out.println("min = " + thread2.getMinimum());
    }
}
