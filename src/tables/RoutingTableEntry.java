package src.tables;

import src.address.DestinationAddress;
import src.address.GatewayAddress;

/**
 * Represents an entry(row) in the router table.
 *
 * @author silval
 */
public class RoutingTableEntry {
    private DestinationAddress destinationAddress;
    private GatewayAddress gatewayAddress;
    private String interfaceType;

    /**
     * The src.tables.RoutingTableEntry constructor.
     *
     * @param line A line of a routes.txt file, with three columns containing
     *             destination address, gateway address, and interface.
     * @throws Exception If line is not properly formatted.
     */
    public RoutingTableEntry(String line) throws Exception {
        String[] columns = line.split("\\s+");
        if (columns.length != 3) {
            System.out
                    .println("ERROR: Routing table has incorrect formatting!");
        } else {
            this.destinationAddress = new DestinationAddress(columns[0]);
            this.gatewayAddress = new GatewayAddress(columns[1]);
            this.interfaceType = columns[2];
        }
    }

    /**
     * Get the destination address of this entry.
     *
     * @return the destinationAddress
     */
    public DestinationAddress getDestinationAddress() {
        return this.destinationAddress;
    }

    /**
     * Get the gateway address of this entry.
     *
     * @return the gatewayAddress
     */
    public GatewayAddress getGatewayAddress() {
        return this.gatewayAddress;
    }

    /**
     * Get the interface for this entry.
     *
     * @return the interfaceType, either eth0, eth1, or ppp0.
     */
    public String getInterfaceType() {
        return this.interfaceType;
    }

    /**
     * Determine if the entry is point to point.
     *
     * @return true if point to point, false otherwise.
     */
    public boolean isPointToPoint() {
        return this.interfaceType.equalsIgnoreCase("ppp0");
    }
}