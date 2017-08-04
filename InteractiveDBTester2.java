import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InteractiveDBTester2 {

	public static void main(String[] args) {

		// *** Add code for steps 1 - 3 of the main method ***
		// Check whether exactly one command-line argument is given; if not,
		// display "Please provide input file as command-line argument" and
		// quit.

		if (args.length != 1) {
			System.out.println("Please provide input file as command-line " + "argument");
			System.exit(0);
		}

		// Check whether the input file exists and is readable; if not, display
		// "Error: Cannot access input file" and quit.
		File file = new File(args[0]);
		if (!file.exists() && !file.canRead()) {
			System.out.print("Error: Cannot access input file");
			System.exit(0);
		}

		Scanner stdin = new Scanner(System.in); // for reading console input
		printOptions();
		boolean done = false;
		while (!done) {
			System.out.print("Enter option ( dfhisqr ): ");
			String input = stdin.nextLine();
			input = input.toLowerCase(); // convert input to lower case

			// only do something if the user enters at least one character

			if (input.length() > 0) {
				char choice = input.charAt(0); // strip off option character
				String remainder = ""; // used to hold the remainder of input
				if (input.length() > 1) {
					// trim off any leading or trailing spaces
					remainder = input.substring(1).trim();
				}

				CustomerDatabase cdb = new CustomerDatabase();
				List<String> customerList = new ArrayList<String>();

				try (Scanner scn = new Scanner(file);) {
					while (scn.hasNext()) {
						String line1 = scn.nextLine();
						String[] breakDown = line1.split(",");
						String customer = breakDown[0];
						customerList.add(customer);
						Customer one = new Customer(customer);
						for (int i = 1; i < breakDown.length; i++) {
							one.getWishlist().add(breakDown[i]);
						}
						cdb.myDB.add(one);
					}
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}

				switch (choice) {

				case 'd':
					// *** Add code to implement this option ***

					// Load the data from the input file and use it to
					// construct a customer database.

					// If product is not in the database, display
					// "product not found"
					if (cdb.containsProduct(remainder)) {
						cdb.removeProduct(remainder);
						System.out.println("product discontinued");
					} else {
						System.out.println("product not found");
					}

					break;

				case 'f':
					// *** Add code to implement this option ***

					// If customer is not in the database, display
					// "customer not found"

					if (!cdb.containsCustomer(remainder)) {
						System.out.println("customer not found");

						// Otherwise, find customer and display the
						// customer
						// (on one line) in the format:
						// customer:product1,product2,product3
					} else {
						System.out.print(remainder + ":");
						String makeNewArray = "";
						for (int i = 0; i < cdb.getProducts(remainder).size(); i++) {
							if (i == cdb.getProducts(remainder).size() - 1) {
								makeNewArray += cdb.getProducts(remainder).get(i);
							} else {
								makeNewArray += cdb.getProducts(remainder).get(i) + ",";
							}
						}

						System.out.println(makeNewArray.trim());
					}
					break;

				case 'h':
					printOptions();
					break;

				case 'i':
					// *** Add code to implement this option ***

					// -------------- Making ArrayList of product without
					// duplicates---------------

					int count = 0;
					int countProduct = 0;
					List<Integer> numProdPerCust = new ArrayList<Integer>();
					List<String> productsNoDup = new ArrayList<String>();
					Iterator<Customer> itr3 = cdb.myDB.iterator();
					while (itr3.hasNext() && count < cdb.myDB.size()) {
						Iterator<String> productIterator1 = cdb.myDB.get(count).getWishlist().iterator();

						while (productIterator1.hasNext()) {
							countProduct += 1;
							String item = productIterator1.next();
							Iterator<String> productIterator2 = productsNoDup.iterator();
							boolean found = false;
							while (productIterator2.hasNext() && !found) {
								String word = productIterator2.next();
								found = item.equals(word);
							}
							if (!found) {
								productsNoDup.add(item);
							}
						}
						count += 1;
						numProdPerCust.add(countProduct);
						countProduct = 0;
					}

					Iterator<Customer> itr = cdb.myDB.iterator();
					int numCustomer = cdb.myDB.size();
					int numProduct = 0;
					while (itr.hasNext()) {
						numProduct += cdb.getProducts(itr.next().getUsername()).size();
					}
					List<String> productTotalList = new ArrayList<String>();
					Iterator<Customer> itr0 = cdb.myDB.iterator();

					while (itr0.hasNext()) {
						productTotalList.addAll(cdb.getProducts(itr0.next().getUsername()));
					}

					// Display on a line: "Customers: integer, Products:
					// integer"
					System.out.print("Customers: " + numCustomer);
					System.out.println(",Products: " + productsNoDup.size());

					// Display on a line: "# of products/customer: most
					// integer, least integer, average integer"
					Iterator<Customer> itr2 = cdb.myDB.iterator();

					List<Integer> productCount = new ArrayList<Integer>();
					while (itr2.hasNext()) {
						productCount.add(cdb.getProducts(itr2.next().getUsername()).size());
					}
					int most = productCount.get(0);
					int least = productCount.get(0);
					Iterator<Integer> productCountItr = productCount.iterator();
					while (productCountItr.hasNext()) {
						int size = productCountItr.next();
						if (most < size) {
							most = size;
						}
						if (least > size) {
							least = size;
						}
					}

					int average = numProduct / numCustomer;

					System.out.println(
							"# of products/customer: most " + most + ", least " + least + ", average " + average);

					// Display on a line: "# of customers/product: most integer,
					// least integer,
					// average integer"

					// ---------Saving number of Customers found per product
					// from productsNoDup list-----

					int numCurr = 0;
					List<Integer> numCustomers = new ArrayList<Integer>();
					Iterator<Customer> itr5 = cdb.myDB.iterator();
					Iterator<String> noDup = productsNoDup.iterator();
					while (noDup.hasNext()) {
						Iterator<String> custIterator = customerList.iterator();
						String product = noDup.next();
						while (custIterator.hasNext()) {
							String customer = custIterator.next();
							if (cdb.hasProduct(customer, product)) {
								numCurr += 1;
							}
						}
						numCustomers.add(numCurr);
						numCurr = 0;
					}
					System.out.println("product no dup: " + productsNoDup.size());
					// ---------Using numCustomers list to find Most and Least
					// -------------------
					int numMost = numCustomers.get(0);
					int numLeast = numCustomers.get(0);

					Iterator<Integer> custOverProdItr = numCustomers.iterator();
					while (custOverProdItr.hasNext()) {
						if (numMost < custOverProdItr.next()) {
							numMost = custOverProdItr.next();
						}
					}
					Iterator<Integer> custOverProdItr1 = numCustomers.iterator();
					while (custOverProdItr1.hasNext()) {
						if (numLeast > custOverProdItr1.next()) {
							numLeast = custOverProdItr1.next();
						}
					}

					// ---------and
					// Average-------------------------------------------------------
					int total = 0;
					Iterator<Integer> custOverProdItr2 = numCustomers.iterator();
					while (custOverProdItr2.hasNext()) {
						total += custOverProdItr2.next();
					}

					int average2 = total / productsNoDup.size();

					System.out.println("# of customers/product: most " + numMost + ", least " + numLeast + ", average "
							+ average2);
					System.out.println("number of product " + productTotalList.size());

					// Display on a line: "Most popular product: product(s)
					// [integer]"
					int count3 = 0;
					Iterator<String> noDup2 = productsNoDup.iterator();
					List<String> customerNames = new ArrayList<String>();
					System.out.println(productTotalList);
					while (noDup2.hasNext()) {
						count3 = 0;
						String compare2 = noDup2.next();
						Iterator<String> custOverProdItr3 = productTotalList.iterator();
						while (custOverProdItr3.hasNext()) {
							String compare = custOverProdItr3.next();
							if (compare2.equals(compare)) {
								count3 += 1;
								if (count3 == numMost) {
									customerNames.add(compare2);
								}
							}
						}
					}
					System.out.print("Most popular product: ");

					break;

				case 's':
					// *** Add code to implement this option ***

					if (!cdb.containsProduct(remainder)) {
						System.out.println("product not found");
					} else {

						System.out.print(remainder + ":");

						String makeNewArray = "";
						for (int i = 0; i < cdb.getCustomers(remainder).size(); i++) {
							if (i == cdb.getCustomers(remainder).size() - 1) {
								makeNewArray += cdb.getCustomers(remainder).get(i);

							} else {
								makeNewArray += cdb.getCustomers(remainder).get(i) + ",";

							}
						}
						System.out.println(makeNewArray.trim());
					}

					break;

				case 'q':
					done = true;
					System.out.println("quit");
					break;

				case 'r':
					// *** Add code to implement this option ***
					if (cdb.containsCustomer(remainder)) {
						cdb.removeCustomer(remainder);
						System.out.println("customer removed");
					} else {
						System.out.println("customer not found");
					}

					break;

				default: // ignore any unknown commands
					break;
				}
			}
		}

		stdin.close();
	}

	/**
	 * Prints the list of command options along with a short description of one.
	 * This method should not be modified.
	 */
	private static void printOptions() {
		System.out.println("d <product> - discontinue the given <product>");
		System.out.println("f <customer> - find the given <customer>");
		System.out.println("h - display this help menu");
		System.out.println("i - display information about this customer database");
		System.out.println("s <product> - search for the given <product>");
		System.out.println("q - quit");
		System.out.println("r <customer> - remove the given <customer>");
	}
}