import java.io.*;
import java.util.*;


////////////////////////////////////////////////////////////////////////////////
//ALL STUDENTS COMPLETE THESE SECTIONS
//Title:            Project 1
//Files:            InteractiveDBTester.java, ArrayListIterator.java, 
//					Customer.java, CustomerDatabase.java
//Semester:         CS367 Summer 2016
//
//Author:           Kevin Herro
//Email:            kherro@wisc.edu
//CS Login:         herro
//Lecturer's Name:  Strominger
//Lab Section:      001
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION /////////////////////
//
//CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//If pair programming is allowed:
//1. Read PAIR-PROGRAMMING policy (in cs302 policy) 
//2. choose a partner wisely
//3. REGISTER THE TEAM BEFORE YOU WORK TOGETHER 
//a. one partner creates the team
//b. the other partner must join the team
//4. complete this section for each program file.
//
//Pair Partner:     Jason Choe
//Email:            choe2@wisc.edu
//CS Login:         choe
//Lecturer's Name:  Strominger
//Lab Section:      001
////////////////////////////////////////////////////////////////////////////////


/**
 * The InteractiveDBTester class is used to test and manipulate
 * the customer database with specific commands
 *
 * <p>Bugs: None known
 *
 * @author Kevin Herro, Jason Choe
 */
public class InteractiveDBTester {

