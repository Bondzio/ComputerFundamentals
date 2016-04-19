package com.junjunguo.os.p3;

import java.io.*;

/**
 * The main class of the P3 exercise. This class is only partially complete.
 */
public class Simulator implements Constants {
    /** The queue of events to come */
    private EventQueue eventQueue;
    /** Reference to the memory unit */
    private Memory     memory;
    /** Reference to the GUI interface */
    private Gui        gui;
    /** Reference to the statistics collector */
    private Statistics statistics;
    /** The global clock */
    private long       clock;
    /** The length of the simulation */
    private long       simulationLength;
    /** The average length between process arrivals */
    private long       avgArrivalInterval;
    private CPU        cpu;
    private IO         io;

    /**
     * Constructs a scheduling simulator with the given parameters.
     *
     * @param memoryQueue        The memory queue to be used.
     * @param cpuQueue           The CPU queue to be used.
     * @param ioQueue            The I/O queue to be used.
     * @param memorySize         The size of the memory.
     * @param maxCpuTime         The maximum time quant used by the RR algorithm.
     * @param avgIoTime          The average length of an I/O operation.
     * @param simulationLength   The length of the simulation.
     * @param avgArrivalInterval The average time between process arrivals.
     * @param gui                Reference to the GUI interface.
     */
    public Simulator(Queue memoryQueue, Queue cpuQueue, Queue ioQueue, long memorySize,
            long maxCpuTime, long avgIoTime, long simulationLength, long avgArrivalInterval, Gui gui) {
        this.simulationLength = simulationLength;
        this.avgArrivalInterval = avgArrivalInterval;
        this.gui = gui;
        statistics = new Statistics();
        eventQueue = new EventQueue();
        memory = new Memory(memoryQueue, memorySize, statistics);
        clock = 0;
        this.cpu = new CPU(gui, cpuQueue, maxCpuTime, statistics);
        this.io = new IO(gui, ioQueue, avgIoTime, statistics);
    }

    /**
     * Starts the simulation. Contains the main loop, processing events. This method is called when the "Start
     * simulation" button in the GUI is clicked.
     */
    public void simulate() {
        System.out.print("Simulating...");
        eventQueue.insertEvent(new Event(NEW_PROCESS, 0)); // Genererate the first process arrival event
        while (clock < simulationLength && !eventQueue.isEmpty()) { // Process until the simulation length is exceeded
            Event event          = eventQueue.getNextEvent(); // Find the next event: removed from event queue
            long  timeDifference = event.getTime() - clock; // Find out how much time that passed...
            clock = event.getTime(); // ...and update the clock.
            memory.timePassed(timeDifference); // Let the memory unit know that time has passed
            gui.timePassed(timeDifference);
            cpu.timePassed(timeDifference);
            io.timePassed(timeDifference);
            if (clock < simulationLength) { // Deal with the event
                processEvent(event);
            } // Note that the processing of most events should lead to new events being added to the event queue!
        }
        System.out.println("..done.");
        statistics.printReport(simulationLength); // End the simulation by printing out the required statistics
    }

    /**
     * Processes an event by inspecting its type and delegating the work to the appropriate method.
     *
     * @param event The event to be processed.
     */
    private void processEvent(Event event) {
        switch (event.getType()) {
            case NEW_PROCESS:
                createProcess();
                break;
            case SWITCH_PROCESS:
                switchProcess();
                break;
            case END_PROCESS:
                endProcess();
                break;
            case IO_REQUEST:
                processIoRequest();
                break;
            case END_IO:
                endIoOperation();
                break;
        }
    }

    /**
     * Simulates a process arrival/creation.
     */
    private void createProcess() {
        Process newProcess = new Process(memory.getMemorySize(), clock); // Create a new process
        memory.insertProcess(newProcess);
        flushMemoryQueue();
        long nextArrivalTime = clock + 1 + (long) (2 * Math.random() * avgArrivalInterval);
        eventQueue.insertEvent(new Event(NEW_PROCESS, nextArrivalTime)); // Add an event for the next process arrival
        statistics.nofCreatedProcesses++; // Update statistics
    }

    /**
     * Transfers processes from the memory queue to the ready queue as long as there is enough memory for the
     * processes.
     */
    private void flushMemoryQueue() {
        Process p = memory.checkMemory(clock); // removed from memory queue if not null
        while (p != null) { // if there is enough memory, processes are moved from the memory queue to the cpu queue
            cpu.insertProcess(p);
            if (cpu.isIdle()) switchProcess();
            //            if (cpu.isIdle()) processEvent(new Event(SWITCH_PROCESS, clock));
            flushMemoryQueue(); // try to use the freed memory
            p = memory.checkMemory(clock); // Check for more free memory
        }
    }

