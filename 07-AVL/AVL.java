import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Your implementation of an AVL.
 *
 * @author Yeonhak Kim
 * @version 1.0
 * @userid ykim713
 * @GTID 903170274
 *
 * Collaborators: NA
 *
 * Resources: NA
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("Data in element"
                        + " cannot be null");
            }
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            size++;
            root = new AVLNode<T>(data);
            root.setBalanceFactor(0);
            root.setHeight(1);
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * Helper method for add method. Adds recursively.
     *
     * @param curr current node being worked on
     * @param data the data to add
     * @return an AVLNode to be added
     */
    public AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            AVLNode<T> newNode = new AVLNode<T>(data);
            newNode.setHeight(heightHelper(newNode));
            newNode.setBalanceFactor(balanceFactorHelper(newNode));
            return newNode;
        }
        if (curr.getData().compareTo(data) < 0) {
            curr.setRight(addHelper(curr.getRight(), data));
            curr.setHeight(heightHelper(curr));
            curr.setBalanceFactor(balanceFactorHelper(curr));
            return rotate(curr);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
            curr.setHeight(heightHelper(curr));
            curr.setBalanceFactor(balanceFactorHelper(curr));
            return rotate(curr);
        } else {
            curr.setHeight(heightHelper(curr));
            curr.setBalanceFactor(balanceFactorHelper(curr));
            return rotate(curr);
        }
    }

    /**
     * checks for necessity of rotation and performs rotation
     * accordingly.
     * @param curr the current node to check
     * @return node a properly balanced node
     */
    private AVLNode<T> rotate(AVLNode<T> curr) {
        if (curr.getBalanceFactor() == 2) {
            if (curr.getLeft() != null
                    && curr.getLeft().getBalanceFactor() == -1) {
                curr = leftRightRotate(curr);
            } else {
                curr = rightRotate(curr);
            }
        } else if (curr.getBalanceFactor() == -2) {
            if (curr.getRight() != null
                    && curr.getRight().getBalanceFactor() == 1) {
                curr = rightLeftRotate(curr);
            } else {
                curr = leftRotate(curr);
            }
        }
        return curr;
    }

    /**
     * left rotation
     * @param curr the current node to rotate
     * @return a rotated node
     */
    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> right = curr.getRight();
        curr.setRight(right.getLeft());
        right.setLeft(curr);

        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactorHelper(curr));

        right.setHeight(heightHelper(right));
        right.setBalanceFactor(balanceFactorHelper(right));
        return right;
    }

    /**
     * right rotation
     * @param curr the current node to rotate
     * @return a rotated node
     */
    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> left = curr.getLeft();
        curr.setLeft(left.getRight());
        left.setRight(curr);

        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactorHelper(curr));

        left.setHeight(heightHelper(left));
        left.setBalanceFactor(balanceFactorHelper(left));
        return left;
    }

    /**
     * right-left rotation
     * @param curr the current node to rotate
     * @return a rotated node
     */
    private AVLNode<T> rightLeftRotate(AVLNode<T> curr) {
        curr.setRight(rightRotate(curr.getRight()));
        return leftRotate(curr);
    }

    /**
     * left-right rotation
     * @param curr the current node to rotate
     * @return a rotated node
     */
    private AVLNode<T> leftRightRotate(AVLNode<T> curr) {
        curr.setLeft(leftRotate(curr.getLeft()));
        return rightRotate(curr);
    }

    /**
     * updates height
     * @param node the node to get the height of
     * @return height of the node
     */
    private int heightHelper(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 0;
        } else if (node.getLeft() == null) {
            return 1 + node.getRight().getHeight();
        } else if (node.getRight() == null) {
            return 1 + node.getLeft().getHeight();
        }
        return 1 + Math.max(node.getLeft().getHeight(),
                node.getRight().getHeight());
    }

    /**
     * updates BF
     * @param node the node to get the balance factor of
     * @return int the balance factor
     */
    private int balanceFactorHelper(AVLNode<T> node) {
        if (node == null) {
            return 0;
        }
        return heightHelper(node.getLeft()) - heightHelper(node.getRight());
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        AVLNode<T> toRemove = new AVLNode<T>(null);
        root = removeHelper(root, data, toRemove);
        return toRemove.getData();
    }

    /**
     * Helper method for remove. Removes recursively.
     * @param curr the current node being worked on
     * @param data the data to remove
     * @param toRemove holds data to be returned by remove
     * @throws java.util.NoSuchElementException if data is not in the tree
     * @return BSTNode
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data,
                                    AVLNode<T> toRemove) {
        if (curr == null) {
            throw new java.util.NoSuchElementException(
                    "data is not in the tree");
        }
        if (data.equals(curr.getData())) {
            toRemove.setData(curr.getData());
            size--;
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null) {
                curr.setHeight(heightHelper(curr));
                curr.setBalanceFactor(balanceFactorHelper(curr));
                return rotate(curr.getLeft());
            } else if (curr.getLeft() == null) {
                curr.setHeight(heightHelper(curr));
                curr.setBalanceFactor(balanceFactorHelper(curr));
                return rotate(curr.getRight());
            } else {
                AVLNode<T> tempNode = new AVLNode<T>(null);
                curr.setRight(successor(curr.getRight(), tempNode));
                curr.setData(tempNode.getData());
            }
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, toRemove));
        } else {
            curr.setLeft(removeHelper(curr.getLeft(), data, toRemove));
        }
        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactorHelper(curr));
        return rotate(curr);
    }

    /**
     * Gets successor node
     * @param curr the current node being worked on
     * @param tempNode holds data to set to the remove nodes spot
     * @return BSTNode to reset above
     */
    private AVLNode<T> successor(AVLNode<T> curr, AVLNode<T> tempNode) {
        if (curr.getLeft() == null) {
            tempNode.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(successor(curr.getLeft(), tempNode));
        curr.setHeight(heightHelper(curr));
        curr.setBalanceFactor(balanceFactorHelper(curr));
        return rotate(curr);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        return getHelper(root, data);
    }

    /**
     * Helper method for get. Gets recursively.
     * @param curr the node currently being worked on
     * @param data the data to remove
     * @throws java.util.NoSuchElementException if data is not in the tree
     * @return data of type T
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is "
                    + "not in the tree");
        }
        if (data.equals(curr.getData())) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) > 0) {
            return getHelper(curr.getRight(), data);
        } else {
            return getHelper(curr.getLeft(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return containsHelper(root, data);
    }

    /**
     * Helper method for contain.
     * @param curr the node currently being worked on
     * @param data the data to check for containment
     * @return true if the data is in the tree
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        if (data.equals(curr.getData())) {
            return true;
        } else if (data.compareTo(curr.getData()) > 0) {
            return containsHelper(curr.getRight(), data);
        } else {
            return containsHelper(curr.getLeft(), data);
        }
    }


    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * In your BST homework, you worked with the concept of the predecessor, the
     * largest data that is smaller than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     *
     * This should NOT be used in the remove method.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        AVLNode<T> trackData = new AVLNode(null);
        AVLNode<T> dataNode = navigate(root, data, trackData);
        if (dataNode.getRight().getLeft() == null) {
            return dataNode.getData();
        } else {
            return predecessorHelper(
                    dataNode.getRight().getLeft()).getData();
        }
    }

    /**
     * Helper method for predecessor. Left subtree exist case.
     * @param curr the current node being worked on
     * @return BSTNode that is predecessor
     */
    private AVLNode<T> predecessorHelper(AVLNode<T> curr) {
        if (curr.getRight() == null) {
            return curr;
        } else {
            return predecessorHelper(curr.getRight());
        }
    }

    /**
     * Function to navigate to the node recursively
     * @param curr the node currently being worked on
     * @param data the data of the node to navigate to
     * @param dataNode the dataNode that contains
     *  predecessor upto "data" as a root and "AVLNode with target data" as
     *  a right child if the data exist in the tree.
     *
     *  So, predecessor method will always use navigate method to
     *  get to our target data node. While navigating, every time
     *  it navigates to right, it will update the root of dataNode
     *  to its current AVLNode before right shift occurs. At last,
     *  when it finds the target data, it will store that target data
     *  AVLNode to right child of dataNode. If data is not found, it
     *  will throw Exception.
     *
     *  Root is used for left subtree does not exist case.
     *
     *  Right child is used for left subtree exist case and we would
     *  have to recurse again to get the predecessor.
     *
     *  The point of this method is to navigate just once to get data
     *  for each case and use it according to the case we are dealing.
     *
     *         -Figure Description-
     * Start: Start with null.
     *    (null)
     *     / \
     *   null null
     *
     * Process: Keep updating predecessor while navigating.
     *  (updated node)
     *       /   \
     *    null    null
     *
     * End: With data found. Otherwise throw Exception.
     *  (updated node) --------> used for no left subtree case.
     *      /    \
     *    null  (target data node)
     *
     * @throws java.util.NoSuchElementException if data is not in the tree
     * @return AVLNode that contains predecessor upto "data" + dataNode
     */
    private AVLNode<T> navigate(AVLNode<T> curr, T data, AVLNode<T> dataNode) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data"
                    + " is not in the tree");
        }
        if (data.equals(curr.getData())) {
            dataNode.setRight(curr);
            return dataNode;
        } else if (data.compareTo(curr.getData()) > 0) {
            dataNode.setData(curr.getData());
            return navigate(curr.getRight(), data, dataNode);
        } else {
            return navigate(curr.getLeft(), data, dataNode);
        }
    }

    /**
     * Finds and retrieves the k-smallest elements from the AVL in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                50
     *              /    \
     *            25      75
     *           /  \     / \
     *          12   37  70  80
     *         /  \    \      \
     *        10  15    40    85
     *           /
     *          13
     * kSmallest(0) should return the list []
     * kSmallest(5) should return the list [10, 12, 13, 15, 25].
     * kSmallest(3) should return the list [10, 12, 13].
     *
     * @param k the number of smallest elements to return
     * @return sorted list consisting of the k smallest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > n, the number
     *                                            of data in the AVL
     */
    public List<T> kSmallest(int k) {
        if (k < 0) {
            throw new IllegalArgumentException("k is less than 0.");
        } else if (k > size) {
            throw new IllegalArgumentException("k is more than "
                    + "the number of data in the AVL.");
        }
        List<T> list = new ArrayList<>();
        return inorder(list, root, k);
    }

    /**
     * inorder method
     * @param list ArrayList containing the nodes in the tree
     * @param curr the data that is currently looked at
     * @param k k smallest number of elements
     * @return linkedlist containing the nodes in the tree
     */
    private List<T> inorder(List<T> list, AVLNode<T> curr, int k) {
        if (curr == null) {
            return list;
        }
        if (list.size() < k) {
            list = inorder(list, curr.getLeft(), k);
        }
        if (list.size() < k) {
            list.add(curr.getData());
        }
        if (list.size() < k) {
            return inorder(list, curr.getRight(), k);
        } else {
            return list;
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
