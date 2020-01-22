# ProcessorSimulation
A Java simulation for a simple accumulator ISA processor

### Usage
Compile then run the `Main` class and provide the 'assembly' file to run as the first command line argument.

Example:
- Compile: `javac Main.java`
- Run: `java Main fibonacci.s`

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

### Memory
Currently supports 128 memory addresses (0-127). Be careful not to overwrite instructions
written in memory in your programs. For example, if your program is 20 lines long, do not 
overwrite memory addresses 0-19 in your program to avoid errors.

### Instruction Packing
The system word size is 16 bits. The 0th bit (MSB) is an addressing mode flag, bits 1-8
represent the **signed integer operand** (range -128 to 127), and bits 9-12 represent the opcode.
Bits 13-15 are padded zero bits.

### Examples
See `fibonacci.s` for a program that prints the first 11 fibonacci numbers.