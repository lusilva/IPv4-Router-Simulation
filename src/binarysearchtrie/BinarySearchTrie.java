package src.binarysearchtrie;

import src.address.DestinationAddress;
import src.address.GatewayAddress;
import src.address.IPv4Address;
import src.tables.RoutingTable;
import src.tables.RoutingTableEntry;

/**
 * Stores the binary search trie created from the router table.
 *
 * @author silval
 */
public class BinarySearchTrie {
    private Node root;
    private RoutingTable routingTable;

    /**
     * Default constructor, no arguments.
     */
    @SuppressWarnings("WeakerAccess")
    public BinarySearchTrie() {
        this.setRoot(new Node());
        this.setRoutingTable(null);
    }

    /**
     * Creates a binary search trie from a router table.
     *
     * @param routingTable The src.tables.RoutingTable from which to make the binary
     *                     search trie.
     */
    public BinarySearchTrie(RoutingTable routingTable) {
        this();
        this.setRoutingTable(routingTable);
        try {
            this.createTreeFromTable_(routingTable);
        } catch (Exception e) {
            System.err.println("ERROR: Could not create binary search trie from router table.");
            e.printStackTrace();
        }
    }

    /**
     * Get the underlying router table.
     *
     * @return The router table this trie represents.
     */
    public RoutingTable getRoutingTable() {
        return this.routingTable;
    }

    /**
     * Set the router table that this represents.
     *
     * @param routingTable The router table from which this is constructed.
     */
    private void setRoutingTable(RoutingTable routingTable) {
        this.routingTable = routingTable;
    }

    Node getRoot() {
        return this.root;
    }

    private void setRoot(Node root) {
        this.root = root;
    }

    /**
     * Helper called by the constructor to make the binary search trie from a
     * router table.
     *
     * @param routingTable The src.tables.RoutingTable from which to construct this trie.
     */
    private void createTreeFromTable_(RoutingTable routingTable) {
        // For each entry in the router table...
        for (int row = 0; row < routingTable.size(); ++row) {
            // Get the router table entry.
            RoutingTableEntry entry = routingTable.getEntry(row);
            // If entry is null, exit with message.
            if (entry == null) {
                System.err.println("ERROR: Could not create tree - entry is null");
                return;
            }
            // Get the destination address of the entry.
            DestinationAddress destination = entry.getDestinationAddress();
            // Loop through the prefix, constructing the tree.
            Node currentPtr = getRoot();
            for (int bit = 0; bit < destination.getPrefixLength(); ++bit) {
                // If bit is 1, go to the right.
                if (destination.getBitAtPosition(bit) != 0) {
                    if (currentPtr.getRight() == null) {
                        Node rightChild = new Node();
                        currentPtr = currentPtr.setRight(rightChild);
                    } else {
                        currentPtr = currentPtr.getRight();
                    }
                } else {
                    // bit is 0, go to the left.
                    if (currentPtr.getLeft() == null) {
                        Node leftChild = new Node();
                        currentPtr = currentPtr.setLeft(leftChild);
                    } else {
                        currentPtr = currentPtr.getLeft();
                    }
                }
            }
            currentPtr.setValue(entry.getGatewayAddress());
        }
    }

    /**
     * Lookup the gateway address in the trie for a certain destination.
     *
     * @param destination The destination address.
     * @return The gateway address specified by the router table.
     */
    public GatewayAddress lookupGateway(IPv4Address destination) {
        int bit = 0;
        GatewayAddress gateway = null;
        Node currentPtr = getRoot();
        while (currentPtr != null) {
            if (!currentPtr.isEmpty()) {
                gateway = currentPtr.getValue();
            }
            if (destination.getBitAtPosition(bit) != 0) {
                currentPtr = currentPtr.getRight();
            } else {
                currentPtr = currentPtr.getLeft();
            }
            bit++;
        }
        return gateway;
    }
}
