
import java.util.Scanner;

import models.Customer;
import services.TreeService;
import services.TreeServiceImpl;

public class SplayRunner {
	private static final String SUPPLY_CHAIN_DATA = "Supply_Chain.csv";

	public static void main(String[] args) {
		TreeService treeService = TreeServiceImpl.getInstance();
		treeService.loadData(SUPPLY_CHAIN_DATA);

		System.out.println("*******Supply Chain Data********");

		System.out.println("Creating tree with Customer Details");
		treeService.createCustomerTree();

		System.out.println("Creating tree with Order Details");
		treeService.createOrderTree();

		Scanner sc = new Scanner(System.in);
		Customer searchData;
		do {
			System.out.println("Enter Customer Id to see Details: ");
			int customerId = sc.nextInt();

			searchData = treeService.searchCustomer(customerId);
			if (searchData != null) {
				System.out.println("****************************************************************");
				do {
					System.out.println("Enter Order Id to see Details: ");
					int orderId = sc.nextInt();
					treeService.searchOrder(orderId);
					System.out.println("****************************************************************");
					System.out.println("Press 'y' to search another order else press 'n'");
				} while (sc.next().equals("y"));
			}
			System.out.println("Press 'y' to search another customer else press 'n'");
		} while (sc.next().equals("y"));
		sc.close();
	}
}
