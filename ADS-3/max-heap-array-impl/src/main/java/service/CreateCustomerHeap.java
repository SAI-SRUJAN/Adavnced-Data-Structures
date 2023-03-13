package service;

import domain.CountryNode;
import domain.CustomerNode;
import domain.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreateCustomerHeap extends Operation{
    private final List<Order> _orderData;
    public CreateCustomerHeap(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var dataSortedByCustomer = _orderData
                .stream().sorted(Comparator.comparing(Order::getCustomerId)).collect(Collectors.toList());
        var index = 0;
        var customerNodeType = new CustomerNode();
        var heap = new MaxHeap(dataSortedByCustomer.size(), customerNodeType);
        CustomerNode currentNode1 = null;
        for (var order : dataSortedByCustomer) {
            var newNode = customerNodeType.CreateNewObject(order.getCustomerId(), order);
            if (currentNode1 == null || !currentNode1.GetId().equals(newNode.GetId())) {
                currentNode1 = (CustomerNode) newNode;
                index = heap.InsertNode(currentNode1);
            } else {
                index = heap.UpdateHeap(index, currentNode1.GetValue() + newNode.GetValue());
            }
        }
        return heap;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        System.out.println("\nTime taken to create heap");
        return Perform();
    }
}
