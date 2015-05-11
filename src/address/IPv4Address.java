package src.address;

import java.net.UnknownHostException;

/**
 * Represents an IPv4 address.
 *
 * @author silval
 */
public class IPv4Address {
    // The address in a.b.c.d string notation.
    private String strAddress;
    // The address as an integer.
    private long intAddress;
    // The address as bits, used for debugging.
    // private String bitAddress;

    /**
     * The constructor for an IPv4 address.
     *
     * @param ipAddress The IPv4 address in a.b.c.d notation.
     * @throws UnknownHostException If the IPv4 address is not valid.
     */
    public IPv4Address(String ipAddress) throws UnknownHostException {
        if (!this.validIP_(ipAddress)) {
            throw new UnknownHostException(ipAddress + " is not valid");
        }
        this.strAddress = ipAddress;
        this.intAddress = this.convertToInt_(ipAddress);
        // this.bitAddress = this.convertToBit_();
    }

    /**
     * Converts an a.b.c.d address to an integer(long).
     *
     * @param addr The string representation of an IPv4 address to be converted.
     * @return The integer(long) representation of the address.
     */
    private long convertToInt_(String addr) {
        String[] addrArray = addr.split("\\.");
        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }
        if (num < 0) {
            System.err.println("ERROR: Could not convert ip to long");
            return 0;
        }
        return num;
    }

    /**
     * Gets the address as an integer.
     *
     * @return The integer(long) IPv4 address.
     */
    public long getAddress() {
        return this.intAddress;
    }

    /**
     * Gets the address as a string in a.b.c.d format.
     *
     * @return The string IPv4 address.
     */
    public String getAddressString() {
        return this.strAddress;
    }

    /**
     * Get the bit at a certain position in the address.
     *
     * @param position The bit position to be retrieved. 0 is MSB, 31 is LSB.
     * @return either 0 or 1
     */
    public int getBitAtPosition(int position) {
        if (position < 0 || position > 31) {
            System.err.println("ERROR: Bit position out of range.");
            return 0;
        }
        return (int) (this.getAddress() & (1 << (31 - position)));
    }

    /**
     * Validates an IPv4 address in a.b.c.d format.
     *
     * @param ipAddress The address to be validated.
     * @return true if address is valid, false otherwise.
     */
    private boolean validIP_(String ipAddress) {
        try {
            // Check to make sure the address is not empty.
            if (ipAddress == null || ipAddress.isEmpty()) {
                return false;
            }
            String[] parts = ipAddress.split("\\.");
            // Make sure it is in a.b.c.d notation.
            if (parts.length != 4) {
                return false;
            }
            // Make sure each part is between 0 and 255
            for (String s : parts) {
                int i = Integer.parseInt(s);
                if ((i < 0) || (i > 255)) {
                    return false;
                }
            }
            // The address cannot end with a period.
            if (ipAddress.endsWith(".")) {
                return false;
            }
            // If all the statements above passed, then this is a valid address.
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
