/**
 * Represents main memory accessible to CPUs
 */
public class Memory {

    /**
     * Stores words in memory
     */
    private char memoryStore[];

    /**
     * Initialises memory
     */
    public Memory() {
        memoryStore = new char[128];
    }

    /**
     * Access a word of data from memory
     * @param address The address of memory to access
     * @return The word of data at the address
     */
    public char getWord(byte address) {
        return memoryStore[address];
    }

    /**
     * Stores a word of data in memory
     * @param word The data
     * @param address The address to store data at
     */
    public void storeWord(char word, byte address) {
        memoryStore[address] = word;
    }


}
