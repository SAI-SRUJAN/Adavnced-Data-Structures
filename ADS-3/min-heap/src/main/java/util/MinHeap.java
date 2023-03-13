package util;

import java.util.LinkedList;
import java.util.Queue;

import models.HNode;

/**
 * This class provides the implementation for the Min Heap. Each element in the
 * heap is stored in a node which has 3 pointers in addition to the data
 * element. One pointer refers to the node's parent in the heap and the 2
 * pointers hold references to the left and right child of the node
 * respectively.
 *
 */
public class MinHeap<E extends Comparable<E>> implements Heap<E> {

	private int size;
	private HNode<E> root;
	Queue<HNode<E>> heapNodes = new LinkedList<>();

	/**
	 * Returns the number of elements in the heap.
	 * 
	 * @return number of elements in the heap
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Tests whether the heap is empty.
	 * 
	 * @return true if the heap is empty, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Inserts the element into the heap.<br>
	 * Workflow of this method: <br>
	 * 1. If the heap is empty, we make the new node as the root node of the
	 * heap.<br>
	 * 2. When inserting the various elements into the heap, we keep a track of all
	 * the nodes inserted into the heap by adding them into a queue.<br>
	 * 3. Since the min heap has to hold the complete binary tree property, each
	 * time when a new node is to be added we get the first node in the queue which
	 * would be the parent for the new node, add the new node as the left child of
	 * the node if it's not there or the right child if the left child is already
	 * filled. If both the children are added, we remove that node from the queue
	 * and then get the next parent node details from the queue and add the new node
	 * as left it's child.<br>
	 * 4. Once the new node is added we bubble up the node, to maintain the min heap
	 * property.<br>
	 * 5. If the new added node is lesser than the parent, then we bubble up the
	 * node by swapping the node values. This process repeats until the node is in
	 * it's correct position and the heap property is satisfied.
	 * 
	 * 
	 * @param data is the element to be inserted
	 */
	@Override
	public void insert(E data) {
		HNode<E> newNode = new HNode<E>(data);
		if (isEmpty()) {
			this.root = newNode;
			heapNodes.add(this.root);
			size = 1;
			return;
		}
		// Get the first element from the queue
		HNode<E> parent = heapNodes.peek();
		// If the node has both it's children, we get the next node in the queue
		if (parent.getLeftChild() != null && parent.getRightChild() != null) {
			heapNodes.remove();
			parent = heapNodes.peek();
		}
		// If the left child is null, we first assign the new node as it's left child
		// else assign to it's right child
		if (parent.getLeftChild() == null) {
			parent.setLeftChild(newNode);
		} else {
			parent.setRightChild(newNode);
		}
		newNode.setParent(parent);
		heapNodes.add(newNode);
		// Push the node value to maintain the heap property
		bubbleUp(newNode);
		size++;
	}

	/**
	 * This method is used to push the value up in the heap after insertion to
	 * maintain the min heap property. If the value of the node is less than it's
	 * parent node then we swap the node values and continue the process until the
	 * value is greater than or equal to it's parent node.
	 * 
	 * @param node is the node to be pushed up in the min heap
	 */
	private void bubbleUp(HNode<E> node) {
		HNode<E> parent = node.getParent();
		while (parent != null && (node.getElement().compareTo(parent.getElement()) < 0)) {
			swapValues(parent, node);
			node = parent;
			parent = node.getParent();
		}
	}

	/**
	 * This method returns the min element from the heap. The min element of the
	 * heap is always at the root of the heap.<br>
	 *
	 * Workflow within this method:<br>
	 * 1. Store the data element from the top node into a local variable<br>
	 * 2. Swap the top node of the heap with the last node of the heap.<br>
	 * 3. Delete the last node from the heap<br>
	 * 4. Heapify(Push down) to restore the heap property <br>
	 * 5. Return the data element stored in the local variable
	 *
	 * @return The min element from the heap
	 */
	public E removeMin() {
		if (isEmpty())
			return null;

		E temp = this.root.getElement();

		if (this.size == 1) {
			this.root = null;
			this.size--;
			return temp;
		}

		// Swapping the values of the root and last node
		HNode<E> lastNode = getLastNode(this.root);
		swapValues(lastNode, this.root);

		// Removing the last node from the heap
		if (lastNode == lastNode.getParent().getLeftChild())
			lastNode.getParent().setLeftChild(null);
		else
			lastNode.getParent().setRightChild(null);

		this.size--;

		// Push down the top node to maintain heap property
		pushDownRoot();
		return temp;
	}

