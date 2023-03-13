package model;

public class CustomerSale implements Comparable<CustomerSale> {

	private CustomerProductRecord product;
	private double totalSales;

	public CustomerSale(CustomerProductRecord product, double totalSales) {
		this.product = product;
		this.totalSales = totalSales;
	}

	public CustomerProductRecord getProduct() {
		return product;
	}

	public void setProduct(CustomerProductRecord product) {
		this.product = product;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(". Customer name: ");
		sb.append(this.product.getCustomerFName() + " " + this.product.getCustomerLName());
		sb.append(". Total sales: ");
		sb.append(totalSales);
		return sb.toString();
	}

	@Override
	public int compareTo(CustomerSale o) {
		if (this.totalSales == o.totalSales)
			return 0;
		return this.totalSales < o.totalSales ? 1 : -1;
	}

}
