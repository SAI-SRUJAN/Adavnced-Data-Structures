import domain.MarketNode;
import service.*;
import util.DataReader;
import util.Table;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class ArrayMaxHeapRunner {

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {

        var orders = DataReader.GetCustomerData();
        System.out.println("***********************************************************************");
        System.out.println("Countries with most orders");
        var countryHeap= new CreateCountryHeap(orders).ComputeWithTimer();
        var result = new TopCountriesWithMostOrderRequest((MaxHeap) countryHeap).ComputeWithTimer();
        List<String> headers = Arrays.asList("Country Name", "Total Orders");
        Table.Draw(headers, (List<Object>) result);

        System.out.println("***********************************************************************");
        System.out.println("Customers with most sales");
        var customerHeap= new CreateCustomerHeap(orders).ComputeWithTimer();
        var result1 = new TopCustomersWithHighestSales((MaxHeap) customerHeap).ComputeWithTimer();
        headers = Arrays.asList("Customer", "Total Sales");
        Table.Draw(headers, (List<Object>) result1);

        System.out.println("***********************************************************************");
        System.out.println("Market with most Late Delivery proportion");
        var marketHeap= new CreateMarketHeap(orders).ComputeWithTimer();
        var result2 = new LateDeliveryProportionPerMarket((MaxHeap) marketHeap).ComputeWithTimer();
        headers= Arrays.asList("Market","Late Delivery Proportion");
        Table.Draw(headers, (List<Object>) result2);
    }
}