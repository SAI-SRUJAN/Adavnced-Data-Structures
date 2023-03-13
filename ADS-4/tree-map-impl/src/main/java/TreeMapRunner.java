import services.*;
import util.DataReader;
import util.Table;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class TreeMapRunner {

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        Scanner sc = new Scanner(System.in);
        var orderData = DataReader.GetCustomerData();
        var customerMap = (TreeMap<Integer>) new CreateCustomerMap(orderData).ComputeWithTimer();
        var orderMap = (TreeMap<Integer>) new CreateOrderMap(orderData).ComputeWithTimer();
        var categoryMap = (TreeMap<String>) new CreateCategoryMap(orderData).ComputeWithTimer();
        System.out.println("Search for a customer Id: ");
        var customerId = sc.nextInt();
        var orderIds = new QueryMap<>(customerMap, customerId).ComputeWithTimer();
        Table.Draw(List.of("Order Id"),
                (List<Object>) orderIds);
        System.out.println("Search for a order Id: ");
        Integer orderId = sc.nextInt();
        var orderDetails = new QueryMap<>(orderMap, orderId).ComputeWithTimer();
        Table.Draw(List.of("Order Id", "Product", "Delivery Status"),
                (List<Object>) orderDetails);
        System.out.println("Search for a Category: ");
        sc.nextLine();
        String category = sc.nextLine();
        var products = new QueryMap<>(categoryMap, category).ComputeWithTimer();
        if (products != null)
            Table.Draw(List.of("Product Name"),
                    (List<Object>) products);
        else
            System.out.println("Sorry no category by this name found");
    }
}


