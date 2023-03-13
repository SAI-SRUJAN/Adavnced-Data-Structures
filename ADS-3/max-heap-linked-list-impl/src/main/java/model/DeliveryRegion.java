package model;

/**
 * This class encapsulates the data for each delivery region.
 * It holds information like the region name, number of deliveries in the region and
 * number of delayed deliveries in the region
 * */

public class DeliveryRegion implements Comparable<DeliveryRegion>{

    private static final String LATE_DELIVERY_STATUS_LABEL = "Late delivery";

    private String region;
    private int numDeliveries;
    private int numLateDeliveries;

    public DeliveryRegion(Record r){
        this.region = r.getRegion();
        this.addDelivery(r.getDeliveryStatus());
    }

    public void addDelivery(String status){
        this.numDeliveries++;
        if(LATE_DELIVERY_STATUS_LABEL.equalsIgnoreCase(status))
            this.numLateDeliveries++;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getNumDeliveries() {
        return numDeliveries;
    }

    public void setNumDeliveries(int numDeliveries) {
        this.numDeliveries = numDeliveries;
    }

    public int getNumLateDeliveries() {
        return numLateDeliveries;
    }

    public void setNumLateDeliveries(int numLateDeliveries) {
        this.numLateDeliveries = numLateDeliveries;
    }

    public double getLateDeliveryProportion(){
        if(this.numDeliveries == 0)
            return 0.0;
        return (double) this.numLateDeliveries / this.numDeliveries;
    }

    @Override
    public int compareTo(DeliveryRegion deliveryRegion) {
        return Double.compare(this.getLateDeliveryProportion(), deliveryRegion.getLateDeliveryProportion());
    }

    @Override
    public String toString(){
        return this.getRegion() + " - " + this.getLateDeliveryProportion();
    }
}
