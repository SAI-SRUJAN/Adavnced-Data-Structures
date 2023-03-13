package service;

import domain.Order;

import java.util.List;

public class OrderTreeUpdate extends Operation{
    private final List<Order> _orderData;
    private final ArrayTree _balancedTree;

    public OrderTreeUpdate(ArrayTree balancedTree , List<Order> orderData){
        _orderData = orderData;
        _balancedTree = balancedTree;
    }
    @Override
    protected Object Perform() throws CloneNotSupportedException {
        for (var order :
                _orderData) {
            var index = _balancedTree.SearchNode(order.getOrderId(),0);
            if(index != -1)
                _balancedTree.UpdateNode(index,order);
        }
        return true;
    }

    @Override
    public void Compute(String description) throws CloneNotSupportedException {
        System.out.println(description);
        Perform();
    }
}
