package searchengine;

import java.io.*;
import java.util.*;


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
        IndexEdges indexEdges = new IndexEdges();
        
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
        InformationRetrieval informationRetrieval = new InformationRetrievalBasic(searchedWord, indexEdges.tab);
        
        // Display the IR vector
        System.out.println(informationRetrieval.toString());
        
        // Compute the weight vector
        double[] pageRankVector = pageRank.getPageRank();
        double[] informationRetrievalVector = informationRetrieval.getInformationRetrieval();
        
        // Check the length of these two vectors
        if (pageRankVector.length != informationRetrievalVector.length)
            throw new Exception();
        
        double[] weightVector = new double[pageRankVector.length];
        for (int i = 0; i < weightVector.length; i++) {
            weightVector[i] = pageRankVector[i] * informationRetrievalVector[i];
        }
        
        // Find the k highest weights
        int k = 10;
        int[] indexVector = new int[weightVector.length];
        for (int i = 0; i < indexVector.length; i++) {
            indexVector[i] = i;
        }
        for (int j = 0; j < k; j++) {
            for (int i = indexVector.length - 2; i >= j; i--) {
                if (weightVector[i] < weightVector[i + 1]) {
                    double weightTemp = weightVector[i];
                    weightVector[i] = weightVector[i + 1];
                    weightVector[i + 1] = weightTemp;
                    int indexTemp = indexVector[i];
                    indexVector[i] = indexVector[i + 1];
                    indexVector[i + 1] = indexTemp;
                }
            }
        }
        int[] resultVector = Arrays.copyOfRange(indexVector, 0, k);
        
        // Display the result vector
        System.out.println("Results:");
        System.out.println("\\begin{table}[h]");
        System.out.println("\\centering");
        System.out.println("\\begin{tabular}{ | r | l | }");
        System.out.println("\\hline");
        for (int i = 0; i < resultVector.length; i++) {
            String string = indexEdges.tab.getFilename(resultVector[i]);
            String description = string.replace("_", "\\_");
            String url = "http://rm.wikipedia.org/wiki/" + string;
            System.out.println((i + 1) + " & \\href{" + url + "}{" + description + "} \\\\ \\hline");
        }
        System.out.println("\\end{tabular}");
        System.out.println("\\caption{TODO: complete}");
        System.out.println("\\end{table}");
    }
}