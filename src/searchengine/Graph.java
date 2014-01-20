package searchengine;


public class Graph {
    protected int numberOfNodes;
    protected Node[] node;
    
    // Constructors
    public Graph() {
	this.numberOfNodes = 0;
	this.node = new Node[0];
    }
    
    public Graph(int numberOfNodes) {
	this.numberOfNodes = numberOfNodes;
	this.node = new Node[numberOfNodes];
	for (int i = 0; i < numberOfNodes; i++) {
	    this.node[i] = new Node();
	}
    }
    
    // Accessor
    public Node getNode(int i) {
	return this.node[i];
    }
    
    
    public void addEdge(int edgeSource, int edgeDestination) {
	this.node[edgeSource].addOutboundLink();
	this.node[edgeDestination].addNodesThatLinkTo(edgeSource);
	return;
    }
    
    @Override
    public String toString() {
	String string = "";
	for (int i = 0; i < numberOfNodes; i++) {
	    string = string + "Node " + i + ":\n" + node[i].toString() + "\n";
	}
	return string;
    }
}