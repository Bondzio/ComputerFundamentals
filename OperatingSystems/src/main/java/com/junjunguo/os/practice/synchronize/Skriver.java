package com.junjunguo.os.practice.synchronize;

/**
 * This file is part of OperatingSystems.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 04/02/16.
 */
public class Skriver extends Thread {
    // To ord som skal skrives til skjermen av denne skriveren
    private String forsteOrd, andreOrd;
    // Referanse til Skjerm-objektet
    private Skjerm skjerm;

    // Oppretter et nytt Skriver-objekt
    public Skriver(Skjerm skjerm, String forsteOrd, String andreOrd) {
        super();
        this.skjerm = skjerm;
        this.forsteOrd = forsteOrd;
        this.andreOrd = andreOrd;
    }

    // Metoden som utføres av denne Skriver-tråden
    public void run() {
        for (int i = 0; i < 5; i++) {
            // Skriv ut de to ordene
            skjerm.skrivUt(forsteOrd, andreOrd);
            // Vent noen tidels sekunder
            try {
                Thread.sleep((int) (Math.random() * 700));
            } catch (InterruptedException e) {
                // Kommer hit hvis noen kalte interrupt() på denne tråden.
                // Det skjer ikke i denne applikasjonen.
            }
        }
    }

    // Metoden som blir kjørt når applikasjonen starter
    public static void main(String[] args) {
        if (args.length < 4) {
            args = new String[]{"kake", "spade", "bamse", "mums"};
        }

        // Opprett skjerm-objektet
        Skjerm skjerm = new Skjerm();
        // Start to skrivertråder som skriver ut ordene som ble gitt inn fra kommandolinja
        Skriver skriver1, skriver2;
        skriver1 = new Skriver(skjerm, args[0], args[1]);
        skriver1.start();
        skriver2 = new Skriver(skjerm, args[2], args[3]);
        skriver2.start();
    }
}
