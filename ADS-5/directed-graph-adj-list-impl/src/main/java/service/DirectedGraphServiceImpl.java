package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import model.Customer;
import model.CustomerProductRecord;
import model.Edge;
import model.Order;
import model.Product;
import util.DirectedGraph;
import util.FileLoader;
import util.Graph;

/**
 * This class encapsulates the implementation needed to initialize the graphs
 * and run queries on the graph.
 *
 *
 * The following queries are currently supported on the graph.<br>
 * 1. Given a customer find all the orders and the products that were
 * ordered.<br>
 * 2. Given a customer id find all the delayed orders.
 */

public class DirectedGraphServiceImpl implements DirectedGraphService {

	/**
	 * We effectively store 2 graphs with a set of common vertices. Firstly, we have
	 * a graph with customers and orders as vertices. Apart from this we have a
	 * graph linking up orders and the associated products from within the order.
	 * The two graphs can be seen as a single connected graph as the order vertices
	 * are shared between them. This effectively means that a customer vertex is
	 * connected to all associated order vertices, which are in turn connected to
	 * all associated product vertices.
	 */

	private static DirectedGraphServiceImpl service;
	List<CustomerProductRecord> fileData;
	private Graph<Customer, Order> customerOrderGraph;
	private Graph<Order, Product> orderProductGraph;

	/**
	 * This method creates an instance of the service if not created and returns
	 * that instance of the service.
	 * 
	 * @return Returns the instance of the service
	 */
	public static DirectedGraphServiceImpl getInstance() {

		if (service == null) {
			synchronized (DirectedGraphServiceImpl.class) {
				if (service == null)
					service = new DirectedGraphServiceImpl();
			}
		}
		return service;
	}

	/**
	 * This method is used to load all the data from the file into a list called
	 * file data in this object in the format required for processing the data for
	 * creating the corresponding graphs.
	 * 
	 * @param path of the file to be loaded
	 */
	@Override
	public void loadData(String path) {
		List<String> rawData = FileLoader.readFile(path);
		fileData = new ArrayList<>();
		String[] data;
		for (String line : rawData) {
			data = line.split(",");
			CustomerProductRecord product = new CustomerProductRecord(Double.parseDouble(data[2]), data[3], data[4],
					Integer.parseInt(data[8]), data[11], Integer.parseInt(data[13]), data[7], data[9], data[12],
					data[22], data[21], Double.parseDouble(data[14]), Double.parseDouble(data[19]),
					Double.parseDouble(data[18]), data[6]);
			fileData.add(product);
		}
	}

	/**
	 * This method is used to build the required graphs for querying
	 * 
	 */
	@Override
	public void buildGraphs() {
		this.buildCustomerOrderGraph();
		this.buildOrderProductGraph();
	}

