package src.router;

import src.address.GatewayAddress;
import src.binarysearchtrie.BinarySearchTrie;
import src.pdu.IPv4PDU;
import src.tables.ARPTable;
import src.tables.NATTable;
import src.tables.TranslationTableEntry;

/**
 * The router class that actually does the work of router PDUs using the binary
 * search trie and arp table.
 *
 * @author silval
 */
public class Router {
    private final ARPTable arpTable;
    private final BinarySearchTrie routingTrie;
    private NATTable natTable;

    /**
     * Create a router with the given router binary search trie and the
     * arpTable.
     *
     * @param routingTrie The binary search router trie.
     * @param arpTable    The ARP table.
     */
    public Router(BinarySearchTrie routingTrie, ARPTable arpTable) {
        this.routingTrie = routingTrie;
        this.arpTable = arpTable;
        this.setNatTable(null);
    }

    /**
     * Create a router with a natTable for part 2.
     *
     * @param routingTrie The binary search router trie.
     * @param arpTable    The ARP table.
     * @param natTable    The NAT table.
     */
    public Router(BinarySearchTrie routingTrie, ARPTable arpTable, NATTable natTable) {
        this(routingTrie, arpTable);
        this.setNatTable(natTable);
    }

    /**
     * Generate the route for a given pdu using the binary search trie and arp
     * table.
     *
     * @param pdu The pdu to get the route for.
     * @return The route
     */
    public Route generateRoute(IPv4PDU pdu) {
        Route route = new Route(pdu);
        // When receiving a pdu, check it against the translation table and reverse the translation
        // if one was applied. (for part 2)
        if (this.hasNAT() && getNatTable().hasBeenTranslated(pdu)) {
            handleReceiveWithNAT(route);
        }
        // Lookup the correct gateway for this pdu using the routing binary search trie.
        GatewayAddress gateway = doRoutingTrieLookup(pdu);
        if (gateway == null) return null;

        // Decrement the TTL for this pdu.
        pdu.decrementTTL();

        route.setGatewayAddress(gateway);
        // Do another router table lookup to determine if route is point to point.
        if (getRoutingTrie().getRoutingTable().getEntry(gateway).isPointToPoint()) {
            route.setPointToPoint(true);
            // If the gateway address is zero, then it is directly connected.
        } else if (route.getGatewayAddress().getAddress() == 0) {
            route.setDirectlyConnected(true);
            String macAddress = this.getArpTable().getARPEntry(pdu.getDestinationAddress()
                                                                       .getAddressString());
            route.setMacAddress(macAddress);
        } else {
            // Else, just do an ARP table lookup.
            String macAddress = getArpTable().getARPEntry(gateway.getAddressString());
            route.setMacAddress(macAddress);
        }
        route.setInterfaceType(getRoutingTrie().getRoutingTable().getEntry(gateway)
                                       .getInterfaceType());
        // Before sending a PDU, modify it's source if NAT is present at that interface.
        // (for part 2)
        if (this.hasNAT() && getNatTable().contains(route.getInterfaceType())) {
            handleSendWithNAT(route);
        }
        return route;
    }

    /**
     * Lookup the gateway address using the binary search trie.
     *
     * @param pdu The pdu to be routed.
     * @return The gateway address for this pdu.
     */
    private GatewayAddress doRoutingTrieLookup(IPv4PDU pdu) {
        // Get the gateway address from the binary search trie.
        GatewayAddress gateway = getRoutingTrie().lookupGateway(pdu.getDestinationAddress());
        if (gateway == null) {
            System.err.println("ERROR: Could not get gateway address");
            return null;
        }
        return gateway;
    }

    /**
     * Does all necessary translations when sending if a NAT table is present.
     *
     * @param route The new, translated route.
     */
    private void handleSendWithNAT(Route route) {
        IPv4PDU pdu = route.getPDU();
        TranslationTableEntry entry = new TranslationTableEntry(pdu);
        entry.setTranslatedSource(getNatTable().getAddress(route.getInterfaceType()));
        // Check if port is already in the translation table.
        if (getNatTable().alreadyHasSourcePort(pdu.getSourcePortNumber())) {
            entry.setTranslatedSourcePort(getNatTable()
                                                  .generateNewSourcePort(pdu.getSourcePortNumber
                                                                                     ()));
        } else {
            entry.setTranslatedSourcePort(pdu.getSourcePortNumber());
        }
        entry.setTranslatedDestination(pdu.getDestinationAddress());
        entry.setTranslatedDestinationPort(pdu.getDestinationPortNumber());
        getNatTable().addTranslation(entry);
        pdu.setSourceAddress(entry.getTranslatedSource());
        pdu.setSourcePortNumber(entry.getTranslatedSourcePort());
    }

    /**
     * Does all necessary translations when receiving if NAT table is present.
     *
     * @param route The new, translated route.
     */
    private void handleReceiveWithNAT(Route route) {
        IPv4PDU pdu = route.getPDU();
        // Get the translation table entry for this pdu.
        TranslationTableEntry entry = getNatTable().getTranslation(pdu.getSourceAddress(),
                                                                          pdu.getDestinationPortNumber());
        // Set the pdu values back to their original values (un-translate)
        pdu.setDestinationAddress(entry.getOriginalSource());
        pdu.setDestinationPortNumber(entry.getOriginalSourcePort());
    }

    /**
     * Get the router trie for this router.
     *
     * @return The binary search trie.
     */
    private BinarySearchTrie getRoutingTrie() {
        return routingTrie;
    }

    /**
     * Get the ARP table for this router.
     *
     * @return The ARP table.
     */
    private ARPTable getArpTable() {
        return arpTable;
    }

    /**
     * Get the NAT table for this router.
     *
     * @return The NAT table.
     */
    private NATTable getNatTable() {
        return natTable;
    }

    /**
     * Set the NAT table for this router.
     *
     * @param natTable The NAT table to be set.
     */
    private void setNatTable(NATTable natTable) {
        this.natTable = natTable;
    }

    /**
     * Determine if this router has a NAT table.
     *
     * @return true if NAT table present, false otherwise.
     */
    private boolean hasNAT() {
        return getNatTable() != null;
    }
}
