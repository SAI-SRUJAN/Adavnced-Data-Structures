package util;

import models.TNode;

public interface Tree<E extends Comparable<E>> {

	/**
	 * Returns the number of elements in the tree.
	 * 
	 * @return number of elements in the tree
	 */
	public int size();

	/**
	 * Tests whether the tree is empty.
	 * 
	 * @return true if the tree is empty, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Inserts the element in the tree.
	 * 
	 * @param data is the element to be inserted
	 */
	public TNode<E> insert(E data);

	/**
	 * Traverse the tree(Pre-Order Traversal)
	 * 
	 */
	public void traverse();

	/**
	 * Search the element in the tree.
	 * 
	 * @param data is the element to be searched
	 */
	public TNode<E> search(E data);

	/**
	 * Returns the root element of the tree.
	 * 
	 * @return null if tree is empty, else returns the root node data
	 */
	public E getRoot();
}
