package hash_table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides the implementation for the HashTable.
 * The references to the entries elements in the hashtable are persisted in an array (that serves as the buckets). The size of the array
 * (number of buckets) is governed by the passed capacity. In case the capacity is not passed during instantiation, the default used is 2000.
 * Based on the hash value of the keys, elements are mapped to one of the buckets (array indices) and the relevant hash entry is
 * stored in that bucket.
 * To handle collisions, this implementation relies on the concept of chaining. A node class holding the key, value
 * and a pointer to the key value pair in the bucket has been defined to be able to implement the required chaining mechanism.
 *
 * Details regarding the implementation of these approaches can be found in the JavaDocs and comments associated with
 * the relevant methods of this class.
 *
 * */

public class HashTableImpl<K, V> implements MapADT<K, V> {

    private static final int DEFAULT_CAPACITY = 1000;
    private Node<K, V>[] mapElements;
    private int capacity;
    private Set<K> keySet;

    /**
     * Create a HashTableImpl object with default capacity of 2000
     * */
    public HashTableImpl(){
        this.initialize(DEFAULT_CAPACITY);
    }

    /**
     * Create a HashTableImpl object with the specified capacity
     *
     * @param capacity The desired capacity of the HashTable
     * */
    public HashTableImpl(int capacity){
        this.initialize(capacity);
    }

    private void initialize(int capacity){
        mapElements = new Node[capacity];
        this.keySet = new HashSet<K>();
        this.capacity = capacity;
    }

    /**
     * This method maps the key element to one of the buckets within the HashTable.
     * The algorithm uses the hashCode function of the passed key object to map keys to buckets.
     *
     * The formula used for this hash function is : Bucket index = Absolute value of hash of the key % num_buckets
     *
     * @param key The key to be mapped to a bucket
     * @return The index of the bucket for the said key
     * */
    private int hashFunction(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % this.capacity;
    }

    /**
     * The number is determined by retrieving the size of the keySet of this hashmap. The operation uses the size()
     * function of java.util.Set for this purpose and hence executes in constant time O(1) - Note that this is because
     * java.util.Set.size() executes in constant time
     *
     * @return The number distinct key-value pairs that are currently present in the HashTable
     * */
    @Override
    public int size() {
        return this.keySet.size();
    }


    /**
     * The number is determined by retrieving the size of the keySet of this hashmap. The operation uses the size()
     * function of java.util.Set for this purpose and hence executes in constant time O(1) - Note that this is because
     * java.util.Set.size() executes in constant time
     *
     * @return A boolean value indicating if the HashTable is empty
     * */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * The operation uses the contains(K element) function of java.util.Set for this purpose and hence executes
     * in constant time O(1) - Note that this is because java.util.Set.contains(K element) executes in constant time
     *
     * @return A boolean value indicating if a key value pair with the passed key exists in the HashTale
     * */
    @Override
    public boolean containsKey(K key) {
        return this.keySet.contains(key);
    }


    /**
     * Get the value associated with the provided key from the HashTable
     *
     * @param key The key whose associated value needs to be retrieved
     * @return The value associated with the key. null is returned if the key is not present in the HashTable
     * */
    @Override
    public V get(K key) {
        Node<K, V> e;
        return (e = this.getNode(hashFunction(key), key)) == null ? null : e.value;
    }

