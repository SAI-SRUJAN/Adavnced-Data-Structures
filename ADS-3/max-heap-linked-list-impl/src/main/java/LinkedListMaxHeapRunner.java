import model.Customer;
import model.DeliveryRegion;
import model.OrderCountry;
import model.Record;
import service.MaxHeapService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinkedListMaxHeapRunner {

    public static void main(String[] args) {
        String fileName = "Supply_Chain.csv";
        List<Record> records = new ArrayList<>();
        String l;

        String[] tmp;
        Record r;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(LinkedListMaxHeapRunner.class.getClassLoader().getResourceAsStream(fileName)))
        )){
            br.readLine();
            while((l=br.readLine()) != null){
                r = new Record(l);
                records.add(r);
            }
        }catch(FileNotFoundException f){f.printStackTrace();}
        catch(IOException io){io.printStackTrace();}

        int numResults = 5;

        MaxHeapService maxHeapService = new MaxHeapService(records);
        System.out.printf("Displaying top %d customers with highest sales%n", numResults);
        for(Customer c: maxHeapService.getTopCustomers(5)){
            System.out.println(c.toString());
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("Displaying top %d countries with most orders%n", numResults);
        for(OrderCountry c: maxHeapService.getCountriesWithMostOrders(5)){
            System.out.println(c.toString());
        }
        System.out.println("----------------------------------------------------");
        System.out.printf("Displaying top %d regions with highest proportion of delayed deliveries%n", numResults);
        for(DeliveryRegion reg: maxHeapService.getRegionsWithHighestDeliveryDelays(5)){
            System.out.println(reg.toString());
        }
        System.out.println("----------------------------------------------------");
    }
}
