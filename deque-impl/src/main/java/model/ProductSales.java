package model;

public class ProductSales implements Comparable<ProductSales> {

    private String productName;
    private String category;
    private double totalSales;

    public ProductSales(String productName, String category) {
        this.productName = productName;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void addSale(double amt){
        totalSales += amt;
    }

    @Override
    public int compareTo(ProductSales productSales) {
        return Double.valueOf(this.totalSales - productSales.totalSales).intValue();
    }

    @Override
    public String toString(){
        return this.category + " - " + this.productName + " - " + this.totalSales;
    }
}
