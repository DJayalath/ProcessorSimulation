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
     * Runs tests for processor
     */
    public void runTests() {
        System.out.print("Running load and store test... ");
        System.out.print((loadAndStore()) ? "SUCCESS!\n" : "FAIL!\n");

        System.out.print("Running compile, load and store test... ");
        System.out.print((compileLoadAndStore()) ? "SUCCESS!\n" : "FAIL!\n");
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
        if (memory.getWord((byte) 64) == (char) 0b0111111110000000)
            return true;

        return false;
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
        if (memory.getWord((byte) 64) == (char) 0b0111111110000000)
            return true;

        return false;
    }

}
