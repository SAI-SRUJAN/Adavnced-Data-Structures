package services;

import exception.EmptyTreeException;
import models.Customer;
import models.Order;

public interface TreeService {
	public void loadData(String path);

	public void createCustomerTree();

	public void createOrderTree();

	public Customer searchCustomer(int customerId) throws EmptyTreeException;

	public Order searchOrder(int orderId) throws EmptyTreeException;
}
