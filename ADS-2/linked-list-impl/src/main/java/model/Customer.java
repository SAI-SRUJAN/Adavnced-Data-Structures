package model;

import java.util.HashSet;

/**
 * This class encapsulates the Customer details that will be stored within the Customer BST
 * */

public class Customer implements Comparable<Customer>, NodeElement{

    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String segment;
    private HashSet<Integer> orders;

    public Customer(Record r) {
        this.id = r.getCustomerId();
        this.firstName = r.getCustomerFirstName();
        this.lastName = r.getCustomerLastName();
        this.city = r.getCustomerCity();
        this.country = r.getCustomerCountry();
        this.segment = r.getCustomerSegment();
        this.orders = new HashSet<>();
    }

    public void addOrderId(int orderId){
        this.orders.add(orderId);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getSegment() {
        return segment;
    }

    public HashSet<Integer> getOrders() {
        return orders;
    }

    @Override
    public int getNodeIdentifier() {
        return this.getId();
    }

    @Override
    public int compareTo(Customer customer) {
        return Integer.compare(this.getNodeIdentifier(), customer.getNodeIdentifier());
    }

    @Override
    public String toString(){
        String res = String.format("Id: %s%n" +
                        "First name: %s%n" +
                        "Last name: %s%n" +
                        "City: %s%n" +
                        "Country: %s%n" +
                        "Segment: %s%n" +
                        "Order ids: %s%n--------------------------------------------------------------------%n" ,
                this.id, this.firstName, this.lastName, this.city, this.country, this.segment, this.orders.toString());
        return res;
    }
}
