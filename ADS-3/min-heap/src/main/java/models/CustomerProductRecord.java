package models;

/**
 * This class encapsulates the records read from the data file
 * */


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
	private String orderStatus;
	private double orderItemDiscount;
	private double orderProfit;
	private double orderItemTotal;
	private String customerCountry;

	public CustomerProductRecord(double customerSale, String deliveryStatus, String category, int customerId,
			String market, int orderId, String customerFName, String customerLName, String orderCountry,
			String productName, String orderStatus, double orderItemDiscount, double orderProfit, double orderItemTotal,
			String customerCountry) {
		this.customerSale = customerSale;
		this.deliveryStatus = deliveryStatus;
		this.category = category;
		this.customerId = customerId;
		this.orderId = orderId;
		this.market = market;
		this.customerFName = customerFName;
		this.customerLName = customerLName;
		this.orderCountry = orderCountry;
		this.productName = productName;
		this.orderStatus = orderStatus;
		this.orderItemDiscount = orderItemDiscount;
		this.orderProfit = orderProfit;
		this.orderItemTotal = orderItemTotal;
		this.customerCountry = customerCountry;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public double getOrderItemDiscount() {
		return orderItemDiscount;
	}

	public void setOrderItemDiscount(double orderItemDiscount) {
		this.orderItemDiscount = orderItemDiscount;
	}

	public double getOrderProfit() {
		return orderProfit;
	}

	public void setOrderProfit(double orderProfit) {
		this.orderProfit = orderProfit;
	}

	public double getOrderItemTotal() {
		return orderItemTotal;
	}

	public void setOrderItemTotal(double orderItemTotal) {
		this.orderItemTotal = orderItemTotal;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

}
