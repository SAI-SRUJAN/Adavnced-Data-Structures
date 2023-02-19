package service;

import model.CustomerSales;
import model.ProductSales;
import model.Record;

import java.util.*;
import java.util.stream.Collectors;


/**
 * This class provides the implementation that generates results using the Deque data structure<br>
 *
 * The questions answered are as below:<br>
 * 1. Which is the most popular product in every category ?<br>
 * 2. What proportion of the deliveries in every region are delayed ?<br>
 * 3. Who are my top/bottom n customers based on sales<br>
 * 4. Which country do I receive the most number of orders from<br>
 * */
public class InsightsService {

    /**
     * All data will be loaded into a list at startup.
     * The data will be added to a deque to run various computations as and when required
     * */

    List<Record> rawData;
    int numRecords;

    public InsightsService(List<Record> records) {
        this.rawData = records;
        this.numRecords = records.size();
    }


    /**
     * This method takes in the data in the list and generates a deque containing the records sorted
     * based on the provided comparator
     * */
    private Deque<Record> generateDequeue(List<Record> data, Comparator<Record> comparator){
        data.sort(comparator);
        return new LinkedList<>(data);
    }

    /**
     * This method returns the most popular product by category and the total sale amount associated with the product
     *
     * @return A list containing the most popular product from each category and the associated sales.
     *
     * This function retrieves the most popular product from each category. The popularity of the product
     * is determined by total sales for the said product. Workflow within this method:
     *
     * 1. Pass through the raw data list to extract the number of distinct categories.<br>
     * 2. Foreach category, pick the relevant products form the raw data list and enqueue them in a Deque.<br>
     *      The elements will be enqueued in increasing order of product name (lexicographic comparison) so the deque has all records associated with a product within succession
     * 3. Remove elements form the queue one by one, aggregating sales by product. Update the most popular product within the category if any product turns out to be more popular than the one previously marked as most popular within the category.<br>
     * 4. Repeat steps 2 and 3 for every category<br>
     * */
    public List<ProductSales> getMostPopularProductByCategory(){

        if(this.numRecords == 0)
            return null;

        List<ProductSales> result = new ArrayList<>();

        // Collect the list of categories
        Set<String> categories = this.rawData.stream().map(Record::getCategoryName).collect(Collectors.toSet());

        List<Record> categoryProducts;

        // For each category determine the most popular product
        for(String c: categories){
            categoryProducts = this.rawData.stream().filter(x-> x.getCategoryName().equals(c)).collect(Collectors.toList());

            Deque<Record> categoryData = generateDequeue(categoryProducts, Comparator.comparing(Record::getProductName));

            Record tmp = categoryData.pollFirst();

            ProductSales curr = new ProductSales(tmp.getProductName(), c);
            curr.addSale(tmp.getSalesPerCustomer());
            ProductSales max = curr;

            while(!categoryData.isEmpty()){
                tmp = categoryData.pollFirst();

                if(curr.getProductName().equals(tmp.getProductName())){
                    curr.addSale(tmp.getSalesPerCustomer());
                }else{
                    if(curr.compareTo(max) > 0){
                        max = curr;
                    }
                    curr = new ProductSales(tmp.getProductName(), c);
                    curr.addSale(tmp.getSalesPerCustomer());
                }
            }
            if(curr.compareTo(max) > 0)
                max = curr;

            result.add(max);
        }

        return result;
    }


    /**
     *
     * This method generates the proportion of late deliveries by region in the format 'region - 0.25'<br>
     *
     * @return The list of region with the proportion of late deliveries for the region in the format 'region - 0.25'<br>
     *
     * This method returns the proportion of deliveries that are delayed for every market region<br>
     * Workflow within this method:<br>
     * 1. Add the raw data into a Deque sorted by the market region. This ensures that all records associated with a region appear together within the deque<br>
     * 2. Remove each element from the queue, updating the proportion of delayed requests. Since the elements are sorted by region, all
     * records associated with a region are removed from the deque in succession. Hence, if the newly popped element corresponds to a
     * different region that the last one, it implies that all records associated with the previous region have been processed.
     * Hence, at this stage, we can add the results associated with the previous region into the list holding the results. This process continues till we
     * have records available to process in the deque
     * */
    public List<String> getLateDeliveriesByRegion(){

        List<String> result = new ArrayList<>();

        // Enqueue elements in sorted by market region in deque
        Deque<Record> data = generateDequeue(this.rawData, Comparator.comparing(Record::getMarket));

        String currRegion = null;
        int currLateDeliveries = 0, currNumDeliveries = 0;
        Record tmp;

        while(!data.isEmpty()){
            tmp = data.pollFirst();

            if(currRegion == null){
                currRegion = tmp.getMarket();
                currLateDeliveries = "Late delivery".equals(tmp.getDeliveryStatus()) ? 1 : 0;
                currNumDeliveries = 1;
                continue;
            }

            // If the region in the latest record is the same as the current region, add the delay and the total delivery counters.
            // Else, it implies that the last region has been processed completely. In this case add the results of the last region to the result list and reset the
            // counters for the new region
            if(tmp.getMarket().equals(currRegion)){
                currLateDeliveries += "Late delivery".equals(tmp.getDeliveryStatus()) ? 1 : 0;
                currNumDeliveries++;
            }else{
                result.add(currRegion + " - " + ((double)currLateDeliveries / currNumDeliveries));
                currRegion = tmp.getMarket();
                currLateDeliveries = "Late delivery".equals(tmp.getDeliveryStatus()) ? 1 : 0;
                currNumDeliveries = 1;
            }
        }

        // Add the result from the last region from the deque as will not be added within the loop
        result.add(currRegion + " - " + ((double) currLateDeliveries / currNumDeliveries));
        return result;
    }