	/**
	 * This method returns the last node in the heap.
	 *
	 * To identify the last node the following algorithm is used.<br>
	 * 1. Calculate the left most height of the heap from the node passed.<br>
	 * 2. We then calculate the left most height of the heap from the right child of
	 * the node passed. If the height calculated in first step is (equal height) 1
	 * less than the height calculated from the right child, the value lies in the
	 * right subtree and so we recursively traverse right subtree and continue the
	 * process until the height is 1 and the last node is reached. Similarly, if the
	 * height of the right subtree is lesser than left subtree then value lies in
	 * left subtree and so we traverse the the left subtree recursively until we
	 * find the last node.
	 * 
	 * @param node the right or the left sub tree to be traversed
	 * 
	 * @return the last node in the heap
	 **/
	private HNode<E> getLastNode(HNode<E> node) {
		int height = getLeftHeight(node);
		if (height == 1) {
			return node;
		} else if ((height - 1) == getLeftHeight(node.getRightChild())) {
			return getLastNode(node.getRightChild());
		} else {
			return getLastNode(node.getLeftChild());
		}
	}

	/**
	 * Calculates the left height of the heap from the node passed as the input.
	 * 
	 * @param node the node from where the height has to be calculated
	 * @return height
	 */
	private int getLeftHeight(HNode<E> node) {
		int height = 0;
		while (node != null) {
			node = node.getLeftChild();
			height++;
		}
		return height;
	}

	/**
	 * This method pushes the root node down the binary tree to ensure that the heap
	 * property is satisfied. This method is used to restore the min heap property
	 * after running the remove min operation.<br>
	 *
	 * Workflow within this method:<br>
	 * 1. If the root node is null, return<br>
	 * 2. If the node being processed is the leaf node, return <br>
	 * 3. If the node does not have a right child and the data element in the node
	 * is greater than the data element at its left child, swap the data elements
	 * between the node and its left child and return.<br>
	 * 4. If current node has 2 children, and the value of the data element at the
	 * current node is lesser than the value of elements at both its children,
	 * return<br>
	 * 5. Finally, if the value of the node is greater than the value at either/both
	 * of its children, swap the element at the node with the element at its child
	 * holding the lesser value and continue the process in the relevant sub-tree
	 */
	private void pushDownRoot() {

		HNode<E> currNode = this.root;
		HNode<E> lesserChild = null;

		while (true) {
			if (currNode == null)
				return;

			if (currNode.getLeftChild() == null && currNode.getRightChild() == null)
				return;

			if (currNode.getRightChild() == null) {
				if (currNode.getElement().compareTo(currNode.getLeftChild().getElement()) > 0)
					swapValues(currNode, currNode.getLeftChild());
				return;
			}

			if (currNode.getElement().compareTo(currNode.getLeftChild().getElement()) <= 0
					&& currNode.getElement().compareTo(currNode.getRightChild().getElement()) <= 0)
				return;

			if (currNode.getLeftChild().getElement().compareTo(currNode.getRightChild().getElement()) < 0) {
				lesserChild = currNode.getLeftChild();
			} else {
				lesserChild = currNode.getRightChild();
			}
			swapValues(currNode, lesserChild);
			currNode = lesserChild;
		}
	}

	/**
	 * This method swaps the elements of the two passed nodes in the heap
	 *
	 * @param node1
	 * @param node2
	 */
	private void swapValues(HNode<E> node1, HNode<E> node2) {
		E temp = node1.getElement();
		node1.setElement(node2.getElement());
		node2.setElement(temp);
	}

	/**
	 * Returns the top element from the heap which is the minimum(lowest) value but
	 * does not remove it from the heap
	 * 
	 * @return Returns the top element from the heap, but does not remove it
	 */
	@Override
	public E min() {
		if (!isEmpty())
			return this.root.getElement();
		return null;
	}

}
