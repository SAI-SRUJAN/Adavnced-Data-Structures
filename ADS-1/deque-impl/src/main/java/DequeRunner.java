import model.ProductSales;
import model.Record;
import service.InsightsService;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class DequeRunner {
    public static void main(String[] args) {
        String fileName = "Supply_Chain.csv";
        List<Record> records = new ArrayList<>();
        String l;

        String[] tmp;
        Record r;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(DequeRunner.class.getClassLoader().getResourceAsStream(fileName)))
        )){
            br.readLine();
            while((l=br.readLine()) != null){
                tmp = l.split(",");
                r = new Record();

                r.setCustomerId(Integer.parseInt(tmp[8]));
                r.setCustomerFirstName(tmp[7]);
                r.setCustomerLastName(tmp[9]);
                r.setSalesPerCustomer(Double.parseDouble(tmp[2]));
                r.setCategoryName(tmp[4]);
                r.setProductName(tmp[22]);
                r.setMarket(tmp[11]);
                r.setDeliveryStatus(tmp[3]);
                r.setOrderCountry(tmp[12]);

                records.add(r);
            }
        }catch(FileNotFoundException f){f.printStackTrace();}
        catch(IOException io){io.printStackTrace();}

        long startTime;

        InsightsService service = new InsightsService(records);

        startTime = System.currentTimeMillis();
        List<ProductSales> popularProductByCategory = service.getMostPopularProductByCategory();
        System.out.println("Query time : " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Top products by category: ");
        popularProductByCategory.sort(Comparator.comparing(ProductSales::getCategory));
        popularProductByCategory.forEach(System.out::println);

        System.out.println("***********************************************");

        startTime = System.currentTimeMillis();
        List<String> lateDeliveriesByRegion = service.getLateDeliveriesByRegion();
        System.out.println("Query time : " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Proportion of late deliveries by region: ");
        lateDeliveriesByRegion.forEach(System.out::println);

        System.out.println("***********************************************");

        startTime = System.currentTimeMillis();
        List<String> custWithMaxSales = service.getTopCustBySales(true, 5);
        System.out.println("Query time : " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Top 5 customers by sales: ");
        custWithMaxSales.forEach(System.out::println);

        System.out.println("***********************************************");

        startTime = System.currentTimeMillis();
        String countryWithMostSales = service.getCountryWithMostOrders();
        System.out.println("Query time : " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Country with most customers: " + countryWithMostSales);
    }
}
