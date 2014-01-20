package searchengine;

public class SearchEngine {
    public static void main(String[] args) {
        // Generate the file which describe the web graph
	String filename = "Links.data";
        Links links = new Links(filename);
        links.generate();
        // Compute the PageRank vector
	PageRank pageRank;
	if (args.length != 1) {
	    return;
	} else if (args[0].equals("-i")) {
	    // Compute PageRank with the iterative method
	    pageRank = new PageRankIterative(filename);
	} else if (args[0].equals("-a")) {
	    // Compute PageRank with the algebraic method
	    pageRank = new PageRankAlgebraic(filename);
	} else if (args[0].equals("-p")){
	    // Compute PageRank with the power method
	    pageRank = new PageRankPower(filename);
	} else {
	    return;
	}
        // Display the PageRank vector
	System.out.println(pageRank.toString());
    }
}