#Operating Systems

## [Simulation](simulation.md)

[Simulation: Multiple Threads & Process](simulation.md)

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

> The Java programming language provides two basic synchronization idioms: [synchronized methods and synchronized statements](https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html).


```java 
synchronized (queue) {
    queue.wait();
}
```
>public final void wait()
>Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object. In other words, this method behaves exactly as if it simply performs the call wait(0).

```java
synchronized (gui) {
    if (s > 2) {
        gui.notifyAll();// wakeup all waiting thread
    } else if (s == 2) {
        gui.notify();
        gui.notify();
    } else {
        gui.notify(); // wakeup one waiting thread
    }
}
```

>public final void notify()
> Wakes up a single thread that is waiting on this object's monitor. If any threads are waiting on this object, one of them is chosen to be awakened. The choice is arbitrary and occurs at the discretion of the implementation. A thread waits on an object's monitor by calling one of the wait methods.
 
```java
volatile int s;
```
> [Atomic Access: volatile](https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html)
> Using **`volatile`** variables reduces the risk of memory consistency errors, because any write to a volatile variable establishes a happens-before relationship with subsequent reads of that same variable. 
>
> This means that **changes to a volatile variable are always visible to other threads**. 
>
> What's more, it also means that when a thread reads a volatile variable, it sees not just the latest change to the volatile, but also the side effects of the code that led up the change.

**[The Java volatile Visibility Guarantee](http://tutorials.jenkov.com/java-concurrency/volatile.html)**

> The Java volatile keyword is used to mark a Java variable as "being stored in main memory". More precisely that means, that every read of a volatile variable will be read from the computer's main memory, and not from the CPU cache, and that every write to a volatile variable will be written to main memory, and not just to the CPU cache.

> "the volatile modifier guarantees that any thread that reads a field will see the most recently written value.”


**[Singleton pattern](https://en.wikipedia.org/wiki/Singleton_pattern)**

- Lazy initialization
    - This method uses double-checked locking, which should not be used prior to J2SE 5.0, as it is vulnerable to subtle bugs. 
    - The problem is that an out-of-order write may allow the instance reference to be returned before the Singleton constructor is executed

```java
public class SingletonDemo {
      private static volatile SingletonDemo instance;
      private SingletonDemo() { }
      public static SingletonDemo getInstance() {
          if (instance == null ) {
              synchronized (SingletonDemo.class) {
                  if (instance == null) {
                      instance = new SingletonDemo();
                  }
              }
          }
          return instance;
      }
}
```


- An alternate simpler and cleaner version may be used at the expense of potentially lower concurrency in a multithreaded environment:

```java
public class SingletonDemo {
    private static SingletonDemo instance = null;
    private SingletonDemo() { }

    public static synchronized SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }

        return instance;
    }
}
```


- The enum way
    - In the second edition of his book Effective Java, Joshua Bloch claims that "a single-element enum type is the best way to implement a singleton" for any Java that supports enums. 
        - The use of an enum is very easy to implement and has no drawbacks regarding serializable objects...
    - This approach implements the singleton by taking advantage of Java's guarantee that any enum value is instantiated only once in a Java program. 
        - Since Java enum values are globally accessible, so is the singleton, initialized lazily by the class loader. 
        - The drawback is that the enum type is somewhat inflexible.

```java 
public enum Singleton {
    INSTANCE;
    public void execute (String arg) {
        // Perform operation here 
    }
}
```


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
