package service;

import model.Customer;
import model.DeliveryRegion;
import model.OrderCountry;
import model.Record;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class provides the implementation supporting retrieval of information to answer the following queries:
 * 1. Retrieve the top k customers by sale amount
 * 2. Retrieve the top k countries by number of orders
 * 3. Retrieve the top k regions with the highest proportion of delayed deliveries
 * */

public class MaxHeapService {

    /*
    This attribute holds the records that are read from the data file initially. These records are processed
    and loaded into the Max-Heaps.
    */
    List<Record> data;
    MaxHeapImpl<DeliveryRegion> deliveryRegionMaxHeap;
    MaxHeapImpl<Customer> customerMaxHeap;
    MaxHeapImpl<OrderCountry> orderCountryMaxHeap;

    /**
     *
     * Upon initialization, 3 heaps are initialized using the provided data. Details of the 3 heaps are as below:
     * 1. customerMaxHeap - This max heap holds data associated with the customers and the total sales
     * associated with each of the customers. Customers are ranked based on total sales amount.
     * 2. deliveryRegionMaxHeap - This max heap holds data associated with regions and the proportion of delayed
     * deliveries in each of the regions. Regions are ranked based on the proportion of delayed deliveries in each of the regions
     * 3. orderCountryMaxHeap - This max heap holds data associated with order countries and the number of
     * orders coming in from each country. Countries are ranked based on the number of orders from each of the countries.
     *
     * The constructor initializes the all the aforementioned heaps on object initialization.
     *
     * @param data The list of records read from the data file
     * */
    public MaxHeapService(List<Record> data){
        // Build the requisite heaps to support the data queries
        this.data = data;
        buildDeliveryDelayHeap();
        buildCustomersHeap();
        buildOrderCountryHeap();
    }

    /**
     * This method builds the orderCountryMaxHeap. Details regarding this heap can be found in the constructor description
     * of this class. The records are sorted based on region. The list is then aggregated by region to generate an object of
     * DeliveryRegion class per region in the data.
     * Finally, this list of DeliveryRegion objects is stored in the max heap using the HeapImpl class
     * */
    private void buildDeliveryDelayHeap(){
        //Sort data by delivery region
        double startTime = System.nanoTime();

        data.sort(Comparator.comparing(Record::getRegion));
        List<DeliveryRegion> regions = new ArrayList<>();
        DeliveryRegion deliveryRegion = null;
        // Pass through the sorted list, generating aggregated data based on delivery region
        for(Record r: this.data){
            if(deliveryRegion == null || r.getRegion().compareTo(deliveryRegion.getRegion()) != 0){
                deliveryRegion = new DeliveryRegion(r);
                regions.add(deliveryRegion);
            }else{
                deliveryRegion.addDelivery(r.getDeliveryStatus());
            }
        }

        // Build a max heap using the list containing data aggregated by delivery region
        System.out.println("Building max heap holding delivery delay statistics data");
        deliveryRegionMaxHeap = new MaxHeapImpl<DeliveryRegion>(regions);

        double endTime = System.nanoTime();
        System.out.printf("Heap build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /**
     * This method builds the customerMaxHeap. Details regarding this heap can be found in the constructor description
     * of this class. The records are sorted based on customer id. The list is then aggregated by customer id to generate an object of
     * Customer class per customer in the data.
     * Finally, this list of Customer objects is stored in the max heap using the HeapImpl class
     * */
    private void buildCustomersHeap(){
        // Sort data based on customer id
        double startTime = System.nanoTime();

        data.sort(Comparator.comparing(Record::getCustomerId));
        List<Customer> customers = new ArrayList<>();
        Customer c = null;
        // Pass through the sorted list, generating aggregated data based on customer id
        for(Record r: this.data){
            if(c == null || r.getCustomerId() != c.getCustomerId()){
                c = new Customer(r);
                customers.add(c);
            }else{
                c.addSale(r.getOrderItemTotal());
            }
        }

        // Build a max heap using the list containing data aggregated by customer id
        System.out.println("Building max heap holding customer data");
        this.customerMaxHeap = new MaxHeapImpl<Customer>(customers);

        double endTime = System.nanoTime();
        System.out.printf("Heap build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /**
     * This method builds the orderCountryMaxHeap. Details regarding this heap can be found in the constructor description
     * of this class. The records are sorted based on order country. The list is then aggregated by order country to generate an object of
     * OrderCountry class per country in the data.
     * Finally, this list of OrderCountry objects is stored in the max heap using the HeapImpl class
     * */
    private void buildOrderCountryHeap(){
        // Sort data based on order country
        double startTime = System.nanoTime();

        data.sort(Comparator.comparing(Record::getOrderCountry));
        List<OrderCountry> orderCountries = new ArrayList<>();
        OrderCountry c = null;
        // Pass through the sorted list, generating aggregated data based on order country
        for(Record r: this.data){
            if(c == null || !r.getOrderCountry().equals(c.getCountry())){
                c = new OrderCountry(r);
                orderCountries.add(c);
            }else{
                c.addOrder();
            }
        }

        // Build a max heap using the list containing data aggregated by order country
        System.out.println("Building max heap holding number of orders by country");
        this.orderCountryMaxHeap = new MaxHeapImpl<OrderCountry>(orderCountries);

        double endTime = System.nanoTime();
        System.out.printf("Heap build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }

    /**
     * This method retrieves the top k elements from deliveryRegionMaxHeap
     *
     * @param k The number of elements to retrieve from the heap.
     * @return The top k elements from the deliveryRegionMaxHeap
     * */
    public List<DeliveryRegion> getRegionsWithHighestDeliveryDelays(int k){
        // extract the top k elements from the heap containing data aggregated by delivery region
        return deliveryRegionMaxHeap.extractMax(k);
    }

    /**
     * This method retrieves the top k elements from customerMaxHeap
     *
     * @param k The number of elements to retrieve from the heap.
     * @return The top k elements from the customerMaxHeap
     * */
    public List<Customer> getTopCustomers(int k){
        // extract the top k elements from the heap containing data aggregated by customer id
        return customerMaxHeap.extractMax(k);
    }

    /**
     * This method retrieves the top k elements from orderCountryMaxHeap
     *
     * @param k The number of elements to retrieve from the heap.
     * @return The top k elements from the orderCountryMaxHeap
     * */
    public List<OrderCountry> getCountriesWithMostOrders(int k){
        // extract the top k elements from the heap containing data aggregated by order country
        return orderCountryMaxHeap.extractMax(k);
    }

}
