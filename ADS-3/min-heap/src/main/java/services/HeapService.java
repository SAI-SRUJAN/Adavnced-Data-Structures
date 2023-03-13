package services;

import java.util.List;

import models.CountryOrders;
import models.Customer;
import models.DeliveryMarket;

public interface HeapService {
	
	public void loadData(String path);
	
	public void buildHeaps();
	
	public List<DeliveryMarket> getMinDeliveryMarket(int k);
	
	public List<CountryOrders> getMinCountryOrders(int k);
	
	public List<Customer> getMinCustomerSales(int k);

}
