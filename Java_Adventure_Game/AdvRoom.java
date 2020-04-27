/*
 * File: AdvRoom.java
 * ------------------
 * This file defines a class that models a single room in the
 * Adventure game.
 */

import java.io.*;
import java.util.*;

/* Class: AdvRoom */
/**
 * This class defines a single room in the Adventure game. A room is
 * characterized by the following properties:
 * 
 * <ul>
 * <li>A room number, which must be greater than zero
 * <li>Its name, which is a one-line string identifying the room
 * <li>Its description, which is a multiline array describing the room
 * <li>A list of objects contained in the room
 * <li>A flag indicating whether the room has been visited
 * <li>A motion table specifying the exits and where they lead </li>
 * 
 * The external format of the room data file is described in the assignment
 * handout. The comments on the methods exported by this class show how to use
 * the initialized data structure.
 */
public class AdvRoom {

	// Private constructor - clients will use the factory method readFromFile
	private AdvRoom () {
		roomDescription = new ArrayList<String>();
		objectsContained = new ArrayList<AdvObject>();
		motionTable = new ArrayList<AdvMotionTableEntry>();
	}
	
	/* Method: getRoomNumber() */
	/**
	 * Returns the room number.
	 * 
	 * @usage int roomNumber = room.getRoomNumber();
	 * @return The room number
	 */
	public int getRoomNumber() {
		
		return roomNumber;
	}

	/* Method: getName() */
	/**
	 * Returns the room name, which is its one-line description.
	 * 
	 * @usage String name = room.getName();
	 * @return The room name
	 */
	public String getName() {
		
		return roomName;
	}

	/* Method: getDescription() */
	/**
	 * Returns an array of strings that correspond to the long description of
	 * the room (including the list of the objects in the room).
	 * 
	 * @usage String[] description = room.getDescription();
	 * @return An array of strings giving the long description of the room
	 */
	public String[] getDescription() {
		String[] description = new String[roomDescription.size() + objectsContained.size()];
		for (int i = 0; i < description.length; i++) {
			if (i < roomDescription.size()) {
				description[i] = roomDescription.get(i);
			} else {
				description[i] = "There is " + 
								objectsContained.get(i - roomDescription.size()).getDescription() + 
								" here.";
			}
		}
		return description;
	}

	/* Method: addObject(obj) */
	/**
	 * Adds an object to the list of objects in the room.
	 * 
	 * @usage room.addObject(obj);
	 * @param The
	 *            AdvObject to be added
	 */
	public void addObject(AdvObject obj) {
		
		objectsContained.add(obj);
	}

	/* Method: removeObject(obj) */
	/**
	 * Removes an object from the list of objects in the room.
	 * 
	 * @usage room.removeObject(obj);
	 * @param The
	 *            AdvObject to be removed
	 */
	public void removeObject(AdvObject obj) {
		objectsContained.remove(obj);
	}

	/* Method: containsObject(obj) */
	/**
	 * Checks whether the specified object is in the room.
	 * 
	 * @usage if (room.containsObject(obj)) . . .
	 * @param The
	 *            AdvObject being tested
	 * @return true if the object is in the room, and false otherwise
	 */
	public boolean containsObject(AdvObject obj) {
		return (objectsContained.contains(obj));
	}

	/* Method: getObjectCount() */
	/**
	 * Returns the number of objects in the room.
	 * 
	 * @usage int nObjects = room.getObjectCount();
	 * @return The number of objects in the room
	 */
	public int getObjectCount() {
		return objectsContained.size();
	}

	/* Method: getObject(index) */
	/**
	 * Returns the specified element from the list of objects in the room.
	 * 
	 * @usage AdvObject obj = room.getObject(index);
	 * @return The AdvObject at the specified index position
	 */
	public AdvObject getObject(int index) {
		return objectsContained.get(index);
	}

