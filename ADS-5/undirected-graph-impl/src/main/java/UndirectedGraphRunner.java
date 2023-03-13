import model.Order;
import model.Record;
import service.UndirectedGraphService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UndirectedGraphRunner {

    public static void main(String[] args) {
        String fileName = "Supply_Chain.csv";
        List<Record> records = new ArrayList<>();
        String l;

        String[] tmp;
        Record r;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(UndirectedGraphRunner.class.getClassLoader().getResourceAsStream(fileName)))
        )){
            br.readLine();
            while((l=br.readLine()) != null){
                r = new Record(l);
                records.add(r);
            }
        }catch(FileNotFoundException f){f.printStackTrace();}
        catch(IOException io){io.printStackTrace();}

        UndirectedGraphService undirectedGraphService = new UndirectedGraphService(records);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a product name");

        String productName = sc.nextLine();

        System.out.println("Querying for countries that the product was delivered to");
        double startTime = System.nanoTime();
        List<String> countries = undirectedGraphService.getProductPurchaseCountries(productName);
        double endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        System.out.println("Countries: " + countries);


        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        System.out.println("Querying for delayed orders that the product was a part of");
        startTime = System.nanoTime();
        List<Order> orders = undirectedGraphService.getDelayedConsignments(productName);
        endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        System.out.println("Order ids: " + orders);
    }
}
