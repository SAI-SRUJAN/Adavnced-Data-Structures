package model;

import java.util.HashSet;

/**
 * This class encapsulates the Product Categories stored within the Product
 * Categories Skip List
 */
public class Category implements Comparable<Category> {

	private String category;
	private HashSet<String> products;

	public Category(String category, HashSet<String> products) {
		this.category = category;
		this.products = products;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public HashSet<String> getProducts() {
		return products;
	}

	public void addProduct(String productName) {
		this.products.add(productName);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Category name: " + this.category + "\n");
		sb.append("Products: " + String.join(",", this.products));
		return sb.toString();
	}

	@Override
	public int compareTo(Category o) {
		return this.category.compareTo(category);
	}
}
