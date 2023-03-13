package Service;

import domain.Order;
import util.Table;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * This operation uses stack to find list of top Customers with most sales.</br>
 *</br>
 * algorithm:</br>
 * 1. Gather the all the 'order data' and sort it by customer ID.</br>
 * 2. Then send the above gathered data one by one into the stack.</br>
 * 3. While inserting each order into stack, check if top of stack and incoming order is of same customerId.</br>
 *          if yes then accumulate the result and add the sales and update the top.</br>
 *          if no then add new data to the stack with a new customer entry and that orders sales.</br>
 * 4. At the end of the loop we will get all the distinct countries and total count of orders from each.</br>
 * 5. Return the top 5 countries with most order count.</br>
 *</br>
 * Complexity :</br>
 *  1. Ordering by Customer ID -> n * log(n)</br>
 *  2. operating on each order -> n  ( as stack.push(), stack.pop() and stack.peek() are O(1) )</br>
 *  3. order again to get top 5 -> n*log(n)</br>
 *</br>
 *  as the max is n*log(n) so the complexity is n*log(n)</br>
 */

public class TopCustomersWithHighestSales extends Operation {
    private final List<Order> _orderData;
    public TopCustomersWithHighestSales(List<Order> orderData) {
        this._orderData = orderData;
    }

    @Override
    public void Compute() throws CloneNotSupportedException {
        List<String> headers= Arrays.asList("Customer","Total Sales");
        Table.Draw(headers, Perform());
    }

    @Override
    protected List<Object> Perform() throws CloneNotSupportedException {
        // use stack to combine same customerIds together and accumulate their sales in one.
        Stack<Order> customerStack = new Stack<>();
        for (Order customer: _orderData) {
            Order customerClone = customer.clone();
            if(customerStack.empty()) customerStack.push(customerClone);
            var topOfStack = customerStack.peek();
            if(topOfStack.getCustomerId().equals(customerClone.getCustomerId())){
                var top = customerStack.pop();
                var salesSum = top.getCustomerSales() + customerClone.getCustomerSales();
                top.setCustomerSales(salesSum);
                customerStack.push(top);
            } else customerStack.push(customerClone);
        }
        // end

        // again sort and return top 5.
        return customerStack.stream()
                .sorted(Comparator.comparing(Order::getCustomerSales).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
