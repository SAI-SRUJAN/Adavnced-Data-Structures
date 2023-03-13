import java.util.Scanner;

import service.SkipListService;
import service.SkipListServiceImpl;

public class SkipListRunner {

	private static final String SUPPLY_CHAIN_DATA = "Supply_Chain.csv";

	public static void main(String[] args) {
		SkipListService skipListService = SkipListServiceImpl.getInstance();
		skipListService.loadData(SUPPLY_CHAIN_DATA);

		System.out.println("*******Supply Chain Data********");

		skipListService.buildSkipLists();

		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Select option to search data: ");
			System.out.println("1. Search for customer details");
			System.out.println("2. Search for order details");
			System.out.println("3. Search for product categories");

			int option = sc.nextInt();

			if (option == 1) {
				System.out.println("Enter Customer Id to see Details: ");
				int customerId = sc.nextInt();
				skipListService.searchCustomer(customerId);
				System.out.println("****************************************************************");
			}
			if (option == 2) {
				System.out.println("Enter Order Id to see Details: ");
				int order = sc.nextInt();
				skipListService.searchOrder(order);
				System.out.println("****************************************************************");
			}
			if (option == 3) {
				System.out.println("Enter Product Category to see Details: ");
				sc.nextLine();
				String categoryName = sc.nextLine().trim();
				skipListService.searchCategory(categoryName);
				System.out.println("****************************************************************");
			}
			System.out.println("Press 'y' to go to main menu, else press 'n'");
		} while (sc.next().equals("y"));

		sc.close();
	}
}
