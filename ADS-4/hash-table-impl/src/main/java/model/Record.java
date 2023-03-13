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
    private double discount;
    private int itemId;
    private int itemQuantity;
    private double itemTotal;
    private String orderRegion;
    private String orderStatus;
    private String productName;
    private String productCategory;

    public Record(String fileRecord) {
        String[] tmp = fileRecord.split(",");
        this.customerId = Integer.parseInt(tmp[8]);
        this.customerFirstName = tmp[7].trim();
        this.customerLastName = tmp[9].trim();
        this.customerCity = tmp[5].trim();
        this.customerCountry = tmp[6].trim();
        this.customerSegment = tmp[10].trim();

        this.orderId = Integer.parseInt(tmp[13]);
        this.discount = Double.parseDouble(tmp[14]);
        this.itemId = Integer.parseInt(tmp[16]);
        this.itemQuantity = Integer.parseInt(tmp[17]);
        this.itemTotal = Double.parseDouble(tmp[18]);
        this.orderRegion = tmp[20].trim();
        this.orderStatus = tmp[21].trim();
        this.productName = tmp[22].trim();
        this.productCategory = tmp[4].trim();
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

    public String getOrderRegion() {
        return orderRegion;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }
}
