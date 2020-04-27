package hw7;

/**
 * Creates a Huffman Node
 * @author CSC 143
 */
public class HuffmanNode implements Comparable<HuffmanNode>{

	// ascii value of the specific character from the file
	protected int asciiChar;
	
	// frequency of the character in the given file
	protected int frequency;
	
	// Huffman node to follow for "0" path
	protected HuffmanNode left;
	
	// Huffman node to follow for "1" path
	protected HuffmanNode right;

	/**
	 * constructor to read in the ascii array.
	 * Position/index in the array corresponds to 
	 * letterVal (param 1) & freq is the data 
	 * contained at that index in the array (param 2)
	 */
	public HuffmanNode(int letterVal, int freq) {
		this.asciiChar = letterVal;
		this.frequency = freq;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * constructor to use in declaring new branch nodes
	 * logic (in TreeClass)  will be that we pop the next Two Nodes in priority
	 * at a time from the Queue, add them together, and make
	 * a new node that is the root of the two nodes passed.
	 */
	public HuffmanNode(int letterVal, int freq, HuffmanNode l, HuffmanNode r) {
		this.asciiChar = letterVal;
		this.frequency = freq;
		this.left = l;
		this.right = r;
	}
	
	/** returns true if the current huffman node is a leaf, false if not */
	public boolean isLeaf() {
		if (this.right == null && this.left == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/** compares two Huffman Nodes */
	@Override
	public int compareTo(HuffmanNode h) {
		
		// Node calling compareTo greater 
		// frequency than node being checked
		if (this.frequency > h.frequency) {
			return 1;
		}
		
		// two nodes have equal frequencies
		else if (this.frequency == h.frequency) {
			return 0;
		}
		
		// Node calling compareTo less
		// frequent than node being checked
		else {
			return -1;
		}
	}
}
