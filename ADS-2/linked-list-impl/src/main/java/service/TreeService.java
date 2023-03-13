package service;

import model.Customer;
import model.Order;
import model.Record;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides the implementation supporting retrieval of information associated with customers and their orders
 * based on a BST implementation
 *
 * */

public class TreeService {

    /*
    This attribute holds the records that are read from the data file initially. These records are processsed
    and loaded into the BSTs.
    */
    List<Record> records;

    TreeImpl<Customer> customerTreeImpl;
    TreeImpl<Order> orderTreeImpl;

    /**
     * Upon initialization, build a binary search tree with customer and order data from the passed records.
     *
     * @param records: The list encapsulating raw data read form the source data file
     * */
    public TreeService(List<Record> records) {
        this.records = records;
        this.buildCustomerTree();
        this.buildOrderTree();
    }


    /**
     * This method builds a tree with the Customer objects. Each customer object holds information regarding a customer
     * and also identifiers to the orders associated with the customer. Workflow within this method is as follows:
     * 1. Sort the records based on customer id so that all records associated with a customer appear together in the list
     * 2. Iterate through the sorted list adding the customer to the resultant list every time we encounter a new customer id.
     * 3. If we come across a record with a customer id that we have already seen, it implies that the record corresponds
     * to another order of an already seen customer. In this case, just add the order id to the already created customer object
     *
     * */
    private void buildCustomerTree(){
        List<Record> recordsSortedByCustomerId = records.stream()
                .sorted(Comparator.comparingInt(Record::getCustomerId))
                .collect(Collectors.toList());

        List<Customer> customers = new ArrayList<>();
        int lastCustomerId = -1;
        Customer c = null;
        for(Record r: recordsSortedByCustomerId){
            if(lastCustomerId != r.getCustomerId()){
                c = new Customer(r);
                lastCustomerId = r.getCustomerId();
                customers.add(c);
            }
            Objects.requireNonNull(c).addOrderId(r.getOrderId());
        }

        System.out.println("Building a binary search tree with customer data. Num nodes in tree - " + customers.size());
        this.customerTreeImpl = new TreeImpl<>(customers, false);
    }

    /**
     * This method builds a tree with the Orders objects. Each order object holds information the items that are a part of the order
     * Workflow within this method is as follows:
     * 1. Sort the records based on order id so that all records associated with an order appear together in the list
     * 2. Iterate through the sorted list adding the order to the resultant list every time we encounter a new order id.
     * 3. If we come across a record with an order id that we have already seen, it implies that the record corresponds
     * to another item within the already seen order. In this case, just add the order item to the already created order object
     *
     * */
    private void buildOrderTree(){
        List<Record> recordsSortedByOrderId = records.stream()
                .sorted(Comparator.comparingInt(Record::getOrderId))
                .collect(Collectors.toList());

        List<Order> orders = new ArrayList<>();
        int lastOrderId = -1;
        Order o = null;
        for(Record r: recordsSortedByOrderId){
            if(lastOrderId != r.getOrderId()){
                o = new Order(r);
                lastOrderId = r.getOrderId();
                orders.add(o);
            }
            Objects.requireNonNull(o).addOrderItem(r);
        }

        System.out.println("Building a binary search tree with order data. Num nodes in tree - " + orders.size());
        this.orderTreeImpl = new TreeImpl<>(orders, true);
    }

    /**
     * This method encapsulates the invocation of the relevant methods to fetch and display customer and associated order information.
     * Workflow of this method is as follows:
     * 1. Retrieve the Customer object corresponding to the given customer id from the customer BST
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
            for(int orderId: c.getOrders()){
                o = this.getOrderDetails(orderId);
                System.out.println(o);
            }
        }
    }

    /**
     * @param customerId: The unique identifier for the customer whose associated data needs to be retrieved
     * @return The Customer object associated with the provided customer id. If the customer is not found, null is returned
     * */
    public Customer getCustomerDetails(int customerId){
        System.out.println("Querying customer BST for customer ID: " + customerId);
        return this.customerTreeImpl.findNode(customerId);
    }

    /**
     * @param orderId: The unique identifier for the order whose associated data needs to be retrieved
     * @return The Order object associated with the provided order id. If the order is not found, null is returned
     * */
    public Order getOrderDetails(int orderId){
        System.out.println("Querying order BST for order ID: " + orderId);
        return this.orderTreeImpl.findNode(orderId);
    }
}
