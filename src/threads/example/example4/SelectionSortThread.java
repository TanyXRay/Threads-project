package threads.example.example4;

/**
 * Класс, который инкапсулирует поток, сортирующий строки
 * методом выбора в порядке убывания.
 * Поток создается путем наследования класса Thread.
 */
class SelectionSortThread extends Thread {
    private String[] arrayStrings;
    private Thread thread;

    public SelectionSortThread(String[] arrayStrings, String threadName) {
        this.arrayStrings = arrayStrings;
        thread = new Thread(this, threadName);
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
        System.out.println("Begin => " + thread.getName());
        int i, j, k;
        String s;

        //Цикл сортировки выбором в порядке убывания строк
        for (i = 0; i < arrayStrings.length; i++) {
            k = i;

            // Поиск наибольшего (максимального) элемента
            s = arrayStrings[i];

            for (j = i + 1; j < arrayStrings.length; j++) {
                if (arrayStrings[j].compareTo(s) > 0) {
                    k = j; // индекс максимального элемента
                    s = arrayStrings[j];
                }
            }
            // Обменять местами максимальный элемент с AS[i]
            arrayStrings[k] = arrayStrings[i];
            arrayStrings[i] = s;
        }
        System.out.println("End => " + thread.getName());
    }

    public String[] get() {
        return arrayStrings;
    }

    public Thread getThread() {
        return thread;
    }

    public void print() {
        System.out.print(thread.getName() + " = [");
        for (String s : arrayStrings)
            System.out.print(s + " ");
        System.out.println("]");
    }
}

/**
 * Класс, инкапсулирующий поток, в котором сортируется массив строк методом вставки.
 * Поток создается путем наследования класса Thread.
 * Сортировка происходит в порядке убывания элементов.
 */
class InsertionSortThread extends Thread {
    private String[] arrayStrings;
    private Thread thread;

    public InsertionSortThread(String[] arrayStrings, String threadName) {
        this.arrayStrings = arrayStrings;
        thread = new Thread(this, threadName);
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
        //Здесь нужно реализовать сортировку методом вставки
        System.out.println("Begin => " + thread.getName());
        int i, j;
        String s;

        //Цикл сортировки вставками
        for (i = 0; i < arrayStrings.length; i++) {
            s = arrayStrings[i];

            // Поиск места элемента в последовательности
            for (j = i - 1; j >= 0 && arrayStrings[j].compareTo(s) < 0; j--) {
                // сдвинуть элемент вправо насколько возможно
                arrayStrings[j + 1] = arrayStrings[j];
            }
            arrayStrings[j + 1] = s;
        }
        System.out.println("End => " + thread.getName());
    }

    public String[] get() {
        return arrayStrings;
    }

    public Thread getThread() {
        return thread;
    }

    public void print() {
        System.out.print(thread.getName() + " = [");
        for (String s : arrayStrings)
            System.out.print(s + " ");
        System.out.println("]");
    }
}

/**
 * Класс, инкапсулирующий поток, в котором происходит сортировка пузырьком.
 * Сортировка строк происходит в нисходящем порядке.
 * * Поток создается путем наследования класса Thread.
 */
class BubbleSortThread extends Thread {
    private String[] arrayStrings;
    private Thread thread;

    public BubbleSortThread(String[] arrayStrings, String threadName) {
        this.arrayStrings = arrayStrings;
        thread = new Thread(this, threadName);
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
        //Пузырьковая сортировка
        System.out.println("Begin => " + thread.getName());
        int i, j;
        String s;

        //Цикл сортировки
        for (i = 0; i < arrayStrings.length; i++) {

            for (j = arrayStrings.length - 1; j > i; j--) {
                // внутренний цикл прохода
                // сортировка по убыванию
                if (arrayStrings[j - 1].compareTo(arrayStrings[j]) < 0) {
                    s = arrayStrings[j];
                    arrayStrings[j] = arrayStrings[j - 1];
                    arrayStrings[j - 1] = s;
                }
            }
        }
        System.out.println("End => " + thread.getName());
    }

    public String[] get() {
        return arrayStrings;
    }

    public Thread getThread() {
        return thread;
    }

    public void print() {
        System.out.print(thread.getName() + " = [");
        for (String s : arrayStrings)
            System.out.print(s + " ");
        System.out.println("]");
    }
}

