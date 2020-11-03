package paaohjelma;

import ohtu.ohtuvarasto.Varasto;

public class Main {

    public static void main(String[] args) {
        Varasto warez = new Varasto(100.0);
        System.out.println("Varasto: " + warez);
    }
}
