package searchengine;

public interface InformationRetrieval {
    // Retrieve the IR vector
    public double[] getInformationRetrieval();
    // Describe the IR vector
    @Override
    public String toString();    
}
