package src.address;

import java.net.UnknownHostException;

/**
 * Used to represent an IPv4 destination address.
 *
 * @author silval
 * @see IPv4Address
 */
public class DestinationAddress extends IPv4Address {
    // The prefix length of the address.
    private int prefixLength;

    /**
     * The src.address.DestinationAddress constructor.
     *
     * @param ipAddress The IPv4 address of the destination in a.b.c.d/x format.
     * @throws UnknownHostException If IPv4 address is not valid.
     */
    public DestinationAddress(String ipAddress) throws UnknownHostException {
        super(ipAddress.split("/")[0]);
        String[] parts = ipAddress.split("/");
        // Make sure address has prefix length associated with it.
        if (parts.length != 2) {
            throw new UnknownHostException(ipAddress + " is not valid. Missing prefix length");
        }
        // Set the prefix length.
        prefixLength = Integer.parseInt(parts[1]);
        if (prefixLength < 0 || prefixLength > 32) {
            throw new UnknownHostException(ipAddress + " is not valid. Invalid prefix length");
        }
    }

    /**
     * Gets the prefix length.
     *
     * @return The prefix length associated with this address.
     */
    public int getPrefixLength() {
        return this.prefixLength;
    }
}
