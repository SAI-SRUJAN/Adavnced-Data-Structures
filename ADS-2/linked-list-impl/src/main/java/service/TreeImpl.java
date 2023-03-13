package service;

import model.NodeElement;
import model.TreeNode;

import java.util.Collections;
import java.util.List;

/**
 * This class provides the implementation for the binary search tree (BST).
 * The list of elements to be stored as tree nodes is provided to the constructor at the time of initialing the object.
 * The constructor sorts this list (if not sorted already) and creates a balanced binary search tree storing the data.
 *
 * The class also exposes a method that allows searching for elements in the tree based on the identifier of the element
 * stored within each node. The search takes O(log n) time as the tree is height balanced.
 *
 * Rotate operations haven't been implemented as the use case does not require addition of nodes to the BST post initialization.
 * In case further use cases that require adding nodes to an already balanced tree comes up, rotation operations can be
 * implemented to make sure that the tree stays balanced after the insert operation. This effectively will make the tree an
 * AVL tree that will guarantee O(lon n) insertion and search times
 *
 * */

public class TreeImpl<T extends Comparable<T> & NodeElement> {

    private TreeNode<T> root;

    /**
     * Initialization steps:
     * 1. If sorting is required (ie, the passed list is not sorted already, sort the list)
     * 2. Invoke the build tree method with the sorted list containing data elements to be stored as tree nodes
     *
     * The tree building operation works in O(nlog n) time. This is because of the sorting operation that might be needed
     * to get the elements sorted
     *
     * @param data: The list of objects that are to be stored in the tree nodes
     * @param sortingReq: A boolean field indicating if the list needs to be sorted before creating a tree
     * */
    public TreeImpl(List<T> data, boolean sortingReq){
        if(sortingReq)
            Collections.sort(data);
        double startTime = System.nanoTime();
        this.root = buildTree(data, null);
        double endTime = System.nanoTime();
        System.out.printf("Tree build time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
    }


    /**
     * Given a sorted list, this method recursively builds a height balanced binary tree. Workflow of this method:
     * 1. If the size of the list is 0, return null
     * 2. If the size of the list is 1, return a tree node with no children (ie. leaf node)
     * 3. If the size of the list is greater than 1, create a new node with the middle element from the list. Invoke the
     * buildTree function recursively using the part of the list to the left and the right of the mid node. These will form the
     * left and right children of the node respectively.
     *
     * @param data: The list of objects that are to be stored in the tree nodes
     * @param parent: The parent node for the newly created node
     * */
    private TreeNode<T> buildTree(List<T> data, TreeNode<T> parent) {

        int size = data.size();
        if(size == 0)
            return null;

        if(size == 1){
            return new TreeNode<T>(data.get(0), parent, null, null);
        }

        int mid = size / 2;
        TreeNode<T> newNode = new TreeNode<T>(data.get(mid), parent);
        newNode.setLeftChild(buildTree(data.subList(0, mid), newNode));
        newNode.setRightChild(buildTree(data.subList(mid + 1, size), newNode));

        return newNode;

    }

    /**
     * This method returns the root node of the initialized BST
     *
     * @return This method returns the root node of the tree contained within the TreeImpl object
     * */
    public TreeNode<T> getRoot() {
        return root;
    }

    /**
     * This method finds the node containing the data associated with the passed identifier. The method uses the
     * runBinarySearch method to search through the tree
     *
     * @param identifierValue: This parameter specifies the identifier for the object to be searched for in the tree
     * @return The node that is being searched for if it is found, else null
     * */
    public T findNode(int identifierValue){
        double startTime = System.nanoTime();
        T result = findNode(this.root, identifierValue);
        double endTime = System.nanoTime();
        System.out.printf("Query time: %f milliseconds %n----------------------------------------------------%n", (endTime - startTime) / 1000000);
        return result;
    }


    /**
     *This method recursively performs a binary search to find a given element in the tree. Workflow is as follows:
     * 1. If the node is null, return null (indicating that the element in not present in the tree). This is the break condition for the recursion.
     * 2. If the value of the node is equal to the value being searched for, the element can be returned as the search target is found
     * 3. If the value in the current node is greater than the value being searched for, look for the node in the left sub-tree of the node.
     * Else, continue the search in the right sub-tree.
     *
     * The search operation works in O(lon g) time as the search scope is halved at every stage owing to the tree being a height balanced BST
     *
     * @param startNode: This parameter specifies the tree node from where the search needs to be started
     * @param identifierValue: This parameter specifies the identifier for the object to be searched for in the tree
     * */
    private T findNode(TreeNode<T> startNode, int identifierValue){
        if(startNode == null){
            return null;
        }

        if(startNode.getVal().getNodeIdentifier() == identifierValue)
            return startNode.getVal();

        TreeNode<T> nextNode = startNode.getVal().getNodeIdentifier() > identifierValue ?
                startNode.getLeftChild() : startNode.getRightChild();

        return findNode(nextNode, identifierValue);
    }
}
