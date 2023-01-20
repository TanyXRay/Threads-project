package threads.atomic.atomic_reference;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
    private static String message = "hello";
    private static AtomicReference<String> atomicReference;

    public static void main(final String[] arguments) {
        atomicReference = new AtomicReference(message);

        Thread thread = new Thread(() -> {
            String init = "First thread ";
            atomicReference.set(init);
            boolean success = false;
            while (!success) {
                String prevValue = atomicReference.get();
                String newValue = atomicReference.get() + "complete";
                success = atomicReference.compareAndSet(prevValue, newValue);
                System.out.println("Atomic Reference of Message is: " + atomicReference.get());
            }
        });

        thread.start();
    }
}
