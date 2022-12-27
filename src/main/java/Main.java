/**
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 */
public class Main {
    /**
     * Ce TP2 fait un pretraitement des textes fournis dans le dataset.
     * Puis, a partir des query, imprime sur la console le TFIDF ou le bigrams d'un mot.
     * @param args index 0 = dataset, index 1 = query.txt
     * @see Preprocess
     * @see Query
     */
    public static void main(String[] args) throws Exception {
        int lenght = args.length;
        if (lenght == 2){

            String pathDataset = args[0].trim();
            String pathQuery = args[1].trim();
            Preprocess preprocess = new Preprocess(pathDataset);
            new Query(preprocess.getStruct().getWordMap(), preprocess.getTotalFichiers(), pathQuery);

        }
        else{
            System.out.println("Veuillez mettre le path de dataset, puis le path du query.txt");

        }
    }
}
