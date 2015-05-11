package src.tables;

import src.address.IPv4Address;
import src.pdu.IPv4PDU;

/**
 * Represents an entry in the translation table;
 *
 * @author silval
 */
public class TranslationTableEntry {
    private IPv4Address originalSource;
    private int originalSourcePort;
    private IPv4Address originalDestination;
    private int originalDestinationPort;
    private IPv4Address translatedSource;
    private int translatedSourcePort;
    private IPv4Address translatedDestination;
    private int translatedDestinationPort;

    /**
     * Default constructor, initialize with defaults.
     */
    public TranslationTableEntry() {
        setOriginalSource(null);
        setOriginalSourcePort(-1);
        setOriginalDestination(null);
        setOriginalDestinationPort(-1);
        setTranslatedSource(null);
        setTranslatedSourcePort(-1);
        setTranslatedDestination(null);
        setTranslatedDestinationPort(-1);
    }

    /**
     * Construct an entry with a pdu.
     *
     * @param pdu The pdu used to construct this entry.
     */
    public TranslationTableEntry(IPv4PDU pdu) {
        this();
        setOriginalSource(pdu.getSourceAddress());
        setOriginalSourcePort(pdu.getSourcePortNumber());
        setOriginalDestination(pdu.getDestinationAddress());
        setOriginalDestinationPort(pdu.getDestinationPortNumber());
    }

    /**
     * Get the original source address.
     *
     * @return The original source address.
     */
    public IPv4Address getOriginalSource() {
        return originalSource;
    }

    /**
     * Set the original source address.
     *
     * @param originalSource The original source address.
     */
    private void setOriginalSource(IPv4Address originalSource) {
        this.originalSource = originalSource;
    }

    /**
     * Get the original source port.
     *
     * @return The original source port.
     */
    public int getOriginalSourcePort() {
        return originalSourcePort;
    }

    /**
     * Set the original source port.
     *
     * @param originalSourcePort The original source port number.
     */
    private void setOriginalSourcePort(int originalSourcePort) {
        this.originalSourcePort = originalSourcePort;
    }


    /**
     * Set the original destination address.
     *
     * @param originalDestination The original destination address.
     */
    private void setOriginalDestination(IPv4Address originalDestination) {
        this.originalDestination = originalDestination;
    }


    /**
     * Set the original destination port.
     *
     * @param originalDestinationPort The original destination port.
     */
    private void setOriginalDestinationPort(int originalDestinationPort) {
        this.originalDestinationPort = originalDestinationPort;
    }

    /**
     * Get the translated source address.
     *
     * @return The translated source address.
     */
    public IPv4Address getTranslatedSource() {
        return translatedSource;
    }

    /**
     * Set the translated source address.
     *
     * @param translatedSource The translated source address.
     */
    public void setTranslatedSource(IPv4Address translatedSource) {
        this.translatedSource = translatedSource;
    }

    /**
     * Get the translated source port number.
     *
     * @return The translated source port number.
     */
    public int getTranslatedSourcePort() {
        return translatedSourcePort;
    }

    /**
     * Set the translated source port number.
     *
     * @param translatedSourcePort The translated source port to be set.
     */
    public void setTranslatedSourcePort(int translatedSourcePort) {
        this.translatedSourcePort = translatedSourcePort;
    }

    /**
     * Get the translated destination address.
     *
     * @return The translated destination.
     */
    public IPv4Address getTranslatedDestination() {
        return translatedDestination;
    }

    /**
     * Set the translated destination address.
     *
     * @param translatedDestination The translated destination address.
     */
    public void setTranslatedDestination(IPv4Address translatedDestination) {
        this.translatedDestination = translatedDestination;
    }


    /**
     * Set the translated destination port.
     *
     * @param translatedDestinationPort The translated destination port.
     */
    public void setTranslatedDestinationPort(int translatedDestinationPort) {
        this.translatedDestinationPort = translatedDestinationPort;
    }
}
