# PageRank

## Description
This repository contains our project for the course ["Algorithmique des
réseaux sociaux"](http://www.di.ens.fr/~lelarge/soc.html) taught by Marc
Lelarge of the &Eacute;cole Normale Supérieure, Paris, France.

Our goal is to implement the PageRank algorithm and execute it on a subset
of Wikipedia's web pages. We will study the effect of the dumping factor
and consider an alternative model to the random surfer : the intentional
surfer.


## Authors
  - *Andreea Beica*, graduate student in Computer Science of the &Eacute;cole
Normale Supérieure, Paris, France
  - *Baptiste Lefebvre*, graduate student in Computer Science of the &Eacute;cole
Normale Supérieure, Paris, France


## Compilation
To compile our project please follow these steps:
  - Download these .jar libraries and save them in the main folder:
    - [JBLAS 1.2.3](http://mikiobraun.github.io/jblas/jars/jblas-1.2.3.jar)
    - [JSOUP 1.7.3](http://jsoup.org/packages/jsoup-1.7.3.jar)
    - [Commons Lang 3.2.1](http://mirrors.ircam.fr/pub/apache//commons/lang/binaries/commons-lang3-3.2.1-bin.tar.gz)
  - Build the project with
  
  ```console
  $ make
  ```
  - Test the project with:
  
  ```console
  $ make test
  ```
  - Clean the project with:
  
  ```console
  $ make clean
  ```

## Notes

### Specification
We want to have these following input and output data:
  - Input data:
    - a folder containing some html files
    - a query (i.e. one word to begin)
  - Output data:
    - a list of paths in the input folder to reach the most relevant html
    files for the query

For the input data, we consider:
  - [Wikipedia database
  download](http://en.wikipedia.org/wiki/Wikipedia:Database_download)
  - [Wikipedia romansh language](http://dumps.wikimedia.org/other/static_html_dumps/current/rm/wikipedia-rm-html.tar.7z)


### Results
It's hard to find a formal method to evaluate our search engine. We will
compare our results with those of Google on the same set of html pages.

### Data structures
The Links file contains the whole information to reconstruct the graph
describe by the html pages (vertices) and the hypertext links (edges). The
format for this file is a sequence of odd integers, the first one *n* give
us the total number of nodes and then by pair *s* *d* we can retrieve the
edges of the graph.

### Report
We must write a report of around 10 pages and choose its composition:
  - 1 page for the title and table of contents
  - 1 page for the references
  - 2 pages for the presentation of the subject
  - 2 pages for the project architecture and word processing
  - 2 pages for the algorithms (PageRank, random surfer, intentional
  surfer, ...)
  - 2 pages for our results (i.e. comparisons)
  - 1 page for the conclusion

We must think to mention problems encountered and possible improvements.


## References
  - [The Anatomy of a Large-Scale Hypertextual Web Search
  Engine](http://www.di.ens.fr/~lelarge/soc/brin_page_google.pdf)
