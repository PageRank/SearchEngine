package searchengine;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HTable {
    // Instance variables
    Hashtable files;
    int cont;
    Hashtable<String, ArrayList<Index>> index;  //inverted index for the words
    Hashtable<String, ArrayList<String>> pointsTo; // list of links from the current article
    
    // Constructor
    public HTable()throws FileNotFoundException, IOException {
        files = new Hashtable();
        cont = 0;
        index = new Hashtable<String, ArrayList<Index>>();
        pointsTo = new Hashtable<String, ArrayList<String>>();
    }
    
    public String hextoascii(String url) {
        int i = 0;
        StringBuilder output = new StringBuilder();
        String str;
        while (i < url.length()) {
            if (url.charAt(i) != '%') {
                output.append(url.charAt(i));
                i++;
            } else {
                str = url.substring(i + 1, i + 3);
                output.append((char)Integer.parseInt(str, 16));
                i += 3;
            }
        }
        return output.toString();
    }
    
    // Browses file directory and adds all files to a hashtable
    public void AddFilesFromFolder(final File folder) throws FileNotFoundException {
        for (final File FileEntry : folder.listFiles()) {
            if (FileEntry.isDirectory()) {
                AddFilesFromFolder(FileEntry);
            } else if (!FileEntry.getName().contains("User") && !FileEntry.getName().contains("Template") && !FileEntry.getName().contains("Talk") && !FileEntry.getName().contains("Wikipedia")) {
                //System.out.println("Adding file " + FileEntry.getName() + " to hashtable with value "+(cont+1));
                files.put(FileEntry.getName(), cont++);
            }
        }
    }
    
    public void makepointsToandindex(final File folder) throws FileNotFoundException {
        String word;
        int i;
        int count, in_title;
        ArrayList<Index> list;
        String a;
        int cont = 0;
        for (final File FileEntry : folder.listFiles()) {
            if (FileEntry.isDirectory()) {
                makepointsToandindex(FileEntry);
            } else if (FileEntry.isFile() && !FileEntry.getName().contains("User") && !FileEntry.getName().contains("Template") && !FileEntry.getName().contains("Talk") && !FileEntry.getName().contains("Wikipedia")) {
                //create inverted index
                String input = new Scanner(FileEntry).useDelimiter("\\A").next();
                Document doc = Jsoup.parse(input);
                String text = doc.body().text().toLowerCase();
                String[] words = text.split("\\s");
                for (i = 0; i < words.length; i++) 
                    if (words[i].matches("[a-zA-Z]+") || words[i].matches("[0-9]+")) { 
                        {
                            //for each word in the text
                            words[i] = words[i].toLowerCase().replaceAll("[.;,!?']", "");
                            count = StringUtils.countMatches(text, words[i]);
                            //System.out.println("In file "+FileEntry.getName()+"the word "+words[i]+" appears "+count+" times");
                            if (FileEntry.getName().toLowerCase().contains(words[i])) {
                                in_title = 1;/*System.out.println(words[i]+" appears in title");*/
                            } else {
                                in_title = 0;
                            }
                            Index aux = new Index((Integer)files.get(FileEntry.getName()),in_title,count);
                            if (index.containsKey(words[i])) {
                                if (!aux.in(index.get(words[i]))) {
                                    index.get(words[i]).add(aux);
                                    //System.out.println("Adding document "+FileEntry.getName()+" to index of "+words[i]);
                                }
                            } else {
                                list = new ArrayList<Index>();
                                list.add(aux);
                                index.put(words[i], list);
                                //System.out.println("Adding "+words[i]+" to index");
                            }
                        }
                        //else System.out.println(words[i]+" word not in document");
                    }
                    //System.out.println("Creating pointsTo table for file " + FileEntry.getName());
                    //Create pointsTo hashtable for every document
                    Elements links = doc.select("a");
                    pointsTo.put(FileEntry.getName(), new ArrayList<String>());
                    for (Element link : links) {
                        a = link.attr("href");
                        if (a.contains("../../../../articles/")) {
                            a = a.replaceAll("../../../../articles/./././","");
                            a = hextoascii(a);
                            //System.out.println("Found link to " + a);
                            if (files.containsKey(a)) {
                                //System.out.println(a + " is in hashtable");  
                                if (!pointsTo.get(FileEntry.getName()).contains(a) && !FileEntry.getName().equals(a)) {
                                    //System.out.println("Adding " + a + " to pointsTo of file " + FileEntry.getName());
                                    pointsTo.get(FileEntry.getName()).add(a);
                                }
                            }
                        }
                    }              
                }
            }
        }
    

    // Sort inverted index
    public void sortindex() {
        Enumeration e = index.keys();
        // Iterate through Hashtable keys Enumeration
        while (e.hasMoreElements()) {
            String keyaux = e.nextElement().toString();
            ArrayList<Index> aux = index.get(keyaux);
            Collections.sort(aux, Collections.reverseOrder());
            index.remove(keyaux);
            index.put(keyaux, aux);
        }
    }
}