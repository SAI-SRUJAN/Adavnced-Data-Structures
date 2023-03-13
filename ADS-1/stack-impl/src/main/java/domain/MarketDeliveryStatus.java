package domain;

public class MarketDeliveryStatus {
    private String market;
    private Double orderCount;
    private Double lateDeliveryCount;

    public MarketDeliveryStatus(String market, Double orderCount, Double lateDeliveryCount ) {
        this.market = market;
        this.orderCount = orderCount;
        this.lateDeliveryCount = lateDeliveryCount;
    }

    private Double lateDeliveryProportion() {
        return lateDeliveryCount/orderCount;
    }

    public String getMarket() {
        return market;
    }

    public Double getOrderCount() {
       return  this.orderCount;
    }

    public void setOrderCount(Double orderCount) {
        this.orderCount = orderCount;
    }

    public Double getLateDeliveryCount() {
        return  this.lateDeliveryCount;
    }

    public void setLateDeliveryCount(Double lateDeliveryCount) {
        this.lateDeliveryCount = lateDeliveryCount;
    }

    @Override
    public String toString() {
        return String.format("|%-45s|%-45s|", getMarket(), lateDeliveryProportion());
    }
}
