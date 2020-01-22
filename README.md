# ProcessorSimulation
A Java simulation for a simple 16-bit accumulator ISA processor

### Usage
Compile then run the `Main` class and provide the 'assembly' file to run as the first command line argument.

Example:
- Compile: `javac Main.java`
- Run: `java Main fibonacci.s`

### Single operand instruction set (similar to LMC)
| Assembler  | Action | Description |
| ------------- | ------------- | ------------- |
| LDA <Operand>  | acc := Operand  | Load into accumulator
| STA <Operand>  | memory[Operand] := acc  | Store in memory
| ADD <Operand> | acc := acc + Operand | Add to accumulator
| SUB <Operand> | acc := acc - Operand | Subtract from accumulator
| MUL <Operand> | acc := acc * Operand | Multiply with accumulator
| DIV <Operand> | acc := acc / Operand | Divide accumulator by
| BRP <Operand> | pc := Operand IF acc > 0 | Branch to line if accumulator positive
| BRZ <Operand> | pc := Operand IF acc == 0 | Branch to line if accumulator zero
| BRA <Operand> | pc := Operand | Branch always to line
| OUT | PRINT acc | Print contents of accumulator
| END | END | End of program

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

| 0 | 1 - 8 | 9 - 12 | 13 - 15 |
| --- | --- | ------ | ------- |
| Addressing Mode Flag | Signed Integer Operand | Opcode | Padded zero bits |

### Examples
See `fibonacci.s` for a program that prints the first 11 fibonacci numbers.
