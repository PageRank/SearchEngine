package searchengine;

import java.io.*;
import org.jblas.*;


public class PageRankAlgebraic implements PageRank {
    // Instance variable
    protected double[] pageRank;
    
    // Constructor
    public PageRankAlgebraic(String linksFilename, double dampingFactor) {
	DataInputStream linksDataInputStream = null;
	int numberOfNodes = 0;
	DoubleMatrix matrix = null;
	try {
	    try {
		// Open a DataInputStream for the Links file
		File linksFile = new File(linksFilename);
		FileInputStream linksFileInputStream = new FileInputStream(linksFile);
		BufferedInputStream linksBufferedInputStream = new BufferedInputStream(linksFileInputStream);
		linksDataInputStream = new DataInputStream(linksBufferedInputStream);
		// Retrieve the numberOfNodes
		numberOfNodes = linksDataInputStream.readInt();
		// Retrieve the web matrix
		matrix = DoubleMatrix.zeros(numberOfNodes, numberOfNodes);
		while (true) {
		    int edgeSource = linksDataInputStream.readInt();
		    int edgeDestination = linksDataInputStream.readInt();
		    matrix.put(edgeDestination, edgeSource, 1.0);
		}
	    } catch (EOFException exception) {
		// End of file reached
	    }
	    // For each node
	    for (int j = 0; j < numberOfNodes; j++) {
		// Compute the out degree
		double L = 0.0;
		for (int i = 0; i < numberOfNodes; i++)
		    L = L + matrix.get(i, j);
		if (0.0 == L) {
		    // matrix.put(j, j, 1.0);
		} else {
		    // Divide each link weight by the out degree
		    for (int i = 0; i < numberOfNodes; i++)
			matrix.put(i, j, matrix.get(i, j) / L);
		}
	    }
	    // Compute PageRank
	    matrix = matrix.mul(dampingFactor);
	    matrix = DoubleMatrix.eye(numberOfNodes).sub(matrix);
	    matrix = Solve.solve(matrix, DoubleMatrix.eye(numberOfNodes));
	    matrix = matrix.mmul(DoubleMatrix.ones(numberOfNodes, 1));
	    matrix = matrix.mul((1.0 - dampingFactor) / (double)numberOfNodes);
	    this.pageRank = matrix.toArray();
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