    /**
     * Simulates a process switch.
     */
    private void switchProcess() {
        //Stop our current process
        Process process = cpu.removeProcess();
        if (process != null) {
            //If we actually were processing something, the statistics for process are already updated
            //But we need to update global statistics, and add the process back to queue
            statistics.nofForcedProcessSwitches++;
            cpu.insertProcess(process);
        }

        Process nextProcess = cpu.switchProcess();
        gui.setCpuActive(nextProcess);
        if (nextProcess != null) { //The cpu queue may have been empty, in that case nextProcess == null
            nextProcess.enterCPU(clock);
            if (nextProcess.getTimeToNextIoOperation() > cpu.getMaxCpuTime() && // not switch to IO queue
                nextProcess.getCpuTimeNeeded() > cpu.getMaxCpuTime()) { // need to switch back to cpu queue
                eventQueue.insertEvent(new Event(SWITCH_PROCESS, clock + cpu.getMaxCpuTime()));
            } else if (nextProcess.getTimeToNextIoOperation() > nextProcess.getCpuTimeNeeded()) {
                //If next event is the end of process, schedule END_PROCESS
                eventQueue.insertEvent(new Event(END_PROCESS, clock + nextProcess.getCpuTimeNeeded()));
            } else { //Otherwise the next event must be IO request, schedule IO_REQUEST
                eventQueue.insertEvent(new Event(IO_REQUEST, clock + nextProcess.getTimeToNextIoOperation()));
            }
        }
    }

    /**
     * Ends the current process, and deal locates any resources allocated to it.
     */
    private void endProcess() {
        Process process = cpu.removeProcess(); // stop current process
        statistics.nofCompletedProcesses++; // update statistics
        memory.processCompleted(process); // process leaving the system, free the memory
        process.updateStatistics(statistics);
    }

    /**
     * Processes an event signifying that the active process needs to perform an I/O operation.
     */
    private void processIoRequest() {
        //Stop our current process
        Process oldProcess = cpu.removeProcess();
        //Insert it into IO queue
        io.insertProcess(oldProcess);

        //If IO is idle, start processing immediately and schedule END_IO
        if (io.isIdle()) {
            io.switchProcess();
            this.eventQueue.insertEvent(new Event(Constants.END_IO, clock + io.getAvgIoTime()));
        }
        //        //CPU is now free, schedule new process
        //        switchProcess();
        //
        //
        //
        //        Event e = io.addIoRequest(cpu.removeProcess(), clock);
        //        if (e != null) {
        //            this.eventQueue.insertEvent(e);
        //            processEvent(e);
        //        }
        switchProcess();
    }

    /**
     * Processes an event signifying that the process currently doing I/O is done with its I/O operation.
     */
    private void endIoOperation() {
        Process process = io.removeProcess(); // stop current IO operation
        gui.setIoActive(process);
        if (process != null) {
            statistics.nofProcessedIoOperations++; // update statistics
            cpu.insertProcess(process); // add back to cup queue
            if (cpu.isIdle()) switchProcess(); // start process if cpu is free
            //            Event e = io.startIoOperation(clock);
            this.eventQueue.insertEvent(new Event(Constants.END_IO, clock + io.getAvgIoTime()));
            //            if (e != null) {
            //                this.eventQueue.insertEvent(e);
            //            }
        }
    }

    /**
     * Reads a number from the an input reader.
     *
     * @param reader The input reader from which to read a number.
     * @return The number that was inputted.
     */
    public static long readLong(BufferedReader reader) {
        try {
            return Long.parseLong(reader.readLine());
        } catch (IOException ioe) {
            return 100;
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * The startup method. Reads relevant parameters from the standard input, and starts up the GUI. The GUI will then
     * start the simulation when the user clicks the "Start simulation" button.
     *
     * @param args Parameters from the command line, they are ignored.
     */
    public static void main(String args[]) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter '0' auto input, else customer input!");
        if (readLong(reader) == 0) {
            new SimulationGui(2048, 500, 225, 250000, 5000);
        } else {
            System.out.println("Please input system parameters: ");
            System.out.print("Memory size (KB): ");
            long memorySize = readLong(reader);
            while (memorySize < 400) {
                System.out.println("Memory size must be at least 400 KB. Specify memory size (KB): ");
                memorySize = readLong(reader);
            }
            System.out.print("Maximum uninterrupted cpu time for a process (ms): ");
            long maxCpuTime = readLong(reader);
            System.out.print("Average I/O operation time (ms): ");
            long avgIoTime = readLong(reader);
            System.out.print("Simulation length (ms): ");
            long simulationLength = readLong(reader);
            while (simulationLength < 1) {
                System.out.println("Simulation length must be at least 1 ms. Specify simulation length (ms): ");
                simulationLength = readLong(reader);
            }
            System.out.print("Average time between process arrivals (ms): ");
            long avgArrivalInterval = readLong(reader);
            new SimulationGui(memorySize, maxCpuTime, avgIoTime, simulationLength, avgArrivalInterval);
        }
    }
}
