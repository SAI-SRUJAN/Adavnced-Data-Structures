package Service;

import domain.MarketDeliveryStatus;
import domain.Order;
import util.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This operation uses stack to find late delivery rate for each market.
 *
 * algorithm:</br>
 * 1. Gather the all the 'order data' and sort it by market.</br>
 * 2. Then send the above gathered data one by one into the stack.</br>
 * 3. While inserting each order into stack, check if top of stack and incoming order is of same Market.</br>
 *          if yes then increase the order count by 1.</br>
 *                 and if delivery type is late delivery then add late delivery count by 1.</br>
 *          if no then add new data to the stack with a new market entry and make order count as 1.</br>
 *                and if delivery type is late delivery then add late delivery count also 1.</br>
 *
 * 4. At the end of the loop we will get all the distinct market, order count and late delivery count.</br>
 * 5. Return the market name and late delivery proportion i.e. (late delivery count / order count).</br>
 *
 * Complexity :</br>
 *  1. Ordering by Customer ID -> n * log(n)</br>
 *  2. operating on each order -> n  ( as stack.push(), stack.pop() and stack.peek() are O(1) )</br>
 *  3. order again to get top 5 -> n*log(n)</br>
 *</br>
 *  as the max is n*log(n) so the complexity is n*log(n)</br>
 */

public class LateDeliveryProportionPerMarket extends Operation{
    private final List<Order> _orderData;
    public LateDeliveryProportionPerMarket(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected List<Object> Perform(){
        var dataSortedByRegion = _orderData.
                stream().sorted(Comparator.comparing(Order::getMarket))
                .collect(Collectors.toList());

        var dataReducedByMarket= new Stack<MarketDeliveryStatus>();
        for (Order order : dataSortedByRegion) {
            if (dataReducedByMarket.empty())
                if(order.getDeliveryStatus().equals("Late delivery"))
                    dataReducedByMarket.push(new MarketDeliveryStatus(order.getMarket(),
                            1D, 1D));
                else
                    dataReducedByMarket.push(new MarketDeliveryStatus(order.getMarket(),
                            1D, 0D));
            else {
                var topOfStack = dataReducedByMarket.peek();
                if (!topOfStack.getMarket().equals(order.getMarket())) {
                    if(order.getDeliveryStatus().equals("Late delivery"))
                        dataReducedByMarket.push(new MarketDeliveryStatus(order.getMarket(),
                                1D, 1D));
                    else
                        dataReducedByMarket.push(new MarketDeliveryStatus(order.getMarket(),
                                1D, 0D));
                } else {
                    var top = dataReducedByMarket.pop();
                    if(order.getDeliveryStatus().equals("Late delivery")) {
                        top.setOrderCount(top.getOrderCount() + 1D);
                        top.setLateDeliveryCount(top.getLateDeliveryCount() + 1D);
                    }
                    else
                        top.setOrderCount(top.getOrderCount() + 1D);
                    dataReducedByMarket.push(top);
                }
            }
        }

        return new ArrayList<>(dataReducedByMarket);
    }

    @Override
    public void Compute(){
        List<String> headers= Arrays.asList("Market","Late Delivery Proportion");
        Table.Draw(headers, Perform());
    }
}
