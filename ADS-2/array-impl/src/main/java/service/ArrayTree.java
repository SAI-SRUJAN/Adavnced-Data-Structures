package service;

import domain.ITreeNode;

import java.util.Arrays;
import java.util.List;

/**
 * This class has the implementation of the Array based AVL tree.
 *
 * Constructor takes size of the tree and the type of the ITreeNode whose
 * type the array must be.
 */

public class ArrayTree {
    private ITreeNode[] nodes;

    public ArrayTree(Integer n,ITreeNode treeNode){
        nodes = treeNode.CreateArray(2*n);
    }

    /**
     * This method gets the node instance given the array index.
     * @param index
     * @return
     */
    public ITreeNode getNode(Integer index) {
        if(index != -1)
            return nodes[index];
        return null;
    }

    /**
     * This methods updates the node at the given index with the value that is provided to the method.
     * @param index
     * @param Value
     */
    public void UpdateNode(Integer index,Object Value){
        this.nodes[index].AddValue(Value);
    }

    /**
     * this method is used to search for a node in the tree using the nodeId as the element.
     *
     * Algorithm : <br/>
     * Search starts from the first index i.e. root of the tree and then depending on the node's id and the search key
     * the search is shifted on either left or right child of the root and this procedure continues till the search key is
     * found. If element is found array index is returned else -1 is returned.
     *
     * @param id
     * @param startIndex
     * @return
     */
    public Integer SearchNode(Integer id, Integer startIndex){
        if(startIndex > nodes.length-1 || nodes[startIndex] == null){
            return -1;
        }
        if(nodes[startIndex].GetId().equals(id)) {
            return startIndex;
        }
        if(id < nodes[startIndex].GetId())
            return SearchNode(id, leftChildIndex(startIndex));
        else
            return SearchNode(id, rightChildIndex(startIndex));
    }

    /**
     * This is the method which creates AVL tree in form of an array.
     * it takes ordered list of nodes by nodeId as the input and performs the operations to convert that sorted array
     * into AVL tree.
     * Algorithm :<br/>
     * <ol>
     *     <li>Mid of the sorted array is taken and added as the 1st index of our ArrayTree</li>
     *     <li>All the elements from start to mid-1 are taken and same create BST method is called</li>
     *     <li>All the elements from mid+1 to start are taken and same create BST is called</li>
     *     <li>This process repeats till all the elements are taken into the tree</li>
     *
     * </ol>
     * Resultant array will be of the form<br/>
     * parent = i ; leftChild = 2*i + 1; rightChild = 2*i+2
     *
     * @param index
     * @param start
     * @param end
     * @param sortedTree
     */
    public void CreateBST(Integer index, Integer start, Integer end, List<ITreeNode> sortedTree) {
        if (start <= end) {
            var mid = (start + end) / 2;
            nodes[index] = sortedTree.get(mid);
            CreateBST(leftChildIndex(index), start, mid - 1, sortedTree);
            CreateBST(rightChildIndex(index), mid + 1, end, sortedTree);
        }
    }

    public List<String> GetHeaders(){
        return Arrays.stream(nodes).findFirst().get().GetHeaders();
    }

    private Integer leftChildIndex(Integer parent) {
        return parent * 2 + 1;
    }

    private Integer rightChildIndex(Integer parent) {
        return parent * 2 + 2;
    }

}
