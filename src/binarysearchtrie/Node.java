package src.binarysearchtrie;

import src.address.GatewayAddress;

/**
 * Represents a node in the binary search trie. Nodes can be empty or contain a
 * gateway address.
 *
 * @author silval
 */
public class Node {
    // The left child of this node.
    private Node left;
    // The right child of this node.
    private Node right;
    // Is this node empty?
    private boolean isEmpty;
    // The route this node contains if it is not empty.
    private GatewayAddress value;

    /**
     * Default constructor. Makes node with no children.
     */
    public Node() {
        this(null, null);
    }

    /**
     * Constructor for a Node.
     *
     * @param left  The left child of this node.
     * @param right The right child of this node.
     */
    @SuppressWarnings("WeakerAccess")
    public Node(@SuppressWarnings("SameParameterValue") Node left, @SuppressWarnings
                                                                           ("SameParameterValue")
    Node right) {
        this.left = left;
        this.right = right;
        this.isEmpty = true;
        this.value = null;
    }

    /**
     * Get the left child of this node.
     *
     * @return The left child.
     */
    public Node getLeft() {
        return this.left;
    }


    /**
     * Set the left child of this node.
     *
     * @param newChild The new left child of this node.
     * @return The new child.
     */
    public Node setLeft(Node newChild) {
        if (this.left == null) {
            this.left = newChild;
        } else {
            System.err.println("ERROR: Node already has left child! Nothing " +
                                       "added.");
        }
        return this.left;
    }

    /**
     * Get the right child of this node.
     *
     * @return The right child.
     */
    public Node getRight() {
        return this.right;
    }

    /**
     * Set the right child of this node.
     *
     * @param newChild The new right child of this node.
     * @return The new child.
     */
    public Node setRight(Node newChild) {
        if (this.right == null) {
            this.right = newChild;
        } else {
            System.err.println("ERROR: Node already has right child! Nothing added.");
        }
        return this.right;
    }

    /**
     * Return the src.tables.RoutingTableEntry stored in this node.
     *
     * @return The table entry.
     */
    public GatewayAddress getValue() {
        return value;
    }

    /**
     * Set the value of this node to a src.tables.RoutingTableEntry.
     *
     * @param value The value to set
     */
    public void setValue(GatewayAddress value) {
        this.value = value;
        this.isEmpty = false;
    }

    /**
     * Determine if this node is empty or not.
     *
     * @return the isEmpty
     */
    public boolean isEmpty() {
        return isEmpty;
    }

}
