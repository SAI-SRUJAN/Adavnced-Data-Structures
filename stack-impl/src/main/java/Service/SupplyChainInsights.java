package Service;
import domain.Order;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SupplyChainInsights {
    private final List<Order> _orderData;
    public SupplyChainInsights(List<Order> customerData) {
        _orderData = customerData.stream()
                .sorted(Comparator.comparing(Order::getCustomerId))
                .collect(Collectors.toList());
    }

    public void GetTopFiveCustomersWithHighestSales() throws CloneNotSupportedException {
        new TopCustomersWithHighestSales(_orderData).ComputeWithTimer();
    }

    public void GetTopFiveCountriesWithMostOrderRequest() throws CloneNotSupportedException {
        new TopCountriesWithMostOrderRequest(_orderData).ComputeWithTimer();
    }

    public void GetLateDeliveryProportionPerMarket() throws CloneNotSupportedException {
        new LateDeliveryProportionPerMarket(_orderData).ComputeWithTimer();
    }

    public void GetTopProductSoldInEachCategory() throws CloneNotSupportedException {
       new TopProductInEachCategory(_orderData).ComputeWithTimer();
    }
}
