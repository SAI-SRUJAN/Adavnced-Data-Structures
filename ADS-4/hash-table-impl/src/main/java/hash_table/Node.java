package hash_table;

import java.util.Map;
import java.util.Objects;

/**
 * This class provides the required implementation for the HashTableEntry to be used within this HashTable
 * Each node has 3 attributes
 * 1. key: The key of the Entry stored at the node
 * 2. value: The value associated with the key stored at the node
 * 3. next: The next Node in the chain of nodes which resolve to the same bucket in the HashTable. This is important to implement the chaining approach
 * */
public final class Node<K, V> implements HashTableEntry<K, V>{

    final K key;
    V value;
    Node<K, V> next;

    Node(K key, V value, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    /**
     * Retrieve the key from the node
     * @return The key from the node
     * */
    public K getKey() {
        return this.key;
    }

    /**
     * Retrieve the value from the node
     * @return The value from the node
     * */
    public V getValue() {
        return this.value;
    }

    /**
     * Get a string representation of the node
     * @return The string representation of the node
     * */
    public String toString() {
        return this.key + "=" + this.value;
    }

    /**
     * Get the hash value associated with the node
     * @return The hash value associated with the node
     * */
    public int hashCode() {
        return Objects.hashCode(this.key) ^ Objects.hashCode(this.value);
    }

    /**
     * Update the value in the node
     * @param newValue The new value to be set in the node
     * @return The updated value in the node
     * */
    public V setValue(V newValue) {
        V oldValue = this.value;
        this.value = newValue;
        return oldValue;
    }

    /**
     * @param o Node to compare this Node to
     * @return Boolean flag indicating equality of the passed object with the current object
     * */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            if (o instanceof Map.Entry) {
                HashTableEntry<?, ?> e = (HashTableEntry)o;
                if (Objects.equals(this.key, e.getKey()) && Objects.equals(this.value, e.getValue())) {
                    return true;
                }
            }

            return false;
        }
    }

}
