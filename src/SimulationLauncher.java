package src;

import src.binarysearchtrie.BinarySearchTrie;
import src.pdu.IPv4PDU;
import src.router.Route;
import src.router.Router;
import src.tables.ARPTable;
import src.tables.NATTable;
import src.tables.RoutingTable;
import src.tables.TableReader;

import java.util.Scanner;

/**
 * Launches the IPv4 router simulation.
 *
 * @author silval
 */
public class SimulationLauncher {

    private static final String ROUTES_FILE = "routes.txt";
    private static final String ARP_FILE = "arp.txt";
    private static final String NAT_FILE = "nat.txt";

    /**
     * The main entry point into the simulation.
     *
     * @param args The command line arguments, should be empty.
     */
    public static void main(String[] args) {
        // Create reader to read from router table.
        TableReader reader;
        // The binary search trie created from the router table.
        BinarySearchTrie routingTrie;
        // The arp table read from arp.txt.
        ARPTable arpTable;
        try {
            // Try to find routes.txt file.
            reader = new TableReader(ROUTES_FILE);
            // Construct the router table.
            RoutingTable routingTable = reader.constructRoutingTable();
            reader.close();
            // Construct the binary search trie from the router table.
            routingTrie = new BinarySearchTrie(routingTable);
            // Try to find arp.txt file.
            reader = new TableReader(ARP_FILE);
            // Construct the ARP table.
            arpTable = reader.constructARPTable();
            reader.close();
        } catch (java.io.FileNotFoundException e) {
            // If file not found, tell user.
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        if (args.length == 0) {
            runPart1(routingTrie, arpTable);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("-nat")) {
            runPart2(routingTrie, arpTable);
        } else {
            System.err.println("ERROR: invalid command line argument(s)");
            System.err.println("usage: run with '-nat' to run part 2, else run with no arguments " +
                                     "for part 1");
        }
    }

    /**
     * Runs part 1 of the simulation, when no "-nat" flag is specified.
     *
     * @param routingTrie The routing binary search trie.
     * @param arpTable    The arp table constructed from arp.txt.
     */
    private static void runPart1(BinarySearchTrie routingTrie, ARPTable arpTable) {
        // Create a new router from the routing trie and arp table.
        Router router = new Router(routingTrie, arpTable);
        getAndRoutePDUs(router);
    }

    /**
     * Run part 2 of the simulation, when "-nat" flag is specified.
     *
     * @param routingTrie The routing binary search trie.
     * @param arpTable    The arp table constructed from arp.txt.
     */
    private static void runPart2(BinarySearchTrie routingTrie, ARPTable arpTable) {
        TableReader reader;
        try {
            reader = new TableReader(NAT_FILE);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
            return;
        }
        NATTable natTable = reader.constructNATTable();

        Router router = new Router(routingTrie, arpTable, natTable);
        getAndRoutePDUs(router);
    }

    /**
     * Get the PDUs from System.in and route them.
     *
     * @param router The router to route the PDUs.
     */
    private static void getAndRoutePDUs(Router router) {
        // Read PDUs from System.in, until empty line is found.
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            try {
                // Get the next line.
                String line = sc.nextLine();
                if (line.length() == 0) {
                    break;
                }
                // Try to make PDU, this may fail if, this may fail if line is not properly
                // formatted.
                IPv4PDU pdu = new IPv4PDU(line);
                // Actually generate the route, and print it.
                Route route = router.generateRoute(pdu);
                route.print();
            } catch (Exception e) {
                System.err.println("ERROR: Could not create route.");
                break;
            }
        }
        sc.close();
    }

}
