import java.util.List;

import service.QueueService;
import service.QueueServiceImpl;

public class QueueRunner {

	private static final String SUPPLY_CHAIN_DATA = "Supply_Chain.csv";

	public static void main(String[] args) {
		// Get the instance of the service to call in the various functions
		QueueService queueService = QueueServiceImpl.getInstance();
		queueService.loadData(SUPPLY_CHAIN_DATA);

		System.out.println("*******Supply Chain Data********");

		long startTime = System.currentTimeMillis();
		List<String> topSalesCategory = queueService.getProductSalesPerCategory();
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		System.out.println("Top products by category: ");
		for (String s : topSalesCategory)
			System.out.println(s);
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");

		startTime = System.currentTimeMillis();
		List<String> lateDeliveries = queueService.getLateDeliveryByRegion();
		endTime = System.currentTimeMillis();
		elapsed = endTime - startTime;
		System.out.println("Proportion of late deliveries by region: ");
		for (String s : lateDeliveries)
			System.out.println(s);
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");

		startTime = System.currentTimeMillis();
		List<String> bestCustomers = queueService.bestCustomers();
		endTime = System.currentTimeMillis();
		elapsed = endTime - startTime;
		System.out.println("Top 5 customers by sales: ");
		for (String s : bestCustomers)
			System.out.println(s);
		System.out.println("\nTime taken for execution : " + elapsed + "ms");
		System.out.println("****************************************************************");

		startTime = System.currentTimeMillis();
		String country = queueService.mostOrdersByCountry();
		endTime = System.currentTimeMillis();
		elapsed = endTime - startTime;
		System.out.println("Country with most orders : " + country);
		System.out.println("\nTime taken for execution : " + elapsed + "ms");

	}
}
