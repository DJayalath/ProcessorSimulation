import java.util.Hashtable;
import java.util.Map;

/**
 * Singleton class representing a 2-way lookup table
 * to get opcode bit patterns and associated mnemonics
 */
public class OpcodeTable {

    /**
     * Stores the single instance of this class
     */
    private static OpcodeTable singleInstance = null;

    /**
     * Map from mnemonics to bit patterns
     */
    private static Map<String, Byte> forward = new Hashtable<>();

    /**
     * Map from bit patterns to mnemonics
     */
    private static Map<Byte, String> backward = new Hashtable<>();

    /**
     * Assigns instruction set mappings
     */
    private OpcodeTable() {
        OpcodeTable.add("LDA", (byte) 1);
        OpcodeTable.add("STA", (byte) 2);
        OpcodeTable.add("ADD", (byte) 3);
        OpcodeTable.add("SUB", (byte) 4);
        OpcodeTable.add("MUL", (byte) 5);
        OpcodeTable.add("DIV", (byte) 6);
        OpcodeTable.add("BRP", (byte) 7);
        OpcodeTable.add("BRZ", (byte) 8);
        OpcodeTable.add("BRA", (byte) 9);
        OpcodeTable.add("OUT", (byte) 10);
        OpcodeTable.add("END", (byte) 11);
    }

    /**
     * Static method to create a single instance of this class
     * @return The instance of this class
     */
    public static OpcodeTable getInstance()
    {
        if (singleInstance == null)
            singleInstance = new OpcodeTable();

        return singleInstance;
    }

    /**
     * Adds a mapping to both maps so lookups work both ways
     * @param mnemonic Opcode mnemonic
     * @param value Opcode bit pattern
     */
    public static synchronized void add(String mnemonic, Byte value) {
        forward.put(mnemonic, value);
        backward.put(value, mnemonic);
    }

    /**
     * Get the bit pattern for the opcode
     * @param mnemonic The opcode mnemonic
     * @return The associated bit pattern
     */
    public synchronized Byte getBitPattern(String mnemonic) {
        return forward.get(mnemonic);
    }

    /**
     * Get the mnemonic for the opcode
     * @param bitPattern The opcode bit pattern
     * @return The associated mnemonic
     */
    public synchronized String getMnemonic(Byte bitPattern) {
        return backward.get(bitPattern);
    }

}
