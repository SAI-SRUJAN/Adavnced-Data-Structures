package models;

import java.util.HashSet;

public class Order implements Comparable<Order> {

	private int orderId;
	private double orderDiscount;
	private double orderProfit;
	private String orderCountry;
	private String orderStatus;
	private HashSet<String> products;
	private double orderTotal;

	public Order(int orderId, double orderDiscount, double orderProfit, String orderCountry, String orderStatus,
			HashSet<String> products, double orderTotal) {
		this.orderId = orderId;
		this.orderDiscount = orderDiscount;
		this.orderProfit = orderProfit;
		this.orderCountry = orderCountry;
		this.orderStatus = orderStatus;
		this.products = products;
		this.orderTotal = orderTotal;
	}

	public Order(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getOrderDiscount() {
		return orderDiscount;
	}

	public void addOrderDiscount(double orderDiscount) {
		this.orderDiscount += orderDiscount;
	}

	public double getOrderProfit() {
		return orderProfit;
	}

	public void addOrderProfit(double orderProfit) {
		this.orderProfit += orderProfit;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public HashSet<String> getProducts() {
		return products;
	}

	public void addProduct(String product) {
		this.products.add(product);
	}

	public String getOrderCountry() {
		return orderCountry;
	}

	public void setOrderCountry(String orderCountry) {
		this.orderCountry = orderCountry;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void addOrderTotal(double orderTotal) {
		this.orderTotal += orderTotal;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Order Id: ");
		sb.append(orderId);
		sb.append(System.lineSeparator());
		sb.append("Order Status: ");
		sb.append(orderStatus);
		sb.append(System.lineSeparator());
		sb.append("Order Country: ");
		sb.append(orderCountry);
		sb.append(System.lineSeparator());
		sb.append("Order Total: ");
		sb.append(orderTotal);
		sb.append(System.lineSeparator());
		sb.append("Order Discount: ");
		sb.append(orderDiscount);
		sb.append(System.lineSeparator());
		sb.append("Order Profit: ");
		sb.append(orderProfit);
		sb.append(System.lineSeparator());
		sb.append("Products: ");
		String delimiter = "";
		for (String product : products) {
			sb.append(delimiter).append(product);
			delimiter = ",";
		}
		return sb.toString();
	}

	@Override
	public int compareTo(Order o) {
		if (this.orderId == o.orderId)
			return 0;
		return this.orderId > o.orderId ? 1 : -1;
	}
}
