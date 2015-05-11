package src.tables;

import java.util.HashMap;

/**
 * Stores the ARP table using an underlying HashMap.
 *
 * @author silval
 */
public class ARPTable {
    private HashMap<String, String> table;

    /**
     * Constructor for the src.tables.ARPTable class.
     */
    public ARPTable() {
        setTable(new HashMap<String, String>());
    }

    /**
     * Adds an entry to the ARP table.
     *
     * @param ipAddress  The IPv4 address.
     * @param macAddress The corresponding MAC address.
     */
    public void addEntry(String ipAddress, String macAddress) {
        if (ipAddress == null || macAddress == null) {
            System.out.println("ERROR: ARP table entry cannot be null!");
        } else if (getTable().containsKey(ipAddress)) {
            System.out.printf("ERROR: %s already has MAC address associated with it.",
                                     ipAddress);
        } else {
            getTable().put(ipAddress, macAddress);
        }
    }

    /**
     * Gets the ARP entry associated with the given IP address.
     *
     * @param ipAddress The IP address.
     * @return The MAC address associated with the given IP address.
     */
    public String getARPEntry(String ipAddress) {
        if (!getTable().containsKey(ipAddress)) {
            System.out.printf("ERROR: %s could not be found in ARP table. \n",
                                     ipAddress);
            return null;
        }
        return getTable().get(ipAddress);
    }

    /**
     * Get the underlying table hashmap.
     *
     * @return The table hashmap.
     */
    private HashMap<String, String> getTable() {
        return table;
    }

    /**
     * Set the underlying table hashmap, used by constructor.
     *
     * @param table The table to be set.
     */
    private void setTable(HashMap<String, String> table) {
        this.table = table;
    }
}
