package service;

import util.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This operation uses heap to find late delivery rate for each market and sort them in desc order.
 *
 * algorithm:</br>
 * 1. Gather the all the 'order data' and sort it by market.</br>
 * 2. Then send the above gathered data one by one into the heap algorithm.</br>
 * 3. While inserting each order into heap, check if last index inserted/updated was for same market.</br>
 *          if yes then call updateHeap method where node at the index is updated </br>
 *                 by mew proportion and depending on the new value the node is shifted
 *                 up or down in the heap and the new index for that node is returned.</br>
 *          if no then add new node in the heap with a new market entry and new proportion value</br>
 *                and the index where the node gets inserted in the heap is returned</br>
 *
 * 4. At the end of the loop we will get all the distinct market, order count and late delivery count.</br>
 * 5. Return the market name and late delivery proportion in desc-order i.e. (late delivery count / order count).</br>
 *
 * Complexity :</br>
 *  1. Ordering by Market -> n * log(n)</br>
 *  2. operating on each order -> n  ( log n) | for updating the position of node as per its priority</br>
 *  3. order again to get top 5 -> 5*log(n) => can be assumed as log n as 5 is not a long number.</br>
 *</br>
 *  as the max is n*log(n) so the complexity is n*log(n)</br>
 */

public class LateDeliveryProportionPerMarket extends Operation{
    private final MaxHeap _heap;
    public LateDeliveryProportionPerMarket(MaxHeap heap) {
        this._heap = heap;
    }

    @Override
    protected List<Object> Perform() throws CloneNotSupportedException {

        // fetch top 5 markets with late delivery proportions.
        var resultHeap = (MaxHeap) _heap.clone();
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < 5; i++) {
            result.add(resultHeap.ExtractMax());
        }
        return result;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        System.out.println("\nLate Delivery proportion per Market in Desc Order");
        return Perform();
    }
}
