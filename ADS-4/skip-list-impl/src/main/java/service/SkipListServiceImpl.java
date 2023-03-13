package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Category;
import model.Customer;
import model.CustomerProductRecord;
import model.Node;
import model.Order;
import util.FileLoader;
import util.ISkipList;
import util.SkipList;

/**
 * This class provides the implementation supporting retrieval of the following
 * information:<br>
 * 1. Given a customer ID, provide details of the customer<br>
 * 2. Given an order ID, provide details of the order<br>
 * 3. Given a category name, list out all products from the category
 *
 * Data to support all the aforementioned queries is stored in a Skip List
 */

public class SkipListServiceImpl implements SkipListService {

	List<CustomerProductRecord> fileData;
	private static SkipListServiceImpl service;
	ISkipList<Integer, Customer> customerList;
	ISkipList<Integer, Order> orderList;
	ISkipList<String, Category> productCategoryList;

	/**
	 * This method creates an instance of the service if not created and returns
	 * that instance of the service.
	 * 
	 * @return Returns the instance of the service
	 */
	public static SkipListServiceImpl getInstance() {

		if (service == null) {
			synchronized (SkipListServiceImpl.class) {
				if (service == null)
					service = new SkipListServiceImpl();
			}
		}
		return service;
	}

	/**
	 * This method is used to load all the data from the file into a list called
	 * file data in this object in the format required for processing the data for
	 * creating the corresponding heaps.
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

	@Override
	public void buildSkipLists() {
		this.buildCustomersList();
		this.buildOrdersList();
		this.buildCategoryList();
	}

	/**
	 * This method creates the skip list with the customer details.<br>
	 * 
	 * Workflow of this method:<br>
	 * 
	 * 1. The data is read from the raw data list(file data) in order.<br>
	 * 
	 * 2. A record of the customer with the details is created, as we iterate
	 * through the file data.<br>
	 * 
	 * 3. If the customer record already exists in the list, the record is updated
	 * with the other products bought by the customer.<br>
	 * 
	 * 4. If the customer record is not present in the list, it will be added into
	 * the list.<br>
	 * 
	 * 
	 * 
	 */
	private void buildCustomersList() {
		System.out.println("Creating Skip List with Customer Details");
		double startTime = System.currentTimeMillis();
		this.customerList = new SkipList<>(10);
		// Iterate over all the records in file data
		for (CustomerProductRecord p : fileData) {
			// If the customer object is not present, add to the list
			if (!this.customerList.containsKey(p.getCustomerId())) {
				HashSet<String> products = new HashSet<String>();
				products.add(p.getProductName());
				HashSet<Integer> orders = new HashSet<Integer>();
				orders.add(p.getOrderId());
				// Create a new Customer Object
				Customer customerRecord = new Customer(p.getCustomerId(), p.getCustomerFName(), p.getCustomerLName(),
						products, orders, p.getCustomerCountry());
				this.customerList.put(p.getCustomerId(), customerRecord);
			} else {
				// If customer object is already present, update details
				Node<Integer, Customer> customer = this.customerList.get(p.getCustomerId());
				customer.getValue().addProduct(p.getProductName());
				customer.getValue().addOrderIds(p.getOrderId());
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method creates the skip list with the order details.<br>
	 * 
	 * Workflow of this method:<br>
	 * 
	 * 1. The data is read from the raw data list(file data) in order.<br>
	 * 
	 * 2. A record of the order with the details is created, as we iterate through
	 * the file data.<br>
	 * 
	 * 3. If the order record already exists in the list, the record is updated with
	 * the other products bought in the order.Also, the total amount of the order,
	 * total discount for the order, total profit of the order will be updated. We
	 * update this for every order as we have these details in the data for every
	 * item, so we calculate the overall numbers for the order as a whole.<br>
	 * 
	 * 4. If the order record is not present in the list, it will be added into the
	 * list.<br>
	 * 
	 * 
	 */
	private void buildOrdersList() {
		System.out.println("Creating Skip List with Order Details");
		double startTime = System.currentTimeMillis();
		this.orderList = new SkipList<>(10);
		// Iterate over all the records in file data
		for (CustomerProductRecord p : fileData) {
			// If the order object is not present, add to the list
			if (!orderList.containsKey(p.getOrderId())) {
				HashSet<String> products = new HashSet<String>();
				products.add(p.getProductName());
				// Create a new Order Object
				Order orderRecord = new Order(p.getOrderId(), p.getOrderItemDiscount(), p.getOrderProfit(),
						p.getOrderCountry(), p.getOrderStatus(), products, p.getOrderItemTotal());
				orderList.put(p.getOrderId(), orderRecord);
			} else {
				// If order object is already present, update details
				Node<Integer, Order> order = orderList.get(p.getOrderId());
				order.getValue().addProduct(p.getProductName());
				order.getValue().addOrderDiscount(p.getOrderItemDiscount());
				order.getValue().addOrderProfit(p.getOrderProfit());
				order.getValue().addOrderTotal(p.getOrderItemTotal());
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method creates the skip list with the product category details.<br>
	 * 
	 * Workflow of this method:<br>
	 * 
	 * 1. The data is read from the raw data list(file data) in order.<br>
	 * 
	 * 2. A record of the category with the details is created, as we iterate
	 * through the file data.<br>
	 * 
	 * 3. If the category record already exists in the list, the record is updated
	 * with the other products bought in that category.<br>
	 * 
	 * 4. If the category record is not present in the list, it will be added into
	 * the list.<br>
	 * 
	 * 
	 */
	private void buildCategoryList() {
		System.out.println("Creating Skip List with Product Category Details");
		double startTime = System.currentTimeMillis();
		this.productCategoryList = new SkipList<>(10);
		// Iterate over all the records in file data
		for (CustomerProductRecord p : fileData) {
			// If the category object is not present, add to the list
			if (!productCategoryList.containsKey(p.getCategory())) {
				HashSet<String> products = new HashSet<String>();
				products.add(p.getProductName());
				// Create a new Category Object
				Category category = new Category(p.getCategory(), products);
				productCategoryList.put(p.getCategory(), category);
			} else {
				// If category object is already present, update details
				Node<String, Category> productCategory = productCategoryList.get(p.getCategory());
				productCategory.getValue().addProduct(p.getProductName());
			}
		}
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method searches the skip list with the customer id and returns all the
	 * details of the customer such as products bought by that customer, customer
	 * full name and country where the customer resides.<br>
	 * 
	 * @param customerId is the ID of the customer whose details are to be searched
	 * @return The Customer object associated with the provided customer ID. If the
	 *         ID is not found, null is returned
	 */
	@Override
	public Customer searchCustomer(int customerId) {
		double startTime = System.nanoTime();
		Node<Integer, Customer> searchData = customerList.get(customerId);
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		if (searchData == null) {
			System.out.println("Customer ID is not found. Please enter the correct ID.");
			return null;
		} else {
			System.out.println(searchData.getValue().toString());
		}
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms");
		return searchData.getValue();
	}

	/**
	 * This method searches the skip list with the order id and returns all the
	 * products in that order, the total profit for the order, the total amount of
	 * that order, the total discount provided in that order, the status of that
	 * order and country to where that order was placed.<br>
	 * 
	 * @param orderId is the ID of the order whose details are to be searched
	 * @return The Order object associated with the provided order ID. If the ID is
	 *         not found, null is returned
	 */
	@Override
	public Order searchOrder(int orderId) {
		double startTime = System.nanoTime();
		Node<Integer, Order> searchData = this.orderList.get(orderId);
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		if (searchData == null) {
			System.out.println("Order ID is not found. Please enter the correct ID.");
			return null;
		} else {
			System.out.println(searchData.getValue().toString());
		}
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms");
		return searchData.getValue();
	}

	/**
	 * This method searches the skip list with the category and returns all the
	 * products in that category.<br>
	 * 
	 * @param category is the name of category for which the products are to be
	 *                 retrieved.
	 * @return The Category object associated with the provided category. If the
	 *         category is not found, null is returned
	 * 
	 */
	@Override
	public Category searchCategory(String category) {
		double startTime = System.nanoTime();
		Node<String, Category> searchData = this.productCategoryList.get(category);
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		if (searchData == null) {
			System.out.println("Category not found. Please enter the correct category.");
			return null;
		} else {
			System.out.println(searchData.getValue().toString());
		}
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms");
		return searchData.getValue();
	}
}
