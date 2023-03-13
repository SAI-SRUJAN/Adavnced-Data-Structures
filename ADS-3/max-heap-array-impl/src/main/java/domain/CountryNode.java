package domain;

public class CountryNode implements IHeapNode  {

    private String _name;
    private Double _value;

    private CountryNode(String name, Double value){
        _name = name;
        _value = value;
    }

    public CountryNode() {}
    @Override
    public IHeapNode[] CreateArray(Integer Size) {
        return new CountryNode[Size];
    }

    @Override
    public IHeapNode CreateNewObject(Object name, Object value) {

        return new CountryNode((String)name,(Double)value);
    }

    @Override
    public void UpdateValue(Object value) {
        _value = (Double) value;
    }

    @Override
    public Object GetId() {
        return (String)_name;
    }

    @Override
    public Double GetValue() {
        return _value;
    }

    @Override
    public String toString() {
        return String.format("%-45s|%-45s|", GetId(), GetValue());
    }
}
