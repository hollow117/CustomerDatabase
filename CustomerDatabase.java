import java.util.*;

///////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Main Class File:  InteractiveDBTester.java
//File:             CustomerDatabase.java
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
 * The CustomerDatabase class constructs the customer database with class
 * members and specific methods to manipulate and gain information for the
 * database
 *
 * <p>
 * Bugs: None known
 *
 * @author Kevin Herro, Jason Choe
 */

public class CustomerDatabase {
	int size;
	List<Customer> myDB;

	/**
	 * This method constructs a new customer database by initializing the above
	 * class variables
	 *
	 * @return A constructed customer database
	 */
	public CustomerDatabase() {
		size = 0;
		myDB = new ArrayList<Customer>();
	}

	/**
	 * This method adds a new customer to the database
	 *
	 * @param String
	 *            c - the customer who is to be added to the database
	 * @return Nothing
	 */
	public void addCustomer(String c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		/* Check if customer already exists in DB */
		Iterator<Customer> itr = myDB.iterator();
		while (itr.hasNext()) {
			Customer customer = itr.next();
			if (c.equals(customer.getUsername())) {
				return;
			}
		}

		/* Add customer to database */
		myDB.add(new Customer(c));
		size++;
	}

	/**
	 * This method adds a specific product to a specific customer's wishlist
	 *
	 * @param String
	 *            c - the customer who is to add the product to their wishlist
	 * @param String
	 *            p - the product to be added to the customer's wishlist
	 * @return Nothing
	 */
	public void addProduct(String c, String p) {
		if (c == null || p == null) {
			throw new IllegalArgumentException();
		}
		/* Check if customer is in database */
		Iterator<Customer> custItr = myDB.iterator();
		Customer customer = null;
		boolean customerFound = false;
		while (custItr.hasNext()) {
			Customer cust = custItr.next();
			if (c.equals(cust.getUsername())) {
				customer = cust;
				customerFound = true;
			} else if (!custItr.hasNext() && !customerFound) {
				throw new IllegalArgumentException();
			}
		}

		/* Check if product is already in wishlist */
		Iterator<String> wishItr = customer.getWishlist().iterator();
		while (wishItr.hasNext()) {
			String product = wishItr.next();
			if (p.equals(product)) {
				return;
			}
		}

		/* Add product to wishlist */
		customer.getWishlist().add(p);
	}

