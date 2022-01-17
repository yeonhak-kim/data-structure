import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903170274
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data set is empty.");
        }

        backingArray = (T[]) new Comparable[data.size() * 2 + 1];

        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("Data element is null.");
            }
            backingArray[size + 1] = item;
            size++;
        }

        for (int i = size / 2; i > 0; i--) {
            heapify(i);
        }
    }

    /**
     * Helper method for heapifying the element up to the root
     * if the element is smaller than its direct parent.
     * This method basically compares every single element in
     * the data and organizes it into a heap.
     * @param i index of the element where it might need heapify.
     */
    private void heapify(int i) {
        int leftChild = i * 2;
        int rightChild = i * 2 + 1;
        int parent = i;
        // min data goes up the heap
        if (leftChild <= size) {
            if (backingArray[leftChild].
                    compareTo(backingArray[parent]) < 0) {
                parent = leftChild;
            }
        }

        if (rightChild <= size) {
            if (backingArray[rightChild].
                    compareTo(backingArray[parent]) < 0) {
                parent = rightChild;
            }
        }

        if (parent != i) {
            // create temp data which represents child data
            T temp = backingArray[parent];
            // swap parent data with child data(either left or right)
            backingArray[parent] = backingArray[i];
            backingArray[i] = temp;

            heapify(parent);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        checkCapacity();
        size++;
        backingArray[size] = data;

        int index = size;
        // This while loops directly compares the data being added to its
        // direct parent and so on until it reaches at the maximum point.
        while (index > 1 && (backingArray[index]).
                compareTo(backingArray[index / 2]) < 0) {

            T temp = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = temp;

            index = index / 2;
        }
    }

    /**
     * Helper method for add(T data) where
     * it checks the size of the backingArray
     * and resizes accordingly.
     */
    private void checkCapacity() {
        if (backingArray.length == size + 1) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Heap is empty.");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        heapify(1);
        return temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
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
     * Returns the size of the heap.
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
