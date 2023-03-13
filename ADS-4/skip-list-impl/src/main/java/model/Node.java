package model;

/**
 * This class provides the Generic structure that governs node elements of the
 * skip list. The class has been implemented using Generics enabling the use of
 * any class that implement the Comparable interface as an node element within
 * the skip list.
 */
public class Node<K, V> {
	private K key;
	private V value;
	private Node<K, V>[] right;
	private Node<K, V>[] left;

	public Node(K key, V value, int level) {
		this.key = key;
		this.value = value;
		this.right = new Node[level];
		this.left = new Node[level];
	}

	public Node<K, V> getRight(int index) {
		return right[index];
	}

	public void setRight(Node<K, V> right, int index) {
		this.right[index] = right;
	}

	public Node<K, V> getLeft(int index) {
		return left[index];
	}

	public void setLeft(Node<K, V> left, int index) {
		this.left[index] = left;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}
