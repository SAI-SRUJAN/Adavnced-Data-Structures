package domain;

public class Customer {
    private int id;
    private String name;

    public Customer(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(Order order) {
        this.id = order.getCustomerId();
        this.name = order.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
