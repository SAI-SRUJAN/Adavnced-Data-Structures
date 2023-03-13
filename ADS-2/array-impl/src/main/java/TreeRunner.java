import domain.CustomerNode;
import domain.Order;
import domain.OrderNode;
import service.*;
import util.DataReader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TreeRunner {
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        Scanner sc= new Scanner(System.in);
        var orderData = DataReader.GetCustomerData();
        var customerIds = orderData.stream()
                .map(Order::getCustomerId)
                .collect(Collectors.toList());

        var customerTree = CustomerTreeCreation(orderData, customerIds);
        System.out.println("\n\n");
        var orderTree = OrderTreeCreation(orderData);
        System.out.println("\n\n");

        // search for a customerId and show all the products
        // purchased by that customer
        var searcherForCustomerTree = new TreeSearch(customerTree);
        searcherForCustomerTree.Search(2,"Result for CustomerId: ");
        System.out.println("\n\n");

        do {
            System.out.println("Enter Order Id to see Details: ");
            var orderId = sc.nextInt();
            // search for a OrderId and show all the products
            // in that order and its delivery status
            var searcherForOrderTree = new TreeSearch(orderTree);
            searcherForOrderTree.Search(orderId, "Result for OrderId: ");
            System.out.println("to search again press 'y'");
        }while(sc.next().equals("y"));
    }


    private static ArrayTree CustomerTreeCreation(List<Order> orderData, List<Integer> customerIds) throws CloneNotSupportedException {
        // create a balanced tree
        var balancedTree = new TreeCreation(customerIds,new CustomerNode()).CreateNew();

        // fill up the tree with customer
        new CustomerTreeUpdate(balancedTree, orderData)
                .ComputeWithTimer("Update the products for each customer");


        return balancedTree;
    }

    private static ArrayTree OrderTreeCreation(List<Order> orderData) throws CloneNotSupportedException {
        var orderIds = orderData.stream()
                .map(Order::getOrderId)
                .collect(Collectors.toList());

        var balancedTree = new TreeCreation(orderIds,new OrderNode()).CreateNew();
        new OrderTreeUpdate(balancedTree,orderData).ComputeWithTimer("Adding values to orders");
        return balancedTree;
     }
}
