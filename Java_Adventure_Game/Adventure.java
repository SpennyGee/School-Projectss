/*
 * File: Adventure.java
 * --------------------
 * This program plays the Adventure game from Assignment #4.
 */

import java.io.*;
import java.util.*;

/* Class: Adventure */
/**
 * This class is the main program class for the Adventure game.
 */

public class Adventure {

	// Use this scanner for any console input
	private static Scanner scan = new Scanner(System.in);

	/**
	 * This method is used only to test the program
	 */
	public static void setScanner(Scanner theScanner) {
		scan = theScanner;
	}

	/**
	 * Runs the adventure program
	 */
	public static void main(String[] args) {
		// Ask the user for the name of an adventure game
		Adventure adventure = new Adventure();
		System.out.print("What will be your adventure today? ");
		adventure.input = scan.nextLine();
		adventure.input = adventure.input.substring(0,1).toUpperCase() + adventure.input.substring(1).toLowerCase();
		System.out.println();
		
		// Read in the data files for the game into an internal data structure
		adventure.readGameFilesAndInitialize(adventure.input);

		// Play the game by reading and executing commands by the user
		adventure.play();
		scan.close(); // close the input scanner when the game is finished
		//AdventureStub.main(args);
	}
	
	/** Reads the files associated with the given adventure name and stores the appropriate data */
	private void readGameFilesAndInitialize(String adventureName) {
		
		// Attempt to read in the rooms file; store the rooms in a TreeMap,
		// store the commands in a HashMap
		try {
			rooms = new TreeMap<Integer, AdvRoom>();
			validCommands = new HashMap<String, AdvCommand>();
			validCommands.put("QUIT", AdvCommand.QUIT);
			validCommands.put("TAKE", AdvCommand.TAKE);
			validCommands.put("DROP", AdvCommand.DROP);
			validCommands.put("LOOK", AdvCommand.LOOK);
			validCommands.put("INVENTORY", AdvCommand.INVENTORY);
			validCommands.put("HELP", AdvCommand.HELP);
			Scanner roomFileScanner = new Scanner(new File(adventureName + "Rooms.txt"));
			while (roomFileScanner.hasNextInt()) {
				AdvRoom room = AdvRoom.readFromFile(roomFileScanner);
				rooms.put(room.getRoomNumber(), room);
				for (AdvMotionTableEntry entry : room.getMotionTable()) {
					validCommands.putIfAbsent(entry.getDirection(), new AdvMotionCommand(entry.getDirection()));
				}
			}
			roomFileScanner.close();
		} catch (IOException e) {
			System.out.println("Problem reading " + adventureName + "Rooms.txt");
			e.printStackTrace();
		}

		// Attempt to read in the objects file; store the objects in a HashMap
		try {
			validObjects = new HashMap<String, AdvObject>();
			Scanner objectsFileScanner = new Scanner(new File(adventureName + "Objects.txt"));
			while (objectsFileScanner.hasNext()) {
				AdvObject object = AdvObject.readFromFile(objectsFileScanner);
				if (object != null) { 
					validObjects.put(object.getName(), object); 
					rooms.get(object.getInitialLocation()).addObject(object);
				}
			}
			objectsFileScanner.close();
		} catch (IOException e) {
			System.out.println("Problem reading " + adventureName + "Objects.txt");
			e.printStackTrace();
		}
		
		// Attempt to read in the synonyms file; store the synonyms in the appropriate data
		// structure
		try {
			Scanner synFileScanner = new Scanner(new File(adventureName + "Synonyms.txt"));
			String line;
			while ( synFileScanner.hasNextLine() ) {
				line = synFileScanner.nextLine();
				String syn = line.substring(0, line.indexOf('='));
				String commandOrObject = line.substring(line.indexOf('=') + 1);
				if (validCommands.containsKey(commandOrObject)) {
					validCommands.put(syn, validCommands.get(commandOrObject));
				}
				if (validObjects.containsKey(commandOrObject)) {
					validObjects.put(syn, validObjects.get(commandOrObject));
				}
			}
			synFileScanner.close();
		} catch (IOException e) {
			System.out.println("Problem reading " + adventureName + "Synonyms.txt");
			e.printStackTrace();
		}
		// Initialize the player's inventory to empty
		playerInventory = new ArrayList<AdvObject>();
		
		// Initialize the current room to the first room in the given adventure
		currentRoom = rooms.get(rooms.firstKey());
		
		// Initialize the flag for keeping track of the player's option to quit
		gameOver = false;
	}
	
	/** Plays the game */
	private void play() {
		
		// gives the full description of the starting room
		executeLookCommand();
		// the starting room has been visited
		currentRoom.setVisited(true);
		
		// what the game does while it is not over
		while (!gameOver) {
			
			// gets the current room number
			int temp = currentRoom.getRoomNumber();
			
			// checks all of the available entries in the current rooms motion table
			for (AdvMotionTableEntry entry : currentRoom.getMotionTable()) {
				if (entry.getDirection().equals("FORCED")) {
					executeMotionCommand("FORCED");
					if (temp != currentRoom.getRoomNumber()) { break; } // if we took a FORCED exit, break the loop
				}
			}
			
			if (temp != currentRoom.getRoomNumber()) { continue; } // if we took a FORCED exit, re-start the while-loop
			
			// Initializes AdvCommand and AdvObject to null to prepare for command parsing
			AdvCommand command = null;
			AdvObject obj = null;
			
			// Initializes strings 'action' and 'noun' and sets them to null to prepare for command parsing
			String action, noun = action = null;
			
			// signals the user to input a command 
			// then reads the input given by the user
			System.out.print("> ");
			input = scan.nextLine().toUpperCase().trim();
			
			// if the command contains a space the action is the first word and the noun is the second word
			if (input.contains(" ")) {
				action = input.substring(0, input.indexOf(' '));
				noun = input.substring(input.lastIndexOf(' ') + 1);
			} else { action = input; }
			
			// if the user's command words are valid for this Adventure, point command and obj to
			// the appropriate objects for command parsing (will be executed as null if the command
			// and/or object were not found in the appropriate dictionary for this game)
			if (validCommands.containsKey(action)) {
				command = validCommands.get(action);
				if (validObjects.containsKey(noun)) {
					obj = validObjects.get(noun);
				}
			}
			
			// if the user's direction is not available for the given room then it tells the user that
			// if the user's command was unrecognizable to the program then it tells the user the command is unavailable 
			if (command != null) {
				command.execute(this, obj);
			} else { System.out.println("Unavailable command."); }
		}
	}
	
