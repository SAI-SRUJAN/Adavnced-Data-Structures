package hash_table;

public interface HashTableEntry<K, V> {

    /**
     * @return The key from the HashTableEntry
     * */
    K getKey();

    /**
     * @return The value from the HashTableEntry
     * */
    V getValue();

    /**
     * @param val The value to be set within the HashTableEntry
     * @return The newly set value
     * */
    V setValue(V val);

    /**
     * @param other HashTableEntry to compare this HashTableEntry to
     * @return Boolean flag indicating equality of the passed object with the current object
     * */
    boolean equals(Object other);

    /**
     * @return The hashValue of the current HashTableEntry object
     * */
    int hashCode();
}
