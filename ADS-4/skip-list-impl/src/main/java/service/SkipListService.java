package service;

import model.Category;
import model.Customer;
import model.Order;

public interface SkipListService {

	public void loadData(String path);

	public void buildSkipLists();

	public Customer searchCustomer(int customerId);

	public Order searchOrder(int orderId);

	public Category searchCategory(String category);

}