	/* Method: executeMotionCommand(direction) */
	/**
	 * Executes a motion command. This method is called from the
	 * AdvMotionCommand class to move to a new room.
	 * 
	 * @param direction
	 *            The string indicating the direction of motion
	 */
	public void executeMotionCommand(String direction) {

		AdvMotionTableEntry[] motionTable = currentRoom.getMotionTable();
		for (AdvMotionTableEntry entry : motionTable) {
			if ( direction.equals(entry.getDirection()) ) {
				if ( entry.getDestinationRoom() > 0 && (entry.getKeyName() == null 
					|| playerInventory.contains(validObjects.get(entry.getKeyName()))) ) { 
					
					currentRoom = rooms.get(entry.getDestinationRoom());
					if (currentRoom.hasBeenVisited() && currentRoom.getDescription().length != 0) {
						System.out.println(currentRoom.getName());
					} else {
						executeLookCommand();
						currentRoom.setVisited(true);
					}
					return; // if we moved, get out of this method
				}
			}
		}
		if ( validCommands.containsKey(direction) && !direction.equals("FORCED") ) { 
			System.out.println("Unavailable direction.");  // If we didn't move, perhaps explain why
		}
		//super.executeMotionCommand(direction);
	}

	/* Method: executeQuitCommand() */
	/**
	 * Implements the QUIT command. This command should ask the user to confirm
	 * the quit request and, if so, should exit from the play method. If not,
	 * the program should continue as usual.
	 */
	public void executeQuitCommand() {
		
		System.out.println("Are you sure (Y/N)? ");
		input = scan.nextLine().trim().toUpperCase();
		if (input.equals("Y")) { gameOver = true; }
		//super.executeQuitCommand(); 
	}

	/* Method: executeHelpCommand() */
	/**
	 * Implements the HELP command. Your code must include some help text for
	 * the user.
	 */
	public void executeHelpCommand() {
		System.out.println("List of all possible commands:");
		for (String command : validCommands.keySet()) { System.out.println(command); }
		// super.executeHelpCommand();
	}

	/* Method: executeLookCommand() */
	/**
	 * Implements the LOOK command. This method should give the full description
	 * of the room and its contents.
	 */
	public void executeLookCommand() {
		for (String line : currentRoom.getDescription()) { System.out.println(line); }
		//super.executeLookCommand(); 
	}

	/* Method: executeInventoryCommand() */
	/**
	 * Implements the INVENTORY command. This method should display a list of
	 * what the user is carrying.
	 */
	public void executeInventoryCommand() {
		
		if (playerInventory.isEmpty()) { 
			System.out.println("You are empty-handed."); 
		} else {
			for (AdvObject object : playerInventory) {
				System.out.println(object.getName() + ": " + object.getDescription());
			}
		}

		//super.executeInventoryCommand();
	}

	/* Method: executeTakeCommand(obj) */
	/**
	 * Implements the TAKE command. This method should check that the object is
	 * in the room and deliver a suitable message if not.
	 * 
	 * @param obj
	 *            The AdvObject you want to take
	 */
	public void executeTakeCommand(AdvObject obj) {
		
		if (obj != null) {
			if (currentRoom.containsObject(obj)) {
				currentRoom.removeObject(obj);
				playerInventory.add(obj);
				System.out.println("Taken.");
			} else { System.out.println("I don't see any " + obj.getName() + "."); }
		} else { System.out.println("There is no such object."); }
		//super.executeTakeCommand(obj);
	}

	/* Method: executeDropCommand(obj) */
	/**
	 * Implements the DROP command. This method should check that the user is
	 * carrying the object and deliver a suitable message if not.
	 * 
	 * @param obj
	 *            The AdvObject you want to drop
	 */
	public void executeDropCommand(AdvObject obj) {
		
		if (obj != null) {
			if (playerInventory.contains(obj)) {
				playerInventory.remove(obj);
				currentRoom.addObject(obj);
				System.out.println("Dropped.");
			} else { System.out.println("You don't have any " + obj.getName() + " to drop."); }
		} else { System.out.println("You don't have that object."); }

		//super.executeDropCommand(obj);
	}

	/* Private instance variables */
	private SortedMap<Integer, AdvRoom> rooms;  // this adventure's rooms
	private HashMap<String, AdvCommand> validCommands; // this adventure's commands
	private HashMap<String, AdvObject> validObjects; // this adventure's objects
	private List<AdvObject> playerInventory; // the objects currently held by the player of this adventure
	private AdvRoom currentRoom; // the room the player of this adventure currently occupies
	private String input; // for parsing commands
	private boolean gameOver; // false unless the player has confirmed that they'd like to quit or has lost the game
}

