package models;

/**
 * This class encapsulates the delivery market regions data. It holds
 * information like name of the market region, total deliveries in that region,
 * total number of late deliveries in that region and the proportion of late
 * deliveries for that region.
 **/
public class DeliveryMarket implements Comparable<DeliveryMarket> {

	private String market;
	private int totalDeliveries;
	private int lateDeliveries;
	private double lateDeliveryProportion;

	public DeliveryMarket(String market, int totalDeliveries, int lateDeliveries) {
		this.market = market;
		this.totalDeliveries = totalDeliveries;
		this.lateDeliveries = lateDeliveries;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public int getTotalDeliveries() {
		return totalDeliveries;
	}

	public void addTotalDeliveries(int totalDeliveries) {
		this.totalDeliveries += totalDeliveries;
	}

	public int getLateDeliveries() {
		return lateDeliveries;
	}

	public void addLateDeliveries(int lateDeliveries) {
		this.lateDeliveries += lateDeliveries;
	}

	public double getLateDeliveryProportion() {
		return lateDeliveryProportion;
	}

	public void setLateDeliveryProportion(double lateDeliveryProportion) {
		this.lateDeliveryProportion = lateDeliveryProportion;
	}

	@Override
	public int compareTo(DeliveryMarket o) {
		if (this.lateDeliveryProportion == o.lateDeliveryProportion)
			return 0;
		return this.lateDeliveryProportion > o.lateDeliveryProportion ? 1 : -1;
	}

	@Override
	public String toString() {
		return this.getMarket() + " - " + this.getLateDeliveryProportion();
	}

}
