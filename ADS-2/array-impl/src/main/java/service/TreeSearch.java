package service;

import util.Table;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TreeSearch extends Operation{

    private final ArrayTree _balancedTree;
    private Integer _id;
    public TreeSearch(ArrayTree balancedTree){
        _balancedTree = balancedTree;
    }
    @Override
    protected Object Perform() throws CloneNotSupportedException {
        var index = _balancedTree.SearchNode(_id,0);
        var searchedNode = _balancedTree.getNode(index);
        if(searchedNode == null) {
            System.out.println("Sorry Did not find the details");
            return false;
        }
        Table.Draw(_balancedTree.GetHeaders(),searchedNode.GetValue().stream().collect(Collectors.toList()));
        return true;
    }

    @Override
    public void Compute(String description) throws CloneNotSupportedException {
        System.out.println(description);
        Perform();
    }

    public void Search(Integer id, String description) throws CloneNotSupportedException {
        _id = id;
        ComputeWithTimer(description+_id);
    }
}
