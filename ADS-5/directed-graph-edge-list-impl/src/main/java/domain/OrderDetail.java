package domain;

public class OrderDetail {
    private int id;
    private String status;

    public OrderDetail(Order order) {
        this.id = order.getOrderId();
        this.status = order.getDeliveryStatus();
    }


    public int getId() {
        return id;
    }


    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("%-45s|%-45s|", getId(),getStatus());
    }
}
