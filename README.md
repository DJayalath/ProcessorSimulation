# ProcessorSimulation
A Java simulation for a simple accumulator ISA processor

## Instruction Set Architecture
### Single operand instruction set (similar to LMC)
LDA = load into accumulator

STA = store in memory

ADD = add to accumulator

SUB = subtract from accumulator

MUL = multiply with accumulator

DIV = divide accumulator by

BRP = branch if accumulator positive

BRZ = branch if accumulator zero

BRA = branch always

OUT = print accumulator value

END = end of program

### Addressing modes
- Immediate addressing = prefix number with '#'
- Direct addressing = address itself

### Branching labels
Branching labels are unsupported, so instead provide an immediate addressed value for the line of code
to branch to. Note that lines of code are indexed from zero (i.e line 1 is actually line 0).

### Examples
See `fibonacci.s` for a program that prints the first 11 fibonacci numbers.
