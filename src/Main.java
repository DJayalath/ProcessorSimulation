/**
 * Class to run user-defined code
 */
public class Main {

    /**
     * Creates a simulated computer, compiles code,
     * and runs the code in the processor
     * @param args Contains command line argument for source code file
     */
    public static void main(String[] args) {

        // Initialise memory, processor and compiler
        Memory memory = new Memory();
        Compiler compiler = new Compiler(memory);
        Processor processor = new Processor(memory);

        // Compile program and store in memory
        compiler.compile(args[0]);

        // Run program
        processor.run();
    }

}
