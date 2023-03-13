package model;

import java.util.HashSet;

/**
 * This class encapsulates the Customer details that will be stored within the
 * Customers Skip List
 */
public class Customer implements Comparable<Customer> {

	private int customerId;
	private HashSet<String> products;
	private String customerFName;
	private String customerLName;
	private HashSet<Integer> orderIds;
	private String customerCountry;

	public Customer(int customerId, String customerFName, String customerLName, HashSet<String> products,
			HashSet<Integer> orderIds, String customerCountry) {
		this.customerId = customerId;
		this.customerFName = customerFName;
		this.customerLName = customerLName;
		this.products = products;
		this.orderIds = orderIds;
		this.setCustomerCountry(customerCountry);
	}

	public Customer(int customerId) {
		this.customerId = customerId;
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

	public void addProduct(String product) {
		this.products.add(product);
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

	public HashSet<Integer> getOrderIds() {
		return orderIds;
	}

	public void addOrderIds(Integer orderIds) {
		this.orderIds.add(orderIds);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Customer Id: ");
		sb.append(customerId);
		sb.append(System.lineSeparator());
		sb.append("Customer Name: ");
		sb.append(customerFName + " " + customerLName);
		sb.append(System.lineSeparator());
		sb.append("Customer Coutry: ");
		sb.append(customerCountry);
		sb.append(System.lineSeparator());
		sb.append("Products: ");
		String delimiter = "";
		for (String product : products) {
			sb.append(delimiter).append(product);
			delimiter = ",";
		}
		sb.append(System.lineSeparator());
		sb.append("Order IDs: ");
		delimiter = "";
		for (Integer id : orderIds) {
			sb.append(delimiter).append(id);
			delimiter = ",";
		}
		return sb.toString();
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	@Override
	public int compareTo(Customer o) {
		if (this.customerId == o.customerId)
			return 0;
		return this.customerId > o.customerId ? 1 : -1;
	}
}
