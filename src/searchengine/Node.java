package searchengine;

import java.util.*;


public class Node {
    protected int numberOfOutboundLinks;
    protected HashSet<Integer> setOfNodesThatLinkTo;
    
    // Constructor
    public Node() {
	this.numberOfOutboundLinks = 0;
	this.setOfNodesThatLinkTo = new HashSet<Integer>();
    }
    
    // Accessors
    public int getNumberOfOutboundLinks() {
	return numberOfOutboundLinks;
    }
    
    public HashSet<Integer> getSetOfNodesThatLinkTo() {
	return setOfNodesThatLinkTo;
    }
    
    
    public void addOutboundLink() {
	this.numberOfOutboundLinks++;
    }
    
    public void addNodesThatLinkTo(int edgeSource) {
	this.setOfNodesThatLinkTo.add(edgeSource);
    }
    
    @Override
    public String toString() {
	String string;
	string = "  Number of outbound links: ";
	string = string + Integer.toString(this.numberOfOutboundLinks);
	string = string + "\n  Set of nodes that link to: ";
	string = string + this.setOfNodesThatLinkTo.toString();
	return string;
    }
}