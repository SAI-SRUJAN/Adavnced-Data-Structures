package models;

/**
 * This class provides the Generic structure that governs nodes of the Min Heap.
 * The class has been implemented using Generics enabling the use of any class
 * that implement the Comparable interface as a node within the heap.
 */

public class HNode<E> {
	E element;
	private HNode<E> leftChild;
	private HNode<E> rightChild;
	private HNode<E> parent;

	// constructor to create a new node for the tree.
	/**
	 * @param element: Object held within the node
	 */
	public HNode(E element) {
		this.element = element;
		this.leftChild = null;
		this.leftChild = null;
		this.parent = null;
	}

	public E getElement() {
		return element;
	}

	public void setElement(E data) {
		this.element = data;
	}

	public HNode<E> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(HNode<E> leftChild) {
		this.leftChild = leftChild;
	}

	public HNode<E> getRightChild() {
		return rightChild;
	}

	public void setRightChild(HNode<E> rightChild) {
		this.rightChild = rightChild;
	}

	public HNode<E> getParent() {
		return parent;
	}

	public void setParent(HNode<E> parent) {
		this.parent = parent;
	}

}
