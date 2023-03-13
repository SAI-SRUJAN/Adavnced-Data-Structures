package util;

import model.Node;

public interface ISkipList<K, V> {

	/**
	 * Returns the number of elements in the skip list.
	 * 
	 * @return number of elements in the list
	 */
	public int size();

	/**
	 * Tests whether the skip list is empty.
	 * 
	 * @return true if the skip list is empty, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Inserts the element into the skip list.
	 * 
	 * @param key   is the key to be inserted
	 * @param value is the value of the element to be inserted
	 */
	public Node<K, V> put(K key, V value);

	/**
	 * Search for the element in the list and return it.
	 * 
	 * @param key is the key to be searched for
	 * @return Returns the Node of the element searched if key present, else returns
	 *         null
	 */
	public Node<K, V> get(K key);

	/**
	 * Removes the element from the skip list
	 * 
	 * @param key is the element to be removed from the skip list
	 */
	public void remove(K key);

	/**
	 * Checks if the provided key is present in the list
	 * 
	 * @param key is the element to be checked
	 * @return true if element is present in the list, else false
	 */
	public boolean containsKey(K key);
}
