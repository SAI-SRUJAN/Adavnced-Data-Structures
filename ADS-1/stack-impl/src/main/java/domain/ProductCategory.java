package domain;

public class ProductCategory {
    private String category;
    private String product;
    private Double totalSales;

    public ProductCategory(String Category, String Product,Double TotalSales ){
        category = Category;
        product = Product;
        totalSales = TotalSales;
    }

    public String getCategory() {
        return category;
    }

    public String getProduct() {
        return product;
    }

    public Double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Double totalSales) {
        this.totalSales = totalSales;
    }

    @Override
    public String toString() {
        return String.format("|%-45s|%-45s|%-45s|", category, product, Math.round(totalSales));
    }
}
