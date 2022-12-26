import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Query {
    TFIDF tfidf;
    Bigrams bigrams;
    String pathQuery;
    int totalFichiers;
    String queryTFDF = "search ";
    String queryBigrams = "the most probable bigram of ";

    public Query(WordMap wordMap, ArrayList string, int totalFichiers, String pathQuery) throws IOException {
        this.pathQuery = pathQuery;
        this.totalFichiers = totalFichiers;
        init( wordMap, string);
    }

    public void init(WordMap wordMap, ArrayList string) throws IOException {
        lire(wordMap, string);

    }

    public void seeTFIDF(WordMap wordMap, String line){
        System.out.println("dans query seeTFIDF");
        String mot = line.replace(queryTFDF, "");
        System.out.println(mot);
        tfidf = new TFIDF(wordMap, totalFichiers, mot);
    }


    public void lire(WordMap wordMap,ArrayList string ) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathQuery));

        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains(queryTFDF)){
                seeTFIDF(wordMap, line);
            } else if (line.contains(queryBigrams)){
                seeBigram(wordMap, line, string);
            } else{
                System.out.println("mauvais query: " + line);
            }
        }
        reader.close();

    }

    private void seeBigram(WordMap wordMap, String line, ArrayList string) {
        System.out.println("dans query seeBigram");
        String mot = line.replace(queryBigrams, "");
        System.out.println(mot);
        bigrams = new Bigrams(wordMap, mot, string);
    }


}
