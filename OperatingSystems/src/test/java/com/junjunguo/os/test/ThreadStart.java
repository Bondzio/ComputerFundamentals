package com.junjunguo.os.test;

/**
 * This file is part of OperatingSystems.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 24/02/16.
 */
public class ThreadStart {

    public static void main(String[] args) {
        final String[] strings = new String[10];

        Thread thread = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    strings[i] = String.valueOf(i);
                    print(strings);
                    try {
                        Thread.sleep(1000); // in real code this is wrapped in a simple try catch
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

//        thread.run();
        System.out.println("run start : \n");
        thread.start();
        print(strings);
    }

    public static void print(String[] strings) {
        for (String string : strings) {
            System.out.print(string);
        }
        System.out.println();
    }
}
