package searchengine;

import java.io.*;


public class SearchEngine {
    public static void main(String[] args) throws IOException, Exception {
        /*
        // Generate the test file which describe a web graph
	String filename = "Links.data";
        Links links = new Links(filename);
        links.generate();
        */
        // Generate the Links file and the inverted index
        String filename = "Links.out";
        IndexEdges.indexEdges();
        
        // Check the number of input arguments
	if (args.length != 3)
	    return;
	
        // Parse the damping factor
        double dampingFactor;
        try {
            dampingFactor = Double.parseDouble(args[1]);
        } catch (NumberFormatException exception) {
            dampingFactor = 0.85;
        } catch (NullPointerException exception) {
            dampingFactor = 0.85;
        }
        // Parse the searched word
        String searchedWord = args[2];
        
        // Compute the PageRank vector
	PageRank pageRank;
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
        
        // Compute the IR vector
        InformationRetrieval informationRetrieval = new InformationRetrievalBasic();
        
        // Display the IR vector
        System.out.println(informationRetrieval.toString());
        
        // Compute the ranking vector
        double[] pageRankVector = pageRank.getPageRank();
        ////////////////////////////////////////////////////////////////////////
        // TODO: add the apropriate arguments for the constructor //////////////
        double[] informationRetrievalVector = informationRetrieval.getInformationRetrieval();
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        // Check the length of these two vectors
        if (pageRankVector.length != informationRetrievalVector.length)
            throw new Exception();
        
        double[] rankVector = new double[pageRankVector.length];
        for (int i = 0; i < rankVector.length; i++) {
            rankVector[i] = pageRankVector[i] * informationRetrievalVector[i];
        }
    }
}