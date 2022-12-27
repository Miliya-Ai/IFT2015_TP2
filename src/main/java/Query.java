import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 */
public class Query {
    TFIDF tfidf;
    Bigrams bigrams;
    String pathQuery;
    int totalFichiers;
    String queryTFIDF = "search ";
    String queryBigrams = "the most probable bigram of ";

    public Query(WordMap wordMap,  int totalFichiers, String pathQuery) throws IOException {
        this.pathQuery = pathQuery;
        this.totalFichiers = totalFichiers;
        init( wordMap);

    }

    public void init(WordMap wordMap) throws IOException {
        lire(wordMap);

    }

    public void seeTFIDF(WordMap wordMap, String line){
        String mot = line.replace(queryTFIDF, "");
        if (mot.contains(" ")){
            System.out.println("Mauvais query");
        }else {
            tfidf = new TFIDF(wordMap, totalFichiers, mot);
        }
    }


    public void lire(WordMap wordMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathQuery));

        String line = null;


            while ((line = reader.readLine()) != null) {
                //line = line.trim();
                if (line.contains(queryTFIDF)) {
                    seeTFIDF(wordMap, line);
                } else if (line.contains(queryBigrams)) {
                    seeBigram(wordMap, line);
                } else if (line.isEmpty()) {
                } else {

                    System.out.println("mauvais query: " + line);
                }
            }
            reader.close();


    }


        private void seeBigram (WordMap wordMap, String line){
            String mot = line.replace(queryBigrams, "");
            if (mot.contains(" ")){
                System.out.println("Mauvais query, votre " + mot + "contient des espace en trop.");
            } else {
                bigrams = new Bigrams(wordMap, mot);
            }
        }


    }

