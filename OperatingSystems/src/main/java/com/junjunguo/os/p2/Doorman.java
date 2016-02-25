package com.junjunguo.os.p2;

/**
 * This class implements the doorman's part of the Barbershop thread synchronization example.
 */
public class Doorman extends Thread {
    private CustomerQueue queue;
    private Gui           gui;
    private boolean       started;

    /**
     * Creates a new doorman.
     *
     * @param queue The customer queue.
     * @param gui   A reference to the GUI interface.
     */
    public Doorman(CustomerQueue queue, Gui gui) {
        this.gui = gui;
        this.queue = queue;
        started = false;
    }

    /**
     * Starts the doorman running as a separate thread.
     */
    public void startThread() {
        started = true;
        notifyC();
        start(); // java start let
    }

    @Override public synchronized void start() {
        super.start();
    }

    @Override public void run() {
        addCustomer();
        super.run();
    }

    /**
     * a thread that add customers to the queue
     */
    private void addCustomer() {
        while (started) {
            try {
                sleep(gui.getDoormanSleepSliderValue());
                newCustomer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * create a new customer and notify a waiting barber that a new customer is waiting
     */
    private void newCustomer() {
        if (!queue.isFull()) {
            queue.newCustomer();
            gui.println(this.getName() + " Doorman: new customer arrived!");
        } else {
            gui.println(this.getName() + " Doorman: too many customers waiting!");
        }
    }

    /**
     * a thread do the notify job. When get a notify from a barber, this method will send a customer ready notify to
     * waiting barbers.
     */
    private void notifyC() {
        new Thread(new Runnable() {
            volatile int s;

            public void run() {
                while (started) {
                    s = queue.queueSize();
                    if (s > 0) {
                        try {
                            synchronized (queue) {
                                queue.wait();
                            }
                            gui.println("-----doorman get notify and notify back");
                            synchronized (gui) {
                                if (s > 2) {
                                    gui.notifyAll();// wakeup all waiting barbers
                                } else if (s == 2) {
                                    gui.notify();
                                    gui.notify();
                                } else {
                                    gui.notify(); // wakeup one waiting barber
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * Stops the doorman thread.
     */
    public void stopThread() {
        started = false;
    }
}
