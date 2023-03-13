package domain;

import java.util.Arrays;
import java.util.List;

public class CustomerNode implements IHeapNode{

    private String _name;
    private Integer _id;
    private Double _value;

    private CustomerNode(Integer id, String name, Double value){
        _name = name;
        _value = value;
        _id= id;
    }

    public CustomerNode() {}

    @Override
    public IHeapNode[] CreateArray(Integer Size) {
        return new CustomerNode[Size];
    }

    @Override
    public IHeapNode CreateNewObject(Object Id, Object value) {
        var customer = (Order)value;
        return new CustomerNode((Integer)Id, ((Order) value).getName(),((Order) value).getCustomerSales());
    }

    @Override
    public void UpdateValue(Object value) {
        _value = (Double) value;
    }

    @Override
    public Object GetId() {
        return (Integer)_id;
    }

    @Override
    public Double GetValue() {
        return _value;
    }


    @Override
    public String toString() {
        return String.format("%-45s|%-45s|", _name,_value);
    }
}
