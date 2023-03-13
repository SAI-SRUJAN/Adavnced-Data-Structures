package model;

/**
 * This class encapsulates the Order details that will be stored within the
 * vertices of the graph
 */

public class Order implements Vertex {

	private int orderId;
	private String orderStatus;
	private String orderCountry;
	private String deliveryStatus;

	public Order(int orderId, String orderStatus, String orderCountry, String deliveryStatus) {
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.orderCountry = orderCountry;
		this.deliveryStatus = deliveryStatus;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderCountry() {
		return orderCountry;
	}

	public void setOrderCountry(String orderCountry) {
		this.orderCountry = orderCountry;
	}

	@Override
	public String getVertexId() {
		return Integer.toString(this.getOrderId());
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
		return sb.toString();
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

}
