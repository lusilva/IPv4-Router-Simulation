package src.tables;

import src.address.GatewayAddress;

import java.util.ArrayList;

/**
 * Represents the router table read from the text file.
 *
 * @author silval
 */
public class RoutingTable {
    // The actual array representing the router table.
    private final ArrayList<RoutingTableEntry> table;

    /**
     * src.tables.RoutingTable constructor.
     */
    public RoutingTable() {
        this.table = new ArrayList<RoutingTableEntry>();
    }

    /**
     * Adds an entry to the router table
     *
     * @param entry The src.tables.RoutingTableEntry to be added to the table.
     */
    public void addEntry(RoutingTableEntry entry) {
        if (entry == null) {
            System.out
                    .println("ERROR: Cannot add empty element to router table.");
            return;
        }
        this.table.add(entry);
    }

    /**
     * Gets the router table entry at the specified location.
     *
     * @param index The index of the entry to be retrieved.
     * @return The src.tables.RoutingTableEntry at the specified index, or null if
     * index is
     * out of bounds.
     */
    public RoutingTableEntry getEntry(int index) {
        if (index < 0 || index > table.size()) {
            System.out.println("ERROR: Routing table index out of bounds.");
            return null;
        }
        return this.table.get(index);
    }

    /**
     * Get an entry in the router table by gateway address.
     *
     * @param gateway The gateway address.
     * @return The entry with the specified gateway address.
     */
    public RoutingTableEntry getEntry(GatewayAddress gateway) {
        for (RoutingTableEntry entry : this.table) {
            if (entry.getGatewayAddress().getAddress() == gateway.getAddress()) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Get the size of this router table (how many rows it has)
     *
     * @return The size of the table.
     */
    public int size() {
        return this.table.size();
    }
}
