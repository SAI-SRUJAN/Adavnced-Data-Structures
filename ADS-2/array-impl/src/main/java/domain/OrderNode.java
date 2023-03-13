package domain;

import java.util.HashSet;
import java.util.List;

public class OrderNode implements ITreeNode{

    private Integer _orderId;
    private HashSet<Object> _products;

    public OrderNode() {}
    private OrderNode(Integer Id){
        _orderId = Id;
        _products = new HashSet<>();
    }
    @Override
    public ITreeNode[] CreateArray(Integer Size) {
        return new OrderNode[Size];
    }

    @Override
    public ITreeNode CreateNewObject(Integer Id) {
        return new OrderNode(Id);
    }

    @Override
    public void AddValue(Object value) {
        _products.add(value);
    }

    @Override
    public Integer GetId() {
        return getOrderId();
    }

    @Override
    public HashSet<Object> GetValue() {
        return _products;
    }

    @Override
    public List<String> GetHeaders() {
        return List.of("Order Id","Product","Delivery Status");
    }

    private Integer getOrderId() {
        return _orderId;
    }
}
