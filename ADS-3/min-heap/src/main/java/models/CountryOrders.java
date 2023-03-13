package models;

/**
 * This class encapsulates the data for each country with the number of orders
 * where the products are delivered. It holds information like the country name
 * and the number of orders from that country
 */
public class CountryOrders implements Comparable<CountryOrders> {

	private String country;
	private int totalOrders;

	public CountryOrders(String country, int totalOrders) {
		this.country = country;
		this.totalOrders = totalOrders;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void addTotalOrders(int totalOrders) {
		this.totalOrders += totalOrders;
	}

	@Override
	public int compareTo(CountryOrders o) {
		if (this.totalOrders == o.totalOrders)
			return 0;
		return this.totalOrders > o.totalOrders ? 1 : -1;
	}

	@Override
	public String toString() {
		return this.getCountry() + " - " + this.getTotalOrders();
	}
}
