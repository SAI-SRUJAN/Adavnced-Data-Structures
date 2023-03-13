package util;

import com.opencsv.bean.CsvToBeanBuilder;
import domain.Order;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;


public class DataReader {
    public static List<Order> GetCustomerData() throws FileNotFoundException {
        String fileName = "Supply_Chain.csv";

        List<Order> customers = new CsvToBeanBuilder(new InputStreamReader(Objects.requireNonNull(DataReader.class.getClassLoader().getResourceAsStream(fileName))))
                .withType(Order.class)
                .build()
                .parse();
        System.out.println("total items: " + customers.size());
        return customers;
    }

}
