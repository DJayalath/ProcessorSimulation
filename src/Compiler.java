import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Compiles mnemonic-based code into machine code
 */
public class Compiler {

    /**
     * Reference to the 2-way opcode lookup table
     */
    private OpcodeTable opcodeTable;

    /**
     * Store a reference to computer memory
     */
    private Memory memory;

    /**
     * Stores list of all lines of code
     */
    private ArrayList<String> lineBuffer;

    /**
     * Constructor initialises lineBuffer, assigns accessible memory
     * and gets an instance of the opcode lookup table
     */
    public Compiler(Memory memory) {
        lineBuffer = new ArrayList<>();
        this.memory = memory;
        opcodeTable = OpcodeTable.getInstance();
    }

    /**
     * Compiles a given file and stores instructions in memory
     * ready for processor execution
     * @param fileName The 'assembly' source code file
     */
    public void compile(String fileName) {
        readLinesToBuffer(fileName);
        translateLines();
    }

    /**
     * Reads a file and puts each line into the lineBuffer
     * @param fileName The name of the file to read
     */
    private void readLinesToBuffer(String fileName) {

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            // Loop over each line of file
            // This pattern avoids scope of line leaking outside loop
            for(String line; (line = br.readLine()) != null; ) {
                lineBuffer.add(line);
            }

        } catch (Exception e) {
            System.out.println("Failed to read line!");
        }
    }

    /**
     * Translates each line of 'assembly' into machine code
     * and stores each instruction contiguously in memory
     */
    private void translateLines() {

        for (int i = 0; i < lineBuffer.size(); i++) {

            String line = lineBuffer.get(i);

            // Split into opcode/operand
            String[] splitLine = line.split(" ");

            String[] instruction = new String[2];
            instruction[0] = splitLine[0];

            // Place a zero value if line doesn't have operand
            instruction[1] = (splitLine.length < 2) ? "#0" : splitLine[1];

            // Lookup machine code from mnemonic opcode
            byte opcode = opcodeTable.getBitPattern(instruction[0]);

            // Set addressing mode when constructing instruction bit pattern
            char instrBitPattern = (char) ((instruction[1].charAt(0) != '#') ? 0x8000: 0x0);

            // Get operand as byte from line, respecting addressing mode
            byte operand = (byte) Integer.parseInt((instrBitPattern != 0x8000) ? instruction[1].substring(1) : instruction[1]);

            // Shift operand into bits 1-8
            char shiftedOperand = (char) (((char) (operand & 0xFF)) << 7);
            // Set bits in instruction
            instrBitPattern |= shiftedOperand;

            // Shift opcode into bits 9-12
            char shiftedOpcode = (char) (((char) (opcode & 0xF)) << 3);
            // Set bits in instruction
            instrBitPattern |= shiftedOpcode;

            // Store instructions contiguously in memory
            memory.storeWord(instrBitPattern, (byte) i);
        }

    }

}
