import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903170274
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        size = 0;
        backingArray = (T[]) (new Object[INITIAL_CAPACITY]);
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index must be in "
                    + "range of [0,size].");
        } else if (data == null) {
            throw new IllegalArgumentException("null cannot be added"
                    + " to the ArrayList.");
        } else {
            // Resize dummy array if the given array is already full.
            T[] resizedArray = (T[]) (new Object[backingArray.length]);
            if (size == backingArray.length) {
                resizedArray = (T[]) (new Object[2 * backingArray.length]);
            }

            // Adding at the tail.
            if (size == index) {

                // Array that is full(Need resizing action).
                if (size == backingArray.length) {
                    for (int i = 0; i < size + 1; i++) {
                        if (i == size) {
                            resizedArray[i] = data;
                        }
                        resizedArray[i] = backingArray[i];
                    }
                    backingArray = resizedArray;

                    // Resizing not needed. Amortized O(1).
                } else {
                    backingArray[index] = data;
                }

                // Adding anywhere inside. (resizedArray is already
                // resized accordingly from above).
            } else {
                // j keeps track of backingArray index.
                int j = 0;
                // i keeps track of resizedArray index.
                for (int i = 0; i < size + 1; i++) {
                    if (i == index) { // index where new data is added.
                        resizedArray[i] = data;
                    } else {
                        resizedArray[i] = backingArray[j];
                        j++;
                    }
                }
                backingArray = resizedArray;
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("null cannot be added"
                    + " to the ArrayList.");
        } else {
            T[] addedArray = null;
            if (size == backingArray.length) {
                // Doubled capacity and then allocate data accordingly.
                addedArray = (T[]) (new Object[2 * backingArray.length]);
                addedArray[0] = data; // Add at front.
                for (int i = 1; i < (size + 1); i++) {
                    addedArray[i] = backingArray[i - 1];
                }
            } else {
                // Resizing not needed.
                addedArray = (T[]) (new Object[backingArray.length]);
                addedArray[0] = data; // Add at front.
                for (int i = 1; i < (size + 1); i++) {
                    addedArray[i] = backingArray[i - 1];
                }
            }
            backingArray = addedArray;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("null cannot be added"
                    + " to the ArrayList.");
        } else {
            if (size == backingArray.length) {
                // Double capacity and then allocate data accordingly.
                T[] resizeArray = (T[]) (new Object[2 * backingArray.length]);
                for (int i = 0; i < size; i++) {
                    resizeArray[i] = backingArray[i];
                }
                backingArray = resizeArray;
            }
            // Resizing not needed. Amortized O(1).
            backingArray[size] = data;
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be in"
                    + " range of [0,size]");
        } else {
            T dataRemoved = null;
            if (index == (size - 1)) {
                dataRemoved = backingArray[index];
                backingArray[index] = null;
                size--;
            } else {
                T[] dummy = (T[]) (new Object[backingArray.length]);
                int j = 0;
                for (int i = 0; i < size; i++) {
                    if (i == index) {
                        dataRemoved = backingArray[i];
                    } else {
                        dummy[j] = backingArray[i];
                        j++;
                    }
                }
                backingArray = dummy;
                size--;
            }
            return dataRemoved;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("There is no element "
                    + "to be removed. ArrayList is empty.");
        } else {
            T[] dummy = (T[]) (new Object[backingArray.length]);
            T dataRemoved = backingArray[0];
            for (int i = 1; i < size; i++) {
                dummy[i - 1] = backingArray[i];
            }
            backingArray = dummy;
            size--;
            return dataRemoved;
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("There is no element "
                    + "to be removed. ArrayList is empty.");
        } else {
            T dataRemoved = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return dataRemoved;
        }
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be "
                    + "in range of [0,size)");
        } else {
            return backingArray[index];
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        boolean logic = false;
        if (size == 0) {
            logic = true;
        }
        return logic;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        T[] clearArray = (T[]) (new Object[INITIAL_CAPACITY]);
        backingArray = clearArray;
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
