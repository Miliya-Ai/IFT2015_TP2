import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Lit un fichier d'entrée composé de plusieurs requêtes. Les réponses sont imprimees une par une sur la console.
 * Il existe deux types de requêtes. L'une consiste à suggérer le prochain mot le plus probable qui
 * apparaît après un mot donné : the most probable bigram of "word". La seconde permet de récupérer
 * le document le plus pertinent d'un mot donné : search "word".
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 * @see TFIDF
 * @see Bigrams
 */
public class Query {
    protected TFIDF tfidf;
    Bigrams bigrams;
    private final String pathQuery;
    private final int totalFichiers;
    private final String queryTFIDF = "search ";
    private final String queryBigrams = "the most probable bigram of ";

    // ------------------------------------ CONSTRUCTEUR  ------------------------------------ //

    /**
     * Initialise l'environnement de requetes.
     * @param wordMap contenant tous les mots et fichiers du dataset fourni
     * @param totalFichiers dans le dataset
     * @param pathQuery path du query passe en argument a Main
     * @throws IOException lorsque le fichier n'existe pas, un message d'erreur s'affichera
     */
    public Query(WordMap wordMap,  int totalFichiers, String pathQuery) throws IOException {
        this.pathQuery = pathQuery;
        this.totalFichiers = totalFichiers;
        init( wordMap);
    }

    // ------------------------------------ QUERY  ------------------------------------ //

    /**
     * Lit le fichier de query.
     * @param wordMap wordmap construit lors du pretraitement
     * @throws IOException lorsque le fichier n'existe pas, un message d'erreur s'affichera
     */
    private void init(WordMap wordMap) throws IOException {
        lire(wordMap);

    }

    /**
     * Lit le fichier de requete. Dependamment de la requete, appel la methode {@link #seeTFIDF(WordMap, String)} ou
     * {@link #seeBigram(WordMap, String)}
     * @param wordMap wordmap contenant tous les mots et fichiers
     * @throws IOException lorsque le fichier n'existe pas, un message d'erreur s'affichera
     */
    private void lire(WordMap wordMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathQuery));
        String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains(queryTFIDF)) {
                    seeTFIDF(wordMap, line);

                } else if (line.contains(queryBigrams)) {
                    seeBigram(wordMap, line);

                } else if (line.isEmpty()) {
                    //do nothing

                } else {
                    System.out.println("mauvais query: " + line); //ex: "searchhhh", "The most probable bigram of"
                }
            }
            reader.close();
    }

    /**
     * A partir de la requete, extraire le mot et trouver le fichier contenant le score TFIDF le plus optimal.
     * @param wordMap wordmap contenant tous les mots et fichiers
     * @param line la requete
     */
    private void seeTFIDF(WordMap wordMap, String line){
        String mot = line.replace(queryTFIDF, "");
        if (mot.contains(" ")){
            // ex: "search       a", "search a      "
            System.out.println("Mauvais query, votre mot \"" + mot + "\" contient des espace en trop.");
        }else {
            tfidf = new TFIDF(wordMap, totalFichiers, mot);
        }
    }

    /**
     * A partir de la requete, extraire le mot et suggérer le prochain mot1 le plus probable qui
     * peut apparaître après le mot.
     * @param wordMap wordmap contenant tous les mots et fichiers
     * @param line la requete
     */
    private void seeBigram (WordMap wordMap, String line){
        String mot = line.replace(queryBigrams, "");
        if (mot.contains(" ")){
            //ex: "the most probable bigram of       a", "the most probable bigram of      "
            System.out.println("Mauvais query, votre mot \"" + mot + "\" contient des espace en trop.");
        } else {
            this.bigrams = new Bigrams(wordMap, mot);
        }
    }


    }

