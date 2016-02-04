package com.junjunguo.os.practice.synchronize;

/**
 * This file is part of OperatingSystems.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 04/02/16.
 */
public class Skjerm {
    // Siden denne funksjonen er synchronized,
    // kan bare en tråd være inne i den av gangen
    public synchronized void skrivUt(String ord1, String ord2) {
        System.out.print(ord1);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.fillInStackTrace();
        }
        System.out.println(ord2);
    }
}
