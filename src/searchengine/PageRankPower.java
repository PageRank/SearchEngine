package searchengine;

import java.io.*;
import java.util.*;


public class PageRankPower implements PageRank {
    // Instance variable
    protected double[] pageRank;
    
    // Constructor
    public PageRankPower(String linksFilename, double dampingFactor) {
	DataInputStream linksDataInputStream = null;
	int numberOfNodes = 0;
	Graph graph = null;
	try {
	    try {
		// Open a DataInputStream for the Links file
		File linksFile = new File(linksFilename);
		FileInputStream linksFileInputStream = new FileInputStream(linksFile);
		BufferedInputStream linksBufferedInputStream = new BufferedInputStream(linksFileInputStream);
		linksDataInputStream = new DataInputStream(linksBufferedInputStream);
		// Retrieve the numberOfNodes
		numberOfNodes = linksDataInputStream.readInt();
		// Retrieve the web graph
		graph = new Graph(numberOfNodes);
		while (true) {
		    int edgeSource = linksDataInputStream.readInt();
		    int edgeDestination = linksDataInputStream.readInt();
		    graph.addEdge(edgeSource, edgeDestination);
		}
	    } catch (EOFException exception) {
		// End of file reached
	    }
	    // Compute PageRank
            double error;
            double epsilon = 0.0000001;
	    // Initialize the PageRank vector
	    this.pageRank = new double[numberOfNodes];
	    for (int i = 0; i < numberOfNodes; i++)
		this.pageRank[i] = 1.0 / (double)numberOfNodes;
	    int count = 0;
            do {
                count++;
                // Only two arrays of this.pageRank.length() in memory with this copy
                double[] temp = this.pageRank.clone();
                
		// For each page of the web graph
		for (int i = 0; i < numberOfNodes; i++) {
		    HashSet<Integer> setOfNodesThatLinkTo = graph.getNode(i).getSetOfNodesThatLinkTo();
		    Iterator<Integer> iterator = setOfNodesThatLinkTo.iterator();
		    this.pageRank[i] = ((1.0 - dampingFactor) / (double)numberOfNodes);
		    // For each page that link to
		    while (iterator.hasNext()) {
			int j = iterator.next();
			this.pageRank[i] = this.pageRank[i] + dampingFactor * temp[j] / (double)graph.getNode(j).getNumberOfOutboundLinks();
		    }
		}
		// Add the leak caused by the nodes with no outbound link
		double sum = 0;
		for (double d : this.pageRank)
		    sum = sum + d;
		for (int i = 0; i < this.pageRank.length; i++)
		    this.pageRank[i] = this.pageRank[i] + (1.0 - sum) / (double)numberOfNodes;
		
                // Compute the difference between pageRank and temp
                error = 0.0;
                for (int i = 0; i < this.pageRank.length; i++) {
                    double diff = this.pageRank[i] - temp[i];
                    error = error + diff * diff;
                }
	    } while (error > epsilon);
            System.out.println("Number of iterations for the PageRank algorithm: " + count);
	} catch (FileNotFoundException exception) {
            // Missing Links file
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
    
    // Accessor
    @Override
    public double[] getPageRank() {
	return pageRank;
    }
    
    
    @Override
    public String toString() {
	int iMax = this.pageRank.length;
	String string = "";
	for (int i = 0; i < iMax; i++)
	    string = string + "pageRank[" + i + "]: " + this.pageRank[i] + "\n";
	return string;
    }
}