package searchengine;

import java.io.*;


public class SearchEngine {
    public static void main(String[] args) throws IOException {
        /*
        // Generate the test file which describe a web graph
	String filename = "Links.data";
        Links links = new Links(filename);
        links.generate();
        */
        // Generate the Links file and the inverted index
        String filename = "Links.out";
        IndexEdges.indexEdges();
        // Compute the PageRank vector
	PageRank pageRank;
	if (args.length != 3) {
	    return;
	} else {
            // Parse the damping factor
            double dampingFactor;
            try {
                dampingFactor = Double.parseDouble(args[1]);
            } catch (NumberFormatException exception) {
                dampingFactor = 0.85;
            } catch (NullPointerException exception) {
                dampingFactor = 0.85;
            }
            // Parse the researched word
            String researchedWord = args[2];
            // Parse the method of computation for PageRank
            if (args[0].equals("-i")) {
                // Compute PageRank with the iterative method
                pageRank = new PageRankIterative(filename, dampingFactor);
            } else if (args[0].equals("-a")) {
                // Compute PageRank with the algebraic method
                pageRank = new PageRankAlgebraic(filename, dampingFactor);
            } else if (args[0].equals("-p")){
                // Compute PageRank with the power method
                pageRank = new PageRankPower(filename, dampingFactor);
            } else {
                return;
            }
            // Display the PageRank vector
            System.out.println(pageRank.toString());
        }
    }
}