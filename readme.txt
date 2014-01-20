edges.txt: 
first line = number of nodes (documents)
then each line= node1  ------> node2 (there is an edge leaving from node1 to node2)
OBS: number of nodes are docID's;


invertedindex.txt:
list all words that are indexed
each word is indexed as follows (in a hashtable, for each word there is a list composed of |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|)

word1    ----> |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|
                        |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|
                       |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|


word2    ----> |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|
                        |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|
                       |docID | does it appear in the title (0=no, 1=yes)| number_of_occurences|



the inverted index is sorted descendingly, first by "Does it appear in the title", and then for the number of occurences
                         
