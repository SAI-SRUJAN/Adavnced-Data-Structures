package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import exception.EmptyTreeException;
import models.CustomerProductRecord;
import models.TNode;
import models.Customer;
import models.Order;

import util.FileLoader;
import util.SplayTree;
import util.Tree;

public class TreeServiceImpl implements TreeService {

	List<CustomerProductRecord> fileData;
	private static TreeServiceImpl service;
	Tree<Customer> customerTree;
	Tree<Order> orderTree;

	/**
	 * This method creates an instance of the service if not created and returns
	 * that instance of the service.
	 * 
	 * @return Returns the instance of the service
	 */
	public static TreeServiceImpl getInstance() {

		if (service == null) {
			synchronized (TreeServiceImpl.class) {
				if (service == null)
					service = new TreeServiceImpl();
			}
		}
		return service;
	}

	/**
	 * This method is used to load all the data from the file into a list called
	 * file data in this object in the format required for processing the data for
	 * creating the nodes of the tree to be searched for to get the required
	 * details.
	 * 
	 * @param path of the file to be loaded
	 */
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
	 * This method creates the tree with the customer details.<br>
	 * 
	 * Workflow of this method:<br>
	 * 
	 * 1. The data is read from the raw data list(file data) in order.<br>
	 * 
	 * 2. A record of the customer with the details is created, as we iterate
	 * through the file data.<br>
	 * 
	 * 3. If the customer record already exists in the tree, the record is updated
	 * with the other products bought by the customer.<br>
	 * 
	 * 4. If the customer record is not present in the tree, it will be added into
	 * the tree.<br>
	 * 
	 * Since we are creating a splay tree, the last record inserted into the tree
	 * will be splayed on to the top, if the customer record is already present in
	 * the tree then that record will be splayed onto the top of the tree.
	 * 
	 * 
	 * 
	 */
	public void createCustomerTree() {
		double startTime = System.currentTimeMillis();
		customerTree = new SplayTree<Customer>();
		for (CustomerProductRecord p : fileData) {
			HashSet<String> products = new HashSet<String>();
			products.add(p.getProductName());
			HashSet<Integer> orders = new HashSet<Integer>();
			orders.add(p.getOrderId());
			Customer customerRecord = new Customer(p.getCustomerId(), p.getCustomerFName(), p.getCustomerLName(),
					products, orders, p.getCustomerCountry());
			TNode<Customer> treeNode = customerTree.insert(customerRecord);
			if (treeNode != null) {
				treeNode.getElement().addProduct(p.getProductName());
				treeNode.getElement().addOrderIds(p.getOrderId());
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method creates the tree with the order details.<br>
	 * 
	 * Workflow of this method:<br>
	 * 
	 * 1. The data is read from the raw data list(file data) in order.<br>
	 * 
	 * 2. A record of the order with the details is created, as we iterate through
	 * the file data.<br>
	 * 
	 * 3. If the order record already exists in the tree, the record is updated with
	 * the other products bought in the order.Also, the total amount of the order,
	 * total discount for the order, total profit of the order will be updated. We
	 * update this for every order as we have these details in the data for every
	 * item, so we calculate the overall numbers for the order as a whole.<br>
	 * 
	 * 4. If the order record is not present in the tree, it will be added into the
	 * tree.<br>
	 * 
	 * Since we are creating a splay tree, the last record inserted into the tree
	 * will be splayed on to the top, if the order record is already present in the
	 * tree then that record will be splayed onto the top of the tree.
	 * 
	 * 
	 * 
	 */
	public void createOrderTree() {
		double startTime = System.currentTimeMillis();
		orderTree = new SplayTree<Order>();
		for (CustomerProductRecord p : fileData) {
			HashSet<String> products = new HashSet<String>();
			products.add(p.getProductName());
			Order orderRecord = new Order(p.getOrderId(), p.getOrderItemDiscount(), p.getOrderProfit(),
					p.getOrderCountry(), p.getOrderStatus(), products, p.getOrderItemTotal());
			TNode<Order> treeNode = orderTree.insert(orderRecord);
			if (treeNode != null) {
				treeNode.getElement().addProduct(p.getProductName());
				treeNode.getElement().addOrderDiscount(p.getOrderItemDiscount());
				treeNode.getElement().addOrderProfit(p.getOrderProfit());
				treeNode.getElement().addOrderTotal(p.getOrderItemTotal());
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method searches the tree with the customer id and returns all the
	 * details of the customer such as products bought by that customer, customer
	 * full name and country where the customer resides.Also, the node with that
	 * customer details would be splayed to the top of the tree.<br>
	 *
	 * An exception would be thrown if we search for the details without creating
	 * the tree.<br>
	 * 
	 * @param customerId is the ID of the customer whose details are to be searched
	 * 
	 */
	public Customer searchCustomer(int customerId) throws EmptyTreeException {
		if (customerTree == null)
			throw new EmptyTreeException("Tree is Empty");
		Customer customerRecord = new Customer(customerId);
		double startTime = System.nanoTime();
		TNode<Customer> searchData = customerTree.search(customerRecord);
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		if (searchData == null) {
			System.out.println("Customer ID is not found. Please enter the correct ID.");
			return null;
		} else {
			System.out.println(searchData.getElement().toString());
			System.out.println("\nRoot Node of the tree-" + customerTree.getRoot().getCustomerId());
		}
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms");
		return searchData.getElement();
	}

	/**
	 * This method searches the tree with the order id and returns all the products
	 * in that order, the total profit for the order, the total amount of that
	 * order, the total discount provided in that order, the status of that order
	 * and country to where that order was placed.Also, the node with that order
	 * details would be splayed to the top of the tree.<br>
	 * 
	 * @param orderId is the ID of the order whose details are to be searched
	 * 
	 */
	public Order searchOrder(int orderId) throws EmptyTreeException {
		if (orderTree == null)
			throw new EmptyTreeException("Tree is Empty");
		Order orderRecord = new Order(orderId);
		double startTime = System.nanoTime();
		TNode<Order> searchData = orderTree.search(orderRecord);
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		if (searchData == null) {
			System.out.println("Order ID is not found. Please enter the correct ID.");
			return null;
		} else {
			System.out.println(searchData.getElement().toString());
			System.out.println("\nRoot Node of the tree-" + orderTree.getRoot().getOrderId());
		}
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms");
		return searchData.getElement();
	}

}
