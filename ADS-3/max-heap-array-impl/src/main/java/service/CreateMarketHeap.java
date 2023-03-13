package service;

import domain.MarketNode;
import domain.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CreateMarketHeap extends Operation{
    private final List<Order> _orderData;
    public CreateMarketHeap(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var dataSortedByMarket = _orderData
                .stream().sorted(Comparator.comparing(Order::getMarket)).collect(Collectors.toList());

        var nodeType = new MarketNode();
        MarketNode currentNode = null;
        var index = 0;
        var heap = new MaxHeap(dataSortedByMarket.size(), nodeType);

        for (var order : dataSortedByMarket) {
            var newNode = nodeType.CreateNewObject(order.getMarket(), order);

            // checks if the new nodes id and last processed node's id is same
            // if no then a new node is inserted
            // else the last inserted node's value is updated and its new index is processed.
            if (currentNode == null || !currentNode.GetId().equals(newNode.GetId())) {
                currentNode = (MarketNode) newNode;
                index = heap.InsertNode(currentNode);
            } else {
                index = heap.UpdateHeap(index, newNode.GetValue());
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
