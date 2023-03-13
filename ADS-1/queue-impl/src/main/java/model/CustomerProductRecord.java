package model;

public class CustomerProductRecord {

	private double customerSale;
	private String deliveryStatus;
	private String category;
	private int customerId;
	private int orderId;
	private String market;
	private String customerFName;
	private String customerLName;
	private String orderCountry;
	private String productName;

	public CustomerProductRecord(double customerSale, String deliveryStatus, String category, int customerId, String market,
			int orderId, String customerFName, String customerLName, String orderCountry, String productName) {
		this.customerSale = customerSale;
		this.deliveryStatus = deliveryStatus;
		this.category = category;
		this.customerId = customerId;
		this.orderId = orderId;
		this.market = market;
		this.customerFName = customerFName;
		this.customerLName = customerLName;
		this.orderCountry = orderCountry;
		this.setProductName(productName);
	}

	public double getCustomerSale() {
		return customerSale;
	}

	public void setCustomerSale(double customerSale) {
		this.customerSale = customerSale;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
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

	public String getOrderCountry() {
		return orderCountry;
	}

	public void setOrderCountry(String orderCountry) {
		this.orderCountry = orderCountry;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
