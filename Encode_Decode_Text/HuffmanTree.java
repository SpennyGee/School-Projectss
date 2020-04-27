package hw7;
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

/** this program meets all of the requirements */

/**
 * Creates a Huffman Tree
 * @author CSC 143
 */
public class HuffmanTree {
	
	// node at level 1
	private HuffmanNode overallRoot;
	
	//PART I
	
	// Make a tree from the int[] array of frequencies.
	// The index in array represents ascii value.
	// MakeCode.java will use this constructor - done -
	public HuffmanTree(int[] count) {
	
		PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>();
		
		// go through whole array and make a new
		// node for each character that has a frequency
		// greater than zero
		for(int i = 0; i < count.length; i++) {
			
			// don't add info about characters
			// that don't appear
			if(count[i] > 0) {
				q.add(new HuffmanNode(i, count[i]));
			}
		}
		
		// pseudo end of character for huffman encoding
		// to recognize end of file
		q.add(new HuffmanNode(count.length, -1));
		
		// Pull out the two next closest nodes from the queue.
		// The higher priority of the two (first) becomes the left branch
		// of a new node (parent) that is the sum of (first + second)
		// second becomes the right branch of parent.
		while(q.size() > 1) {
			
			HuffmanNode first = q.remove();
			HuffmanNode second = q.remove();
			int newRoot = first.frequency + second.frequency;
			
			HuffmanNode parent = new HuffmanNode(0, newRoot, first, second);
			
			// add the new node back to the queue 
			q.add(parent);
		}
		
		//final root in the priority queue is the overall root of the tree
		overallRoot = q.remove();
	}
	
	//**WRITE** done
	/** 
	 * need to keep track of the path of each node, overall root has
	 * "" or no path, but we keep track of each node's location from the 
	 * overall root by 0s and 1s (if it was the directly left of the overall root,
	 * the path for that node is 0..if it the path was 01 it would be left of the overall node
	 *  then right of the level two node, etc...
	 */
	public void write(PrintStream out) {
		writeHelper(this.overallRoot, out, "");
	}
	
	//**WRITEHELPER** done
	/**
	 * if current node is a leaf(base case), have 'out' print the value and the path to get to that leaf,
	 * if it's not a leaf, keep track of the current path from the overall root by 
	 * recursively calling to writeHelper with left/right node and updated path (+ 0/1)
	 */
	public void writeHelper(HuffmanNode current, PrintStream out, String path) {
		
		// base case: if the node is a leaf print leaf value and path
		if(current.isLeaf()) {
			out.println(current.asciiChar);
			out.println(path);
			
		// else use recursion until method reaches base case leaf
		} else {
			writeHelper(current.left, out, path + "0");
			writeHelper(current.right, out, path + "1");
		}
	}


	// PART II 

	/** 
	 * Constructs HoffmanTree from a scanner which is reading 
	 * ascii value as string (newline)
	 * pathway to that value from overallRoot as string (newline)
	 * (....repeat til end of file)
	 */
	public HuffmanTree(Scanner input) {
		while (input.hasNextLine()) {
			
			// acsii value
			int value = Integer.parseInt(input.nextLine());
        
			// pathCode from overallRoot (0s and 1s)
			String pathCode = input.nextLine();
      
			// build a tree based on the a given root which
			// has a unique value and pathCode, treeHelper
			// returns a HuffmanNode which will be the overallRoot
			// of the file, from which we can get to any other node
			overallRoot = treeHelper(overallRoot, value, pathCode); 
		}
	}
	
	
	/** construct whole tree as a series of subtrees recursively */
	public HuffmanNode treeHelper(HuffmanNode current, int asciiValue, String pathCode) {
		
		// base case, there is no next digit in the pathway. Return a
		// a new node that contains the current asciiValue,
		// and our psuedo end of character to signal end of path/leaf node
	  	if (pathCode.isEmpty()) {
      			  return new HuffmanNode(asciiValue, -1);

		// else, use the current node to build appropriate next 
		// node based on pathway
		// look at pathCode.charAt(0)
		// if 0: call treeHelper with current.left and a substring/update of the path
		// if 1: same but current.right
  		} else {
	  	
	  	
	  		// signal 'null' leaf
	  		if(current == null){
	  			current = new HuffmanNode(-1, -1);
	  		}
	  		
	  		// if next step away from root is left, charAt(0) == 0...
	  		if(pathCode.charAt(0) == '0'){
	  			current.left = treeHelper(current.left, asciiValue, pathCode.substring(1));

	  		// else next step is right
	  		}else{
	  			current.right = treeHelper(current.right, asciiValue, pathCode.substring(1));
	  		}
		}
	  	return current;
	}

	// input will read the HuffmanTree we made during encoding, from encode.java, output will
	// rewrite the asciiValue as characters, eof signals when to stop
	public void decode(BitInputStream input, PrintStream output, int eof) {
		
		int character = decodeHelper(overallRoot, input);
			output.write(character);
		while(character != eof) {
			character = decodeHelper(overallRoot, input);
			output.write(character);
		}	
	}

	// return leaf values (base case) otherwise, go through input(Huffman Tree)
	// input can do  input.readBit(), which will be only 0s and 1s representing
	// pathways...so input.readBit() == 0, helper(root.left())...
	 private int decodeHelper(HuffmanNode root, BitInputStream input) {
		
		 // base case: if the node is a leaf, return the leaf value
		 if(root.isLeaf()) {
			 return root.asciiChar;
		
		 // else use recursion until method reaches base case leaf	 
		 } else {
			 // next step is to the left
			 if (input.readBit() == 0) {
				 return decodeHelper(root.left, input);
			 } else {
				return  decodeHelper(root.right, input);
			 }
		 }
	 }
}
