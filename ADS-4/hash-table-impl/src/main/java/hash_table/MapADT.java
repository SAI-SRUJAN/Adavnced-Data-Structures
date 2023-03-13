package hash_table;

import java.util.Iterator;
import java.util.Set;

public interface MapADT<K, V> {

    /**
     * @return The number of distinct key value pairs in the Map
     * */
    int size();

    /**
     * @return Boolean value indicating if the map is empty
     * */
    boolean isEmpty();

    /**
     * @param key The key to be looked up in the Map
     * @return Boolean value indicating if the given key is present in the Map
     * */
    boolean containsKey(K key);

    /**
     * @param key The key to be looked up in the Map
     * @return The value associated with the key in the Map
     * */
    V get(K key);

    /**
     * @param key The key to be looked up in the Map
     * @param defaultValue The default value to be returned if the key does not exist in the Map
     * @return The value associated with the key in the Map. If the value does not exist, the passed default value is returned
     * */
    V getOrDefault(K key, V defaultValue);

    /**
     * @param key The key to be looked up in the Map
     * @param value The value associated with the key
     * @return The value added into the Map
     * */
    V put(K key, V value);

    /**
     * @return The iterator over all keys in the HashTable
     * */
    Iterator<K> keys();

    /**
     * @return The iterator over all values in the HashTable
     * */
    Iterator<V> values();

    /**
     * @return The iterator over all Entries in the HashTable
     * */
    Iterator<HashTableEntry<K, V>> entries();

    /**
     * @return The set containing all keys in the HashTable
     * */
    Set<K> keySet();
}
