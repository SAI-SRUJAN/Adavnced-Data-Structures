package services;

import domain.Order;

import java.util.List;

public class CreateCustomerMap extends Operation{
    private final List<Order> _orderData;
    public CreateCustomerMap(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var customerTreeMap = new TreeMap<Integer>();
        for(var order:_orderData){
            customerTreeMap.Put(order.getCustomerId(),order.getOrderId());
        }
        return customerTreeMap;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        System.out.println("\nTime taken to create customer tree-map");
        return Perform();
    }
}
