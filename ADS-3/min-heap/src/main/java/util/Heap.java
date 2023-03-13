package util;

public interface Heap<E extends Comparable<E>> {

	/**
	 * Returns the number of elements in the heap.
	 * 
	 * @return number of elements in the heap
	 */
	public int size();

	/**
	 * Tests whether the heap is empty.
	 * 
	 * @return true if the heap is empty, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Inserts the element into the heap.
	 * 
	 * @param data is the element to be inserted
	 */
	public void insert(E data);

	/**
	 * Removes the top element from the heap which is the minimum(lowest) value.
	 * 
	 * @return Removes the top element from the heap and returns it
	 */
	public E removeMin();

	/**
	 * Returns the top element from the heap which is the minimum(lowest) value but
	 * does not remove it from the heap
	 * 
	 * @return Returns the top element from the heap, but does not remove it
	 */
	public E min();

}
