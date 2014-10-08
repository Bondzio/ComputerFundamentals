```
— Dark is a computer architecture simulator
— You can write programs in assembly language and test their execution on accumulator machine, stack machine, load-store machine, memory-memory machine or virtual machine
— In AoC you will only be tested in two of the architectures: Load-Store and Stack architecture
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
**Assembly vs. Java**
— Assembly instructions are low-level
— Writing assembly requires knowledge of the hardware machine
— Assembly runs only on a given machine, while Java can run on any machine
— A line in Java can correspond to several dozen lines of assembly
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼

**Overview of the stack machine**
— All instructions manipulate a stack
— All operations take place at the top of the stack
— Arithmetic is done by popping the top two items of the stack and pushing back the result
￼￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼

**Arithmetic**
Common arithmetic / logic operations: 
— add
— sub
— div
— mul 
Example:
push 3  ; 3 is placed on the stack
push 5  ; 5 is placed on the stack
add       ; The two above are popped and the result is pushed on the stack￼￼￼￼￼￼￼￼ 



￼￼
￼
Stack manipulation
— dup - copy the top item and puts it on top
— swap - swaps the order of the top two elements — drop - the top item is taken away
— rot - the top three values are rotated so that the value that was on the top, is now located at the bottom
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼
How to perform a conditional jump?
It requires 2 steps:
1. a test is performed on the two top elements of the stack and the stack is updated with the result of the test(1 for true and 0 for false)
2. a jump is performed considering this result
Available tests:
— eq - EQual
— ge - Greater or Equal — gt - Greater Than
— le - Less or Equal
— lt - Less Than
— ne - Not Equal
Order: result ← stack[top − 1] ⊕ stack[top]
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼The jump is based on the test:
— jfalse - Jump if 0 is on the stack — jtrue-Jumpif1isonthestack — jmp - Jump no matter
The pairs eq / jtrue and ne / jfalse are equivalent!
￼Example:
start lt
jtrue end
jmp start end
; compares the top two elements of the stack
; if stack[top-1]< stack[top] jump to end
; jump back to start
￼￼￼￼￼￼￼￼￼￼￼ 
￼￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼

Arithmetic I - Add Syntax
add REGISTER_A,REGISTER_B,{REGISTER_C|NUMBER} Semantics register_a ← register_b + {register_c |NUMBER} Register_a receives the sum of the second and final values. Example:
 load $2, 10    ; load the value 10 into $2
 add $3, $2, 10 ; sum of $2 and 10 is stored in $3
 add $4, $2, $3 ; sum of $2 and $3 is stored in $4
What value is now stored in register 4?
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼

Arithmetic II - Sub Syntax:
sub REGISTER_A,REGISTER_B,{REGISTER_C|NUMBER} Semantics:
register_a ← register_b − {register_c|NUMBER}
Register_a receive the difference of the second and final values. Example:
sub $1, $2, $3 ; $1 = $2 - $3
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼15
A small example
ASSIGNMENT: r8 = (r1 * r2) + (r3 * r4)
Java DARK
￼int temp1 = r1 * r2;
int temp2 = r3 * r4;
r8  = temp1 + temp2;
mul    $5, $1, $2
mul    $6, $3, $4
add    $8, $5, $6
￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼
Memory I - Load Syntax:
load REGISTER_A,{REGISTER_B+NUMBER|NUMBER|VARIABLE} Semantics:
register_a ← {mem[register_b+NUM]|NUM|VAR}
Register_a receives either the value specified as a number, the contents of variable or the contents of memory location that is found from register_b + number.
Example:
load $4, $3+0 ; The contents of memory address $3 is loaded in register $4
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼17
Memory II - Store Syntax:
store REGISTER_A,{REGISTER_B+NUMBER|VARIABLE}
Semantics:
{mem[register_b+NUM]|VAR} ← register_a
The register_a is stored in memory location pointed by register_b + number or by a variable.
Example:
store $2, $3+0 ; Register $2 is stored in the memory location pointed by $3.
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼18
Control Flow I - JMP
The program jumps to a given label in the code.
Example:
top:
￼inc $7 jmp top
￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼19
Control Flow II - Conditional jump
There are many situations where you just want to jump in given situations. These are the conditional jumps:
— jeq - Jump if EQual
— jne - Jump if Not Equal
— jge - Jump if Greater or Equal — jgt - Jump if Greater
— jle - Jump if Less or Equal
— jlt - Jump if Less
 load $3, 5
 start:
   inc $2
   jeq $2, $3, stop
   jmp start
stop:
￼￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼20
IF statements
In Java and similar languages, we often need to express conditions in the form of IF sentences. One example:
Java
if (a<b) {
  a = a + b;
} else {
  a = a - b;
}
DARK
￼else: end:
; Note: a=$2, b=$3
jge   $2, $3, else
add   $2, $2, $3
jmp   end
sub $2, $2, $3
￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼21
WHILE loops
Java
int a = 0;
while(a <= 10) {
DARK
￼}
start:
      jgt $5, $6, end
inc $5
      jmp start
end:
...
a = a + 1;
; Note: a=$5,$6=10
load $5, $0
load $6, 10
￼￼￼￼￼￼￼￼￼￼￼ 
￼￼
￼22
FOR loops
Java DARK
int j = 5;
for(int i = 0; i <= j; i++){
load $5, 0
load $6, 5
; i=0 ; j=5
￼}
jgt $5, $6, end
; something to do
inc $5
jmp start
// something to do start:
Note that the assembly code for a for-loop is identical to the while-loop of the previous example.
end:
￼￼>)>)>)>
```
