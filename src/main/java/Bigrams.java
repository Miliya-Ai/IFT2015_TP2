import java.util.ArrayList;

/**
 * Suggest the next most probable word like an auto-complete function.
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 */

public class Bigrams {

    private final String mot;
    private String motMaxDataSet = "";
    int frequenceMaxDataSet = 0;
    private final ArrayList<String> toutMotsSuivant = new ArrayList<>();

    // ------------------------------------ CONSTRUCTEUR  ------------------------------------ //

    /**
     * Initialise l'environnement pour trouver le bigram
     * @param wordMap wordmap construit lors du pretraitement
     * @param mot le mot de la requete
     */
    public Bigrams(WordMap wordMap, String mot){
        this.mot = mot;
        bigram(wordMap, mot);

    }

    /**
     * Cherche dans le wordMap, les mots voisins du mot de la requete pour determiner celui ayant la frequence la plus
     * elevee.
     * @param wordMap wordmap construit lors du pretraitement
     * @param mot le mot de la requete
     */
    public void bigram(WordMap wordMap, String mot){
        FileMap fileMap;
        ArrayList value;
        ArrayList<String> bigram;

        if (wordMap.containsKey(this.mot)){
            fileMap = (FileMap) wordMap.get(this.mot);

            for (Object file: fileMap.keySet()){
                value = (ArrayList) fileMap.get(file);
                //les mots voisins sont toujours storer a l'index 1 du value de l'entry
                bigram = (ArrayList) value.get(1);
                toutMotsSuivant.addAll(bigram);
            }
            if (toutMotsSuivant.size() == 0){
                // dernier mot du document pour tous les fichiers
                System.out.println("Il n'y a pas de bigramme pour le mot : " + mot);
            } else {
                bigramAllFile(toutMotsSuivant);
                System.out.println(mot + " " + motMaxDataSet); //Bigram trouve
            }
        } else {
            System.out.println("Dans le dataset fourni, il n'existe pas ce mot : " + mot);
        }

    }

    /**
     * Trouve le mot voisin le plus frequent du mot de la requete
     * @param bigram arraylist de tous les mots voisins
     */
    public void bigramAllFile(ArrayList<String> bigram) {
        String motMax = "";
        String motTemp = "";
        int frequenceMax = 0;
        int frequenceTemp = 0;

        for (int i = 0; i < bigram.size(); i++) {
            motTemp = bigram.get(i);
            //compte la frequence de chaque mot
            for (String s : bigram) {
                if (motTemp.equals(s)) {
                    frequenceTemp++;
                }
            }
            //determine le mot ayant la frequence la plus elevee
            if (frequenceTemp > frequenceMax) {
                frequenceMax = frequenceTemp;
                motMax = motTemp;

            } else if (frequenceTemp == frequenceMax) {
                int comparaison;
                // retourne le plus petit mot en fonction de l'ordre lexicographique
                comparaison = motMax.compareTo(motTemp);
                if (comparaison < 0) { // motMax est plus petit
                    motMax = motMax;
                } else if (comparaison > 0) { //motTemp est plus petit
                    motMax = motTemp;
                }
            }
            frequenceTemp = 0;
            frequenceMaxDataSet = frequenceMax;
            motMaxDataSet = motMax;

        }
    }


}