    /**
     * This method returns the top/bottom n customers by sales.<br>
     *
     * @param top This boolean flag indicates if we wish to extract the top or bottom n customers. top = true will return the top n customers,
     *           whereas top = false will return the bottom n customers
     * @param count This parameter defines the value of number of customers we wish to retrieve. For eg:
     *             top = true, count = 5 will return the top 5 customers
     *             top = false, count = 4 will return the bottom 4 customers
     *
     * @return A list containing the top/bottom n countries based on the parameters top and count passed to this method
     *
     * Workflow of the method:<br>
     * 1. Generate a deque with the data sorted by customer id<br>
     * 2. Pop from queue one by and compute the sum of sales per customer. All records associated with a customer will be together in the queue as the queue has been
     *              has elements sorted by customer id<br>
     * 3. Sort the resultant objects based on total sales and add the result into another deque.<br>
     * 4. Return n elements form the front or back of the queue depending on whether we need the top elements or bottom elements<br>
     *
     * Note: Use of a Deque to store the results gives us the benefit of being able to retrieve either the top n or the bottom n customers. This is because a deque allows
     * removal at both ends.<br>
     * */
    public List<String> getTopCustBySales(boolean top, int count){

        if(this.numRecords == 0)
            return null;

        Deque<Record> data = generateDequeue(this.rawData, Comparator.comparing(Record::getCustomerId));
        CustomerSales maxCust;

        Record tmp = data.pollFirst();
        CustomerSales currentCustomer = new CustomerSales(tmp.getCustomerId(), tmp.getCustomerFirstName(), tmp.getCustomerLastName());
        currentCustomer.addSaleAmount(tmp.getSalesPerCustomer());
        maxCust = currentCustomer;

        List<CustomerSales> sls= new ArrayList<>();

        while(!data.isEmpty()){
            tmp = data.pollFirst();
            if(tmp.getCustomerId() == currentCustomer.getCustomerId()){
                currentCustomer.addSaleAmount(tmp.getSalesPerCustomer());
            }else{
                sls.add(currentCustomer);
                currentCustomer = new CustomerSales(tmp.getCustomerId(), tmp.getCustomerFirstName(), tmp.getCustomerLastName());
                currentCustomer.addSaleAmount(tmp.getSalesPerCustomer());
            }
        }

        // Add the sales associated with the last customer as it will not be added from within the loop
        sls.add(currentCustomer);
        sls.sort(Comparator.comparing(CustomerSales::getTotalSales));
        Deque<CustomerSales> salesByCustomer = new LinkedList<>(sls);

        List<String> res = new ArrayList<>();
        while(!salesByCustomer.isEmpty() && count-- > 0){
            res.add(top ? salesByCustomer.pollLast().toString() : salesByCustomer.pollFirst().toString());
        }

        return res;
    }

    /**
     *This method returns the country with the most orders in the format 'country - 123'<br>
     *
     * @return A string specifying the country with the most orders and the corresponding number of orders in the format 'country - 123'
     *
     *This method returns the country with the most customers.<br>
     * The workflow within this method is as follows:<br>
     *
     * 1. Sort the records by order country and then add them to a deque<br>
     * 2. Parse through the deque to add the number of orders from each region<br>
     * 3. If the number of customers from a country exceeds the max number of customers from any region seen previously, update the variable holding the region with the max customers<br>
     * */
    public String getCountryWithMostOrders(){

        if(this.numRecords == 0)
            return null;

        Deque<Record> data = generateDequeue(this.rawData, Comparator.comparing(Record::getOrderCountry));

        String countrywithMaxCust = null;
        int maxNumCust = Integer.MIN_VALUE;
        int numCust = 0;

        String currCountry = null;
        Record tmp;

        while (!data.isEmpty()){
            tmp = data.pollFirst();
            if(currCountry == null){
                currCountry = tmp.getOrderCountry();
                numCust = 1;
                continue;
            }

            if (!tmp.getOrderCountry().equals(currCountry)) {
                if (numCust> maxNumCust) {
                    maxNumCust = numCust;
                    countrywithMaxCust = currCountry;
                }
                currCountry = tmp.getOrderCountry();
                numCust = 0;
            }
            numCust++;
        }
        // This condition has been added for the case where the last region in the list has the max number of customers
        if (numCust > maxNumCust) {
            maxNumCust = numCust;
            countrywithMaxCust = currCountry;
        }
        return countrywithMaxCust + " - " + maxNumCust;
    }
}