	/**
	 * This method searches the database for a specific customer
	 *
	 * @param String
	 *            c - the customer to be searched for in the database
	 * @return true if customer is found in database, false otherwise
	 */
	public boolean containsCustomer(String c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		/* Check if customer is in database */
		Iterator<Customer> itr = myDB.iterator();
		while (itr.hasNext()) {
			Customer cust = itr.next();
			if (c.equals(cust.getUsername())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method searches the database for a specific product
	 *
	 * @param String
	 *            p - the product to be searched for in the database
	 * @return true if the product is found in at least one customer's wishlist,
	 *         false otherwise
	 */
	public boolean containsProduct(String p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		/* Check if product appears in at least one customer's wish list */
		Iterator<Customer> itr = myDB.iterator();
		while (itr.hasNext()) {
			Customer cust = itr.next();
			Iterator<String> wishItr = cust.getWishlist().iterator();
			while (wishItr.hasNext()) {
				String product = wishItr.next();
				if (p.equals(product)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method searches for a specific product in a specific customer's
	 * wishlist
	 *
	 * @param String
	 *            c - the customer whos wishlist will be searched
	 * @param String
	 *            p - the product to be searched for
	 * @return true iff product is in wishlist for customer, false if customer
	 *         is not in database
	 */
	public boolean hasProduct(String c, String p) {
		if (c == null || p == null) {
			throw new IllegalArgumentException();
		}
		/*
		 * Check if customer is in database If not, return false
		 */
		Customer cust = null;
		Customer customer = null;
		boolean customerFound = false;

		Iterator<Customer> custItr = myDB.iterator();
		while (custItr.hasNext()) {
			cust = custItr.next();
			if (c.equals(cust.getUsername())) {
				customer = cust;
				customerFound = true;
			}
			if (!custItr.hasNext() && !customerFound) {
				return false;
			}
		}

		/* Check if product is in customer's wishlist */
		Iterator<String> wishItr = customer.getWishlist().iterator();
		while (wishItr.hasNext()) {
			String product = wishItr.next();
			if (p.equals(product)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method searches the database for product p and returns a list of all
	 * customers who have it in their wishlist
	 *
	 * @param String
	 *            p - the product to search for in the database
	 * @return new List containing customers who have the specific product in
	 *         their wishlists
	 */
	public List<String> getCustomers(String p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}

		List<String> customers = new ArrayList<String>();

		/* Iterate over each customer in DB */
		Iterator<Customer> custItr = myDB.iterator();
		while (custItr.hasNext()) {
			Customer cust = custItr.next();
			Iterator<String> wishItr = cust.getWishlist().iterator();
			/* Iterate over each customer's wishlist */
			while (wishItr.hasNext()) {
				String product = wishItr.next();
				if (p.equals(product)) {
					customers.add(cust.getUsername());
				}
			}
		}

		/* Determine what to return */
		Iterator<String> customersItr = customers.iterator();
		if (!customersItr.hasNext()) {
			return null;
		} else {
			return customers;
		}
	}

	/**
	 * This method returns a single customer's wishlist
	 *
	 * @param String
	 *            c - the customer whos wishlist is to be returned
	 * @return the wishlist for the customer
	 */
	public List<String> getProducts(String c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		/* Find customer in DB and return wishlist */
		Iterator<Customer> itr = myDB.iterator();
		Customer customer = null;
		while (itr.hasNext()) {
			customer = itr.next();
			if (c.equals(customer.getUsername())) {
				return customer.getWishlist();
			}
		}
		return null;
	}

	/**
	 * This method creates and returns a new Iterator over the Customer objects
	 * in the database in the prder they were added to the database
	 *
	 * @return an Iterator over the Customer objects in the database
	 */
	public Iterator<Customer> iterator() {
		return new ArrayListIterator<Customer>(myDB);
	}

	/**
	 * This method removes a single customer from the database
	 *
	 * @param String
	 *            c - the customer to be removed from the database
	 * @return true if the customer was successfully removed, false otherwise
	 */
	public boolean removeCustomer(String c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		/* Find customer and remove */
		Iterator<Customer> itr = myDB.iterator();
		while (itr.hasNext()) {
			Customer cust = itr.next();
			if (c.equals(cust.getUsername())) {
				itr.remove();
				size--;
				return true;
			}
		}
		return false;
	}

	/**
	 * This method removes a specific product from the database
	 *
	 * @param String
	 *            p - the product to be removed from the database
	 * @return true if the product was successfully removed, false otherwise
	 */
	public boolean removeProduct(String p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		/* Iterate over each customer in DB */
		Iterator<Customer> custItr = myDB.iterator();
		Customer customer;
		String product;
		Iterator<String> wishItr = null;
		boolean removed = false;
		while (custItr.hasNext()) {
			customer = custItr.next();
			/* Iterate over each customer's wishlist */
			wishItr = customer.getWishlist().iterator();
			while (wishItr.hasNext()) {
				product = wishItr.next();
				if (p.equals(product)) {
					wishItr.remove();
				}
			}
		}

		/* Ensure the product has been removed */
		custItr = myDB.iterator();
		while (custItr.hasNext()) {
			customer = custItr.next();
			wishItr = customer.getWishlist().iterator();
			while (wishItr.hasNext()) {
				product = wishItr.next();
				if (p.equals(product)) {
					removed = false;
				}
			}
		}
		return removed;
	}

	/**
	 * This method returns the number of customers in the database
	 *
	 * @return the number of customers in the database
	 */
	public int size() {
		return size;
	}
}