package service;

import domain.Order;

import java.util.List;

public class CustomerTreeUpdate extends Operation{
    private final List<Order> _orderData;
    private final ArrayTree _balancedTree;

    public CustomerTreeUpdate(ArrayTree balancedTree , List<Order> orderData){
        _orderData = orderData;
        _balancedTree = balancedTree;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        for (var order :
                _orderData) {
            var index = _balancedTree.SearchNode(order.getCustomerId(),0);
            if(index != -1)
                _balancedTree.UpdateNode(index,order.getOrderId());
        }
        return true;
    }

    @Override
    public void Compute(String description) throws CloneNotSupportedException {
        System.out.println(description);
        Perform();
    }
}
