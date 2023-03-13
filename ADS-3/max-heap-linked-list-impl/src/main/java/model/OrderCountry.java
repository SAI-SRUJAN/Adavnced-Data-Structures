package model;

/**
 * This class encapsulates the data for each country to which orders are shipped to.
 * It holds information like the country name and the number of orders from that country
 * */
public class OrderCountry implements Comparable<OrderCountry>{

    String country;
    int numOrders;

    public OrderCountry(Record r){
        this.country = r.getOrderCountry();
        this.numOrders = 1;
    }


    public void addOrder(){
        this.numOrders++;
    }

    public String getCountry() {
        return country;
    }

    public int getNumOrders() {
        return numOrders;
    }

    @Override
    public int compareTo(OrderCountry orderCountry) {
        return Double.compare(this.getNumOrders(), orderCountry.getNumOrders());
    }

    @Override
    public String toString() {
        return this.country + " - " + this.numOrders;
    }
}
