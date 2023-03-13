package util;

import exception.EmptyTreeException;
import models.TNode;

public class SplayTree<E extends Comparable<E>> implements Tree<E> {

	private int size;
	private TNode<E> root;

	/**
	 * If element is not present in the tree, it inserts the element in the tree and
	 * splays it on to the top of the tree. If the element already exists in the
	 * tree, it will splay it on to the top and return that node.
	 * 
	 * @param data is the element to be inserted
	 * 
	 * @return Returns the node of the tree if already present in the tree, else
	 *         returns null.
	 */
	@Override
	public TNode<E> insert(E data) {
		// If node is already present it splays the node to the top of the tree and
		// returns the node
		TNode<E> element = null;
		if (!isEmpty())
			element = search(data);
		if (element != null) {
			return element;
		} else {
			// Adds the node to the tree and splays the node to the top of the tree
			TNode<E> nodeToAdd = new TNode<E>(data);
			root = insertNode(root, nodeToAdd);
			size++;
			splay(nodeToAdd);
			return null;
		}
	}

	/**
	 * Inserts the element in the tree, by recursively calling the function. Based
	 * on the value of the data to be added the right or the left subtree will be
	 * traversed accordingly.
	 * 
	 * 
	 * @param node    is the tree where the element is to be inserted
	 * @param newNode is the new element to be inserted into the tree
	 * 
	 * @return Returns the root after the new node is inserted
	 */
	public TNode<E> insertNode(TNode<E> node, TNode<E> newNode) {
		if (node == null) {
			return newNode;
		}
		if (newNode.getElement().compareTo(node.getElement()) < 0) {
			node.setLeftChild(insertNode(node.getLeftChild(), newNode));
			node.getLeftChild().setParent(node);
		} else if (newNode.getElement().compareTo(node.getElement()) > 0) {
			node.setRightChild(insertNode(node.getRightChild(), newNode));
			node.getRightChild().setParent(node);
		}
		return node;
	}

	/**
	 * Search the element in the tree.
	 * 
	 * @param data is the element to be searched
	 * 
	 * @return Returns the node of the tree if present in the tree, else returns
	 *         null
	 */
	@Override
	public TNode<E> search(E data) {
		if (isEmpty()) {
			throw new EmptyTreeException("Tree Is Empty");
		}
		TNode<E> element = searchNode(root, data);
		return element;
	}

	/**
	 * Searches the element in the tree, if element is found and if it is not the
	 * root node it will splay the element to the top of the tree by rotating the
	 * tree to the left or right based on its position.
	 * 
	 * @param data is the element to be searched
	 * @param node is the sub-tree to be searched(left or right sub-tree if it is
	 *             not found in the root node)
	 * 
	 * @return Returns the node of the tree if present in the tree, else returns
	 *         null
	 */
	public TNode<E> searchNode(TNode<E> node, E data) {
		if (node != null) {
			if (node.getElement().compareTo(data) == 0) {
				if (root.getElement().compareTo(data) != 0) {
					splay(node);
				}
				return node;
			}
			if (node.getElement().compareTo(data) > 0) {
				return searchNode(node.getLeftChild(), data);
			} else {
				return searchNode(node.getRightChild(), data);
			}
		} else {
			return null;
		}
	}

	/**
	 * Splays the element to the top of the tree.
	 * 
	 * @param node is the tree node to be splayed to the top
	 * 
	 */
	public void splay(TNode<E> node) {
		// Iterate through until node is the root node
		while (node.getParent() != null) {
			TNode<E> parent = node.getParent();
			TNode<E> grandParent = parent.getParent();
			// If the node is in the left subtree then we rotate it right else rotate it
			// left(push upwards)
			if (node == parent.getLeftChild()) {
				// If the parent of the node and the node are both left child of their
				// respective parents, then we first rotate the parent and then the node,
				// to make the rotation operation quicker.
				if (grandParent != null && parent == grandParent.getLeftChild()) {
					rightRotate(parent);
					rightRotate(node);
				} else {
					rightRotate(node);
				}
			} else {
				// If the parent of the node and the node are both right child of their
				// respective parents, then we first rotate the parent and then the node,
				// to make the rotation operation quicker.
				if (grandParent != null && parent == grandParent.getRightChild()) {
					leftRotate(parent);
					leftRotate(node);
				} else {
					leftRotate(node);
				}
			}
		}
	}

	/**
	 * The node of the tree will be rotated from left to right of the tree.
	 * 
	 * @param node to be rotated to the right
	 * 
	 */
	public void rightRotate(TNode<E> node) {
		TNode<E> parent = node.getParent();
		// Sets the right child of the node as the left child of the parent
		parent.setLeftChild(node.getRightChild());
		// Update the parent of the node's right child
		if (node.getRightChild() != null) {
			node.getRightChild().setParent(parent);
		}
		// Node would be pushed upwards and the grandparent of the node would now be
		// it's parent, also the node would be set as left or the right child
		// accordingly
		node.setParent(parent.getParent());
		if (parent.getParent() == null) {
			root = node;
		} else if (parent == parent.getParent().getRightChild()) {
			parent.getParent().setRightChild(node);
		} else {
			parent.getParent().setLeftChild(node);
		}
		// The node's parent would now be set as the right child of the node
		node.setRightChild(parent);
		parent.setParent(node);
	}

	/**
	 * The node of the tree will be rotated from right to left of the tree.
	 * 
	 * @param node to be rotated to the left
	 * 
	 */
	public void leftRotate(TNode<E> node) {
		TNode<E> parent = node.getParent();
		// Sets the left child of the node as the right child of the parent
		parent.setRightChild(node.getLeftChild());
		// Update the parent of the node's left child
		if (node.getLeftChild() != null) {
			node.getLeftChild().setParent(parent);
		}
		// Node would be pushed upwards and the grandparent of the node would now be
		// it's parent, also the node would be set as left or the right child
		// accordingly
		node.setParent(parent.getParent());
		if (parent.getParent() == null) {
			root = node;
		} else if (parent == parent.getParent().getLeftChild()) {
			parent.getParent().setLeftChild(node);
		} else {
			parent.getParent().setRightChild(node);
		}
		// The node's parent would now be set as the left child of the node
		node.setLeftChild(parent);
		parent.setParent(node);
	}

	/**
	 * Returns the number of elements in the tree.
	 * 
	 * @return number of elements in the tree
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Tests whether the tree is empty.
	 * 
	 * @return true if the tree is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Traverse the tree(Pre-Order Traversal)
	 * 
	 */
	@Override
	public void traverse() {
		traversePreOrder(root);
	}

	/**
	 * Returns the root element of the tree.
	 * 
	 * @return null if tree is empty, else returns the root node data
	 */
	@Override
	public E getRoot() {
		if (isEmpty())
			return null;
		return root.getElement();
	}

	/**
	 * Traverse the tree(Pre-Order Traversal)
	 * 
	 * Visit the node first, then left and right sub tree of the node.
	 * 
	 * @param node of the tree(left or right sub-part of the tree to be traversed)
	 */
	private void traversePreOrder(TNode<E> node) {
		if (node != null) {
			System.out.println(node.getElement().toString());
			traversePreOrder(node.getLeftChild());
			traversePreOrder(node.getRightChild());
		}
	}
}
