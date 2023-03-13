package service;

import java.util.HashMap;
import java.util.List;

import model.Order;
import model.Product;

public interface DirectedGraphService {

	/**
	 * This method is used to load all the data from the file into a list called
	 * file data in this object in the format required for processing the data for
	 * creating the corresponding graphs.
	 * 
	 * @param path of the file to be loaded
	 */
	public void loadData(String path);

	/**
	 * This method is used to build the required graphs for querying
	 * 
	 */
	public void buildGraphs();

	/***
	 * This method searches for the orders and the associated products of a given
	 * customer.
	 * 
	 * @param customerId The id of the customer to lookup for
	 *
	 * @return The orders and the products of the orders of that customer
	 */
	public HashMap<String, List<Product>> getOrderProducts(int customerId);

	/***
	 * This method searches for the orders that were delayed for a given customer.
	 * 
	 * 
	 * @param customerId The id of the customer to lookup for
	 *
	 * @return The orders that were delayed of that customer
	 */
	public List<Order> getDelayedOrders(int customerId);

}
