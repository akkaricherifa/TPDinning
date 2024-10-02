package diningphilosophers;

public class ChopStick {
    private static int stickCount = 0;
    private boolean iAmFree = true;
    private final int myNumber;

    public ChopStick() {
        myNumber = ++stickCount;
    }

    synchronized public boolean tryTake(int delay) throws InterruptedException {

        if (!iAmFree) {
            wait(delay);
            if (iAmFree) {
                return false;
            }
        }
            iAmFree = false;
            return true;

    }

    synchronized public void release() {
        iAmFree=true;
        notifyAll();
        System.out.println("baguette " + myNumber + " relâchée");
    }

   @Override
    public String toString() {
        return "baguette #" + myNumber;
    }

    public boolean isTaken() {
        return true;
    }
}
