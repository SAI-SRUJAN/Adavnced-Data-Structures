package service;

import domain.ITreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeCreation extends Operation{

    private ArrayTree _balancedTree;
    private List<Integer> _nodeIds;
    private ITreeNode _treeNode;
    public TreeCreation(List<Integer> nodeIds,ITreeNode treeNode){
        _nodeIds = nodeIds;
        _treeNode = treeNode;
    }

    @Override
    protected Object Perform() {
       var uniqueIds = _nodeIds.stream().sorted().distinct().collect(Collectors.toList());
        List<ITreeNode> bufferList = new ArrayList<>();
        for( var cId:uniqueIds){
            bufferList.add(_treeNode.CreateNewObject(cId));
        }
        _balancedTree.CreateBST(0,0,bufferList.size()-1,bufferList);
        return _balancedTree;
    }

    @Override
    public void Compute(String description) throws CloneNotSupportedException {
        System.out.println(description);
        Perform();
    }

    public ArrayTree CreateNew() throws CloneNotSupportedException {
        _balancedTree = new ArrayTree(_nodeIds.size(),_treeNode);
        this.ComputeWithTimer("Creating Tree");
        return _balancedTree;
    }
}
