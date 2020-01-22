import java.util.HashMap;

/**
 * Simulates the processor of the instruction set
 */
public class Processor {

    /**
     * Reference to the 2-way opcode lookup table
     */
    private OpcodeTable opcodeTable;

    /**
     * Mask for direct addressing mode flag (MSB = 1)
     */
    private char DIRECT_ADDRESS_MASK = 0x8000;

    /**
     * Mask for operand (8-bits)
     */
    private char OPERAND_MASK = 0xFF;

    /**
     * Mask for opcode (4-bits)
     */
    private char OPCODE_MASK = 0xF;

    /**
     * Store a reference to computer memory
     */
    private Memory memory;

    /**
     * Stores address of next instruction to fetch
     */
    private byte programCounter = 0;

    /**
     * Simulates current instruction register
     */
    private char currentInstruction = 0;

    /**
     * Store current decoded operand
     */
    private byte operand = 0;

    /**
     * Store current decoded opcode
     */
    private byte opcode = 0;

    /**
     * Simulates accumulator in this ISA
     */
    private byte accumulator = 0;

    /**
     * Simulates status register
     */
    private byte status = 0;

    /**
     * Assign reference to computer memory and get
     * and instance of the opcode lookup table
     * @param memory Reference to memory instance
     */
    public Processor(Memory memory) {

        this.memory = memory;
        opcodeTable = OpcodeTable.getInstance();
    }

    /**
     * Begin processor instruction pipeline
     */
    public void run() {

        while (status == 0) {
            cycle();
        }

    }

    /**
     * Run a processor clock cycle
     */
    public void cycle() {
        fetch();
        decode();
        execute();
    }

    /**
     * Fetch instruction from memory and increment
     * program counter register to next instruction
     */
    private void fetch() {
        currentInstruction = memory.getWord(programCounter);
        programCounter++;
    }

    /**
     * Decodes instruction into opcode and operand
     */
    private void decode() {

        // Get operand (bits 1-8)
        operand = (byte) ((currentInstruction >> 7) & OPERAND_MASK);

        // If direct addressing used (bit 0 == 1), get operand from address
        if ((currentInstruction & DIRECT_ADDRESS_MASK) == DIRECT_ADDRESS_MASK) {
            operand = (byte) ((memory.getWord(operand) >> 7) & OPERAND_MASK);
        }

        // Get opcode (bits 9-12)
        opcode = (byte) ((currentInstruction >> 3) & OPCODE_MASK);
    }

    /**
     * Executes the opcode operation on the operand
     */
    private void execute() {

        // Lookup mnemonic from opcode
        String mnemonic = opcodeTable.getMnemonic(opcode);

        switch(mnemonic) {
            // Load operand value into accumulator
            case "LDA":
                accumulator = operand;
                break;
            // Store accumulator value in memory
            case "STA":
                // Construct value-only instruction (use +ve mask to prevent sign extension in casting)
                char valueInstruction = (char) (accumulator & 0xFF);

                // Shift accumulator value into bits 1-8 of instruction
                valueInstruction = (char) (valueInstruction << 7);

                // Store value-only instruction in memory
                memory.storeWord(valueInstruction, operand);

                break;
            // Add operand to accumulator
            case "ADD":
                accumulator += operand;
                break;
            // Subtract operand from accumulator
            case "SUB":
                accumulator -= operand;
                break;
            // Multiply operand and accumulator
            case "MUL":
                accumulator *= operand;
                break;
            // Divide accumulator by operand
            case "DIV":
                accumulator /= operand;
                break;
            // Print contents of accumulator
            case "OUT":
                System.out.println(accumulator);
                break;
            // Sets status register to end execution code
            case "END":
                status = 1;
                break;
        }

    }


}