	/**
	 * This method is used to build the customer orders graph.<br>
	 * The graph has the customer and order vertices. Each of the customer vertex
	 * has a directed edge connecting to each of the associated edge vertices.
	 * 
	 */
	private void buildCustomerOrderGraph() {
		System.out.println("Creating Directed Graph with Customer and Order Details ");
		double startTime = System.currentTimeMillis();
		// Initializing graph to a Directed Graph type
		this.customerOrderGraph = new DirectedGraph<Customer, Order>();
		// Sorting the raw file data by customer id first and then based on the order id
		// Sorting is done to make sure all the orders of customer appear in order which
		// makes the insertion into graph easy as there are multiple records for the
		// same order
		fileData.sort(Comparator.comparing(CustomerProductRecord::getCustomerId)
				.thenComparing(CustomerProductRecord::getOrderId));
		Customer customerRecord = null;
		Order orderRecord = null;
		// Iterate through the file data
		for (CustomerProductRecord record : fileData) {
			// Case when customer id proceeds to the next one
			if (customerRecord == null || record.getCustomerId() != customerRecord.getCustomerId())
				customerRecord = new Customer(record.getCustomerId(), record.getCustomerFName(),
						record.getCustomerLName());
			// Case when order id proceeds to the next one
			if (orderRecord == null || record.getOrderId() != orderRecord.getOrderId()) {
				orderRecord = new Order(record.getOrderId(), record.getOrderStatus(), record.getOrderCountry(),
						record.getDeliveryStatus());
				// Creating an edge object with source and destination
				Edge edge = new Edge(customerRecord.getVertexId(), orderRecord.getVertexId());
				// Adding the vertices and edges into the graph
				this.customerOrderGraph.addEdge(customerRecord, orderRecord, edge);
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method is used to build the order products graph.<br>
	 * The graph has the order and product vertices. Each of the order vertex has a
	 * directed edge connecting to each of the associated product vertices.
	 * 
	 */
	private void buildOrderProductGraph() {
		System.out.println("Creating Directed Graph with Order and Product Details ");
		double startTime = System.currentTimeMillis();
		// Initializing graph to a Directed Graph type
		this.orderProductGraph = new DirectedGraph<Order, Product>();
		// Sorting the raw file data by order id first and then based on the product
		// name. Sorting is done to make sure all the products of same order id appear
		// together which makes the insertion into graph easy as there are multiple
		// records for the same products
		fileData.sort(Comparator.comparing(CustomerProductRecord::getOrderId)
				.thenComparing(CustomerProductRecord::getProductName));
		Order orderRecord = null;
		Product productRecord = null;
		// Iterate through the file data
		for (CustomerProductRecord record : fileData) {
			// Case when order id proceeds to the next one
			if (orderRecord == null || record.getOrderId() != orderRecord.getOrderId()) {
				orderRecord = new Order(record.getOrderId(), record.getOrderStatus(), record.getOrderCountry(),
						record.getDeliveryStatus());
				productRecord = null;
			}
			// Case when we proceed to the next product
			if (productRecord == null || record.getProductName().compareTo(productRecord.getProductName()) != 0) {
				productRecord = new Product(record.getProductName());
				Edge edge = new Edge(orderRecord.getVertexId(), productRecord.getVertexId());
				this.orderProductGraph.addEdge(orderRecord, productRecord, edge);
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/***
	 * Workflow within the method:<br>
	 * 1. Using the customer id, lookup the customerOrderGraph to find out the
	 * orders of the customer<br>
	 * 2. If there are no orders found for the given id, return null<br>
	 * 3. Lookup the products associated with the retrieved orders using the
	 * orderProductGraph<br>
	 * 4. Get the distinct products from the retrieved list of products and return
	 * the orders along with the product details
	 * 
	 * 
	 * @param customerId The id of the customer to lookup for
	 *
	 * @return The orders and the products of the orders of that customer
	 */
	@Override
	public HashMap<String, List<Product>> getOrderProducts(int customerId) {
		double startTime = System.nanoTime();
		HashMap<String, List<Product>> orderProducts = new HashMap<>();
		// Searching the customerOrderGraph for orders of the customer
		List<Order> orders = this.customerOrderGraph.getSourceVertexEdges(Integer.toString(customerId));
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms\n");
		if (orders == null) {
			System.out.println("Customer ID is not found. Please enter the correct ID.");
			return null;
		} else {
			// If orders are found, get the products of the order from orderProductGraph
			for (Order order : orders) {
				List<Product> products = this.orderProductGraph.getSourceVertexEdges(order.getVertexId());
				orderProducts.put(order.toString(), products);
			}
		}
		return orderProducts;
	}

	/***
	 * Workflow within the method:<br>
	 * 1. Using the customer id, lookup the customerOrderGraph to find out the
	 * orders of the customer<br>
	 * 2. If there are no orders found for the given id, return null<br>
	 * 3. If orders are found, return only the orders that had delayed
	 * deliveries<br>
	 * 4. If there were no delayed orders return null
	 * 
	 * 
	 * @param customerId The id of the customer to lookup for
	 *
	 * @return The orders that were delayed of that customer
	 */
	@Override
	public List<Order> getDelayedOrders(int customerId) {
		double startTime = System.nanoTime();
		List<Order> delayedOrders = new ArrayList<>();
		// Searching the customerOrderGraph for orders of the customer
		List<Order> orders = this.customerOrderGraph.getSourceVertexEdges(Integer.toString(customerId));
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms\n");
		if (orders == null) {
			System.out.println("Customer ID is not found. Please enter the correct ID.");
			return null;
		} else {
			// Checking if any of the orders were delayed and adding to the list of delayed
			// orders
			for (Order order : orders) {
				if (order.getDeliveryStatus().equals("Late delivery"))
					delayedOrders.add(order);
			}
		}
		if (delayedOrders.size() == 0) {
			System.out.println("No orders were delayed for this customer");
			return null;
		}
		return delayedOrders;
	}

}
