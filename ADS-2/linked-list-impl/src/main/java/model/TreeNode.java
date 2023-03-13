package model;

/**
 * This class provides the Generic structure that governs nodes of the BST.
 * The class has been implemented using Generics enabling the use of any class that are subcassed on the
 * Comparable and NodeElement interfaces.
 *
 * */

public class TreeNode<T extends Comparable<T> & NodeElement> {

    private T val;
    private TreeNode<T> parent;
    private TreeNode<T> leftChild;
    private TreeNode<T> rightChild;


    /**
     * @param val: Object held within the node
     * @param parent: Pointer to the parent of the node
     * @param leftChild: Pointer to the left child of the node
     * @param rightChild: Pointer to the right child of the node
     * */
    public TreeNode(T val, TreeNode<T> parent, TreeNode<T> leftChild, TreeNode<T> rightChild) {
        this.val = val;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public TreeNode(T val, TreeNode<T> parent) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public TreeNode<T> getLeftChild() {
        return leftChild;
    }

    public TreeNode<T> getRightChild() {
        return rightChild;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public void setLeftChild(TreeNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(TreeNode<T> rightChild) {
        this.rightChild = rightChild;
    }
}
