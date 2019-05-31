import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Hash Table implementation of interface HashTableADT<K, V>
 * 
 * @author Khai Bin Woon
 *
 * @param <K> generic type key
 * @param <V> generic type value
 */
public class HashTable<K, V> implements HashTableADT<K, V> {

    /**
     * A class to store the values as nodes
     * 
     * @author Khai Bin Woon
     *
     * @param <K> generic type key
     * @param <V> generic type value
     */
    protected class ListNode {
        public K key;
        public V value;

        public ListNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /*
     * Instance variables and constructors
     */
    private ArrayList<ListNode>[] bucket;
    private int arraySize;
    private double loadFactor;
    private int itemCount = 0;

    public HashTable(int initialCapacity, double loadFactor) {
        arraySize = initialCapacity;
        this.loadFactor = loadFactor;
        bucket = new ArrayList[initialCapacity];
    }

    /**
     * Determines the index using hashFunction() that uses hashCode() If there is a duplicate key,
     * overwrites the previous value of the key and returns the value
     * 
     * @param key : The key that goes into the hashtable
     * @param value: The Value associated with the key
     * @return previous value associated with the key, null if not mapped, throws
     *         NullPointerException if key is null
     */
    @Override
    public V put(K key, V value) {

        if (key == null) {
            throw new NullPointerException();
        }
        expandBucket(); // checks if need to expand

        int bucketIndex = hashFunction(key); // hashing

        if (bucket[bucketIndex] == null) { // create a new bucket if there is no bucket at the index
            bucket[bucketIndex] = new ArrayList<ListNode>();
        }
        int previousIndex = lookup(bucket[bucketIndex],key);                                                           // check
        V previousValue = null;

        if (previousIndex == -1) { // if not a duplicate key
            bucket[bucketIndex].add(new ListNode(key, value));
            itemCount++;
        } else {
            previousValue = bucket[bucketIndex].get(previousIndex).value;
            bucket[bucketIndex].set(previousIndex, new ListNode(key, value));
        }


        return previousValue; // previousValue is changed from null only if there is duplicate
    }

    /**
     * Clear the hashtable of all its contents
     */
    @Override
    public void clear() {
        bucket = new ArrayList[arraySize];
        itemCount = 0;
    }

    /**
     * The get function returns a value based on a Key without removing it from the table
     * 
     * @param key: The key for which the value is returned
     * @return The value associated with the key, else throws NoSuch Element Exception
     */
    @Override
    public V get(K key) {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        V value;
        int bucketIndex = hashFunction(key);
        int elementIndex = lookup(bucket[bucketIndex],key);;                                                              //check

        if (elementIndex == -1) { // if there is no mapping
            throw new NoSuchElementException();
        } else {
            value = bucket[bucketIndex].get(elementIndex).value;
        }

        return value; // would not return null supposedly unless value is null
    }

    /**
     * Checks if the hashtable is empty
     * 
     * @return true : if Empty, else False
     */
    @Override
    public boolean isEmpty() {
        return itemCount == 0;
    }

    /**
     * Removes key-value pair based on the key entered
     * 
     * @param key: Key of the entry to be removed
     * @return value: Value of the key-value pair removed, null if key did not have a mapping
     * @throws NullPointerException if key is null
     */
    @Override
    public V remove(K key) {

        if (isEmpty()) {
            return null;
        }

        V value;
        int bucketIndex = hashFunction(key);
        if (bucket[bucketIndex] == null) { // if there is no bucket
            return null;                   // prevent NPE
        }

        int elementIndex = lookup(bucket[bucketIndex],key);                                            //check

        if (elementIndex == -1) { // there is no mapping for the key
            return null;
        } else {
            value = bucket[bucketIndex].remove(elementIndex).value;
            itemCount--;
        }

        return value;
    }

    /**
     *
     * @return: The total number of entries in the hashtable
     */
    @Override
    public int size() {
        return itemCount;
    }

    /**
     * modulus the hashCode() for the key with the arraySize so that the index always stays within
     * the range of arraySize but not equal to.
     * 
     * @param key for calculation
     * @return the index for the hash table
     */
    private int hashFunction(K key) {
        return (key.hashCode() % arraySize + arraySize) % arraySize;
    }

    /**
     * Checks if the the ArrayList[] needs to expand, if there isn't a need, this method does
     * nothing In-line comments details the steps
     */
    private void expandBucket() {
        if (itemCount / arraySize > loadFactor) { // if need to expand

            int oldSize = arraySize;
            ArrayList<ListNode>[] old = bucket;  // keep the old array of buckets aside
            arraySize = arraySize * 10;                  // enlarges the array size immediately 
                                                        // for the new bucket hashing
            clear();                                     // get a new array of buckets

            for (int i = 0; i < oldSize; i++) {     // for every bucket in the old array
                if (old[i] != null) {                   // if there is a bucket
                    for (ListNode node : old[i]) { // go through every item in the old bucket
                        put(node.key, node.value);      // and put it in the new bucket(rehashing)
                    }
                }
            }

        }
    }
    
    /**
     * My version of the ArrayList indexOf, instead of going through the list comparing ListNode
     * objects, compare the key instead.
     * @param list - ArrayList of type ListNode
     * @param key - key of the value to be found
     * @return index of key if found, -1 if not found
     */
    private int lookup(ArrayList<ListNode> list, K key) {
        int index = 0;
        for (ListNode node : list) {
            if (node.key.equals(key)) {
                return index;
            }
            index++;
        }
        return -1;
        
    }
    public int getSize() {
        return arraySize;
    }

}
