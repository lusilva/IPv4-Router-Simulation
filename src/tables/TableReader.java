package src.tables;

import src.address.IPv4Address;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Responsible for reading from arp, router, and pdu text files and creating
 * objects.
 *
 * @author silval
 */
public class TableReader extends FileReader {
    // The reader to read from the file.
    private BufferedReader reader;

    /**
     * The src.tables.TableReader constructor.
     *
     * @param fileName The name of the file to be read.
     * @throws FileNotFoundException If the file cannot be opened.
     */
    public TableReader(String fileName) throws FileNotFoundException {
        super(fileName);
        reader = new BufferedReader(this);
    }

    /**
     * Constructs a src.tables.RoutingTable from the file.
     *
     * @return The newly constructed src.tables.RoutingTable.
     * @throws Exception If the router table cannot be created.
     */
    public RoutingTable constructRoutingTable() throws Exception {
        String line = this.readLine();
        RoutingTable table = new RoutingTable();
        while (line != null && line.length() > 0) {
            RoutingTableEntry row = new RoutingTableEntry(line);
            table.addEntry(row);
            line = this.readLine();
        }
        return table;
    }

    /**
     * Constructs the ARP Table from the data stored within the file.
     *
     * @return The newly constructed ARP table.
     */
    public ARPTable constructARPTable() {
        String line = this.readLine();
        ARPTable table = new ARPTable();
        while (line != null && line.length() > 0) {
            String[] columns = line.split("\\s+");
            if (columns.length != 2) {
                System.out.println("ERROR: File does not have correct number of columns");
                return null;
            }
            table.addEntry(columns[0], columns[1]);
            line = this.readLine();
        }
        return table;
    }

    /**
     * Constructs the NAT Table from the data stored within the file.
     *
     * @return The newly constructed NAT table.
     */
    public NATTable constructNATTable() {
        String line = this.readLine();
        NATTable table = new NATTable();
        while (line != null && line.length() > 0) {
            String[] columns = line.split("\\s+");
            if (columns.length != 2) {
                System.out.println("ERROR: File does not have correct number of columns");
                return null;
            }
            IPv4Address address;
            try {
                address = new IPv4Address(columns[1]);
            } catch (java.net.UnknownHostException e) {
                System.out.println("ERROR: " + e.getMessage());
                return null;
            }
            table.addEntry(columns[0], address);
            line = this.readLine();
        }
        return table;
    }

    /**
     * Reads a single line from the file. If it is the last line, it closes the
     * file.
     *
     * @return The line read from the file.
     */
    private String readLine() {
        String line = null;
        // Try to read a line
        try {
            line = this.reader.readLine();
            // If the end of the file has been reached, close the file and
            // reader.
            if (line == null) {
                this.reader.close();
                this.close();
            }
        } catch (IOException e) {
            System.out.println("ERROR: Could not read from file");
        }
        return line;
    }
}