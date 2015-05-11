#Project 1: IPv4 Router Simulation
##for Network Programming (CSCI 4220) by Lucas Silva


#Compilation Instructions: (written with java version "1.8.0_31")
    1. Unzip master folder
    2. Open up a terminal in this directory.
    3. Run:
        a. To compile using ant:
           $ ant
        b. Without ant:
           $ mkdir bin
           $ cd src
           $ javac -d ../bin @sources.txt

#Execution Instructions:
    1. Follow above steps for compilation
    2. cd into the bin/ directory created at compilation
    3. Run:
        $ java src.SimulationLauncher
    4. Make sure all necessary files are in the bin directory (arp.txt, routes.txt, etc..)

#Command line arguments:
    To run part 1, run with no command line arguments.
    To run part 2, run with '-nat' argument.


#Javadoc:
    I've included the javadoc for this assignment, available in the docs/ directory. This contains
    documentation for the overall assignment structure and all of the classes and methods. To view,
    just open index.