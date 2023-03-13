package service;

import hash_table.HashTableImpl;
import model.Customer;
import model.Order;
import model.ProductCategory;
import model.Record;

import java.util.List;

/**
 * This class provides the implementation supporting retrieval of the following information:
 * 1. Given a customer ID, provide details of the customer
 * 2. Given an order ID, provide details of the order
 * 3. Given a category name, list out all products from the category
 *
 * Data to support all the aforementioned queries is stored in a HashTable
 * */

public class HashTableService {

    private HashTableImpl<Integer, Customer> customersHashTable;
    private HashTableImpl<Integer, Order> ordersHashTable;
    private HashTableImpl<String, ProductCategory> productCategoryHashTable;

    public HashTableService(List<Record> records){
        this.buildCustomersMap(records);
        this.buildOrdersMap(records);
        this.buildProductCategoriesMap(records);
    }

    /**
     * This method initializes the HashTable holding customer data
     *
     * @param records The list of records as read from the data file
     * */
    private void buildCustomersMap(List<Record> records){
        double startTime = System.nanoTime();
        this.customersHashTable = new HashTableImpl<>();
        Customer c = null;
        for(Record r: records){
            c = new Customer(r);
            if(!this.customersHashTable.containsKey(c.getId())){
                // If the customer object does not already exist in the HashTable, add the object to the HashTable
                this.customersHashTable.put(c.getId(), c);
            }
            // Add the order information from the current record into the customer object in the HashTable
            this.customersHashTable.get(c.getId()).addOrderId(r.getOrderId());
        }
        double endTime = System.nanoTime();
        System.out.printf("Customers HashTable build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /**
     * This method initializes the HashTable holding orders data
     *
     * @param records The list of records as read from the data file
     * */
    private void buildOrdersMap(List<Record> records){
        double startTime = System.nanoTime();
        this.ordersHashTable = new HashTableImpl<>();
        Order o = null;
        for(Record r: records){
            o = new Order(r);
            if(!this.ordersHashTable.containsKey(o.getId())){
                // If the order object does not already exist in the HashTable, add the object to the HashTable
                this.ordersHashTable.put(o.getId(), o);
            }
            // Add the order item information from the current record into the order object in the HashTable
            this.ordersHashTable.get(o.getId()).addOrderItem(r);
        }
        double endTime = System.nanoTime();
        System.out.printf("Orders HashTable build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /**
     * This method initializes the HashTable holding product category data
     *
     * @param records The list of records as read from the data file
     * */
    private void buildProductCategoriesMap(List<Record> records){
        double startTime = System.nanoTime();
        this.productCategoryHashTable = new HashTableImpl<>();
        ProductCategory c = null;
        for(Record r: records){
            c = new ProductCategory(r);
            if(!this.productCategoryHashTable.containsKey(c.getName())){
                // If the product category does not already exist in the HashTable, add the object to the HashTable
                this.productCategoryHashTable.put(c.getName(), c);
            }
            // Add the product information from the current record into the roduct category object in the HashTable
            this.productCategoryHashTable.get(c.getName()).addProduct(r.getProductName());
        }
        double endTime = System.nanoTime();
        System.out.printf("Product categories HashTable build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /**
     * This method encapsulates the invocation of the relevant methods to fetch and display customer and associated order information.
     * Workflow of this method is as follows:
     * 1. Retrieve the Customer object corresponding to the given customer id from the customer HashTable
     * 2. If the customer object is not found, log the relevant message and exit
     * 3. If the customer object is found, display the same and iterate through the order ids from the customer object.
     * Retrieve and display the order details for each of the order ids
     *
     * @param customerId: The unique identifier for the customer whose associated data needs to be retrieved
     * */
    public void displayOrdersForCustomer(int customerId){
        Customer c = this.getCustomerDetails(customerId);
        Order o;
        if(c != null){
            System.out.println(c);
            c.getOrders().stream().map(this::getOrderDetails).forEach(System.out::println);
        }else{
            System.out.println("Invalid customer id: " + customerId);
        }
    }

    /**
     * Get the Customer object for the specified customer id
     *
     * @param customerId: The unique identifier for the customer whose associated data needs to be retrieved
     * @return The Customer object associated with the provided customer id. If the customer is not found, null is returned
     * */
    public Customer getCustomerDetails(int customerId){
        System.out.println("Querying customer HashTable for customer ID: " + customerId);
        double startTime = System.nanoTime();
        Customer c = this.customersHashTable.get(customerId);
        double endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        return c;
    }

    /**
     * Get the Order object for the specified order id
     *
     * @param orderId: The unique identifier for the order whose associated data needs to be retrieved
     * @return The Order object associated with the provided order id. If the order is not found, null is returned
     * */
    public Order getOrderDetails(int orderId){
        System.out.println("Querying order HashTable for order ID: " + orderId);
        double startTime = System.nanoTime();
        Order o = this.ordersHashTable.get(orderId);
        double endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        return o;
    }

    /**
     * Get the ProductCategory object for the specified category name
     *
     * @param categoryName: The name of the category whose products need to be retrieved
     * @return The ProductCategory object associated with the passed category name. If the order is not found, null is returned
     * */
    public ProductCategory getProductsInCategory(String categoryName){
        System.out.println("Querying product category HashTable for category: " + categoryName);
        double startTime = System.nanoTime();
        ProductCategory c = this.productCategoryHashTable.get(categoryName);
        double endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        return c;
    }

    /**
     * This method encapsulates the invocation of the relevant methods to fetch and display customer and associated order information.
     * Workflow of this method is as follows:
     * 1. Retrieve the Product Category corresponding to the given category name from the product categories HashTable
     * 2. Display the details for from the retrieved product category object
     *
     * @param categoryName: The name of the category whose associated data needs to be retrieved
     * */
    public void displayProductsInCategory(String categoryName){
        ProductCategory c = getProductsInCategory(categoryName);
        if(c != null){
            System.out.println(c);
        }else{
            System.out.println("Invalid category name: " + categoryName);
        }
    }
}
