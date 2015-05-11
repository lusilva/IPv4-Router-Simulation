package src.router;

import src.address.GatewayAddress;
import src.pdu.IPv4PDU;

/**
 * The Route class stores a route for a given PDU.
 *
 * @author silval
 */
public class Route {
    // The PDU for this route.
    private IPv4PDU pdu;
    // Is this root point to point?
    private boolean isPointToPoint;
    // Is this root directly connected?
    private boolean isDirectlyConnected;
    // The gateway for this route given by the router trie.
    private GatewayAddress gatewayAddress;
    // The interface for this route.
    private String interfaceType;
    // The mac address given by the ARP table.
    private String macAddress;

    /**
     * The Route constructor.
     *
     * @param pdu The pdu for which this route applies.
     */
    public Route(IPv4PDU pdu) {
        this.setPDU(pdu);
        this.setGatewayAddress(null);
        this.setPointToPoint(false);
        this.setDirectlyConnected(false);
        this.setInterfaceType(null);
        this.setMacAddress(null);
    }

    /**
     * Determine if this route is point to point.
     *
     * @return true if point to point, false otherwise.
     */
    boolean isPointToPoint() {
        return this.isPointToPoint;
    }

    /**
     * Set point to point value for this root. Default is false.
     *
     * @param isPointToPoint true indicates this route is ppp0.
     */
    public void setPointToPoint(boolean isPointToPoint) {
        this.isPointToPoint = isPointToPoint;
    }

    /**
     * Gate the gateway address for this route.
     *
     * @return The gateway address.
     */
    public GatewayAddress getGatewayAddress() {
        return this.gatewayAddress;
    }

    /**
     * Set the gateway address for this route.
     *
     * @param gatewayAddress The gateway address.
     */
    public void setGatewayAddress(GatewayAddress gatewayAddress) {
        this.gatewayAddress = gatewayAddress;
    }

    /**
     * Prints each formatted route.
     */
    public void print() {
        if (this.getPDU().getTimeToLive() > 0) {
            if (this.isPointToPoint()) {
                System.out.printf("%s:%d->%s:%d via %s(ppp0) ttl %d \n",
                                         this.getPDU().getSourceAddress().getAddressString(),
                                         this.getPDU().getSourcePortNumber(),
                                         this.getPDU().getDestinationAddress().getAddressString(),
                                         this.getPDU().getDestinationPortNumber(),
                                         this.getGatewayAddress().getAddressString(),
                                         this.getPDU().getTimeToLive());
            } else if (this.isDirectlyConnected()) {
                System.out.printf("%s:%d->%s:%d directly connected (%s-%s) ttl %d \n",
                                         this.getPDU().getSourceAddress().getAddressString(),
                                         this.getPDU().getSourcePortNumber(),
                                         this.getPDU().getDestinationAddress().getAddressString(),
                                         this.getPDU().getDestinationPortNumber(),
                                         this.getInterfaceType(),
                                         this.getMacAddress(),
                                         this.getPDU().getTimeToLive());
            } else {
                System.out.printf("%s:%d->%s:%d via %s(%s-%s) ttl %d \n",
                                         this.getPDU().getSourceAddress().getAddressString(),
                                         this.getPDU().getSourcePortNumber(),
                                         this.getPDU().getDestinationAddress().getAddressString(),
                                         this.getPDU().getDestinationPortNumber(),
                                         this.getGatewayAddress().getAddressString(),
                                         this.getInterfaceType(), this.getMacAddress(),
                                         this.getPDU().getTimeToLive());
            }
        } else {
            System.out.printf("%s:%d->%s:%d discarded (TTL expired) \n",
                                     this.getPDU().getSourceAddress().getAddressString(),
                                     this.getPDU().getSourcePortNumber(),
                                     this.getPDU().getDestinationAddress().getAddressString(),
                                     this.getPDU().getDestinationPortNumber());
        }
    }

    /**
     * Determine if this route is directly connected.
     *
     * @return true if directly connected, false otherwise.
     */
    boolean isDirectlyConnected() {
        return this.isDirectlyConnected;
    }

    /**
     * Set the directly connected value for this route.
     *
     * @param isDirectlyConnected true if this route is directly connected.
     */
    public void setDirectlyConnected(boolean isDirectlyConnected) {
        this.isDirectlyConnected = isDirectlyConnected;
    }

    /**
     * Get the interface for this route.
     *
     * @return The interface
     */
    public String getInterfaceType() {
        return interfaceType;
    }

    /**
     * Set the interface for this route.
     *
     * @param interfaceType The interface to be set.
     */
    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    /**
     * Get the MAC Address for this route.
     *
     * @return The MAC address.
     */
    private String getMacAddress() {
        return macAddress;
    }

    /**
     * Set the MAC address for this route.
     *
     * @param macAddress The MAC address to be set.
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Get the PDU for which this route applies.
     *
     * @return The IPv4 PDU.
     */
    IPv4PDU getPDU() {
        return this.pdu;
    }

    /**
     * Set the PDU for which this route applies.
     *
     * @param pdu The pdu to be set.
     */
    private void setPDU(IPv4PDU pdu) {
        this.pdu = pdu;
    }
}
