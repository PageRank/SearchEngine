package searchengine;

import java.util.*;
import org.apache.commons.lang3.builder.CompareToBuilder;


public class Index implements Comparable<Index> {
    // Instance variables
    int fileID;
    int in_title;   //1 if word is in title, 0 if not
    int numb_occurences;
    
    public Index(int fileID, int in_title, int numb_occurences) {
        this.fileID = fileID;
        this.in_title = in_title;
        this.numb_occurences = numb_occurences;
        //add criteria like: does it appear in the url? is it linked from other pages using the keyword? where does it appear in the text?
    }
    
    @Override
    public int compareTo(Index other) {
        return new CompareToBuilder().append(in_title, other.in_title).append(numb_occurences, other.numb_occurences).toComparison();
    }
    
    public boolean equals(Index b) {
        return (this.fileID == b.fileID && this.in_title == b.in_title && this.numb_occurences == b.numb_occurences);
    }
    
    public boolean in(ArrayList<Index> b) {
        for (int i = 0; i < b.size(); i++)
            if (this.equals(b.get(i)))
                return true;
        return false;
    } 
}