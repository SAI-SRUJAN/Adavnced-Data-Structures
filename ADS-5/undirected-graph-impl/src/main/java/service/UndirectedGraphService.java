package service;

import model.Record;
import model.Customer;
import model.Order;
import model.Product;
import model.GraphEdge;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class encapsulates the implementation needed to initialize the graphs and run queries on the graph.
 *
 *
 * The following queries are currently supported on the graph
 * 1. Given a product name get all the countries that the product was ordered from
 * 2. Given a product name get all the delayed orders that the product was a part of.
 * */

public class UndirectedGraphService {

    /**
     * We effectively store 2 graphs with a set of common vertices
     * Firstly we have a graph with customers and orders as vertices. Apart from this we have a graph linking up orders
     * and the associated products from within the order. The two graphs can be seen as a singe connected graph
     * as the order vertices are shared between them. This effectively means that a customer vertex is connected to
     * all associated order vertices, which are in turn connected to all associated product vertices.
     * */
    private Graph<Customer, Order> customerOrderGraph;
    private Graph<Order, Product> orderProductGraph;

    private List<Customer> customerVertices;
    private List<Order> orderVertices;
    private List<Product> productVertices;

    /***
     * @param records The list of records as read from the raw data file
     */
    public UndirectedGraphService(List<Record> records){

        System.out.println("Initializing the graph");
        double startTime = System.nanoTime();
        this.initializeCustomerOrderGraph(records);
        this.initializeOrderProductGraph(records);
        double endTime = System.nanoTime();
        System.out.printf("Graph build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /***
     * @param records The list of records as read from the raw data file
     *
     * This method initializes the customer orders graph. This graph has vertices encoding customer and order details
     * The undirected edges connect customer vertices to all orders associated with each of the customers
     */
    private void initializeCustomerOrderGraph(List<Record> records){

        records.sort(Comparator.comparing(Record::getCustomerId).thenComparing(Record::getOrderId));
        customerVertices = new ArrayList<>();
        orderVertices = new ArrayList<>();

        Customer currCustomer = null;
        Order currOrder = null;

        List<GraphEdge> edges = new ArrayList<>();

        for(Record r: records){

            // Case when customer id proceeds to the next one
            if(currCustomer == null || currCustomer.getId() != r.getCustomerId()){
                currCustomer = new Customer(r);
                customerVertices.add(currCustomer);
                currOrder = new Order(r);
                orderVertices.add(currOrder);
                edges.add(new GraphEdge(currCustomer.getVertexId(), currOrder.getVertexId()));
            }

            // Case when order id proceeds to the next one for the same customer
            if(currOrder.getId() != r.getOrderId()){
                currOrder = new Order(r);
                orderVertices.add(currOrder);
                edges.add(new GraphEdge(currCustomer.getVertexId(), currOrder.getVertexId()));
            }
        }

        this.customerOrderGraph = new Graph<>(customerVertices, orderVertices, edges);
    }

    /***
     * @param records The list of records as read from the raw data file
     *
     * This method initializes the order products graph. This graph has vertices encoding order and product details
     * The undirected edges connect the orders to the relevant products that are a part of the order
     */
    private void initializeOrderProductGraph(List<Record> records){

        records.sort(Comparator.comparing(Record::getOrderId).thenComparing(Record::getItemId));

        HashMap<String, Product> productHashMap = new HashMap<>();

        Order currOrder = null;

        List<GraphEdge> edges = new ArrayList<>();

        for(Record r: records){
            if(currOrder == null || currOrder.getId() != r.getOrderId()){
                currOrder = new Order(r);
            }

            if(!productHashMap.containsKey(r.getProductName()))
                productHashMap.put(r.getProductName(), new Product(r));

            edges.add(new GraphEdge(currOrder.getVertexId(),
                    productHashMap.get(r.getProductName()).getVertexId()));
        }

        productVertices = new ArrayList<>(productHashMap.values());
        this.orderProductGraph = new Graph<>(orderVertices, productVertices, edges);
    }

    /***
     * @param productName The name of the product to lookup for
     *
     * @return The countries that the given product was purchased from
     */
    public List<String> getProductPurchaseCountries(String productName){

        /*
           Workflow within the method:
           1. Using the product name, lookup the orderProductGraph to find out the orders that the product was a part of
           2. If there are no orders found for the given product, return an empty list else continue to step 3
           3. Lookup the customers associated with the retrieved orders using the customerOrderGraph
           4. Get the distinct countries from the retrieved list of customers and return the same
        */
        List<Order> orders = this.orderProductGraph.getConnectionsFromType2Vertex(productName);
        if(orders.size() == 0){
            System.out.println("No orders found for this product. It is possible that an invalid product name was passed");
            return new ArrayList<>();
        }

        List<Customer> customers = orders.stream().map(Order::getVertexId).
                map(x -> this.customerOrderGraph.getConnectionsFromType2Vertex(x)).flatMap(List::stream).collect(Collectors.toList());

        return customers.stream().map(Customer::getCountry).distinct().collect(Collectors.toList());
    }


    /***
     * @param productName The name of the product to lookup for
     *
     * @return The delayed orders that the provided product was a part of
     */
    public List<Order> getDelayedConsignments(String productName){

        /*
           Workflow within the method:
           1. Using the product name, lookup the orderProductGraph to find out the orders that the product was a part of
           2. If there are no orders found for the given product, return an empty list else continue to step 3
           3. Filter the list of retrieved orders to keep only the delayed orders
           4. If there are no delayed orders, log the same, else return the filtered list
        */
        List<Order> orders = this.orderProductGraph.getConnectionsFromType2Vertex(productName);

        if(orders.size() == 0){
            System.out.println("No orders found for this product. It is possible that an invalid product name was passed");
            return new ArrayList<>();
        }

        // Filter to keep only delayed orders
        orders = orders.stream().filter(o -> o.getDeliveryStatus().equalsIgnoreCase("Late delivery")).collect(Collectors.toList());

        if(orders.size() == 0){
            System.out.println("No orders containing this product were delayed");
            return new ArrayList<>();
        }

        return orders;
    }
}
