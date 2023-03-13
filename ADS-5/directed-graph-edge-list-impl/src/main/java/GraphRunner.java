import domain.*;
import util.DataReader;
import util.Table;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GraphRunner {

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        var orderData = DataReader.GetCustomerData();
        var graph = new Graph();

        // creating graph
        var startTime = (double)System.nanoTime();

        for(var order: orderData) {
            var customer = new Customer(order);
            var orderDetails = new OrderDetail(order);
            var product = new Product(order);

            // create the vertices
            graph.addV("customer",customer.getId(),customer);
            graph.addV("order", orderDetails.getId(),orderDetails);
            graph.addV("product",order.getProductId(),product);

            // add the edge between them
            // this will read like customer X 'ordered' order Y
            graph.addE("ordered",customer.getId(),"customer",
                    orderDetails.getId(),"order");
            graph.addE("contains", orderDetails.getId(),"order",
                    product.getId(),"product");

        }

        // graph creation end
        var endTime = (double) System.nanoTime();
        var timeElapsed = endTime - startTime;
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

        System.out.println("Enter Customer id: ");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();

        // find all orders for a customer
        startTime = (double)System.nanoTime();
        ArrayList<OrderDetail> ordersByCustomer = (ArrayList<OrderDetail>) graph.out("ordered",id);
        endTime = (double) System.nanoTime();
        timeElapsed = endTime - startTime;
        // end

        System.out.println("Order and Product Details");
        for(var order: ordersByCustomer){
            startTime = (double)System.nanoTime();
            var products = (List<Object>)graph.out("contains",order.getId());
            endTime = (double) System.nanoTime();
            timeElapsed += endTime - startTime;
            System.out.println("Order Id: "+order.getId());
            Table.Draw(List.of("Product Id","Product Name"),
                    (products));
        }
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        System.out.println();

        System.out.println("*********************************************************");
        System.out.println("*********************************************************");

        startTime = (double)System.nanoTime();
        System.out.println("Orders that got delivered late");
        // find all delayed orders
        List<Object> delayedOrders = ordersByCustomer.stream().filter(x-> x.getStatus()
                        .equalsIgnoreCase("late delivery"))
                        .collect(Collectors.toList());
        endTime = (double) System.nanoTime();
        timeElapsed = endTime - startTime;
        Table.Draw(List.of("Order Id","Delivery Status"),
                (delayedOrders));
        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
        System.out.println();

    }
}


