package models;

public class TNode<E> {
	E element;
	private TNode<E> leftChild;
	private TNode<E> rightChild;
	private TNode<E> parent;

	// constructor to create a new node for the tree.
	public TNode(E element) {
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

	public TNode<E> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(TNode<E> leftChild) {
		this.leftChild = leftChild;
	}

	public TNode<E> getRightChild() {
		return rightChild;
	}

	public void setRightChild(TNode<E> rightChild) {
		this.rightChild = rightChild;
	}

	public TNode<E> getParent() {
		return parent;
	}

	public void setParent(TNode<E> parent) {
		this.parent = parent;
	}

}
