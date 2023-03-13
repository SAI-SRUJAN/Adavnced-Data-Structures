package domain;

public class Country implements Comparable<Country>{

    private String name;
    private Integer count;

    public Country(String Name, Integer Count) {
        this.name = Name;
        this.count = Count;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer value){
        this.count = value;
    }

    @Override
    public int compareTo(Country o) {
        return count.compareTo(o.getCount());
    }

    @Override
    public String toString() {
        return String.format("|%-45s|%-45s|", getName(), getCount());
    }
}
