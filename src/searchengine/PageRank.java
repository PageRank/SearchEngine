package searchengine;

public interface PageRank {
    // Retrieve the PageRank vector
    public double[] getPageRank();
    // Describe the PageRank vector
    @Override
    public String toString();
}