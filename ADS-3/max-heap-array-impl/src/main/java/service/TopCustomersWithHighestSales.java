package service;

import domain.CustomerNode;
import domain.Order;
import util.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This operation uses heap to find list of top customers with most sales.</br>
 * <p>
 * algorithm:</br>
 * 1. Gather the all the 'order data' and sort it by customer Id.</br>
 * 2. Then send the above gathered data one by one into the heap creation operation.</br>
 * 3. While inserting each order into heap, check if last index inserted/updated was for same market.</br>
 *           if yes then call updateHeap method where node at the index is updated </br>
 *                   by adding that order's sales and depending on the new value the node is shifted
 *                 up or down in the heap and the new index for that node is returned.</br>
 *           if no then add new node in the heap with a new customer entry and new order's sales</br>
 *                 and the index where the node gets inserted in the heap is returned</br>
 * 4. At the end of the loop we will get all the distinct customers and total sales from each.</br>
 * 5. Return the top 5 customers with most sales.</br>
 * <p>
 * Complexity :</br>
 * 1. Sort orders by customer name -> n * log(n)</br>
 * 2. Operating on each order -> n  ( log n) | for updating the position of node as per its priority</br>
 * 3. order again to get top 5 -> 5*log(n) as 5 << n so its log n</br>
 * </br>
 * As the max is n*log(n) so the complexity is n*log(n)</br>
 */


public class TopCustomersWithHighestSales extends Operation {
    private final MaxHeap _heap;

    public TopCustomersWithHighestSales(MaxHeap heap) {
        this._heap = heap;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        System.out.println("\nTop 5 Customers with highest sales:");
        return Perform();
    }

    @Override
    protected List<Object> Perform() throws CloneNotSupportedException {
        // use stack to combine same customerIds together and accumulate their sales in one.
        var resultHeap = (MaxHeap) _heap.clone();
        var result = new ArrayList<Object>();
        for (int i = 0; i < 5; i++) {
            result.add(resultHeap.ExtractMax());
        }
        return result;
    }
}
