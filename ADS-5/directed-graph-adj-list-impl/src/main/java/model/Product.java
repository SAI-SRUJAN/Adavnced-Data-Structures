package model;

/**
 * This class encapsulates the Product details that will be stored within the
 * vertices of the graph
 */
public class Product implements Vertex {

	private String productName;

	public Product(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String getVertexId() {
		return this.getProductName();
	}

	@Override
	public String toString() {
		return this.getProductName();
	}
}
