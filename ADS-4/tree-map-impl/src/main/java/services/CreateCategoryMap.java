package services;

import domain.Order;

import java.util.List;
import java.util.Locale;

public class CreateCategoryMap extends Operation{
    private final List<Order> _orderData;
    public CreateCategoryMap(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var categoryTreeMap = new TreeMap<String>();
        for(var order:_orderData){
            categoryTreeMap.Put(order.getCategory().trim().toLowerCase(Locale.ROOT),order.getProduct());
        }
        return categoryTreeMap;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        System.out.println("\nTime taken to create category tree-map");
        return Perform();
    }
}
