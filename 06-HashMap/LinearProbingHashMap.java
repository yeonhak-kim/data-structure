import java.util.*;

/**
 * Your implementation of a LinearProbingHashMap.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903170274
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[initialCapacity];
        size = 0;
    }

    /**
     * Converts key to hashcode.
     *
     * @param key the key
     * @return int hashcode
     */
    private int keyToHashCode(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Either key or value is null.");
        }
        // Resizing backing array without checking duplicate key.
        if (((double) (size + 1) / (double) (table.length)) > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        // Ideal location of the key
        int hashCode = keyToHashCode(key);

        // Ideal location is empty. Just put.
        if (table[hashCode] == null) {
            table[hashCode] = new LinearProbingMapEntry<K, V>(key, value);
            size++;
            return null;
        }
        // clock will only occur once to catch the first DEL
        boolean clock = true;
        // indexDel will catch the first occurrence of DEL
        int indexDel = 0;

        // Linear Probing (Collision)
        for (int probe = 0; probe < table.length; probe++) {
            // If null, terminate the search
            if (table[hashCode] == null) {
                break;
            }
            // Check if it is DEL, treat DEL as null but catch the first index
            if (table[hashCode].isRemoved()) {

                // Catching first DEL, turn off clock after catch
                if (clock) {
                    indexDel = hashCode;
                    clock = false;
                }

            // Check for duplicate key and overwrite value
            } else if ((table[hashCode].getKey()).equals(key)) {
                V oldValue = table[hashCode].getValue();
                table[hashCode].setValue(value);
                return oldValue;
            }

            hashCode = (hashCode + 1) % table.length; // Hash code probes by 1
        }

        /* If clock is false, that means DEL has been caught.
           DEL should always be encountered first than null.
           So, put entry data to DEL location. If clock
           has not been used, then put the entry data to
           first null location. */

        if (!clock) {
            table[indexDel] = new LinearProbingMapEntry<K, V>(key, value);
            size++;
            return null;
        } else {
            table[hashCode] = new LinearProbingMapEntry<K, V>(key, value);
            size++;
            return null;
        }
    }


    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }

        // Ideal location of key
        int hashCode = keyToHashCode(key);

        // If ideal location is null, key does not belong to map
        if (table[hashCode] == null) {
            throw new NoSuchElementException("Key is not in the map.");

        // Linear Probing (Collision)
        } else {

            for (int probe = 0; probe < table.length; probe++) {
                if (table[hashCode] == null) {
                    break;
                }

                // If data is found, remove and return according
                // value.
                if (!table[hashCode].isRemoved()) {
                    if (table[hashCode].getKey().equals(key)) {
                        table[hashCode].setRemoved(true);
                        size--;
                        return table[hashCode].getValue();
                    }
                }
                hashCode = (hashCode + 1) % table.length;
            }
        }
        throw new NoSuchElementException("Key is not in the map.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        // Ideal location of key
        int hashCode = keyToHashCode(key);

        if (table[hashCode] != null) {
            // If key is found and entry is not marked as DEL,
            // retrieve the value.
            if ((table[hashCode].getKey()).equals(key)
                    && !table[hashCode].isRemoved()) {
                return table[hashCode].getValue();

            } else {
                // Linear Probing (Collision)
                for (int probe = 1; probe < table.length; probe++) {
                    if (table[hashCode] != null) {
                        if ((table[hashCode].getKey()).equals(key)
                                && !table[hashCode].isRemoved()) {
                            return table[hashCode].getValue();
                        }
                    }
                    hashCode = (hashCode + 1) % table.length;
                }

                throw new NoSuchElementException("The key "
                        + "is not in the map.");
            }

        } else {
            throw new NoSuchElementException("The key "
                    + "is not in the map.");
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }
        // Ideal location of key
        int hashCode = keyToHashCode(key);

        if (table[hashCode] != null) {
            // If key is found and entry is not marked as DEL,
            // retrieve the value.
            if ((table[hashCode].getKey()).equals(key)
                    && !table[hashCode].isRemoved()) {
                return true;
            } else {
                // Linear probing (Collision)
                for (int probe = 1; probe < table.length; probe++) {
                    if ((table[hashCode]) == null) {
                        break;
                    }
                    if ((table[hashCode].getKey()).equals(key)
                            && !table[hashCode].isRemoved()) {
                        return true;
                    }
                    hashCode = (hashCode + 1) % table.length;
                }
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> returnSet = new HashSet<>();

        int counter = 0;
        for (LinearProbingMapEntry<K, V> e : table) {
            if (e != null) {
                if (!e.isRemoved()) {
                    returnSet.add(e.getKey());
                    counter++;
                }
            }
            if (counter == size) {
                break;
            }
        }
        return returnSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> returnList = new ArrayList<>();

        int counter = 0;
        for (LinearProbingMapEntry<K, V> e : table) {
            if (e != null) {
                if (!e.isRemoved()) {
                    returnList.add(e.getValue());
                    counter++;
                }
            }
            if (counter == size) {
                break;
            }
        }
        return returnList;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     * number of items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length is less"
                    + "than then the number of items.");
        }

        LinearProbingMapEntry<K, V>[] oldTable = table;
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[length];

        int hashCode = 0;
        int counter = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                if (!oldTable[i].isRemoved()) {
                    hashCode = keyToHashCode(oldTable[i].getKey());

                    // Must check for collision!!
                    // If collision occurs, must linear probe!!
                    while (table[hashCode] != null) {
                        hashCode = (hashCode + 1) % table.length;
                    }
                    table[hashCode] = oldTable[i];
                    counter++;
                }

                if (counter == size) {
                    break;
                }
            }
        }
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the initial capacity and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[])
                new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
