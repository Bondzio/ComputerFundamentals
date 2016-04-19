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
     * Inserts a process into IO Queue and updates statistics for process p
     *
     * @param p The process to insert into queue.
     */
    public void insertProcess(Process p) {
        ioQueue.insert(p);
        p.enterIOQueue(this.clock);
    }

    /**
     * Selects a new process from queue to be processed, updates queue time statistics
     *
     * @return the process that was selected or null if queue was empty
     */
    public Process switchProcess() {
        if (!ioQueue.isEmpty()) {
            Process p = (Process) ioQueue.removeNext();
            activeProcess = p;
            p.enterIO(this.clock);
        } else
            activeProcess = null;

        return activeProcess;
    }


    /**
     * Add io request to IO queue.
     *
     * @param requestingProcess the requesting process
     * @param clock             the clock
     * @return the event
     */
    public Event addIoRequest(Process requestingProcess, long clock) {
        ioQueue.insert(requestingProcess); // Add the process to the I/O queue
        requestingProcess.enterIOQueue(clock);
        return startIoOperation(clock);
    }

    /**
     * Start io operation event.
     *
     * @param clock the clock
     * @return the event
     */
    public Event startIoOperation(long clock) {
        if (activeProcess == null) { // Check if a new I/O operation can be started: The device is free
            if (!ioQueue.isEmpty()) { // process waiting
                activeProcess = (Process) ioQueue.removeNext();
                activeProcess.enterIO(clock);
                gui.setIoActive(activeProcess);// Let the first process in the queue start I/O
                                statistics.nofProcessedIoOperations++; // Update statistics
                // Calculate the duration of the I/O operation and return the END_IO event
                long ioOperationTime = avgIoTime; // time for IO to process an operation
                return new Event(END_IO, clock + ioOperationTime);
            } else { // else no process are waiting for I/O
                gui.setIoActive(null);
            }
        } else {  // else another process is already doing I/O
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
            process.leaveIO(this.clock);
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
     * Is idle boolean.
     *
     * @return true if IO is free, false otherwise
     */
    public boolean isIdle() {
        return activeProcess == null;
    }

    /**
     * Gets avg io time.
     *
     * @return the avg io time
     */
    public long getAvgIoTime() {
        return (long) (avgIoTime * Math.random() * 2);
    }
}