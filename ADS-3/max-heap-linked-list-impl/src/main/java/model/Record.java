package model;

/**
 * This class encapsulates the records read from the data file
 * */

public class Record{

    private int orderId;
    private String region;
    private String deliveryStatus;

    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerCountry;
    private double orderItemTotal;

    private String orderCountry;

    public Record(String fileRecord) {
        String[] tmp = fileRecord.split(",");
        this.orderId = Integer.parseInt(tmp[13]);
        this.region = tmp[11];
        this.deliveryStatus = tmp[3];

        this.customerId = Integer.parseInt(tmp[8]);
        this.customerFirstName = tmp[7];
        this.customerLastName = tmp[9];
        this.customerCountry = tmp[6];
        this.orderItemTotal = Double.parseDouble(tmp[18]);

        this.orderCountry = tmp[12];
    }

    public int getOrderId() {
        return orderId;
    }

    public String getRegion() {
        return region;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public double getOrderItemTotal() {
        return orderItemTotal;
    }

    public String getOrderCountry() {
        return orderCountry;
    }
}
