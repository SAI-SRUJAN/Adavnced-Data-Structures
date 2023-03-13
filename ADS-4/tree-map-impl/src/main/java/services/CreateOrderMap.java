package services;

import domain.Order;

import java.util.List;

public class CreateOrderMap extends Operation{
    private final List<Order> _orderData;
    public CreateOrderMap(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var orderTreeMap = new TreeMap<Integer>();
        for(var order:_orderData){
            orderTreeMap.Put(order.getOrderId(),order);
        }
        return orderTreeMap;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        System.out.println("\nTime taken to create Order tree-map");
        return Perform();
    }
}