	/* Method: setVisited(flag) */
	/**
	 * Sets the flag indicating that this room has been visited according to the
	 * value of the parameter. Calling setVisited(true) means that the room has
	 * been visited; calling setVisited(false) restores its initial unvisited
	 * state.
	 * 
	 * @usage room.setVisited(flag);
	 * @param flag
	 *            The new state of the "visited" flag
	 */
	public void setVisited(boolean flag) {
		previouslyVisited = flag;
	}

	/* Method: hasBeenVisited() */
	/**
	 * Returns true if the room has previously been visited.
	 * 
	 * @usage if (room.hasBeenVisited()) . . .
	 * @return true if the room has been visited; false otherwise
	 */
	public boolean hasBeenVisited() {
		return previouslyVisited;
	}

	/* Method: getMotionTable() */
	/**
	 * Returns the motion table associated with this room, which is an array of
	 * directions, room numbers, and enabling objects stored in a
	 * AdvMotionTableEntry.
	 * 
	 * @usage AdvMotionTableEntry[] motionTable = room.getMotionTable();
	 * @return The array of motion table entries associated with this room
	 */
	public AdvMotionTableEntry[] getMotionTable() {
		
		AdvMotionTableEntry[] entries = new AdvMotionTableEntry[motionTable.size()];
		for (int i = 0; i < entries.length; i++) {
			entries[i] = motionTable.get(i);
		}
		return entries;
	}

	/* Method: readFromFile(rd) */
	/**
	 * Reads the data for this room from the Scanner scan, which must have been
	 * opened by the caller. This method returns a room if the room
	 * initialization is successful; if there are no more rooms to read,
	 * readFromFile returns null.
	 * 
	 * @usage AdvRoom room = AdvRoom.readFromFile(scan);
	 * @param scan
	 *            A scanner open on the rooms data file
	 * @return a room if successfully read; null if at end of file
	 */
	public static AdvRoom readFromFile(Scanner scan) {
		
		AdvRoom room = new AdvRoom(); // the object for return
		String line; // for handling each line from the scanner
		while ( (line = scan.nextLine()).isEmpty() ) { } // eat empty lines before entry
		
		// First line with text will be the room's number
		room.roomNumber = Integer.parseInt(line.trim());
		
		// Second line will be the short description
		room.roomName = scan.nextLine().trim();
		
		// Next lines will be the long description; its end is signaled by a line containing five dashes
		while ( !(line = scan.nextLine().trim()).equals("-----") ) {
			room.roomDescription.add(line);
		}
		
		// Next will be the list of direction commands followed by the room numbers to which they point
		while ( scan.hasNextLine() && !(line = scan.nextLine().trim()).isEmpty() ) {
			
			// the first part of the line will be the direction
			String direction = line.substring(0, line.indexOf(" "));
			
			// the next part of the line will be the room number and possible key value
			String key = null; // key should be sent to constructor of AdvMotionTableEntry as null unless it exists
			
			// if the line has a forward slash, it has a key
			if (line.contains("/")) {
				// the key will always be just past the forward slash
				key = line.substring(line.indexOf('/') + 1);
				// cut out the slash and key to prepare 'line' for giving its room number value
				line = line.substring(0, line.indexOf('/'));
			}
			
			// the room number will always be the characters just past the last space through
			// the end of the line
			int roomNumber = Integer.parseInt(line.substring(line.lastIndexOf(' ') + 1));
			
			// Store a new AdvMotionTableEntry in motionTable corresponding with this data
			room.motionTable.add(new AdvMotionTableEntry(direction, roomNumber, key));
		}	
		return room;
	}

	/* Private instance variables */
	private int roomNumber; // this room's number
	private String roomName; // this room's name
	private List<String> roomDescription; // this room's description
	private boolean previouslyVisited; // true if this room has already been visited 
	private List<AdvObject> objectsContained; // the objects contained in this room
	private List<AdvMotionTableEntry> motionTable;  // the exits (and their conditions) associated with this room
}
