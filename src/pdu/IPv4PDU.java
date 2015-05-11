package src.pdu;

import src.address.IPv4Address;

import java.net.UnknownHostException;

/**
 * Represents an IPv4 PDU, and contains all PDU fields.
 *
 * @author silval
 */
public class IPv4PDU {
    private String interfaceType;
    private IPv4Address sourceAddress;
    private IPv4Address destinationAddress;
    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private int protocolNumber; // Not used, but still part of PDU.
    private int timeToLive;
    private int sourcePortNumber;
    private int destinationPortNumber;

    /**
     * Constructor for an IPv4 PDU.
     *
     * @param line A formatted, space delimited line containing the PDU values.
     */
    public IPv4PDU(String line) {
        String[] pduValues = line.split("\\s+");
        if (pduValues.length != 7) {
            System.err.println("ERROR: Invalid PDU format. PDU could not be initialized.");
            return;
        }
        this.interfaceType = pduValues[0];
        try {
            this.sourceAddress = new IPv4Address(pduValues[1]);
            this.destinationAddress = new IPv4Address(pduValues[2]);
        } catch (UnknownHostException e) {
            System.err.println("ERROR: PDU could not be initialized.");
            e.getMessage();
            return;
        }
        this.protocolNumber = Integer.parseInt(pduValues[3]);
        this.timeToLive = Integer.parseInt(pduValues[4]);
        this.sourcePortNumber = Integer.parseInt(pduValues[5]);
        this.destinationPortNumber = Integer.parseInt(pduValues[6]);
    }

    /**
     * Get the source address.
     *
     * @return The source src.address.IPv4Address
     */
    public IPv4Address getSourceAddress() {
        return this.sourceAddress;
    }

    public void setSourceAddress(IPv4Address source) {
        this.sourceAddress = source;
    }

    /**
     * Get the destination address.
     *
     * @return The destination src.address.IPv4Address
     */
    public IPv4Address getDestinationAddress() {
        return this.destinationAddress;
    }

    public void setDestinationAddress(IPv4Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Get the TTL for this PDU.
     *
     * @return The Integer TTL value.
     */
    public int getTimeToLive() {
        return this.timeToLive;
    }

    /**
     * Decrement the TTL.
     */
    public void decrementTTL() {
        this.timeToLive--;
    }

    /**
     * Get the source port number.
     *
     * @return The Integer source port number.
     */
    public int getSourcePortNumber() {
        return this.sourcePortNumber;
    }

    public void setSourcePortNumber(int sourcePortNumber) {
        this.sourcePortNumber = sourcePortNumber;
    }

    /**
     * Get the destination port number.
     *
     * @return The Integer destination port number.
     */
    public int getDestinationPortNumber() {
        return this.destinationPortNumber;
    }

    /**
     * Set the destination port number for this PDU.
     *
     * @param destinationPortNumber The new destination port number.
     */
    public void setDestinationPortNumber(int destinationPortNumber) {
        this.destinationPortNumber = destinationPortNumber;
    }

}