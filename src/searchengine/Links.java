package searchengine;

/*
  Links lets us to write primitive Java integers which describe a web graph
  {numberOfnodes,source_1,destination_1,...,source_n,destination_n} to an output
  stream. The data are read back in by the PageRank class.
  Links lets us feed PageRank with data pending the parsing of the Wikipedia
  pages.
*/

import java.io.*;


public class Links {
    // Description of the web graph
    // {numberOfNodes,source_1,destination_1,...,source_n,destination_n}
    protected static int[] links = {10,0,1,0,3,0,5,0,6,0,7,0,9,1,2,1,5,1,7,1,8,2,4,2,5,2,6,3,4,3,8,4,5,4,7,4,9,5,7,6,8,6,9,7,9};
    private final String filename;
    
    public Links(String filename) {
        this.filename = filename;
    }
    
    // Write the description of the web graph to the file Links
    public void generate() {
	DataOutputStream linksDataOutputStream = null;
	try {
            // Open a DataOutputStream for the file Links
	    File linksFile = new File(filename);
	    FileOutputStream linksFileOutputStream = new FileOutputStream(linksFile);
	    BufferedOutputStream linksBufferedOutputStream = new BufferedOutputStream(linksFileOutputStream);
	    linksDataOutputStream = new DataOutputStream(linksBufferedOutputStream);
            // Save the description of the web graph into the file Links
	    for (int i : links)
		linksDataOutputStream.writeInt(i);
	} catch (FileNotFoundException exception) {
            // Missing file Links
	} catch (IOException exception) {
            // Write error
	} finally {
            // Close the DatatOutputStream
	    try {
		if (linksDataOutputStream != null)
		    linksDataOutputStream.close();
	    } catch (IOException exception) {
	    }
	}        
    }
    
    // Display the description of the web graph from the file Links
    public void display() {
	DataInputStream linksDataInputStream = null;
	try {
            // Open a DataInputStream for the file Links
	    File linksFile = new File(filename);
	    FileInputStream linksFileInputStream = new FileInputStream(linksFile);
	    BufferedInputStream linksBufferedInputStream = new BufferedInputStream(linksFileInputStream);
	    linksDataInputStream = new DataInputStream(linksBufferedInputStream);
            // Retrieve the numberOfNodes
            int numberOfNodes = linksDataInputStream.readInt();
            System.out.println(numberOfNodes);
	    while (true) {
                int edgeSource = linksDataInputStream.readInt();
                int edgeDestination = linksDataInputStream.readInt();
                System.out.println(edgeSource + " " + edgeDestination);
            }
	} catch (EOFException exception) {
            // End of file reached
	} catch (FileNotFoundException exception) {
            // Missing file Links
	    System.out.println("File not found");
	} catch (IOException exception) {
            // Read error
	} finally {
            // Close the DataInputStream
	    try {
		if (linksDataInputStream != null)
		    linksDataInputStream.close();
	    } catch (IOException exception) {
	    }
	}
    }
}