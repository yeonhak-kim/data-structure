import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        for (T element : data) {
            add(element);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            root = addChild(root, data);
        }
    }

    /**
     * Helper method for add(T data).
     * @param curr the current node
     * @param data the data to add
     * @return BSTNode
     */
    private BSTNode<T> addChild(BSTNode<T> curr, T data) {
        // Base case. Recursion terminates when curr == null.
        // Then returns BSTNode which then being added at last.
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        }
        // If data is greater than curr data, go right, else go left.
        // If any case the data is duplicate, then do not add.
        if (curr.getData().compareTo(data) < 0) {
            curr.setRight(addChild(curr.getRight(), data));
            return curr;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addChild(curr.getLeft(), data));
            return curr;
        // curr data == data then do not add. Just return curr.
        } else {
            return curr;
        }
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *      * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its
     * child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data.
     * You MUST use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Predecessor : Maximum value among left children.
     * Succeossor: Minimum value among right children.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        BSTNode<T> toRemove = new BSTNode<T>(null);
        root = removeProcess(root, data, toRemove);
        return toRemove.getData();
    }

    /**
     * Helper method for remove(T data).
     *
     * @param curr the current node
     * @param data the data to remove
     * @param toRemove BSTNode that stores the data of interest
     * @return BSTNode
     */
    private BSTNode<T> removeProcess(BSTNode<T> curr,
                                     T data, BSTNode<T> toRemove) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is "
                    + "not in the tree");
        }
        // Data found.
        // Base Case. Recursion terminates according to each case scenario.
        if (data.equals(curr.getData())) {
            toRemove.setData(curr.getData());
            size--;
            // 1) BSTNode has no child.
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            // 2) BSTNode has one child.
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            // 3) BSTNode has two children.
            } else {
                BSTNode<T> tempNode = new BSTNode<T>(null);
                curr.setLeft(predecessor(curr.getLeft(), tempNode));
                curr.setData(tempNode.getData());
            }
        // Searching data.
        // Recursion. Traversing the tree.
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeProcess(curr.getRight(), data, toRemove));
        } else {
            curr.setLeft(removeProcess(curr.getLeft(), data, toRemove));
        }
        // Return value for case 3.
        return curr;
    }

    /**
     * Only called from the removeProcess method.
     * Used for in case 3 where the removing BSTNode has 2 children.
     * @param curr the node being worked on
     * @param tempNode holds data to set to the remove nodes spot
     * @return BSTNode
     */
    private BSTNode<T> predecessor(BSTNode<T> curr, BSTNode<T> tempNode) {
        if (curr.getRight() == null) {
            tempNode.setData(curr.getData());
            return curr.getLeft();
        }
        curr.setRight(predecessor(curr.getRight(), tempNode));
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
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
            throw new IllegalArgumentException("Data is null");
        }
        BSTNode<T> curr = root;
        return getProcess(root, data);
    }

    /**
     * Helper method for get(T data).
     * Traverse through the tree and search for data.
     * @param curr the current node
     * @param data the data to get
     * @return data in the tree equal to the parameter
     */
    private T getProcess(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not found in the tree.");
        }
        // Data found.
        if (curr.getData().equals(data)) {
            return curr.getData();
            // Traverse Right.
        } else if (curr.getData().compareTo(data) < 0) {
            return getProcess(curr.getRight(), data);
            // Traverse Left.
        } else { //(curr.getData().compareTo(data) > 0)
            return getProcess(curr.getLeft(), data);
        }
    }
    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        BSTNode<T> curr = root;
        return containsProcess(root, data);
    }

    /**
     * Helper method for contains(T data)
     * @param curr the current node
     * @param data the data to add
     * @return true or false corresponding to whether the
     * tree contains the given data
     */
    private boolean containsProcess(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData() == data) {
            return true;
        } else if (curr.getData().compareTo(data) < 0) {
            return containsProcess(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return containsProcess(curr.getLeft(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<T>();
        if (size == 0) {
            return list;
        }
        return preorderProcess(list, root);
    }

    /**
     * Helper method for preorder()
     * [Visit data] -> [Left recursion] -> [Right recursion]
     * @param list linkedlist containing the nodes in the tree
     * @param curr the data that is currently looked at
     * @return linkedlist containing the nodes in the tree
     */
    private List<T> preorderProcess(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return list;
        }
        list.add(curr.getData());
        list = preorderProcess(list, curr.getLeft());
        return preorderProcess(list, curr.getRight());
    }

    /**
     * Generate a in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<T>();
        if (size == 0) {
            return list;
        }
        return inorderProcess(list, root);
    }

    /**
     * Helper method for inorder()
     * [Left recursion] -> [Visit data] -> [Right recursion]
     * @param list linkedlist containing the nodes in the tree
     * @param curr the data that is currently looked at
     * @return linkedlist containing the nodes in the tree
     */
    private List<T> inorderProcess(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return list;
        }
        list = inorderProcess(list, curr.getLeft());
        list.add(curr.getData());
        return inorderProcess(list, curr.getRight());
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<T>();
        if (size == 0) {
            return list;
        }
        return postorderProcess(list, root);
    }

    /**
     * Helper method for postorder()
     * [Left recursion] -> [Right recursion] -> [Visit data]
     * @param list linkedlist containing the nodes in the tree
     * @param curr the data that is currently looked at
     * @return linkedlist containing the nodes in the tree
     */
    private List<T> postorderProcess(List<T> list, BSTNode<T> curr) {
        if (curr == null) {
            return list;
        }
        list = postorderProcess(list, curr.getLeft());
        list = postorderProcess(list, curr.getRight());
        list.add(curr.getData());
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new LinkedList<T>();
        if (size == 0) {
            return list;
        }
        Queue<BSTNode> queue = new LinkedList<BSTNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> node = queue.remove();
            list.add(node.getData());
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return heightProcess(root);
    }

    /**
     * Helper method for height()
     * @param curr the node currently being worked on
     * @return int the height of the tree
     */
    private int heightProcess(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return Math.max(
                heightProcess(curr.getLeft()),
                    heightProcess(curr.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     **
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *                 50
     *             /        \
     *           25         75
     *         /    \
     *        12    37
     *       /  \    \
     *     10   15   40
     *         /
     *       13
     * findPathBetween(13, 40) should return the list [13, 15, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
        if (!containsProcess(root, data1) || !containsProcess(root, data2)) {
            throw new NoSuchElementException("Data does not exist.");
        }

        LinkedList<T> list = new LinkedList<>();
        if (data1 == data2) {
            list.addFirst(data1);
        } else {
            BSTNode<T> commonAnc = deepestCommonAncestor(root, data1, data2);
            list.addFirst(commonAnc.getData());

            recordFront(list, commonAnc, data1);
            recordBack(list, commonAnc, data2);
        }
        return list;
    }

    /**
     * Helper method for findPathBetween(T data1, T data2)
     * The method traverse through the BST
     * and finds the deepest common ancestor.
     * DCA(Deepest Common Ancestor) appears at the first
     * mismatch in children's BSTNode.
     * @param curr the node currently being worked on
     * @param data1 data from
     * @param data2 data to
     * @return Deepest common ancestor
     */
    private BSTNode<T> deepestCommonAncestor(BSTNode<T> curr,
                                             T data1, T data2) {
        boolean a = data1.compareTo(curr.getData()) > 0
                && data2.compareTo(curr.getData()) < 0;
        boolean b = data1.compareTo(curr.getData()) < 0
                && data2.compareTo(curr.getData()) > 0;
        if (a || b || data1.equals(curr.getData())
                || data2.equals(curr.getData())) {
            return curr;
        } else if (data1.compareTo(curr.getData()) > 0) {
            return deepestCommonAncestor(curr.getRight(), data1, data2);
        } else {
            return deepestCommonAncestor(curr.getLeft(), data1, data2);
        }
    }
    /**
     * Helper method for findPathBetween(T data1, T data2)
     * The method records each BSTNode in the path for specified data.
     * This method records the data at front.
     * @param list records data to this list
     * @param curr the node currently being worked on
     * @param data data being searched
     *
     */
    private void recordFront(LinkedList<T> list, BSTNode<T> curr, T data) {
        if (data.compareTo(curr.getData()) > 0) {
            list.addFirst(curr.getRight().getData());
            recordFront(list, curr.getRight(), data);
        } else if (data.compareTo(curr.getData()) < 0) {
            list.addFirst(curr.getLeft().getData());
            recordFront(list, curr.getLeft(), data);
        }
    }

    /**
     * Helper method for findPathBetween(T data1, T data2)
     * The method records each BSTNode in the path for specified data.
     * This method records the data at back.
     * @param list records data to this list
     * @param curr the node currently being worked on
     * @param data data being searched
     *
     */
    private void recordBack(LinkedList<T> list, BSTNode<T> curr, T data) {
        if (data.compareTo(curr.getData()) > 0) {
            list.addLast(curr.getRight().getData());
            recordBack(list, curr.getRight(), data);
        } else if (data.compareTo(curr.getData()) < 0) {
            list.addLast(curr.getLeft().getData());
            recordBack(list, curr.getLeft(), data);
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
    public BSTNode<T> getRoot() {
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
