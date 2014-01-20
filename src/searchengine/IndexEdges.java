package searchengine;

import java.io.*;
import java.util.*;


public class IndexEdges {
    // Instance variables
    public static File folder = new File("rm");
    public static String searchresults = "AltaVistaSearchResults.in";
    
    public static void indexEdges(String[] args) throws IOException {
        HTable tab = new HTable();
        tab.AddFilesFromFolder(folder);
        
        // Print hashtable
        
        Enumeration e = tab.files.keys();
        
        // Iterate through Hashtable keys Enumeration
        while (e.hasMoreElements()) {
            String keyaux = e.nextElement().toString();
            System.out.println(keyaux + " with value " + tab.files.get(keyaux));
        }
        
        tab.makepointsToandindex(folder);
        
        
        // Store edges in file Links
	DataOutputStream linksDataOutputStream = null;
        try {
            String linksFilename = "Links";
            File linksFile = new File(linksFilename);
            FileOutputStream linksFileOutputStream = new FileOutputStream(linksFile);
            BufferedOutputStream linksBufferedOutputStream = new BufferedOutputStream(linksFileOutputStream);
            linksDataOutputStream = new DataOutputStream(linksBufferedOutputStream);
            linksDataOutputStream.writeInt(tab.pointsTo.size());
            e = tab.pointsTo.keys();
            while (e.hasMoreElements()) {
                String keyaux = e.nextElement().toString();
                ArrayList<String> aaux = tab.pointsTo.get(keyaux);
                for (int i = 0; i < aaux.size(); i++) {
                    linksDataOutputStream.writeInt((Integer)tab.files.get(keyaux));
                    linksDataOutputStream.writeInt((Integer)tab.files.get(aaux.get(i)));
                }
            }
	} catch (FileNotFoundException exception) {
            // Missing file Links
	} catch (IOException exception) {
            // Write error
	} finally {
            // Close the DatatOutputStream
	    try {
		if (linksDataOutputStream != null)
		    linksDataOutputStream.close();
	    } catch (IOException exception) {
	    }
	}

        PrintWriter write = new PrintWriter("invertedindex.txt","UTF-8");
        e = tab.index.keys();
        while (e.hasMoreElements())
            write.println(e.nextElement().toString());
        
        
        /*ArrayList<index> aux=tab.index.get("germania");
        for (int i=0;i<aux.size();i++) {
            System.out.println("In file "+aux.get(i).fileID+ " the word GERMANIA appears "+aux.get(i).numb_occurences+" times");
            if(aux.get(i).in_title==1) System.out.println("It also appears in the title");
        }
        
        System.out.println("After index sorting:");
        tab.sortindex();    
        aux=tab.index.get("germania");
        for (int i=0;i<aux.size();i++) {
            System.out.println("In file "+aux.get(i).fileID+ " the word GERMANIA appears "+aux.get(i).numb_occurences+" times");
            if(aux.get(i).in_title==1) System.out.println("It also appears in the title");
        }
             
        /* ArrayList<String> aux1=tab.pointsTo.get("Germania.html");
        System.out.println("File GERMANIA points to :");
        for(int i=0;i<aux1.size();i++) {
            System.out.println(aux1.get(i));
        }
             
             
             
           /*AltaVistaSearch alt=new AltaVistaSearch();
           alt.filterfile(searchresults);
           System.out.println(alt.AltaSearch.size());
        */
    }
}