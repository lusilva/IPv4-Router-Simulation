package src.tables;

import src.address.IPv4Address;
import src.pdu.IPv4PDU;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The NAT Table class stored the NAT table in nat.txt
 *
 * @author silval
 */
public class NATTable {
    private HashMap<String, IPv4Address> table;
    // Translation table of translated source
    private ArrayList<TranslationTableEntry> translationTable;

    /**
     * Default constructor, initialize tables.
     */
    public NATTable() {
        setTable(new HashMap<String, IPv4Address>());
        setTranslationTable(new ArrayList<TranslationTableEntry>());
    }

    /**
     * Add an entry to the NAT table.
     *
     * @param interfaceType The interface to be translated.
     * @param address       The address of the NAT at the specified interface.
     */
    public void addEntry(String interfaceType, IPv4Address address) {
        if (interfaceType == null || address == null) {
            System.out.println("ERROR: NAT Table entry cannot be null!");
        } else if (getTable().containsKey(interfaceType)) {
            System.out
                    .printf("ERROR: %s already has an address associated with it. \n",
                                   interfaceType);
        } else {
            getTable().put(interfaceType, address);
        }
    }

    /**
     * Check if an interface contains a NAT.
     *
     * @param interfaceType The interface to be checked.
     * @return true if NAT present, false otherwise.
     */
    public boolean contains(String interfaceType) {
        return getTable().containsKey(interfaceType);
    }

    /**
     * Get the NAT address for a specified interface.
     *
     * @param interfaceType The interface (eth0, eth1, ppp0, etc..)
     * @return The NAT IPv4 address.
     */
    public IPv4Address getAddress(String interfaceType) {
        if (getTable().containsKey(interfaceType)) {
            return getTable().get(interfaceType);
        } else {
            System.out.printf("ERROR: entry %s does not exist in the NAT table \n", interfaceType);
            return null;
        }
    }

    /**
     * Add a translation to the NAT translation table.
     *
     * @param translationEntry The translation table entry to be added.
     */
    public void addTranslation(TranslationTableEntry translationEntry) {
        getTranslationTable().add(0, translationEntry);
    }

    /**
     * Get the translation table entry for a destination address and port.
     *
     * @param address The translated destination address.
     * @param port    The translated source port.
     * @return The translation table entry.
     */
    public TranslationTableEntry getTranslation(IPv4Address address, int port) {
        for (TranslationTableEntry entry : getTranslationTable()) {
            if (entry.getTranslatedDestination().getAddress() == address.getAddress() &&
                        entry.getTranslatedSourcePort() == port) {
                return entry;
            }
        }
        return null;
    }

    /**
     * Check whether a received pdu has been translated.
     *
     * @param pdu The pdu to be checked.
     * @return true if it is in the translation table, false otherwise.
     */
    public boolean hasBeenTranslated(IPv4PDU pdu) {
        for (String interfaceType : getTable().keySet()) {
            if (getTable().get(interfaceType).getAddress() == pdu.getDestinationAddress()
                                                                      .getAddress()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the NAT table.
     *
     * @return The NAT table.
     */
    private HashMap<String, IPv4Address> getTable() {
        return table;
    }

    /**
     * Set the NAT table.
     *
     * @param table The NAT table to be set.
     */
    private void setTable(HashMap<String, IPv4Address> table) {
        this.table = table;
    }

    /**
     * Get the translation table.
     *
     * @return The translation table.
     */
    private ArrayList<TranslationTableEntry> getTranslationTable() {
        return translationTable;
    }

    /**
     * Set the translation table
     *
     * @param translationTable The translation table to be set.
     */
    private void setTranslationTable(ArrayList<TranslationTableEntry> translationTable) {
        this.translationTable = translationTable;
    }

    /**
     * Check if a source port is already contained in the translation table.
     *
     * @param sourcePort The source port to be checked.
     * @return true if present, false otherwise.
     */
    public boolean alreadyHasSourcePort(int sourcePort) {
        for (TranslationTableEntry entry : getTranslationTable()) {
            if (entry.getTranslatedSourcePort() == sourcePort) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a new source port number.
     *
     * @param sourcePort The source port number to be changed.
     * @return The new source port number.
     */
    public int generateNewSourcePort(int sourcePort) {
        int newPort = sourcePort + 1;
        // Continue incrementing until the new port number
        // is not already in the translation table.
        while (alreadyHasSourcePort(newPort)) {
            newPort++;
        }
        return newPort;
    }
}
