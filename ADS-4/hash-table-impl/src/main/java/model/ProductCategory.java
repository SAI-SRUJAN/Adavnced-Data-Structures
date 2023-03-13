package model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class encapsulates the Product Categories stored within the Product Categories HashTable
 * */

public class ProductCategory {

    private String name;
    private Set<String> products;

    public ProductCategory(Record r){
        this.name = r.getProductCategory();
        this.products = new HashSet<>();
    }

    public void addProduct(String productName){
        this.products.add(productName);
    }

    public String getName() {
        return name;
    }

    public Set<String> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category name: " + this.name + "\n");
        sb.append("Products: " + String.join(",", this.products));
        return sb.toString();
    }
}
