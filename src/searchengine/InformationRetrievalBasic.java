package searchengine;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class InformationRetrievalBasic implements InformationRetrieval {

    protected double[] informationRetrievalVector;
    
    public InformationRetrievalBasic(String searchedword, HTable table){
        ArrayList<Index> aux=table.index.get(searchedword);
        int naux;
        this.informationRetrievalVector=new double[table.files.size()];
        for(int i=0;i<this.informationRetrievalVector.length;i++)
            this.informationRetrievalVector[i]=0;
        //index = fileID, title_appearances, number_occurences
        for(int i=0;i<aux.size();i++){
            naux=aux.get(i).fileID;
            if(aux.get(i).in_title==1)
            this.informationRetrievalVector[naux]=2*aux.get(i).numb_occurences; 
            else this.informationRetrievalVector[naux]=aux.get(i).numb_occurences;
            //System.out.println("Found! "+ naux+" "+ this.informationRetrievalVector[naux]);
        }
        
                
        
        
    }
    @Override
    public double[] getInformationRetrieval() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return this.informationRetrievalVector;
    }
    
    @Override
    public String toString() {
        //throw new UnsupportedOperationException("Not supported yet.");
        int iMax = this.informationRetrievalVector.length;
	String string = "";
	for (int i = 0; i < iMax; i++)
	    string = string + "IR[" + i + "]: " + this.informationRetrievalVector[i] + "\n";
	return string;
    }
    
}
