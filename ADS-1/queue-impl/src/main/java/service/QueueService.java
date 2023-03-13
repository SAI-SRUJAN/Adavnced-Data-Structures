package service;

import java.util.List;

public interface QueueService {

	public void loadData(String path);

	public List<String> getProductSalesPerCategory();

	public List<String> getLateDeliveryByRegion();

	public List<String> bestCustomers();

	public String mostOrdersByCountry();
}
