package com.junjunguo.os.p3;

/**
 * This file is part of OperatingSystems.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 17/04/16.
 */
public class CPU {
    private Queue      cpuQueue;
    private long       maxCpuTime;
    private Statistics statistics;
    private long clock = 0;
    private Process activeProcess;
    //    private Gui        gui;

    /**
     * Creates a new CPU with the given parameters.
     *
     * @param gui        A reference to the GUI interface.
     * @param cpuQueue   The CPU queue to be used.
     * @param maxCpuTime The Round Robin time quant to be used.
     * @param statistics A reference to the statistics collector.
     */
    public CPU(Gui gui, Queue cpuQueue, long maxCpuTime, Statistics statistics) {
        this.cpuQueue = cpuQueue;
        this.maxCpuTime = maxCpuTime;
        this.statistics = statistics;
        //        this.gui = gui;
    }

    /**
     * Switch process : .
     *
     * @param clock the clock
     * @return the event
     */
    public Process switchProcess(long clock) {
        if (activeProcess != null) {
            if (!cpuQueue.isEmpty()) {
                //                activeProcess.leaveCPU(clock);
                //                statistics.nofForcedProcessSwitches++;
                //                cpuQueue.insert(activeProcess); // Put the active process at the end of the queue
                Process newProcess = (Process) cpuQueue.removeNext(); // Switch in a new process from the cpu queue
                activeProcess = newProcess;
                activeProcess.enterCPUQueue(clock);
            } // else the queue is empty the active process is allowed to continue
        } else { // No active process, switch in a process if the queue is non-empty
            if (!cpuQueue.isEmpty()) {
                activeProcess = (Process) cpuQueue.removeNext();
                activeProcess.enterCPUQueue(clock);
            } else {
                activeProcess = null;
            }
        }
        return activeProcess;
    }

    /**
     * Selects a new process from queue to be processed, updates queue time statistics
     *
     * @return the process that was selected or null if queue was empty
     */
    public Process switchProcess() {
        if (!cpuQueue.isEmpty()) {
            Process p = (Process) cpuQueue.removeNext();
            activeProcess = p;
            p.enterCPU(clock);
        } else
            activeProcess = null;

        return activeProcess;
    }

    /**
     * Stops current process.
     *
     * @return the active process that was being processed
     */
    public Process removeProcess() {
        Process p = activeProcess;
        activeProcess = null;
        if (p != null) p.leaveCPU(clock);
        return p;
    }

    /**
     * Insert process to cpu queue
     *
     * @param p the p
     */
    public void insertProcess(Process p) {
        cpuQueue.insert(p);
        p.enterCPUQueue(clock); // Add this process to the CPU queue!
    }

    /**
     * This method is called when a discrete amount of time has passed.
     *
     * @param timePassed The amount of time that has passed since the last call to this method.
     */
    public void timePassed(long timePassed) {
        statistics.cpuQueueLengthTime += cpuQueue.getQueueLength() * timePassed;
        if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength) {
            statistics.cpuQueueLargestLength = cpuQueue.getQueueLength();
        }
        clock += timePassed;
    }

    /**
     * Is idle boolean.
     *
     * @return true if cpu is free, false otherwise
     */
    public boolean isIdle() {
        return activeProcess == null;
    }

    /**
     * The Round Robin time quant to be used.
     *
     * @return the max cpu time
     */
    public long getMaxCpuTime() {
        return maxCpuTime;
    }

}