package com.junjunguo.os.p3;

/**
 * This class contains a lot of public variables that can be updated by other classes during a simulation, to collect
 * information about the run.
 */
public class Statistics {
    /** The number of processes that have exited the system */
    public long nofCompletedProcesses          = 0;
    /** The number of processes that have entered the system */
    public long nofCreatedProcesses            = 0;
    /** The total time that all completed processes have spent waiting for memory */
    public long totalTimeSpentWaitingForMemory = 0;
    /** The time-weighted length of the memory queue, divide this number by the total time to get average queue
     * length */
    public long memoryQueueLengthTime          = 0;
    /** The largest memory queue length that has occurred */
    public long memoryQueueLargestLength       = 0;
    /** The time-weighted length of the cpu queue, divide this number by the total time to get average queue length */
    public long cpuQueueLengthTime             = 0;
    /** The largest cpu queue length that has occurred */
    public long cpuQueueLargestLength          = 0;
    /**
     * The Io queue largest length.
     */
    public long ioQueueLargestLength           = 0;
    /**
     * The Io queue length time.
     */
    public long ioQueueLengthTime              = 0;
    /** Number of processed IO operations */
    public int  nofProcessedIoOperations       = 0;
    /**
     * The number of forced process switches.
     */
    public int  nofForcedProcessSwitches       = 0;
    /**
     * The Total cpu time spent processing.
     */
    public long totalCPUTimeSpentProcessing    = 0;
    /**
     * The Total number of times in cpu queue.
     */
    public long totalNofTimesInCPUQueue        = 0;
    /**
     * The Total number of times in io queue.
     */
    public long totalNofTimesInIOQueue         = 0;
    /**
     * The Total system time.
     */
    public long totalSystemTime                = 0;
    /**
     * The Total time spent waiting for CPU.
     */
    public long totalTimeSpentWaitingForCpu    = 0;
    /**
     * The Total time spent waiting for I/O to process.
     */
    public long totalTimeSpentWaitingForIo     = 0;
    /**
     * The Total I/O time spent for processing.
     */
    public long totalIOTimeSpentProcessing     = 0;

    /**
     * Prints out a report summarizing all collected data about the simulation.
     *
     * @param simulationLength The number of milliseconds that the simulation covered.
     */
    public void printReport(long simulationLength) {
        log();
        log("Simulation statistics:");
        log();

        log("Number of completed processes:", nofCompletedProcesses);
        log("Number of created processes:", nofCreatedProcesses);
        log("Number of forced process switches:", nofForcedProcessSwitches);
        log("Number of processed IO operations:", nofProcessedIoOperations);
        log("Average throughput(processes per second):", ((float) nofCompletedProcesses / simulationLength * 1000));
        log();

        log("Total CPU time spent processing: ", totalCPUTimeSpentProcessing + " ms");
        log("Fraction of CPU time spent processing: ",
                (float) totalCPUTimeSpentProcessing / simulationLength * 100 + " %");
        log("Total CPU time spent waiting: ", (simulationLength - totalCPUTimeSpentProcessing) + " ms");
        log("Fraction of CPU time spent waiting: ",
                (float) (simulationLength - totalCPUTimeSpentProcessing) / simulationLength * 100 + " %");
        log();

        log("Largest occuring memory queue length:", memoryQueueLargestLength);
        log("Average memory queue length:", (float) memoryQueueLengthTime / simulationLength);
        log("Largest occuring cpu queue length:", cpuQueueLargestLength);
        log("Average cpu queue length:", (float) cpuQueueLengthTime / simulationLength);
        log("Largest occuring I/O queue length:", ioQueueLargestLength);
        log("Average I/O queue length:", (float) ioQueueLengthTime / simulationLength);

        if (nofCompletedProcesses > 0) {
            log("Average # of times a process has been placed in memory queue:", 1);
            log("Average time spent waiting for memory per process:",
                    totalTimeSpentWaitingForMemory / nofCompletedProcesses + " ms");

            log("Average # of times a process has been placed in memory queue:", 1);
            log("Average # of times a process has been placed in cpu queue:",
                    (float) totalNofTimesInCPUQueue / nofCompletedProcesses);
            log("Average # of times a process has been placed in I/O queue:",
                    (float) totalNofTimesInIOQueue / nofCompletedProcesses);
            log();

            log("Average time spent in system per process:",
                    (float) totalSystemTime / nofCompletedProcesses + " ms");
            log("Average time spent waiting for memory per process:",
                    (float) totalTimeSpentWaitingForMemory / nofCompletedProcesses + " ms");
            log("Average time spent waiting for cpu per process:",
                    (float) totalTimeSpentWaitingForCpu / nofCompletedProcesses + " ms");
            log("Average time spent processing per process:",
                    (float) totalCPUTimeSpentProcessing / nofCompletedProcesses + " ms");
            log("Average time spent waiting for I/O per process:",
                    (float) totalTimeSpentWaitingForIo / nofCompletedProcesses + " ms");
            log("Average time spent in I/O per process:",
                    (float) totalIOTimeSpentProcessing / nofCompletedProcesses + " ms");
        }
    }

    /**
     * Log print with align
     *
     * @param key   the key
     * @param value the value
     */
    public void log(String key, String value) {
        System.out.printf("%-70s %s%n", key, value);
    }

    /**
     * Log print with align
     *
     * @param key   the key
     * @param value the value
     */
    public void log(String key, long value) {
        log(key, String.valueOf(value));
    }

    /**
     * Log: print with align
     *
     * @param key   the key
     * @param value the value
     */
    public void log(String key, float value) {
        log(key, String.valueOf(value));
    }

    /**
     * Log.
     *
     * @param key the key
     */
    public void log(String key) {
        System.out.println(key);
    }

    /**
     * Log.
     */
    public void log() {
        System.out.println();
    }
}
