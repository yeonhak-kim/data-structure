import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903440143
 *
 * Collaborators: NA
 *
 * Resources: NA
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or "
                    + "more than the array size of " + size);
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
            head.setPrevious(null);
            tail = newNode;
            tail.setNext(null);
        } else if (index == 0) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            //head.setPrevious(null);
        } else if (index == size) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
            //tail.setNext(null);
        }

        if (size != 0 && index != size && index != 0) {
            if (index < (size / 2)) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < (index - 1); i++) {
                    curr = curr.getNext();
                }
                DoublyLinkedListNode<T> oneAfter = curr.getNext();
                curr.setNext(newNode);
                newNode.setPrevious(curr);
                newNode.setNext(oneAfter);
                oneAfter.setPrevious(newNode);
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = 0; i < (size - index - 1); i++) {
                    curr = curr.getPrevious();
                }
                DoublyLinkedListNode<T> oneAfter = curr.getPrevious();
                curr.setPrevious(newNode);
                newNode.setNext(curr);
                newNode.setPrevious(oneAfter);
                oneAfter.setNext(newNode);
            }
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or "
                    + "more or equal to the array size of " + size);
        }
        DoublyLinkedListNode<T> curr = head;
        if (size == 1) {
            DoublyLinkedListNode<T> temp = curr;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        }
        if (index == 0) {
            DoublyLinkedListNode<T> temp = head;
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp.getData();
        }
        if (index == size - 1) {
            DoublyLinkedListNode<T> temp = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp.getData();
        }
        if (index < (size / 2)) {
            for (int i = 0; i < (index - 1); i++) {
                curr = curr.getNext();
            }
            DoublyLinkedListNode<T> temp = curr.getNext();
            DoublyLinkedListNode<T> oneAfter = curr.getNext().getNext();
            curr.setNext(oneAfter);
            oneAfter.setPrevious(curr);
            size--;
            return temp.getData();
        } else {
            curr = tail;
            for (int i = 0; i < (size - index - 2); i++) {
                curr = curr.getPrevious();
            }
            DoublyLinkedListNode<T> temp = curr.getPrevious();
            DoublyLinkedListNode<T> oneAfter = curr.getPrevious().getPrevious();
            curr.setPrevious(oneAfter);
            oneAfter.setNext(curr);
            size--;
            return temp.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("List is empty");
        }
        return removeAtIndex(0);
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
            throw new NoSuchElementException("List is empty");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or "
                    + "more or equal to the array size of " + size);
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            if (index < (size / 2)) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                return curr.getData();
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = 0; i < (size - index - 1); i++) {
                    curr = curr.getPrevious();
                }
                return curr.getData();
            }
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
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Linked List is empty");
        }
        DoublyLinkedListNode<T> curr = tail;
        if (curr.getData().equals(data)) {
            removeFromBack();
            return curr.getData();
        } else {
            for (int i = size - 1; i > 0; i--) {
                curr = curr.getPrevious();
                if (curr.getData().equals(data)) {
                    removeAtIndex(i - 1);
                    return curr.getData();
                }
            }
        }
        throw new NoSuchElementException("Data is not found");
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
