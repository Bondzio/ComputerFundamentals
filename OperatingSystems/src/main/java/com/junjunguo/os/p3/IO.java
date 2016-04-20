package com.junjunguo.os.p3;

/**
 * This file is part of OperatingSystems.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 17/04/16.
 */
public class IO implements Constants {
    private Queue      ioQueue;
    private Process    activeProcess;
    private Statistics statistics;
    private long       avgIoTime;
    private Gui        gui;
    private long clock = 0;

    /**
     * Instantiates a new IO.
     *
     * @param gui        Reference to the GUI interface.
     * @param ioQueue    The I/O queue to be used.
     * @param avgIoTime  The average length of an I/O operation.
     * @param statistics the statistics
     */
    public IO(Gui gui, Queue ioQueue, long avgIoTime, Statistics statistics) {
        this.ioQueue = ioQueue;
        this.avgIoTime = avgIoTime;
        this.statistics = statistics;
        this.gui = gui;
    }

    /**
     * get a new process from I/O queue, updates process statistics
     *
     * @return next process from the queue, null if queue was empty
     */
    public Process switchProcess() {
        if (!ioQueue.isEmpty()) {
            Process p = (Process) ioQueue.removeNext();
            activeProcess = p;
            p.enterIO(this.clock);
        } else {
            activeProcess = null;
        }
        gui.setIoActive(activeProcess); // update GUI
        return activeProcess;
    }

    /**
     * Add io request to IO queue and try to start IO operation.
     *
     * @param requestingProcess the requesting process
     * @return the event
     */
    public Event addIoRequest(Process requestingProcess) {
        ioQueue.insert(requestingProcess); // Add the process to the I/O queue
        requestingProcess.enterIOQueue(this.clock);
        if (activeProcess == null) { // Check if a new I/O operation can be started: The device is free
            Process p = switchProcess();
            if (p != null) {
                statistics.nofProcessedIoOperations++; // Update statistics
                gui.setIoActive(p);
                return new Event(END_IO, clock + getAvgIoTime()); // duration I/O operation, return END_IO event
            }
            gui.setIoActive(null);
        }
        return null;
    }

    /**
     * Remove current IO process from processing.
     *
     * @return the removed process.
     */
    public Process removeProcess() {
        Process process = activeProcess;
        activeProcess = null;
        if (process != null) {
            process.leaveIO(clock);
        }
        return process;
    }

    /**
     * This method is called when a discrete amount of time has passed.
     *
     * @param timePassed The amount of time that has passed since the last call to this method.
     */
    public void timePassed(long timePassed) {
        statistics.ioQueueLengthTime += ioQueue.getQueueLength() * timePassed;
        if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength) {
            statistics.ioQueueLargestLength = ioQueue.getQueueLength();
        }
        clock += timePassed;
    }

    /**
     * Gets avg io time.
     *
     * @return the avg io time
     */
    public long getAvgIoTime() {
        return avgIoTime;
    }
}