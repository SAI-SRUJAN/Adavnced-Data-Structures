package domain;

import com.opencsv.bean.CsvBindByName;

public class Order implements Cloneable {
    @CsvBindByName(column = "Customer Fname")
    private String FirstName;

    @CsvBindByName(column = "Customer Lname")
    private String LastName;

    @CsvBindByName(column = "Customer Id")
    private Integer CustomerId;

    @CsvBindByName(column = "Order Id")
    private Integer OrderId;

    @CsvBindByName(column = "Order Country")
    private String Country;

    @CsvBindByName(column = "Sales per customer")
    private Double CustomerSales;

    @CsvBindByName(column = "Category Name")
    private String Category;

    @CsvBindByName(column = "Product Name")
    private String Product;

    @CsvBindByName(column = "Market")
    private String Market;

    @CsvBindByName(column = "Delivery Status")
    private String DeliveryStatus;

    public String getName() {
       return  FirstName + ' ' + LastName;
    }

    public Integer getCustomerId() {
        return CustomerId;
    }


    @Override
    public String toString() {
        return String.format("%-45s|%-45s|%-45s|", getOrderId(), getProduct(),getDeliveryStatus());
    }


    public String getProduct() {
        return Product;
    }


    public Order clone() throws CloneNotSupportedException
    {
        return (Order) super.clone();
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public Integer getOrderId() {
        return OrderId;
    }
}
