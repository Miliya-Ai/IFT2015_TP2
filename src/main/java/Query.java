import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Query {
    TFIDF tfidf;
    Bigrams bigrams;
    String pathQuery;
    int totalFichiers;
    String queryTFDF = "search ";
    String queryBigrams = "the most probable bigram of ";

    public Query(WordMap wordMap,  int totalFichiers, String pathQuery, Long temps) throws IOException {
        this.pathQuery = pathQuery;
        this.totalFichiers = totalFichiers;
        init( wordMap);
        System.out.println((System.nanoTime()-temps)/1000000+" milliseconds for querys");

    }

    public void init(WordMap wordMap) throws IOException {
        lire(wordMap);

    }

    public void seeTFIDF(WordMap wordMap, String line){
        String mot = line.replace(queryTFDF, "");
        tfidf = new TFIDF(wordMap, totalFichiers, mot);
    }


    public void lire(WordMap wordMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathQuery));

        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains(queryTFDF)){
                seeTFIDF(wordMap, line);
            } else if (line.contains(queryBigrams)){
                seeBigram(wordMap, line);
            } else{
                System.out.println("mauvais query: " + line);
            }
        }
        reader.close();


    }

    private void seeBigram(WordMap wordMap, String line) {
        String mot = line.replace(queryBigrams, "");
        bigrams = new Bigrams(wordMap, mot);
    }


}
