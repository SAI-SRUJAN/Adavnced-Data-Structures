import model.Customer;
import model.Record;
import service.TreeService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LinkedListTreeRunner {
    public static void main(String[] args) {
        String fileName = "Supply_Chain.csv";
        List<Record> records = new ArrayList<>();
        String l;

        String[] tmp;
        Record r;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(LinkedListTreeRunner.class.getClassLoader().getResourceAsStream(fileName)))
        )){
            br.readLine();
            while((l=br.readLine()) != null){
                r = new Record(l);
                records.add(r);
            }
        }catch(FileNotFoundException f){f.printStackTrace();}
        catch(IOException io){io.printStackTrace();}

        TreeService treeService = new TreeService(records);

        int customerId;
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter a customer ID: ");
            customerId = sc.nextInt();
            treeService.displayOrdersForCustomer(customerId);
        }
    }
}
