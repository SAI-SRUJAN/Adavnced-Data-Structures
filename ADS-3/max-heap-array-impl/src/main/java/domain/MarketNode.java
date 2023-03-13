package domain;

public class MarketNode implements IHeapNode {

    private String _market;
    private Double _orderCount;
    private Double _lateDeliveryCount;
    private Double _proportion;
    private MarketNode(String market, Boolean isLateDelivery) {
        _market = market;
        _orderCount = 1D;
        _lateDeliveryCount = isLateDelivery ? 1D : 0D;
        _proportion = _lateDeliveryCount/_orderCount;
    }
    public  MarketNode(){};

    @Override
    public IHeapNode[] CreateArray(Integer Size) {
        return new IHeapNode[Size];
    }

    @Override
    public IHeapNode CreateNewObject(Object name, Object value) {
        var order = (Order) value;
        var isLateDelivery = order.getDeliveryStatus().equals("Late delivery");
        return new MarketNode(order.getMarket(),isLateDelivery);
    }


    @Override
    public void UpdateValue(Object value) {
        var lateDelivery = (Double) value;
        _orderCount++;
        _lateDeliveryCount +=lateDelivery;
        _proportion = _lateDeliveryCount/_orderCount;
    }

    @Override
    public Object GetId() {
        return _market;
    }

    @Override
    public Double GetValue() {
        return _proportion;
    }

    @Override
    public String toString() {
        return String.format("%-45s|%-45s|", _market, _proportion);
    }
}
