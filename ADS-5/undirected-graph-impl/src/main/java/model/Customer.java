package model;

/**
 * This class encapsulates the vertices storing the customer data
 * */

public class Customer implements GraphVertex<Customer> {

    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String segment;

    public Customer(Record r) {
        this.id = r.getCustomerId();
        this.firstName = r.getCustomerFirstName();
        this.lastName = r.getCustomerLastName();
        this.city = r.getCustomerCity();
        this.country = r.getCustomerCountry();
        this.segment = r.getCustomerSegment();
    }

    @Override
    public String getVertexId() {
        return Integer.toString(this.getId());
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

    @Override
    public String toString(){
        String res = String.format("Id: %s%n" +
                        "First name: %s%n" +
                        "Last name: %s%n" +
                        "City: %s%n" +
                        "Country: %s%n" +
                        "Segment: %s%n" ,
                this.id, this.firstName, this.lastName, this.city, this.country, this.segment);
        return res;
    }
}
