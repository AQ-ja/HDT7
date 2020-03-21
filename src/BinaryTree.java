
/**
* Methods extracted from https://www.baeldung.com/java-binary-tree
* */

/**
 * A single BinaryTree class with an association as value
 * @param <E> the Association-type object
 */
public class BinaryTree<E> {
    /**
     * The containing value of the BinaryTree
     */
    private E value;
    /**
     * Left and Right children
     */
    private BinaryTree<E> left, right;

    /**
     * Constructs a new BinaryTree with null value and children
     */
    public BinaryTree()
    {
        value = null;
        left = right = null;
    }

    /**
     * Sets a new Association-type object as the value of the BinaryTree
     * @param value the Association-type object to add
     */
    public BinaryTree(E value)
    {
        this.value = value;
        setLeft(new BinaryTree<>());
        setRight(new BinaryTree<>());
    }

    /**
     * Constructs a new BinaryTree object with specified Association as value and non-null children
     * @param value the Association-type object
     * @param left the left BinaryTree child
     * @param right the right BinaryTree child
     */
    public BinaryTree(E value, BinaryTree<E> left, BinaryTree<E> right)
    {
        this(value);
        if(left != null) setLeft(left);
        if(right != null) setRight(right);
    }

    public BinaryTree<E> getLeft() { return left; }
    public BinaryTree<E> getRight() { return right; }


    /**
     * Adds a new left child BinaryTreeObject
     * @param newLeft the BinaryTree to add as a child
     */
    public void setLeft(BinaryTree<E> newLeft)
    {
        left = newLeft;
    }

    /**
     * Adds a new right child BinaryTreeObject
     * @param newRight the BinaryTree to add as a child
     */
    public void setRight(BinaryTree<E> newRight) {
        right = newRight;
    }

    /**
     * Get the value of the current BinaryTree
     * @return the Association of the current BinaryTree
     */
    public E value() { return value; }

    /**
     * Sets the value of the current BinaryTree
     * @param newValue the Association to put as value
     */
    public void setValue(E newValue) { value = newValue; }

    /**
     * Returns a boolean depending if the BinaryTree value is empty or not
     * @return true if it is empty, false if it is not
     */
    protected boolean isEmpty()
    {
        return value == null;
    }
}