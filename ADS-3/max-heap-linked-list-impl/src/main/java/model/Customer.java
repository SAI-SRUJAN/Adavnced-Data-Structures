package model;

/**
 * This class encapsulates the customer data. It holds information like customer name, id, country and total sales
 * */
public class Customer implements Comparable<Customer>{

    private int customerId;
    private String fName;
    private String lName;
    private String customerCountry;
    private double totalSales;

    public Customer(Record r){
        this.customerId = r.getCustomerId();
        this.fName = r.getCustomerFirstName();
        this.lName = r.getCustomerLastName();
        this.customerCountry = r.getCustomerCountry();
        this.addSale(r.getOrderItemTotal());
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void addSale(double amount) {
        this.totalSales+= amount;
    }

    @Override
    public int compareTo(Customer customer) {
        return Double.compare(this.getTotalSales(), customer.getTotalSales());
    }

    @Override
    public String toString() {
        return this.customerId + " - " + this.fName + " " + this.lName + " - " + totalSales;
    }
}
