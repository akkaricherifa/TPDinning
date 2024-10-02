package diningphilosophers;

import java.io.IOException;

public class Table {

    public static void main(String[] args) {
        ChopStick baguette1 = new ChopStick();
        ChopStick baguette2 = new ChopStick();
        ChopStick baguette3 = new ChopStick();
        Philosopher sb = new Philosopher("Barreau", baguette1, baguette2); sb.start();
        Philosopher ac = new Philosopher("Combes", baguette2, baguette3); ac.start();
        Philosopher rb = new Philosopher("Bastide", baguette3, baguette1); rb.start();
        // Arrêter le programme lors d'une touche au clavier
        try {
            System.in.read();
        } catch (IOException ex) {}
        sb.leaveTable();
        ac.leaveTable();
        rb.leaveTable();
    }
}
