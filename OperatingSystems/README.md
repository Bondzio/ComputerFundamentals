#Operating Systems

> ##PROCESSES

##3. Process Description and Control

##4. Thread

### [Java Class Thread](http://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)

> A thread is a thread of execution in a program. The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.

> Every thread has a priority. Threads with higher priority are executed in preference to threads with lower priority. Each thread may or may not also be marked as a daemon. When code running in some thread creates a new Thread object, the new thread has its priority initially set equal to the priority of the creating thread, and is a daemon thread if and only if the creating thread is a daemon.

> When a Java Virtual Machine starts up, there is usually a single non-daemon thread (which typically calls the method named main of some designated class). The Java Virtual Machine continues to execute threads until either of the following occurs:

> - The exit method of class Runtime has been called and the security manager has permitted the exit operation to take place.

> - All threads that are not daemon threads have died, either by returning from the call to the run method or by throwing an exception that propagates beyond the run method.

There are two ways to create a new thread of execution. 

- declare a class to be a subclass of (`extends`) `Thread`. This subclass should override the run method of class Thread. An instance of the subclass can then be allocated and started.
- create a thread is to declare a class that `implements` the `Runnable` interface. That class then implements the run method. An instance of the class can then be allocated, passed as an argument when creating Thread, and started. 

##5. Concurrency: Mutual Exclusion and Synchronization



##6. Concurrency: Deadlock and Starvation

> ##MEMORY

##7. Memory Management

##8. Virtual Memory

> ##SCHEDULING

##9. Uniprocessor Scheduling 

##10. Multiprocessor and Real-Time Scheduling

> ##INPUT/OUTPUT AND FILES

##11. I/O Management and Disk Scheduling

##12. File Management



#References:

- [Java docs Class Thread](http://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html)
- William Stallings **Operating Systems: Internals and Design Principles** 7th Edition & 8th Edition