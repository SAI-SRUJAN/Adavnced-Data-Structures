package model;

public class ProductSales implements Comparable<ProductSales> {

	private String productName;
	private String category;
	private double totalSales;

	public ProductSales(String productName, String category, double totalSales) {
		this.productName = productName;
		this.category = category;
		this.totalSales = totalSales;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	
	 @Override
	    public int compareTo(ProductSales o) {
	        return this.category.compareTo(o.category);
	    }
}
