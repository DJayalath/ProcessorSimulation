/**
 * Test harness for simulation
 */
public class Tester {

    /**
     * Initialises tester instance and runs tests
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.runTests();
    }

    /**
     * Mask for operand (8-bits)
     */
    private char OPERAND_MASK = 0xFF;

    /**
     * Runs tests for processor
     */
    public void runTests() {
        System.out.print("Running load and store test... ");
        System.out.print((loadAndStore()) ? "SUCCESS!\n" : "FAIL!\n");

        System.out.print("Running compile, load and store test... ");
        System.out.print((compileLoadAndStore()) ? "SUCCESS!\n" : "FAIL!\n");

        System.out.print("Running add test... ");
        System.out.print((addTest()) ? "SUCCESS!\n" : "FAIL!\n");

        System.out.print("Running branch test... ");
        System.out.print((branchTest()) ? " SUCCESS!\n" : " FAIL!\n");

        System.out.print("Running fibonacci test... ");
        System.out.print((fibonacciTest()) ? " SUCCESS!\n" : " FAIL!\n");
    }

    /**
     * Tries loading a value into the accumulator
     * and then storing it again in memory
     * @return True on success
     */
    private boolean loadAndStore() {

        // Initialise memory and processor
        Memory memory = new Memory();
        Processor processor = new Processor(memory);

        // (Address = 0), Instruction = LDA #-1
        memory.storeWord((char) 0b0111111110001000, (byte) 0);

        // (Address = 1), Instruction = STA #64
        memory.storeWord((char) 0b0010000000010000, (byte) 1);

        // Run two cycles to process both instructions
        processor.cycle();
        processor.cycle();

        // Check that -1 has been stored in address 64
        return readOperand(memory.getWord((byte) 64)) == -1;
    }

    /**
     * Tries running the same load and store test but through
     * a program that should be compiled by the compiler
     * @return True on success
     */
    private boolean compileLoadAndStore() {

        // Initialise memory, processor and compiler
        Memory memory = new Memory();
        Compiler compiler = new Compiler(memory);
        Processor processor = new Processor(memory);

        // Compile code in the test file
        compiler.compile("loadStoreTest.s");

        // Run two cycles to process both instructions
        processor.cycle();
        processor.cycle();

        // Check that -1 has been stored in address 64
        return readOperand(memory.getWord((byte) 64)) == -1;
    }

    /**
     * Tries 'addTest.s' to do 5 + 10 through
     * direct addressing and temporary storage
     * @return True on success
     */
    private boolean addTest() {

        // Initialise memory, processor and compiler
        Memory memory = new Memory();
        Compiler compiler = new Compiler(memory);
        Processor processor = new Processor(memory);

        compiler.compile("addTest.s");

        processor.run();

        return readOperand(memory.getWord((byte) 51)) == 15;
    }

    /**
     * Tests 'branchTest.s' to subtract 1 from 10 until 0
     * @return True on success
     */
    private boolean branchTest() {

        // Initialise memory, processor and compiler
        Memory memory = new Memory();
        Compiler compiler = new Compiler(memory);
        Processor processor = new Processor(memory);

        compiler.compile("branchTest.s");

        processor.run();

        return readOperand(memory.getWord((byte) 50)) == 0;
    }

    /**
     * Prints the first 11 fibonacci numbers. Can't go further
     * because it would cause an overflow.
     * @return True on success
     */
    private boolean fibonacciTest() {

        // Initialise memory, processor and compiler
        Memory memory = new Memory();
        Compiler compiler = new Compiler(memory);
        Processor processor = new Processor(memory);

        compiler.compile("fibonacci.s");

        processor.run();

        return readOperand(memory.getWord((byte) 99)) == 89;
    }

    /**
     * Utility function for reading data from memory
     */
    private byte readOperand(char bitPattern) {
        // Get operand (bits 1-8)
        return (byte) ((bitPattern >> 7) & OPERAND_MASK);
    }

}