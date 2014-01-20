package searchengine;

import java.io.*;
import java.util.*;


public class PageRankIterative implements PageRank {
    // Instance variable
    protected double[] pageRank;
    
    // Constructor
    public PageRankIterative(String linksFilename) {
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
	    int tMax = 50;
	    double dampingFactor = 0.85;
	    // Initialize the PageRank vector
	    this.pageRank = new double[numberOfNodes];
	    for (int i = 0; i < numberOfNodes; i++)
		this.pageRank[i] = 1.0 / (double)numberOfNodes;
	    /*
	      Uncomment to get some details about the execution on the standard output
	      System.out.println("t: 0");
	      System.out.println(this.toString());
	    */
	    // Iterate tMax time
	    for (int t = 0; t < tMax; t++) {
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
		/*
		  // Uncomment to get some details about the execution on the standard output
		  System.out.println("t: " + (t + 1));
		  System.out.println(this.toString());
		*/
	    }
	    // Renormalization
	    double sum = 0;
	    for (double d : this.pageRank)
		sum = sum + d;
	    for (int i = 0; i < this.pageRank.length; i++)
		this.pageRank[i] = this.pageRank[i] / sum;
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
	return this.pageRank;
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