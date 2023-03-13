package model;

/**
 * This class encapsulates the Customer details that will be stored within the
 * vertices of the graph
 */

public class Customer implements Vertex {

	private int customerId;
	private String customerFName;
	private String customerLName;

	public Customer(int customerId, String customerFName, String customerLName) {
		this.customerId = customerId;
		this.customerFName = customerFName;
		this.customerLName = customerLName;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	@Override
	public String getVertexId() {
		return Integer.toString(this.getCustomerId());
	}
}
