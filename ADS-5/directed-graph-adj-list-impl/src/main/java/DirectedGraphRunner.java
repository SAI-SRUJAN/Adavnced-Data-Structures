import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Order;
import model.Product;
import service.DirectedGraphService;
import service.DirectedGraphServiceImpl;

public class DirectedGraphRunner {

	private static final String SUPPLY_CHAIN_DATA = "Supply_Chain.csv";

	public static void main(String[] args) {
		DirectedGraphService directedGraphService = DirectedGraphServiceImpl.getInstance();
		directedGraphService.loadData(SUPPLY_CHAIN_DATA);

		System.out.println("*******Supply Chain Data********");

		directedGraphService.buildGraphs();

		Scanner sc = new Scanner(System.in);
		do {
			System.out.println("Enter Customer id: ");
			int id = sc.nextInt();

			System.out.println("Querying for products that was a part of the orders:");
			HashMap<String, List<Product>> orderProducts = directedGraphService.getOrderProducts(id);
			if (orderProducts != null) {
				for (Map.Entry<String, List<Product>> record : orderProducts.entrySet()) {
					System.out.println("\n-------------------------------------\n");
					System.out.println(record.getKey().toString());
					System.out.print("Products:");
					for (Product product : record.getValue()) {
						System.out.print(product.toString() + ", ");
					}
				}
			}
			System.out.println("\n****************************************************************");

			System.out.println("Querying for delayed orders of the customer");
			List<Order> ordersDelayed = directedGraphService.getDelayedOrders(id);
			if (orderProducts != null) {
				for (Order order : ordersDelayed) {
					System.out.println(order.toString());
				}
			}
			System.out.println("****************************************************************");
			System.out.println("Press 'y' to search again, else press 'n'");
		} while (sc.next().equals("y"));
		sc.close();
	}
}
