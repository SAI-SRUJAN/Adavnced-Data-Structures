package model;

/**
 * This class provides the Generic structure that governs nodes of the Max Heap.
 * The class has been implemented using Generics enabling the use of any class that implement the Comparable
 * interface as a node within the heap.
 * */

public class Node<T extends Comparable<T>> {

    private T val;
    private Node<T> parent;
    private Node<T> leftChild;
    private Node<T> rightChild;


    /**
     * @param val: Object held within the node
     * @param parent: Pointer to the parent of the node
     * @param leftChild: Pointer to the left child of the node
     * @param rightChild: Pointer to the right child of the node
     * */
    public Node(T val, Node<T> parent, Node<T> leftChild, Node<T> rightChild) {
        this.val = val;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public Node(T val, Node<T> parent) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public void setVal(T val) {
        this.val = val;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }
}
