package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates the Order details that will be stored within the Orders HashTable
 * */

public class Order implements Comparable<Order>{

    private int id;
    List<OrderItem> orderItems;

    public Order(Record r) {
        this.id = r.getOrderId();
        this.orderItems = new ArrayList<>();
    }

    public void addOrderItem(Record r){
        this.orderItems.add(new OrderItem(r));
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Order order) {
        return Integer.compare(this.getId(), order.getId());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Order Id: %s%n--------------------------------------------------------------------%n",
                this.id));
        for(OrderItem item: this.orderItems){
            sb.append(String.format("Discount: %s%n" +
                            "Item Id: %s%n" +
                            "Item quantity: %s%n" +
                            "Total: %s%n" +
                            "Region: %s%n" +
                            "Status: %s%n" +
                            "Product name: %s%n--------------------------------------------------------------------%n" ,
                    item.getDiscount(), item.getItemId(), item.getItemQuantity(), item.getItemTotal(),
                    item.getRegion(), item.getStatus(), item.getProductName()));
        }
        return sb.toString();
    }
}

class OrderItem{

    private double discount;
    private int itemId;
    private int itemQuantity;
    private double itemTotal;
    private String region;
    private String status;
    private String productName;

    public OrderItem(Record r){
        this.discount = r.getDiscount();
        this.itemId = r.getItemId();
        this.itemQuantity = r.getItemQuantity();
        this.itemTotal = r.getItemTotal();
        this.region = r.getOrderRegion();
        this.status = r.getOrderStatus();
        this.productName = r.getProductName();
    }

    public double getDiscount() {
        return discount;
    }

    public int getItemId() {
        return itemId;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public String getRegion() {
        return region;
    }

    public String getStatus() {
        return status;
    }

    public String getProductName() {
        return productName;
    }
}
