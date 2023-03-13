import model.Record;
import service.HashTableService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HashTableRunner {

    public static void main(String[] args) {
        String fileName = "Supply_Chain.csv";
        List<Record> records = new ArrayList<>();
        String l;

        String[] tmp;
        Record r;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(HashTableRunner.class.getClassLoader().getResourceAsStream(fileName)))
        )){
            br.readLine();
            while((l=br.readLine()) != null){
                r = new Record(l);
                records.add(r);
            }
        }catch(FileNotFoundException f){f.printStackTrace();}
        catch(IOException io){io.printStackTrace();}

        HashTableService hashTableService = new HashTableService(records);

        Scanner sc = new Scanner(System.in);
        System.out.println("The process will automatically query for the orders associated with the selected customer and you need not select an order id separately");
        System.out.println("Enter a customer ID (Note that customer ids are positive integers): ");
        int customerId = Integer.parseInt(sc.nextLine());
        hashTableService.displayOrdersForCustomer(customerId);

        System.out.println("Enter a category name (Note that category names are non empty strings): ");
        String categoryName = sc.nextLine().trim();
        hashTableService.displayProductsInCategory(categoryName);
    }
}
