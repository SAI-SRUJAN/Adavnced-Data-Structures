import Service.SupplyChainInsights;
import util.DataReader;
import java.io.FileNotFoundException;

public class StackRunner {

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        var insightsService = new SupplyChainInsights(DataReader.GetCustomerData());
        System.out.println("\nTop product Sold in Each Category\n");
        GetTopProductSoldInEachCategory(insightsService);
        System.out.println("\nLate Delivery proportion per Market\n");
        GetLateDeliveryProportionPerMarket(insightsService);
        System.out.println("\nTop 5 Customers with Highest Sales\n");
        GetTopCustomersWithHighestSales(insightsService);
        System.out.println("\nTop 5 countries with most orders\n");
        GetCountriesWithMostCustomers(insightsService);
    }

    private static void GetTopCustomersWithHighestSales(SupplyChainInsights insightsService)
            throws CloneNotSupportedException {
        insightsService.GetTopFiveCustomersWithHighestSales();
    }

    private static void GetCountriesWithMostCustomers(SupplyChainInsights insightsService)
            throws CloneNotSupportedException {
        insightsService.GetTopFiveCountriesWithMostOrderRequest();
    }

    private static void GetLateDeliveryProportionPerMarket(SupplyChainInsights insightsService)
            throws CloneNotSupportedException {
        insightsService.GetLateDeliveryProportionPerMarket();
    }

    private static void GetTopProductSoldInEachCategory(SupplyChainInsights insightsService)
            throws CloneNotSupportedException {
        insightsService.GetTopProductSoldInEachCategory();
    }

}
