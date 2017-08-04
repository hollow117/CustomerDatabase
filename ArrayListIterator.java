import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  InteractiveDBTester.java
//File:             ArrayListIterator.java
//Semester:         CS367 Summer 2016
//
//Author:           Kevin Herro kherro@wisc.edu
//CS Login:         herro
//Lecturer's Name:  Strominger
//Lab Section:      001
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
//Pair Partner:     Jason Choe
//Email:            choe2@wisc.edu
//CS Login:         choe
//Lecturer's Name:  Strominger
//Lab Section:      001
///////////////////////////////////////////////////////////////////////////////

/**
 * The ArrayListIterator class provides an iterator
 * for the customer database
 * 
 * <p>Bugs: None known
 *
 * @author Kevin Herro, Jason Choe
 */

public class ArrayListIterator<E> implements Iterator<Customer> {
	/* Private fields */
	private List<Customer> customerList;
	private int currPos;
	
	/**
	 * This method constructs the Iterator by initializing its
	 * class members
	 *
	 * @param List<Customer> list - the list who will be traversed
	 * @return Nothing
	 */
	public ArrayListIterator(List<Customer> list) {
		customerList = list;
		currPos = 0;
	}
	
	/**
	 * This method determines whether the list has another contigous item
	 *
	 * @return true if the list has another contiguous item, false otherwise
	 */
	public boolean hasNext() {
		return (currPos < customerList.size());
	}
	
	/**
	 * This method returns the object the Iterator is currently pointing to
	 * and advances the Iterator to the next object
	 *
	 * @return the current object that the Iterator is pointing to
	 */
	public Customer next() {
		if (currPos >= customerList.size()) {
			throw new NoSuchElementException();
		}
		Customer customer = customerList.get(currPos);
		currPos++;
		return customer;
	}
}