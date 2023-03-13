package models;

import java.util.HashSet;

/**
 * This class encapsulates the customer data. It holds information like customer
 * name, customer id, customer country, all the products bought by the customer
 * and total sales of the customer
 */
public class Customer implements Comparable<Customer> {

	private int customerId;
	private HashSet<String> products;
	private String customerFName;
	private String customerLName;
	private String customerCountry;
	private double totalSales;

	public Customer(int customerId, HashSet<String> products, String customerFName, String customerLName,
			String customerCountry, double totalSales) {
		this.customerId = customerId;
		this.products = products;
		this.customerFName = customerFName;
		this.customerLName = customerLName;
		this.customerCountry = customerCountry;
		this.totalSales = totalSales;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public HashSet<String> getProducts() {
		return products;
	}

	public void addProducts(String products) {
		this.products.add(products);
	}

	public String getCustomerFName() {
		return customerFName;
	}

	public void setCustomerFName(String customerFName) {
		this.customerFName = customerFName;
	}

	public String getCustomerLName() {
		return customerLName;
	}

	public void setCustomerLName(String customerLName) {
		this.customerLName = customerLName;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public double getTotalSales() {
		return totalSales;
	}

	public void addTotalSales(double totalSales) {
		this.totalSales += totalSales;
	}

	@Override
	public int compareTo(Customer customer) {
		return Double.compare(this.getTotalSales(), customer.getTotalSales());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nCustomer Id: ");
		sb.append(customerId);
		sb.append(System.lineSeparator());
		sb.append("Customer Name: ");
		sb.append(customerFName + " " + customerLName);
		sb.append(System.lineSeparator());
		sb.append("Customer Country: ");
		sb.append(customerCountry);
		sb.append(System.lineSeparator());
		sb.append("Products: ");
		String delimiter = "";
		for (String product : products) {
			sb.append(delimiter).append(product);
			delimiter = ",";
		}
		sb.append(System.lineSeparator());
		sb.append("Total Sales: ");
		sb.append(totalSales);
		return sb.toString();
	}
}
