Computer Fundamentals
==

Computer Systems Organization
--
###Instruction-Level parallelism
    - instruction-level parallelism
    - processor-level parallelism

#####Pipelining
A pipeline is a set of data processing elements connected in series, where the output of one element is the input of the next one. The elements of a pipeline are often executed in parallel or in time-sliced fashion; in that case, some amount of buffer storage is often inserted between elements.

Pipelining allows a trade-off between latency (how long it takes to excute an instruction) and processor bandwidth.

![Pipelining](files/pipeline.png "figure 2-4")

**Computer-related pipelines include:**
- Instruction pipelines, such as the classic RISC pipeline, which are used in central processing units (CPUs) to allow overlapping execution of multiple instructions with the same circuitry. The circuitry is usually divided up into stages, including instruction decoding, arithmetic, and register fetching stages, wherein each stage processes one instruction at a time.
- Graphics pipelines, found in most graphics processing units (GPUs), which consist of multiple arithmetic units, or complete CPUs, that implement the various stages of common rendering operations (perspective projection, window clipping, color and light calculation, rendering, etc.).
- Software pipelines, where commands can be written where the output of one operation is automatically fed to the next, following operation. The Unix system call pipe is a classic example of this concept, although other operating systems do support pipes as well.

#####Superscalar Architectures
A superscalar CPU architecture implements a form of parallelism called instruction-level parallelism within a single processor. It therefore allows faster CPU throughput than would otherwise be possible at a given clock rate. A superscalar processor executes more than one instruction during a clock cycle by simultaneously dispatching multiple instructions to different functional units on the processor. Each functional unit is not a separate CPU core but an execution resource within a single CPU such as an arithmetic logic unit, a bit shifter, or a multiplier.

A superscalar CPU is typically also pipelined, pipelining and superscalar architecture are considered defferent performance enhancement techniques.

###Processor-Level parallelism
#####Data Parallel Computers
Data parallelism is a form of parallelization of computing across multiple processors in parallel computing environments. Data parallelism focuses on distributing the data across different parallel computing nodes. It contrasts to task parallelism as another form of parallelism.

**Single Instruction-stream Multiple Data-stream** or **SIMD** processor: 
- consists of a large number of identical processors that perform the same sequence of instructions on different sets of data. 

**Graphics processing units GPU** heavily rely on SIMD:
- because most of the algorithms are highly regular, with repeated operations on pixels, vertices, textures, and edges.

#####Multiprocessors
The processing elements in a data prallel processor are not independent CUPs, since there is only one control unit shared among all of them.

Multiprocessor is a system with more than one CPU sharing a common memory.

![multiprocessor vs multicomputer](files/multiprocom.png)

#####Multicomputers
A system consisting of large numbers of interconnected computers, each having its own private memory, but no common memory.
###Primary memory
Primary memory, Primary storage (or main memory or internal memory), often referred to simply as memory, is the only one directly accessible to the CPU. The CPU continuously reads instructions stored there and executes them as required. Any data actively operated on is also stored there in uniform manner.
![](files/computerstorages.png "Computer storage types")
#####Cache Memory
Historically, CPU have always been faster than memories. 

Cache: the most heavily used memory words are kept in the cache. When the CPU need a word, it first looks in the cache, only if the word is not there CPU look at main memory.
    - when a word is referenced, it and some of its neighbors are brought from the large slow memory into the cache, so that next time it is used, it can be accessed quickly.

######Memory wall
The "memory wall" is the growing disparity of speed between CPU and memory outside the CPU chip. An important reason for this disparity is the limited communication bandwidth beyond chip boundaries. From 1986 to 2000, CPU speed improved at an annual rate of 55% while memory speed only improved at 10%. Given these trends, it was expected that memory latency would become an overwhelming bottleneck in computer performance.
###Secondary memory
#####Memory Hierarchies
```
                Registers
                  Cache
               Main memory
        Magnetic or solid state disk
    Tape            |      Optical disk
```

Parallel computer architectures
--

####Instruction level parallelism
**ILP** is a measure og how many of the operations in a computer program can be performed simultaneously. The potential overlap among instructions is called instruction level parallelism.

Programming
--
**Assembler**: A computer will not understand any program written in a language, other than its machine language. The programs written in other languages must be translated into the machine language. Such translation is performed with the help of software. A program which translates an assembly language program into a machine language program is called an assembler. If an assembler which runs on a computer and produces the machine codes for the same computer then it is called self assembler or resident assembler. If an assembler that runs on a computer and produces the machine codes for other computer then it is called Cross Assembler.<br>
Assemblers are further divided into two types: One Pass Assembler and Two Pass Assembler. One pass assembler is the assembler which assigns the memory addresses to the variables and translates the source code into machine code in the first pass simultaneously. A Two Pass Assembler is the assembler which reads the source code twice. In the first pass, it reads all the variables and assigns them memory addresses. In the second pass, it reads the source code and translates the code into object code.<br><br>
**Compiler**: It is a program which translates a high level language program into a machine language program. A compiler is more intelligent than an assembler. It checks all kinds of limits, ranges, errors etc. But its program run time is more and occupies a larger part of the memory. It has slow speed. Because a compiler goes through the entire program and then translates the entire program into machine codes. If a compiler runs on a computer and produces the machine codes for the same computer then it is known as a self compiler or resident compiler. On the other hand, if a compiler runs on a computer and produces the machine codes for other computer then it is known as a cross compiler.<br><br>
**Interpreter**: An interpreter is a program which translates statements of a program into machine code. It translates only one statement of the program at a time. It reads only one statement of program, translates it and executes it. Then it reads the next statement of the program again translates it and executes it. In this way it proceeds further till all the statements are translated and executed. On the other hand, a compiler goes through the entire program and then translates the entire program into machine codes. A compiler is 5 to 25 times faster than an interpreter.<br>
By the compiler, the machine codes are saved permanently for future reference. On the other hand, the machine codes produced by interpreter are not saved. An interpreter is a small program as compared to compiler. It occupies less memory space, so it can be used in a smaller system which has limited memory space.


Java - Compiled or interpreted
---

When you run javac HelloWorld.java java compiler is invoked which converts human readable code(Contents of .java file) to java byte codes(intermediate form). This bytecodes are stored in a special file(called Class file) with .class extension.<br><br>
Finally when you run java HelloWorld java interpreter is invoked which reads these bytecodes line by line, convert it into machine language and execute it.
This is why Java is called as both compiled as well as interpreted language. But this is not all. There is another concept called - Just-in-time compilation.
