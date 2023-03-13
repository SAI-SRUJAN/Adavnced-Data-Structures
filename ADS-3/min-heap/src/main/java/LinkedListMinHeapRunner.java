import java.util.List;

import models.CountryOrders;
import models.Customer;
import models.DeliveryMarket;

import services.HeapService;
import services.HeapServiceImpl;

public class LinkedListMinHeapRunner {

	private static final String SUPPLY_CHAIN_DATA = "Supply_Chain.csv";

	public static void main(String[] args) {
		HeapService heapService = HeapServiceImpl.getInstance();
		heapService.loadData(SUPPLY_CHAIN_DATA);

		System.out.println("*******Supply Chain Data********");

		heapService.buildHeaps();

		int num = 5;

		System.out.printf("Displaying %d customers with least sales%n", num);
		List<Customer> topCustomers = heapService.getMinCustomerSales(num);
		for (Customer c : topCustomers)
			System.out.println(c.toString());
		System.out.println("****************************************************************");

		System.out.printf("Displaying %d countries with least orders%n", num);
		List<CountryOrders> topCountries = heapService.getMinCountryOrders(num);
		for (CountryOrders country : topCountries)
			System.out.println(country.toString());
		System.out.println("****************************************************************");

		System.out.printf("Displaying %d regions with least proportion of delayed deliveries%n", num);
		List<DeliveryMarket> topRegions = heapService.getMinDeliveryMarket(num);
		for (DeliveryMarket region : topRegions)
			System.out.println(region.toString());
		System.out.println("****************************************************************");

	}
}
