package service;

import domain.CountryNode;
import domain.MarketNode;
import domain.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreateCountryHeap extends Operation{
    private final List<Order> _orderData;
    public CreateCountryHeap(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var dataSortedByCountry = _orderData
                .stream().sorted(Comparator.comparing(Order::getCountry)).collect(Collectors.toList());

        var nodeType = new CountryNode();
        CountryNode currentNode = null;
        var index = 0;
        var heap = new MaxHeap(dataSortedByCountry.size(), nodeType);
        for (var order : dataSortedByCountry) {
            var newNode = nodeType.CreateNewObject(order.getCountry(), 1D);
            if (currentNode == null || !currentNode.GetId().equals(newNode.GetId())) {
                currentNode = (CountryNode) newNode;
                index = heap.InsertNode(currentNode);
            } else {
                index = heap.UpdateHeap(index, currentNode.GetValue() + newNode.GetValue());
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
