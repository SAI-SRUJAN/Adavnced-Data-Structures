package services;

import java.util.ArrayList;
import java.util.HashSet;

public class QueryMap<T extends Comparable<T>> extends Operation{

    private TreeMap<T> treeMap;
    private T _key;

    public QueryMap(TreeMap<T> treeMap, T key) {
        this.treeMap = treeMap;
        _key = key;
    }

    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var searchedNode = treeMap.Get(_key);
        return searchedNode != null ?  new ArrayList<>((HashSet<Object>)searchedNode.get_value()) : null;
    }

    @Override
    public Object Compute() throws CloneNotSupportedException {
        return Perform();
    }
}
