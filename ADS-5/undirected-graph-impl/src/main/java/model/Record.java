package model;

/**
 * This class encapsulates the records read from the data file
 * */

public class Record{

    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerCity;
    private String customerCountry;
    private String customerSegment;

    private int orderId;
    private int itemId;
    private double itemTotal;
    private String orderCountry;
    private String orderStatus;
    private String productName;
    private String deliveryStatus;

    public Record(String fileRecord) {
        String[] tmp = fileRecord.split(",");
        this.customerId = Integer.parseInt(tmp[8]);
        this.customerFirstName = tmp[7].trim();
        this.customerLastName = tmp[9].trim();
        this.customerCity = tmp[5].trim();
        this.customerCountry = tmp[6].trim();
        this.customerSegment = tmp[10].trim();

        this.orderId = Integer.parseInt(tmp[13]);
        this.itemId = Integer.parseInt(tmp[16]);
        this.itemTotal = Double.parseDouble(tmp[18]);
        this.orderCountry = tmp[12].trim();
        this.orderStatus = tmp[21].trim();
        this.deliveryStatus = tmp[3].trim();

        this.productName = tmp[22].trim();
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

    public String getCustomerCity() {
        return customerCity;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public String getCustomerSegment() {
        return customerSegment;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    public String getOrderCountry() {
        return orderCountry;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public String getProductName() {
        return productName;
    }


    public String getDeliveryStatus() {
        return deliveryStatus;
    }
}
