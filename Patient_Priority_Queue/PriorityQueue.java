package h6;
// Just some work at building a priority list of generically comparable items
// from scratch, doesn't work yet..hoping to use this class to make different 
// lists, where each list could store a different type of object as long as that type of
// object is comparable...the idea is we could make a priority lis of PatientVisits, for exammple,
// (PatientVisits needs to be modified to BE comparable, which should just be as easy as comparing 
// urgency (an int) and then arrival time if a tie breaker is needed) and have any PatientVisit
// added to the list be placed in order of priority just using compareTo...
// Problem at the moment, when I try to initialize a new PriorityQueue in a main class, 
// getting a null pointer exception...NEEDS WORK, sharing where I'm currently at


public class PriorityQueue<E extends Comparable<E>> {

	Node head, tail;
	int size;
	
	class Node {
		E data;
		Node prev;
		Node next;
		
		public Node(E e) {
			this.data = e;
			next = null;
			prev = null;
		}
	}
	
	public PriorityQueue() {
		head = tail = null;
		
	}
	
	public int size() {
		int count = 0;
		Node p = head;
		while(p != null) {
			count++;
			p = p.next;
		}
		return count;
	}
	
	public void insert(E e) {
		// node being added
		Node n = new Node(e);
		
		// temp head node to move along list
		
		
		// if first spot is null or lower priority
		// than node being added, new node is head
		// and old head is next
		if (head == null) {
			
			head = n;
			tail = n;
					
		} 
		
		Node h = head;
		if(n.data.compareTo(h.data) > 0) {
			
			head = n;
			h.prev = head;
			head.next = h;
		
		}
		
		// list is not null and head is higher priority
		// than new node
		else {
			
			while (h.next != null) {
				
				// data to add is higher priority than
				// the next nodes data, make new node next
				// node
				if(n.data.compareTo(h.next.data) > 0) {
					Node q = h;
					h = n;
					h.next = q;
					q.prev = h;
					break;
				}
				
				// data to add is lower or 
				// equal priority, keep moving.
				else if (n.data.compareTo(h.next.data) <= 0) {
					h = h.next;
					
				}
			}
			
			// now h.next is null so h
			// is at the tail and data has not been added
			
			if(h.next == null) {
				n.prev = h;
				tail = n;
				
			}
		}

	}
	
	public E front() {
		
		return ((E) head);
		
	}
	
	public E remove() {
		
		if (head == null) {
			throw new NullPointerException();
		}
		
		Node p = head;
		
		if (head.next != null) {
			head = head.next;
		} else {
			head = null;
		}
		return ((E) p);
	
	}
	
	


}
