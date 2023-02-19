package Service;

import domain.Country;
import domain.Order;
import util.Table;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * This operation uses stack to find list of top countries with most order request.</br>
 *
 * algorithm:</br>
 * 1. Gather the all the 'order data' and sort it by country.</br>
 * 2. Then send the above gathered data one by one into the stack.</br>
 * 3. While inserting each order into stack, check if top of stack and incoming order is of same country.</br>
 *          if yes then accumulate the result and increase the count for that country by 1.</br>
 *          if no then add new data to the stack with a new country and make its count as 1.</br>
 * 4. At the end of the loop we will get all the distinct countries and total count of orders from each.</br>
 * 5. Return the top 5 countries with most order count.</br>
 *
 * Complexity :</br>
 *  1. Ordering by country name -> n * log(n)</br>
 *  2. operating on each order -> n  ( as stack.push(), stack.pop() and stack.peek() are O(1) )</br>
 *  3. order again to get top 5 -> n*log(n)</br>
 *</br>
 *  As the max is n*log(n) so the complexity is n*log(n)</br>
 */


public class TopCountriesWithMostOrderRequest extends Operation{

    private final List<Order> _orderData;
    public TopCountriesWithMostOrderRequest(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    protected List<Object> Perform(){
        var dataSortedByCountry = _orderData.
                stream().sorted(Comparator.comparing(Order::getCountry))
                .collect(Collectors.toList());

        var dataReducedByCountry = new Stack<Country>();
        for (Order customer : dataSortedByCountry) {
            if (dataReducedByCountry.empty())
                dataReducedByCountry.push(new Country(customer.getCountry(),1));
            else {
                var topOfStack = dataReducedByCountry.peek();
                if (!topOfStack.getName().equals(customer.getCountry())) {
                    dataReducedByCountry.push(new Country(customer.getCountry(), 1));
                } else {
                    var top = dataReducedByCountry.pop();
                    top.setCount(top.getCount() + 1);
                    dataReducedByCountry.push(top);
                }
            }
        }

        return dataReducedByCountry.stream().sorted(Comparator.reverseOrder())
                .limit(5)
                .collect(Collectors.toList());
    }

    @Override
    public void Compute(){
        List<String> headers= Arrays.asList("Country Name","Total Orders");
        Table.Draw(headers, Perform());
    }
}
