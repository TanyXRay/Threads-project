package threads.atomic.atomic_integer;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    private AtomicInteger i;
    private AtomicInteger j;

    public AtomicIntegerTest(int i, int j) {
        this.i = new AtomicInteger(i);
        this.j = new AtomicInteger(j);
    }

    public AtomicInteger getI() {
        return i;
    }

    public void setI(AtomicInteger i) {
        this.i = i;
    }

    public AtomicInteger getJ() {
        return j;
    }

    public void setJ(AtomicInteger j) {
        this.j = j;
    }
}
