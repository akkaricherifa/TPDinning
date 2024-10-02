package diningphilosophers;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Philosopher extends Thread {
    private final static int DELAY = 1000;
    private final ChopStick myLeftStick;
    private final ChopStick myRightStick;
    private boolean jetContinue = true;
    private final Random myRandom = new Random();
    private final String myName;

    public Philosopher(String name, ChopStick left, ChopStick right) {
        myName = name;
        myLeftStick = left;
        myRightStick = right;
    }

    private void think() throws InterruptedException {
        System.out.println(myName + " pense...");
        sleep(DELAY + myRandom.nextInt(DELAY + 1));
        System.out.println(myName + " arrête de penser");
    }

    private void eat() throws InterruptedException {
        System.out.println(myName + " mange...");
        sleep(DELAY + myRandom.nextInt(DELAY + 1));
        System.out.println(myName + " arrête de manger");
    }

    @Override
    public void run() {
        while (jetContinue) {
            try {
                think();

                // Phase 1: Essayer de prendre les baguettes
                if (tryTakeStick(myLeftStick) && tryTakeStick(myRightStick)) {
                    // Phase 2: Manger si les deux baguettes sont disponibles
                    eat();

                    // Relâcher les baguettes après avoir mangé
                    releaseStick(myLeftStick);
                    releaseStick(myRightStick);
                } else {
                    // Si une des baguettes n'est pas disponible, relâcher celle que le philosophe a déjà prise
                    if (myLeftStick.isTaken()) {
                        releaseStick(myLeftStick);
                    }
                    if (myRightStick.isTaken()) {
                        releaseStick(myRightStick);
                    }
                    System.out.println(myName + " n'a pas pu prendre les deux baguettes, il réessaiera.");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger("Table").log(Level.SEVERE, myName + " a été interrompu", ex);
            }
        }
        System.out.println(myName + " quitte la table");
    }

    public void leaveTable() {
        jetContinue = false;
    }

    private boolean tryTakeStick(ChopStick stick) throws InterruptedException {
        int delay = myRandom.nextInt(100 + DELAY);
        boolean result = stick.tryTake(delay);
        if (result) {
            System.out.println(myName + " a pris " + stick + " en " + delay + " ms");
        } else {
            System.out.println(myName + " n'a pas pu prendre " + stick + " en " + delay + " ms");
        }
        return result;
    }

    private void releaseStick(ChopStick stick) {
        stick.release();
        System.out.println(myName + " a relâché " + stick);
    }
}
