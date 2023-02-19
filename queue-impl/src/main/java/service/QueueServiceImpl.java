package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import java.util.Queue;
import model.CustomerSale;
import model.ProductSales;
import model.CustomerProductRecord;
import util.FileLoader;

public class QueueServiceImpl implements QueueService {

	/**
	 * All data will be present in this list within the object. The data will be
	 * added to a queue to run various computations as and when required
	 */
	List<CustomerProductRecord> fileData;
	private static QueueServiceImpl service;
	private Queue<CustomerProductRecord> productQueue;

	/**
	 * This method takes in the data in the list and generates a queue by adding the
	 * the data records sorted based on the provided comparator
	 * 
	 * @param Comparator according to which the list should be sorted and added to
	 *                   the queue
	 * @return Returns the sorted queue based on the comparator
	 */
	private Queue<CustomerProductRecord> createQueue(Comparator<CustomerProductRecord> comparator) {
		this.fileData.sort(comparator);
		Queue<CustomerProductRecord> sortedQueue = new LinkedList<>();
		for (CustomerProductRecord p : fileData)
			sortedQueue.add(p);
		return sortedQueue;
	}

	/**
	 * This method creates an instance of the service if not created and returns
	 * that instance of the service.
	 * 
	 * @return Returns the instance of the service
	 */
	public static QueueServiceImpl getInstance() {

		if (service == null) {
			synchronized (QueueServiceImpl.class) {
				if (service == null)
					service = new QueueServiceImpl();
			}
		}
		return service;
	}

	/**
	 * A queue gets created initially in which the data would be stored in the
	 * constructor class.
	 */
	private QueueServiceImpl() {
		this.productQueue = new LinkedList<>();
	}

	/**
	 * This method is used to load all the data from the file into a list called
	 * file data in this object in the format required for processing the data for
	 * getting the insights based on it .
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
					data[22]);
			fileData.add(product);
		}
	}

	/**
	 * This function retrieves the most popular product from each category. The
	 * popularity of the product is determined by total sales for the product within
	 * that category. <br>
	 * 
	 * Workflow of this method:<br>
	 *
	 * 1. The elements from the file data list will be enqueued in to the queue
	 * sorted in order according to the product name. So the queue has all records
	 * associated with a product within succession.<br>
	 * 
	 * 2. The total sales for each product will be calculated by removing every
	 * record from the queue and will be stored in the list with the category,
	 * product name and total sales associated with it.<br>
	 * 
	 * 3. We then sort the list according to the category name and then records will
	 * be enqueued into the queue.<br>
	 * 
	 * 4. Remove elements form the queue one by one, updating the popular product as
	 * the current one, if it turns out to be more popular than the one marked as
	 * most popular within the category. Since the elements are sorted by category,
	 * all records associated with a category are removed from the queue in
	 * succession. Hence, if the newly popped element corresponds to a different
	 * category than the last one, it implies that all records associated with the
	 * previous category have been processed. Hence, at this stage, we can add the
	 * results associated with the previous category into the list holding the
	 * results. This process continues till we have records available to process in
	 * the queue.<br>
	 * 
	 * @return Returns the list of categories and the top product within the
	 *         category with sales
	 * 
	 */
	public List<String> getProductSalesPerCategory() {
		this.productQueue = createQueue(Comparator.comparing(CustomerProductRecord::getProductName));
		CustomerProductRecord temp = productQueue.remove();
		double totalSales = temp.getCustomerSale();
		List<ProductSales> topSalesProduct = new ArrayList<>();
		while (!productQueue.isEmpty()) {
			CustomerProductRecord p = productQueue.remove();
			if (temp.getProductName().equals(p.getProductName())) {
				totalSales = totalSales + p.getCustomerSale();
			} else {
				topSalesProduct.add(new ProductSales(temp.getProductName(), temp.getCategory(), totalSales));
				temp = p;
				totalSales = temp.getCustomerSale();
			}
		}

		// The last product would be added with this
		topSalesProduct.add(new ProductSales(temp.getProductName(), temp.getCategory(), totalSales));
		topSalesProduct.sort(ProductSales::compareTo);
		Queue<ProductSales> sortedQueue = new LinkedList<>();
		for (ProductSales p : topSalesProduct)
			sortedQueue.add(p);
		ProductSales tp = sortedQueue.remove();
		String productName = tp.getProductName();
		double maxSales = tp.getTotalSales();
		List<String> topSalesCategory = new ArrayList<>();
		while (!sortedQueue.isEmpty()) {
			ProductSales p = sortedQueue.remove();
			if (tp.getCategory().equals(p.getCategory())) {
				if (p.getTotalSales() > maxSales) {
					maxSales = p.getTotalSales();
					productName = p.getProductName();
				}
			} else {
				topSalesCategory.add(tp.getCategory() + " -> " + productName + " -> " + (double) maxSales);
				tp = p;
				maxSales = p.getTotalSales();
				productName = tp.getProductName();
			}
		}
		// The last category and the maximum product of that would be added
		topSalesCategory.add(tp.getCategory() + " -> " + productName + " -> " + (double) maxSales);
		return topSalesCategory;
	}

