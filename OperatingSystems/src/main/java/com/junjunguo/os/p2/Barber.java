package com.junjunguo.os.p2;

/**
 * This class implements the barber's part of the Barbershop thread synchronization example.
 */
public class Barber extends Thread {
    private CustomerQueue queue;
    private Gui           gui;
    private int           pos;
    private boolean       started;

    /**
     * Creates a new barber.
     *
     * @param queue The customer queue.
     * @param gui   The GUI.
     * @param pos   The position of this barber's chair
     */
    public Barber(CustomerQueue queue, Gui gui, int pos) {
        this.queue = queue;
        this.gui = gui;
        this.pos = pos;
        started = false;
    }

    /**
     * Starts the barber running as a separate thread.
     */
    public void startThread() {
        started = true;
        start();
    }

    @Override public void run() {
        barberWork();
        super.run();
    }

    private void barberWork() {
        while (started) {
            try {
                gui.barberIsSleeping(pos);
                gui.println(this.getName() + " Barber-" + pos + " day dreaming... ... zzz zzz");
                //                sleep(1000 + (int) (Math.random() * (2000)));
                sleep(gui.getBarberSleepSliderValue());
                gui.barberIsAwake(pos);
                gui.println(this.getName() + " Barber-" + pos + " is waiting for customer.");
                synchronized (gui) {
                    gui.wait();
                }
                // start hairdresser when notified
                Customer c = queue.getCustomer();
                gui.fillBarberChair(pos, c);
                gui.println(this.getName() + " Barber-" + pos + " start hair dress customer:" + c.getCustomerID());
                //                sleep(20000 + (int) (Math.random() * (30000)));
                sleep(gui.getBarberWorkSliderValue());
                gui.println(this.getName() + " Barber-" + pos + " finished hair dress customer:" + c.getCustomerID());
                gui.emptyBarberChair(pos);
                synchronized (queue) {
                    queue.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Stops the barber thread.
     */
    public void stopThread() {
        started = false;
    }

    // Add more methods as needed
}

