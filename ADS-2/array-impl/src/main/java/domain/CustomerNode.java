package domain;

import java.util.HashSet;
import java.util.List;

public class CustomerNode implements ITreeNode {
    private Integer customerId;
    private HashSet<Object> orders;

    public CustomerNode(){}
    private CustomerNode(Integer id){
        customerId = id;
        orders = new HashSet<>();
    }
    private Integer getCustomerId() {
        return customerId;
    }
    public HashSet<Object> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "CustomerItemNode{" +
                "customerId=" + customerId +
                ", orderIds=" + orders +
                '}';
    }

    @Override
    public ITreeNode[] CreateArray(Integer Size) {
        return new CustomerNode[Size];
    }

    @Override
    public ITreeNode CreateNewObject(Integer Id) {
        return new CustomerNode(Id);
    }

    @Override
    public void AddValue(Object value) {
        orders.add(value);
    }

    @Override
    public Integer GetId() {
        return getCustomerId();
    }

    @Override
    public HashSet<Object> GetValue() {
        return getOrders();
    }

    @Override
    public List<String> GetHeaders() {
        return List.of("Order Ids");
    }
}
