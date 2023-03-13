package services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import models.CountryOrders;
import models.Customer;
import models.CustomerProductRecord;
import models.DeliveryMarket;
import util.FileLoader;
import util.Heap;
import util.MinHeap;

/**
 * This class provides the implementation supporting retrieval of information to
 * answer the following queries:<br>
 * 1. Retrieve the k customers with least sale amount<br>
 * 2. Retrieve the k countries with least number of orders<br>
 * 3. Retrieve the k regions with the least proportion of delayed deliveries
 */
public class HeapServiceImpl implements HeapService {

	List<CustomerProductRecord> fileData;
	private static HeapServiceImpl service;
	private Heap<DeliveryMarket> deliveryDelayHeap;
	private Heap<Customer> customerSalesHeap;
	private Heap<CountryOrders> countryOrdersHeap;

	/**
	 * This method creates an instance of the service if not created and returns
	 * that instance of the service.
	 * 
	 * @return Returns the instance of the service
	 */
	public static HeapServiceImpl getInstance() {

		if (service == null) {
			synchronized (HeapServiceImpl.class) {
				if (service == null)
					service = new HeapServiceImpl();
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
	 *
	 * This function initializes the 3 heaps using the file data.<br>
	 * Details of the 3 heaps are:<br>
	 * 1. customerSalesHeap - This min heap holds data associated with the customers
	 * and the total sales associated with each of the customers. Customers are
	 * ranked based on total sales amount.<br>
	 * 
	 * 2. deliveryDelayHeap - This min heap holds data associated with regions and
	 * the proportion of delayed deliveries in each of the regions. Regions are
	 * ranked based on the proportion of delayed deliveries in each of the
	 * regions.<br>
	 * 
	 * 3. countryOrdersHeap - This min heap holds data associated with order
	 * countries and the number of orders coming in from each country. Countries are
	 * ranked based on the number of orders from each of the countries.
	 *
	 */
	@Override
	public void buildHeaps() {
		this.createDeliveryDelayHeap();
		this.createCustomerOrderHeap();
		this.createCountryOrdersHeap();
	}

	/**
	 * This method builds the deliveryDelayHeap. The records are sorted based on the
	 * market region. The list is then aggregated by region to generate an object of
	 * DeliveryMarket class per market region in the data.<br>
	 * For each region once the total data is aggregated, we add the market region
	 * details into the heap. Each region will then be placed accordingly in the
	 * heap based on the late delivery proportion of the market region.
	 * 
	 */
	private void createDeliveryDelayHeap() {
		System.out.println("Building the delivery delays heap for each region");
		double startTime = System.currentTimeMillis();
		deliveryDelayHeap = new MinHeap<DeliveryMarket>();
		// Sort the file data based on market region
		fileData.sort(Comparator.comparing(CustomerProductRecord::getMarket));
		// Create a record of DeliveryMarket class with the first record
		CustomerProductRecord temp = fileData.get(0);
		String market = temp.getMarket();
		DeliveryMarket regionDelivery = new DeliveryMarket(market, 1, 0);
		if (temp.getDeliveryStatus().equals("Late delivery"))
			regionDelivery.addLateDeliveries(1);
		// Iterate over all records in file data and insert them into heap
		for (CustomerProductRecord record : fileData) {
			// If the record is of same region then we aggregate the values
			if (market.equals(record.getMarket())) {
				if (record.getDeliveryStatus().equals("Late delivery"))
					regionDelivery.addLateDeliveries(1);
				regionDelivery.addTotalDeliveries(1);
			} else {
				// If the record is of different region then all records of one region is
				// completely aggregated as it is sorted in file data and is inserted into the
				// heap
				regionDelivery.setLateDeliveryProportion(
						((double) regionDelivery.getLateDeliveries() / (double) regionDelivery.getTotalDeliveries()));
				deliveryDelayHeap.insert(regionDelivery);
				// Create a record of DeliveryMarket class for the next market region
				market = record.getMarket();
				regionDelivery = new DeliveryMarket(market, 1, 0);
				if (record.getDeliveryStatus().equals("Late delivery"))
					regionDelivery.addLateDeliveries(1);
			}
		}
		// To add the last region into the heap
		regionDelivery.setLateDeliveryProportion(
				((double) regionDelivery.getLateDeliveries() / (double) regionDelivery.getTotalDeliveries()));
		deliveryDelayHeap.insert(regionDelivery);
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method builds the customerSalesHeap. The records are sorted based on the
	 * customer id. The list is then aggregated by customer id to generate an object
	 * of Customer class per customer in the data.<br>
	 * For each customer once the total sales data is aggregated, we add the
	 * customer details into the heap. Each customer will then be placed accordingly
	 * in the heap based on their total sales amount.
	 * 
	 */
	private void createCustomerOrderHeap() {
		System.out.println("Building the customer information heap");
		double startTime = System.currentTimeMillis();
		customerSalesHeap = new MinHeap<Customer>();
		// Sort the file data based on customer id
		fileData.sort(Comparator.comparing(CustomerProductRecord::getCustomerId));
		Customer c = null;
		HashSet<String> products;
		// Iterate over all records in file data and insert them into heap
		for (CustomerProductRecord record : fileData) {
			if (c == null || c.getCustomerId() != record.getCustomerId()) {
				// If the record is of different customer then all records of one customer is
				// completely aggregated as it is sorted in file data and is inserted into the
				// heap
				if (c != null)
					customerSalesHeap.insert(c);
				// Create a record of Customer class for every different customer in the data
				products = new HashSet<String>();
				products.add(record.getProductName());
				c = new Customer(record.getCustomerId(), products, record.getCustomerFName(), record.getCustomerLName(),
						record.getCustomerCountry(), record.getCustomerSale());
			} else {
				c.addTotalSales(record.getCustomerSale());
				c.addProducts(record.getProductName());
			}
		}
		// To add the last customer into the heap
		customerSalesHeap.insert(c);
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method builds the countryOrdersHeap. The records are sorted based on the
	 * order country. The list is then aggregated by order country to generate an
	 * object of CountryOrders class per country in the data.<br>
	 * For each country once the total orders data is aggregated, we add the order
	 * country details into the heap. Each country will then be placed accordingly
	 * in the heap based on their total orders.
	 * 
	 */
	private void createCountryOrdersHeap() {
		System.out.println("Building the country orders information heap");
		double startTime = System.currentTimeMillis();
		countryOrdersHeap = new MinHeap<CountryOrders>();
		// Sort the file data based on order country
		fileData.sort(Comparator.comparing(CustomerProductRecord::getOrderCountry));
		CountryOrders c = null;
		// Iterate over all records in file data and insert them into heap
		for (CustomerProductRecord record : fileData) {
			if (c == null || !(c.getCountry().equals(record.getOrderCountry()))) {
				// If the record is of different country then all records of one country is
				// completely aggregated as it is sorted in file data and is inserted into the
				// heap
				if (c != null)
					countryOrdersHeap.insert(c);
				// Create a record of CountryOrders class for every different country in the
				// data
				c = new CountryOrders(record.getOrderCountry(), 1);
			} else {
				c.addTotalOrders(1);
			}
		}
		// To add the last country into the heap
		countryOrdersHeap.insert(c);
		double endTime = System.currentTimeMillis();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");
	}

	/**
	 * This method retrieves the top k elements from deliveryDelayHeap
	 *
	 * @param k The number of elements to retrieve from the heap.
	 * @return List of the top k elements from the deliveryDelayHeap
	 */
	public List<DeliveryMarket> getMinDeliveryMarket(int k) {
		double startTime = System.nanoTime();
		List<DeliveryMarket> res = new ArrayList<>();
		while (deliveryDelayHeap.size() != 0 && k > 0) {
			res.add(this.deliveryDelayHeap.removeMin());
			k--;
		}
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms\n");
		return res;
	}

	/**
	 * This method retrieves the top k elements from countryOrdersHeap
	 *
	 * @param k The number of elements to retrieve from the heap.
	 * @return List of the top k elements from the countryOrdersHeap
	 */
	public List<CountryOrders> getMinCountryOrders(int k) {
		double startTime = System.nanoTime();
		List<CountryOrders> res = new ArrayList<>();
		while (countryOrdersHeap.size() != 0 && k > 0) {
			res.add(this.countryOrdersHeap.removeMin());
			k--;
		}
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms\n");
		return res;
	}

	/**
	 * This method retrieves the top k elements from customerSalesHeap
	 *
	 * @param k The number of elements to retrieve from the heap.
	 * @return List of the top k elements from the customerSalesHeap
	 */
	public List<Customer> getMinCustomerSales(int k) {
		double startTime = System.nanoTime();
		List<Customer> res = new ArrayList<>();
		while (customerSalesHeap.size() != 0 && k > 0) {
			res.add(this.customerSalesHeap.removeMin());
			k--;
		}
		double endTime = System.nanoTime();
		double elapsed = endTime - startTime;
		System.out.println("\nTime taken for execution : " + elapsed + "ns");
		System.out.println("Time taken for execution : " + (elapsed / 1000000) + "ms\n");
		return res;
	}
}
