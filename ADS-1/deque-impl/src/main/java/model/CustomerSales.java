package model;

public class CustomerSales implements Comparable<CustomerSales>{

    private int customerId;
    private String customerFirstName;
    private String customerLastName;
    private double totalSales;

    public CustomerSales(int customerId, String customerFirstName, String customerLastName) {
        this.customerId = customerId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void addSaleAmount(double amt){
        this.totalSales += amt;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Customer id: ");
        sb.append(customerId);
        sb.append(". Customer name: ");
        sb.append(this.customerFirstName + " " + this.customerLastName);
        sb.append(". Total sales: ");
        sb.append(totalSales);
        return sb.toString();
    }

    @Override
    public int compareTo(CustomerSales customerSales) {
        return Double.valueOf(this.totalSales - customerSales.totalSales).intValue();
    }
}
