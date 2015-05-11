package src.address;

import java.net.UnknownHostException;

/**
 * Represents an IPv4 gateway address.
 *
 * @author silval
 * @see IPv4Address
 */
public class GatewayAddress extends IPv4Address {

    /**
     * The src.address.GatewayAddress constructor
     *
     * @param ipAddress The IPv4 address of the gateway in a.b.c.d notation.
     * @throws UnknownHostException If IPv4 address is not valid
     */
    public GatewayAddress(String ipAddress) throws UnknownHostException {
        super(ipAddress);
    }
}
