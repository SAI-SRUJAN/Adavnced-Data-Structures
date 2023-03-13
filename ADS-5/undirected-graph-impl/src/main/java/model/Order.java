package model;

/**
 * This class encapsulates the vertices storing order data
 * */

public class Order implements GraphVertex<Order>{

    private int id;
    private String orderCounty;
    private String deliveryStatus;

    public Order(Record r) {
        this.id = r.getOrderId();
        this.deliveryStatus = r.getOrderStatus();
        this.deliveryStatus = r.getDeliveryStatus();
        this.orderCounty = r.getOrderCountry();
    }

    public int getId() {
        return id;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getOrderCounty() {
        return orderCounty;
    }

    @Override
    public String getVertexId() {
        return Integer.toString(this.getId());
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}
