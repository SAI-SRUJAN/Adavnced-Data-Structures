package Service;

import domain.Order;
import domain.ProductCategory;
import util.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This operation uses stack to first 'group the data' according to the product and category</br>
 * and then find the top product with most sales for each category.</br>
 *</br>
 * algorithm:</br>
 * part 1: group by product</br>
 * 1. Gather the all the 'order data' and sort it by product.</br>
 * 2. Then send the above gathered data one by one into the stack.</br>
 * 3. While inserting each order into stack, check if top of stack and incoming order is of same product.</br>
 *          if yes then accumulate the result and add the sales and update the top.</br>
 *          if no then add new data to the stack with a new product entry and that orders sales.</br>
 * 4. At the end of the loop we will get all the distinct product and total count of orders from each.</br>
 *</br>
 * part 2: group by category and find product with max sales for each category</br>
 * 1. take the result from part 1 and sort it by Category.</br>
 * 2. Then one by one send the above data to stack.</br>
 * While inserting each entry into stack, check if top of stack and incoming order is of same category.</br>
 *          if yes and if the incoming record has more sales than the current top then replace top with</br>
 *          incoming entry.</br>
 *          if no then add new data to the stack with a new category entry.</br>
 * 4. At the end of the loop we will get all the distinct categories and product under each wth best sales.</br>
 *
 *  Complexity :</br>
 *  for both part 1 and part 2</br>
 *  1. Ordering by Customer ID -> n * log(n)</br>
 *  2. operating on each order -> n  ( as stack.push(), stack.pop() and stack.peek() are O(1) )</br>
 *  3. order again to get top 5 -> n*log(n)</br>
 *</br>
 *  as the max is n*log(n) so the complexity is n*log(n)</br>
 */

public class TopProductInEachCategory extends Operation{
    private final List<Order> _orderData;
    public TopProductInEachCategory(List<Order> orderData) {
        this._orderData = orderData;
    }
    @Override
    protected List<Object> Perform(){

        // group by product

        var dataSortedByProduct = _orderData.
                stream().sorted(Comparator.comparing(Order::getProduct))
                .collect(Collectors.toList());

        var dataReducedByProduct = new Stack<ProductCategory>();
        for (Order order : dataSortedByProduct) {
            if (dataReducedByProduct.empty())
                dataReducedByProduct.push(new ProductCategory(order.getCategory(),
                        order.getProduct(), order.getCustomerSales()));
            else {
                var topOfStack = dataReducedByProduct.peek();
                if (!topOfStack.getProduct().equals(order.getProduct())) {
                    dataReducedByProduct.push(new ProductCategory(order.getCategory(),
                            order.getProduct(), order.getCustomerSales()));
                } else {
                    var top = dataReducedByProduct.pop();
                    top.setTotalSales(top.getTotalSales() + order.getCustomerSales());
                    dataReducedByProduct.push(top);
                }
            }
        }

        var dataSortedByCategory = dataReducedByProduct.
                stream().sorted(Comparator.comparing(ProductCategory::getCategory))
                .collect(Collectors.toList());

        // group by Category

        var dataReducedByCategory = new Stack<ProductCategory>();
        for (ProductCategory pc : dataSortedByCategory) {
            if (dataReducedByCategory.empty())
                dataReducedByCategory.push(new ProductCategory(pc.getCategory(),
                        pc.getProduct(), pc.getTotalSales()));
            else {
                var topOfStack = dataReducedByCategory.peek();
                if (!topOfStack.getCategory().equals(pc.getCategory())) {
                    dataReducedByCategory.push(new ProductCategory(pc.getCategory(),
                            pc.getProduct(), pc.getTotalSales()));
                } else {
                    var top = dataReducedByCategory.pop();
                    if(pc.getTotalSales() > top.getTotalSales())
                        dataReducedByCategory.push(new ProductCategory(pc.getCategory(),
                                pc.getProduct(), pc.getTotalSales()));
                    else
                        dataReducedByCategory.push(top);
                }
            }
        }
        return new ArrayList<>(dataReducedByCategory);
    }

    @Override
    public void Compute(){
        List<String> headers= Arrays.asList("Category","Product","Total Sales");
        Table.Draw(headers, Perform());
    }
}