    public static void main(String[] args) {
        /* Check whether exactly one command-line argument is given; if not,
         * display "Please provide input file as command-line argument" and quit
         */ 
        if (args.length != 1) {
            System.out.println("Please provide input file as command-line " + 
        "argument");
            System.exit(0);
        }

        /* Check whether the input file exists and is readable; if not, display
         * "Error: Cannot access input file" and quit.
         */
        File file = new File(args[0]);
        if (!file.exists() && !file.canRead()) {
            System.out.print("Error: Cannot access input file");
            System.exit(0);
        }

        /* Construct database from input file */
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

                switch (choice) {

                case 'd':
                    /* Discontinue product if it exists in the database */
                    if (cdb.containsProduct(remainder)) {
                        cdb.removeProduct(remainder);
                        System.out.println("product discontinued");
                    } else {
                        System.out.println("product not found");
                    }

                    break;

                case 'f':
                    /* Find customer in the database and display their 
                     * wishlist */
                    if (!cdb.containsCustomer(remainder)) {
                        System.out.println("customer not found");
                    } else {
                        System.out.print(remainder + ":");
                        String makeNewArray = "";
                        for (int i = 0; i < cdb.getProducts(remainder).size(); 
                        		i++) {
                            if (i == cdb.getProducts(remainder).size() - 1) {
                                makeNewArray += cdb.getProducts(remainder).
                                		get(i);
                            } else {
                                makeNewArray += cdb.getProducts(remainder).
                                		get(i) + ",";
                            }
                        }
                        System.out.println(makeNewArray.trim());
                    }
                    break;

                case 'h':
                    printOptions();
                    break;

                case 'i':
                    /* 
                     * 1. DISPLAY NUMBER OF CUSTOMERS AND TOTAL NUMBER OF 
                     * UNIQUE PRODUCTS 
                     */
                     
                    /* Making ArrayList of product without duplicates */
                    int count = 0;
                    int countProduct = 0;
                    List<Integer> numProdPerCust = new ArrayList<Integer>();
                    List<String> productsNoDup = new ArrayList<String>();
                    Iterator<Customer> itr3 = cdb.myDB.iterator();
                    while (itr3.hasNext() && count < cdb.myDB.size()) {
                        Iterator<String> productIterator1 = cdb.myDB.
                        		get(count).getWishlist().iterator();

                        while (productIterator1.hasNext()) {
                            countProduct += 1;
                            String item = productIterator1.next();
                            Iterator<String> productIterator2 = productsNoDup.
                            		iterator();
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
                        numProduct += cdb.getProducts(itr.next().getUsername()).
                        		size();
                    }
                    List<String> productTotalList = new ArrayList<String>();
                    Iterator<Customer> itr0 = cdb.myDB.iterator();

                    while (itr0.hasNext()) {
                        productTotalList.addAll(cdb.getProducts(itr0.next().
                        		getUsername()));
                    }

                    /* Display on a line: "Customers: integer, Products: 
                     * integer */
                    System.out.print("Customers: " + numCustomer);
                    System.out.println(", Products: " + productsNoDup.size());


                    /*
                     * 2. DISPLAY LARGEST WISHLIST, SMALLEST WISHLIST, AND AVG 
                     * PRODUCTS PER CUSTOMER
                     */
                     
                    Iterator<Customer> itr2 = cdb.myDB.iterator();

                    List<Integer> productCount = new ArrayList<Integer>();
                    while (itr2.hasNext()) {
                        productCount.add(cdb.getProducts(itr2.next().
                        		getUsername()).size());
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

                    double average = (double)numProduct / (double)numCustomer;
                    
                    System.out.println(
                            "# of products/customer: most " + most + 
                            ", least " + least + ", average " + 
                            		Math.round(average));

                    /*
                     * 3. DISPLAY MOST, LEAST, AND AVG CUSTOMERS PER PRODUCT
                     */

                    Iterator<String> noDupItr = productsNoDup.iterator();
                    List<String> numCusts;
                    String product = noDupItr.next();
                    most = cdb.getCustomers(product).size();
                    least = most;
                    int size = 0;
                    int total = 0;
                    
                    noDupItr = productsNoDup.iterator();
                    while (noDupItr.hasNext()) {
                        product = noDupItr.next();
                        numCusts = cdb.getCustomers(product);
                        size = numCusts.size();
                        total += size;
                        if (most < size) {
                            most = size;
                        }
                        if (least > size) {
                            least = size;
                        }
                    }

                    average = (double)total / (double)productsNoDup.size();
                    
                    
                    System.out.println("# customers/products: most " + most + 
                    		", least " + least + ", average " + 
                    		Math.round(average));

                    /*
                     * 4. DISPLAY MOST POPULAR PRODUCT AND ITS FREQUENCY
                     */

                    /* Put all products in one list */
                    List<String> productList = new ArrayList<String>();
                    Iterator<String> wishItr;
                    Iterator<Customer> custItr = cdb.myDB.iterator();
                    Customer customer;
                    
                    while (custItr.hasNext()) {
                        customer = custItr.next();
                        wishItr = customer.getWishlist().iterator();
                        while (wishItr.hasNext()) {
                            product = wishItr.next();
                            productList.add(product);
                        }
                    }
                    
                    
                    /* Determine most popular product */
                    Iterator<String> prodItr = productList.iterator();
                    Iterator<String> prodItr2 = productList.iterator();
                    List<String> mostPopList = new ArrayList<String>();
                    Iterator<String> mostPopItr = mostPopList.iterator();

                    String product2;
                    String productCmp;
                    boolean contains = false;

                    int mostCount = 0;
                    count = 0;
                    
                    while (prodItr.hasNext()) {
                        product = prodItr.next();
                        while (prodItr2.hasNext()) {
                            product2 = prodItr2.next();
                            if (product.equals(product2)) {
                                count++;
                            }
                        }
                        if (count >= mostCount) {
                            while (mostPopItr.hasNext()) {
                                productCmp = mostPopItr.next();
                                if (product.equals(productCmp)) {
                                    contains = true;
                                }
                            }
                            if (count > mostCount) {
                                mostCount = count;
                                mostPopList.removeAll(mostPopList);
                                mostPopList.add(product);
                            }
                            else if (count == mostCount && !contains) {
                                mostCount = count;
                                mostPopList.add(product);
                            }
                        }
                        prodItr2 = productList.iterator();
                        mostPopItr = mostPopList.iterator();
                        contains = false;
                        count = 0;
                    }
                    
                    String mostPopArray = "";
                    
                    for (int i = 0; i < mostPopList.size(); i++) {
                        if (i == mostPopList.size() - 1) {
                            mostPopArray += mostPopList.get(i);
                        }
                        else {
                            mostPopArray += mostPopList.get(i) + ",";
                        }
                    }
                    
                    System.out.println("Most popular product: " + 
                    mostPopArray + " [" + mostCount + "]");

                    break;

                case 's':
                    /* Search for product and display customers who have 
                     * product in wishlist */
                    if (!cdb.containsProduct(remainder)) {
                        System.out.println("product not found");
                    } else {

                        System.out.print(remainder + ":");

                        String makeNewArray = "";
                        for (int i = 0; i < cdb.getCustomers(remainder).
                        		size(); i++) {
                            if (i == cdb.getCustomers(remainder).size() - 1) {
                                makeNewArray += cdb.getCustomers(remainder).
                                		get(i);

                            } else {
                                makeNewArray += cdb.getCustomers(remainder).
                                		get(i) + ",";

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
                    /* Remove customer from database */
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