    /**
     * Get the value associated with the provided key from the HashTable. If the key does not exist, return the passed default value
     *
     * @param key The key whose associated value needs to be retrieved
     * @param defaultValue The value to be returned in case the key does not exist in the HashTable
     * @return The value associated with the key. defaultValue is returned if the key is not present in the HashTable
     * */
    @Override
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value != null ? value: defaultValue;
    }

    /**
     * This method adds a provided key-value pair into the HashTable. If an entry with the same key already exists
     * in the HashTable, the same is overwritten with the newer value. Workflow of this function is as follows:
     *
     * 1. Compute the bucket into which the key-value pair should be stored
     * 2. If no entry exists in the said bucket, simply add a new node with the entry in the relevant bucket
     * 3. If an entry exits in the bucket, we enter workflow a or b as listed below:
     *    a. The key is already present in the keySet:  In this case, locate the node with the said key in the chain and replace the value
     *    b. The key is not present in the keySet: In this case, add a new node with the new entry at the end of the chain.
     *
     * @param key The key whose associated value needs to be retrieved
     * @param value The value to be returned in case the key does not exist in the HashTable
     * @return The value added into the HashTable
     * */
    @Override
    public V put(K key, V value) {
        int hashCode = hashFunction(key);
        Node<K, V> newNode = new Node<>(key, value, null);

        if(this.mapElements[hashCode] == null)
            /*
            * If the corresponding element in array is empty, simply add the new node at the relevant index
            * */
            this.mapElements[hashCode] = newNode;
        else{
            if(this.keySet.contains(key)){
                /*
                 * If the keySet contains the key being added, it means that an entry with the key already exists in the
                 * chain stored at the relevant array index.
                 * In this case, locate the node with the key in the chain and replace the value in the node
                 * */
                getNode(hashCode, key).setValue(value);
            } else{
                /*
                 * If the keySet does not contain the key being added, it means that an entry with the key does not exist in the
                 * chain stored at the relevant array index.
                 * In this case, add the new node with the entry at the end of the chain
                 * */
                getLastNode(hashCode).next = newNode;
            }
        }
        /*
        * Add the newly added key to the keySet to enable subsequent tracking
        * */
        this.keySet.add(key);
        return value;
    }

    /**
     * Get the keySet from the HashTable
     *
     * @return This method returns a set containing all the keys in the HashTable
     * */
    @Override
    public Set<K> keySet() {
        return this.keySet;
    }

    /**
     * This method returns the node containing the Entry corresponding to the provided key
     *
     * @param hash This parameter specifies the index of the bucket that the key maps to
     * @param key The key of the entry to be retrieved
     *
     * @return The node containing the required key value pair. If a node with the passed key does not exist in the passed bucket, null is returned
     * */
    private Node<K, V> getNode(int hash, K key) {

        if(this.mapElements[hash] == null)
            return null;

        Node<K, V> node = this.mapElements[hash];

        while(node != null){
            if(node.key.equals(key))
                return node;
            node = node.next;
        }
        return null;
    }

    /**
     * This method returns the last node in the node chain stored at the bucket identified by the passed index
     *
     * @param hash This parameter specifies the index of the bucket that the key maps to
     *
     * @return The last node in the chain of nodes sotred in the bucket
     * */
    private Node<K, V> getLastNode(int hash){

        Node<K, V> node = this.mapElements[hash];
        if(node == null)
            return null;
        while(node.next != null){
            node = node.next;
        }
        return node;
    }

    /**
     * Get an iterator over the keys in the HashTable
     *
     * @return An iterator over all the keys of the HashTable. Note that the ordering of the keys is not maintained
     * */
    @Override
    public Iterator<K> keys() {
        return this.keySet().iterator();
    }

    /**
     * Get an iterator over the values in the HashTable
     *
     * @return An iterator over all the values of the HashTable. Note that the ordering of the values is not maintained
     * */
    @Override
    public Iterator<V> values() {
        return getEntries().stream().map(HashTableEntry::getValue).collect(Collectors.toList()).iterator();
    }

    /**
     * Get an iterator over the entries in the HashTable
     *
     * @return An iterator over all the HashTableEntries of the HashTable. Note that the ordering of the HashTableEntries is not maintained
     * */
    @Override
    public Iterator<HashTableEntry<K, V>> entries() {
        return getEntries().iterator();
    }

    /**
     * @return A list with all the HashTableEntries of the HashTable. Note that the ordering of the HashTableEntries is not maintained
     * */
    private List<HashTableEntry<K, V>> getEntries(){
        List<HashTableEntry<K, V>> entries = new ArrayList<>();
        for(Node<K, V> n: this.mapElements){
            while(n != null){
                entries.add(n);
                n = n.next;
            }
        }
        return entries;
    }
}
