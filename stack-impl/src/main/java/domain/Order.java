package domain;

import com.opencsv.bean.CsvBindByName;

public class Order implements Cloneable {
    @CsvBindByName(column = "Customer Fname")
    private String FirstName;

    @CsvBindByName(column = "Customer Lname")
    private String LastName;

    @CsvBindByName(column = "Customer Id")
    private Integer CustomerId;

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

    public Double getCustomerSales() {
        return CustomerSales;
    }

    public void setCustomerSales(Double customerSales) {
        CustomerSales = customerSales;
    }


    @Override
    public String toString() {
        return String.format("|%-45s|%-45s|", getName(), getCustomerSales());
    }

    public String getCountry() {
        return Country;
    }

    public String getCategory() {
        return Category;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public Order clone() throws CloneNotSupportedException
    {
        return (Order) super.clone();
    }

    public String getMarket() {
        return Market;
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }
}
