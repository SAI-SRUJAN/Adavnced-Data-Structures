package model;

/**
 * This class encapsulates the vertices storing product data
 * */

public class Product implements GraphVertex<Product>{

    private String name;

    public Product(Record r) {
        this.name = r.getProductName();
    }

    public String getName() {
        return name;
    }

    @Override
    public String getVertexId() {
        return this.getName();
    }
}
