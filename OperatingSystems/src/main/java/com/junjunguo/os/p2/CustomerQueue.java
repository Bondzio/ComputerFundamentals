package com.junjunguo.os.p2;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a queue of customers as a circular buffer.
 */
public class CustomerQueue {

    private List<Customer> queue;
    private int            maxQueue;
    private Gui            gui;

    /**
     * Creates a new customer queue.
     *
     * @param queueLength The maximum length of the queue.
     * @param gui         A reference to the GUI interface.
     */
    public CustomerQueue(int queueLength, Gui gui) {
        maxQueue = queueLength;
        this.gui = gui;
        queue = new ArrayList();
    }

    /**
     * add a New customer to the end of the queue, if queue is not full.
     */
    public void newCustomer() {
        if (queue.size() < maxQueue) {
            Customer c = new Customer();
            // use customer id to decide where to sit
            gui.fillLoungeChair((c.getCustomerID() - 1) % 18, c);
            queue.add(c);
        } else {
            gui.println("the queue is full!");
        }
    }

    /**
     * Gets longest waiting customer and remove it form queue.
     *
     * @return the longest waiting customer
     */
    public Customer getCustomer() {
        if (queue.size() == 0) {
            gui.println("an error occurred !");
            return null;
        }
        Customer c = queue.remove(0);
        gui.emptyLoungeChair((c.getCustomerID() - 1) % 18);
        gui.println("queue: Customer-" + c.getCustomerID() + " moved.");
        return c;
    }

    /**
     * Is queue full: boolean.
     *
     * @return the boolean
     */
    public boolean isFull() {
        return maxQueue == queue.size();
    }

    /**
     * Queue size: int.
     *
     * @return the int
     */
    public int queueSize() {
        return queue.size();
    }
}
