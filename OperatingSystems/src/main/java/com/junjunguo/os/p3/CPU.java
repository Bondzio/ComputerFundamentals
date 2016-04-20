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

    /**
     * Creates a new CPU with the given parameters.
     *
     * @param cpuQueue   The CPU queue to be used.
     * @param maxCpuTime The Round Robin time quant to be used.
     * @param statistics A reference to the statistics collector.
     */
    public CPU(Queue cpuQueue, long maxCpuTime, Statistics statistics) {
        this.cpuQueue = cpuQueue;
        this.maxCpuTime = maxCpuTime;
        this.statistics = statistics;
    }

    /**
     * Switch process : Get a new process from the cpu queue to cpu.
     *
     * @return the next process from the cpu queue, null if the queue was empty.
     */
    public Process switchProcess() {
        if (!cpuQueue.isEmpty()) {
            Process process = (Process) cpuQueue.removeNext();
            activeProcess = process;
            process.enterCPU(clock);
        } else {
            activeProcess = null;
        }
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