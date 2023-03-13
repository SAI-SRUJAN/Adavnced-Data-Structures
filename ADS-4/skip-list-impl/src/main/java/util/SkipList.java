package util;

import java.util.HashSet;
import java.util.Set;

import model.Node;

public class SkipList<K extends Comparable<K>, V> implements ISkipList<K, V> {

	int maxLevel;
	Node<K, V> root;
	private Set<K> keySet;

	public SkipList(int level) {
		// Sets the max level provided as the input
		this.maxLevel = level;
		this.keySet = new HashSet<K>();
		// Initialize the root node with null key and value
		this.root = new Node<K, V>(null, null, this.maxLevel);
	}

	/**
	 * Returns the number of elements in the skip list.
	 * 
	 * @return number of elements in the list
	 */
	public int size() {
		return this.keySet.size();
	}

	/**
	 * Tests whether the skip list is empty.
	 * 
	 * @return true if the skip list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.keySet.size() == 0;
	}

	/**
	 * Checks if the provided key is present in the list
	 * 
	 * @param key is the element to be checked
	 * @return true if element is present in the list, else false
	 */
	public boolean containsKey(K key) {
		return this.keySet.contains(key);
	}

	/**
	 * Inserts the element into the skip list.<br>
	 * 
	 * Workflow of this method:<br>
	 * 1. The list is traversed to find an appropriate position for the element to
	 * be added as the list must be in sorted order.<br>
	 * 
	 * 2. Once, the appropriate position for the element is found, the element is
	 * first added to the base level which is the first level in the list. All the
	 * elements will always be added to the base level in the list.<br>
	 * 
	 * 3. After, the element is added to the base level, a probabilistic method is
	 * used to add the element into the subsequent levels.<br>
	 * 
	 * 4. A flip coin method is used to decide the probability of whether the new
	 * element would be added into the next corresponding levels of the skip list.
	 * This method randomly generates a number between 0 and 1. If the number is
	 * above 0.5, then it returns true else it returns false.<br>
	 * 
	 * 5. Flip coin method would be called at every level to decide whether the
	 * element will be added in that level. When the method returns true, we move
	 * backwards in the base level of the list to find the node which is already
	 * added in the next level and add the new node next to that node in that level.
	 * We update the left and right pointers of the nodes at that particular level
	 * respectively. If no element is found, then this element would be the least
	 * element in that level, so the root’s right pointer at that level will be
	 * pointing to this element of the skip list and the existing elements at that
	 * level would be towards the right of this newly added element.<br>
	 * 
	 * 6. The element will be added to the subsequent levels of the skip list until
	 * the flip coin method returns false or the max level of the skip list is
	 * reached.
	 * 
	 * 
	 * 
	 * @param key   is the key to be inserted
	 * @param value is the value of the element to be inserted
	 */
	public Node<K, V> put(K key, V value) {
		int level = 0;
		// Create a new node with the data to be inserted into the list
		Node<K, V> newNode = new Node<K, V>(key, value, this.maxLevel);
		// If the first node is inserted into the list
		if (this.root.getRight(level) == null) {
			this.root.setRight(newNode, level);
			newNode.setLeft(this.root, level);
		} else {
			Node<K, V> node = this.root;
			// If the elements are already added into the list, we use the skip levels to
			// move forward quicker to the appropriate place where the element has to be
			// added as the list would be sorted in ascending order
			int listlevel = this.maxLevel - 1;
			while (listlevel >= 0) {
				while (node.getRight(listlevel) != null && node.getRight(listlevel).getKey().compareTo(key) <= 0) {
					node = node.getRight(listlevel);
				}
				listlevel--;
			}
			// Once the appropriate position is found we add the element into the list
			// Inserting the node between two nodes and updating the left and the right
			// pointers of the corresponding nodes accordingly
			Node<K, V> temp = node.getRight(level);
			node.setRight(newNode, level);
			newNode.setLeft(node, level);
			if (temp != null) {
				newNode.setRight(temp, level);
				temp.setLeft(newNode, level);
			}
		}
		// To add the node into the next level of the skip list
		level++;
		Node<K, V> skipNode = newNode;
		// We use the flip coin method which decides the probability of adding the node
		// into the next levels.
		// If flip coin returns false or the max level is reached the iteration stops
		while (flipCoin() && level < maxLevel) {
			// Iterate backwards until we get the node which is added into the next level
			while (skipNode != null && skipNode.getLeft(level) == null) {
				skipNode = skipNode.getLeft(0);
			}
			// If null, then we have reached the end of the list and this would be first
			// element in the corresponding level, so we update the right pointer of the root
			// for that level
			if (skipNode == null) {
				skipNode = root;
			}
			// Update the right pointer and left pointer of that corresponding nodes and add the
			// node in that position
			Node<K, V> temp = skipNode.getRight(level);
			skipNode.setRight(newNode, level);
			newNode.setLeft(skipNode, level);
			if (temp != null) {
				newNode.setRight(temp, level);
				temp.setLeft(newNode, level);
			}
			level++;
		}
		this.keySet.add(key);
		return null;
	}

	/**
	 * Search for the element in the list and return it.<br>
	 * 
	 * Workflow of this method: <br>
	 * 1. Search begins from the top level of the list.<br>
	 * 2. If the key value is lesser than the first element in the top level, we
	 * move down to the next level until the first element in that level is lesser
	 * than the key being searched.<br>
	 * 3. Once the first element in a level is found, we move forward in that level
	 * until the next node is greater than the key and then move downwards.<br>
	 * 4. This process goes on until the required key is found in a particular
	 * level.<br>
	 * 5. If the base level is reached and there are no more elements to move
	 * forward, then we've reached the end of the list and the given key is not
	 * present in the list.
	 * 
	 * @param key is the key to be searched for
	 * @return Returns the Node of the element searched if key present, else returns
	 *         null
	 */
	public Node<K, V> get(K key) {
		Node<K, V> node = this.root;
		int level = this.maxLevel - 1;
		// Until the first level of the skip list is searched
		while (level >= 0) {
			// Keep moving forward in the list until the key value is greater than the
			// corresponding node key
			while (node.getRight(level) != null && node.getRight(level).getKey().compareTo(key) <= 0) {
				node = node.getRight(level);
			}
			// If node is found, return the node
			if (node.getKey() != null && node.getKey().compareTo(key) == 0) {
				return node;
			}
			// Move down to the next level
			level--;
		}
		return null;
	}

	/**
	 * This method returns true if the randomly generated number is greater than
	 * 0.5, else returns false
	 **/
	private boolean flipCoin() {
		return Math.random() >= 0.5;
	}

	/**
	 * Removes the element from the skip list. It removes the element from all the
	 * levels of the skip list.
	 * 
	 * @param key is the element to be removed from the skip list
	 */
	public void remove(K key) {
		Node<K, V> nodeToDelete = this.get(key);
		for (int i = 0; i < maxLevel; i++) {
			Node<K, V> leftNode = nodeToDelete.getLeft(i);
			if (nodeToDelete.getLeft(i) != null)
				nodeToDelete.getLeft(i).setRight(nodeToDelete.getRight(i), i);
			if (nodeToDelete.getRight(i) != null)
				nodeToDelete.getRight(i).setLeft(leftNode, i);
		}
	}

	/**
	 * To display all the elements in every level of the skip list. Used only for
	 * debugging purpose.
	 */
	public void display() {
		for (int i = 0; i < maxLevel; i++) {
			Node<K, V> node = this.root.getRight(i);
			while (node != null) {
				System.out.println(node.getKey() + " " + i);
				node = node.getRight(i);
			}
		}
	}

}