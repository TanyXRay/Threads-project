package threads.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Пример решения возникновения дедлоков (взаимной блокировки).
 */
public class DeadlockTest {

    public static void main(String[] args) {
        Runner runner = new Runner();

        Thread thread1 = new Thread(runner::getFirstThread);
        Thread thread2 = new Thread(runner::getSecondThread);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        runner.finished();
    }
}

class Runner {
    private BankAccount bankAccount1 = new BankAccount();
    private BankAccount bankAccount2 = new BankAccount();
    private Random random = new Random();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    /**
     * Если используем вложенные synchronized блоки, то в любом случае забор локов будет в одинаковом порядке,
     * иначе можно вызвать метод tryLock(), чтобы осуществить проверку на локи, и тогда функционал забора локов
     * может быть в разном порядке.
     * @param lock1
     * @param lock2
     */
    private void takeLocks(Lock lock1, Lock lock2) {
        boolean firstLockTaken = false;
        boolean secondLockTaken = false;
        while (true) {
            try {
                firstLockTaken = lock1.tryLock();
                secondLockTaken = lock2.tryLock();
            } finally {
                if (firstLockTaken && secondLockTaken) {
                    return;
                }
                if (firstLockTaken) {
                    lock1.unlock();
                }
                if (secondLockTaken) {
                    lock2.unlock();
                }
            }

            try {
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void getFirstThread() {
        for (int i = 0; i < 10_000; i++) {
            takeLocks(lock1, lock2);
            try {
                BankAccount.transfer(bankAccount1, bankAccount2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public synchronized void getSecondThread() {
        for (int i = 0; i < 10_000; i++) {
            takeLocks(lock2, lock1);
            try {
                BankAccount.transfer(bankAccount2, bankAccount1, random.nextInt(100));
            } finally {
                lock2.unlock();
                lock1.unlock();
            }
        }
    }

    public void finished() {
        int totalBalance = bankAccount1.getBalance() + bankAccount2.getBalance();

        System.out.println("balance of bankAccount1 = " + bankAccount1.getBalance());
        System.out.println("balance of bankAccount2 = " + bankAccount2.getBalance());
        System.out.println("total balance = " + totalBalance);
    }
}

class BankAccount {
    private int balance = 10_000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(BankAccount bankAccount1, BankAccount bankAccount2, int amount) {
        bankAccount1.withdraw(amount);
        bankAccount2.deposit(amount);
    }
}