	/**
	 * This method returns the proportion of deliveries that are delayed for every
	 * market region.<br>
	 * 
	 * Workflow of this method:<br>
	 * 
	 * 1. Add the file data into a queue sorted by the market region. This ensures
	 * that all records associated with a region appear together within the
	 * queue.<br>
	 * 
	 * 2. Remove each element from the queue, updating the proportion of delayed
	 * requests. Since the elements are sorted by region, all records associated
	 * with a region are removed from the queue in succession. Hence, if the newly
	 * popped element corresponds to a different region than the last one, it
	 * implies that all records associated with the previous region have been
	 * processed. Hence, at this stage, we can add the results associated with the
	 * previous region into the list holding the results. This process continues
	 * till we have records available to process in the queue.<br>
	 * 
	 * @return Returns list of Regions with the proportion of late deliveries
	 */
	public List<String> getLateDeliveryByRegion() {
		this.productQueue = createQueue(Comparator.comparing(CustomerProductRecord::getMarket));
		CustomerProductRecord temp = productQueue.remove();
		String market = temp.getMarket();
		int lateDelivery = 0;
		if (temp.getDeliveryStatus().equals("Late delivery"))
			lateDelivery++;
		int totalDelivery = 1;
		List<String> marketDelivery = new ArrayList<>();
		while (!productQueue.isEmpty()) {
			CustomerProductRecord p = productQueue.remove();
			if (market.equals(p.getMarket())) {
				if (p.getDeliveryStatus().equals("Late delivery"))
					lateDelivery++;
				totalDelivery++;
			} else {
				marketDelivery.add(market + " -> " + ((double) lateDelivery / totalDelivery));
				market = p.getMarket();
				lateDelivery = 0;
				if (temp.getDeliveryStatus().equals("Late delivery"))
					lateDelivery++;
				totalDelivery = 1;
			}
		}
		marketDelivery.add(market + " -> " + ((double) lateDelivery / totalDelivery));
		return marketDelivery;
	}

	/**
	 * This method returns the top 5 customers by sales.<br>
	 *
	 * Workflow of this method:<br>
	 * 
	 * 1. Generate a queue with the data sorted by customer id.<br>
	 * 
	 * 2. Pop from queue one by and compute the sum of sales per customer. All
	 * records associated with a customer will be together in the queue as the queue
	 * has been has elements sorted by customer id.<br>
	 * 
	 * 3. Sort the resultant objects based on total sales and add the result into
	 * the list.<br>
	 * 
	 * 4. Return the first 5 elements form the list which corresponds to the best
	 * sales of the customers.<br>
	 * 
	 * @return Returns a list of top 5 customers with name and sales
	 */
	public List<String> bestCustomers() {
		this.productQueue = createQueue(Comparator.comparing(CustomerProductRecord::getCustomerId));
		List<CustomerSale> topSales = new ArrayList<>();
		CustomerProductRecord temp = productQueue.remove();
		double totalSales = temp.getCustomerSale();
		while (!productQueue.isEmpty()) {
			CustomerProductRecord p = productQueue.remove();
			if (temp.getCustomerId() == p.getCustomerId()) {
				totalSales = totalSales + p.getCustomerSale();
			} else {
				topSales.add(new CustomerSale(temp, totalSales));
				temp = p;
				totalSales = temp.getCustomerSale();
			}
		}
		// The last customer sale would be added into the list
		topSales.add(new CustomerSale(temp, totalSales));
		topSales.sort(CustomerSale::compareTo);
		List<String> res = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			res.add(topSales.get(i).toString());
		}
		return res;
	}

	/**
	 * This method returns the country with the most orders.<br>
	 * 
	 * The workflow of this method:<br>
	 *
	 * 1. Sort the records by order country and then add them to a queue.<br>
	 * 
	 * 2. Pop from queue one by and compute the number of orders per country. All
	 * records associated with a country will be together in the queue as the queue
	 * has elements sorted by order country.<br>
	 * 
	 * 3. If the number of orders from a country exceeds the max number of orders
	 * from any country seen previously, update the variable holding the region with
	 * the max orders.<br>
	 * 
	 * @return country with most number of orders
	 * 
	 */
	public String mostOrdersByCountry() {
		this.productQueue = createQueue(Comparator.comparing(CustomerProductRecord::getOrderCountry));
		CustomerProductRecord tp = productQueue.remove();
		String country = tp.getOrderCountry();
		int noOfOrders = 1;
		int maxCustomers = 0;
		while (!productQueue.isEmpty()) {
			CustomerProductRecord p = productQueue.remove();
			if (tp.getOrderCountry().equals(p.getOrderCountry())) {
				noOfOrders++;
			} else {
				if (noOfOrders > maxCustomers) {
					maxCustomers = noOfOrders;
					country = tp.getOrderCountry();
				}
				tp = p;
				noOfOrders = 1;
			}
		}
		// To check the last country in the data if it's greater than the maximum number
		// of orders till then
		if (noOfOrders > maxCustomers) {
			maxCustomers = noOfOrders;
			country = tp.getOrderCountry();
		}
		String maxCountry = country + " -> " + maxCustomers;
		return maxCountry;
	}
}
