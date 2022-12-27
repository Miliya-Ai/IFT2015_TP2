import java.util.ArrayList;

/**
 * Pour chaque mot, calcule la frequence du mot dans chaque fichier (TF) et IDF pour trouver
 * le document le plus pertinent.
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 * Code inspire https://blog.csdn.net/yeyu_xing/article/details/109084754
 */
public class TFIDF {
    private float IDF;
    private ArrayList TFI;
    private float temp = 0;
    private String fileTemp = "";
    private float temp1 = 0;
    private String fileTemp2 = "";
    private boolean prendreTFIDF = true;

    // ------------------------------------ CONSTRUCTEUR  ------------------------------------ //

    public  TFIDF(WordMap wordMap, int totalFichier, String mot){
        calculerTFIDF(wordMap, totalFichier, mot);
    }

    public TFIDF( WordMap wordMap, int totalMot, String fichier, Boolean calculerTFI){
        calculerTF(wordMap, totalMot, fichier);
    }

    // ------------------------------------ TFIDF  ------------------------------------ //

    /**
     * Calcule la frequence du mot dans un fichier donnee
     * @param wordMap wordmap construit lors du pretraitement
     * @param wordAll total de mot dans le fichier
     * @param file le fichier correspondant
     */
    public void calculerTF(WordMap wordMap, int wordAll, String file){
        FileMap temp;
        float wordTf;
        ArrayList fileMapValue;
        ArrayList<Integer> position;

        for (Object word: wordMap.keySet()) {
            temp = (FileMap) wordMap.get(word);
            if (temp.containsKey(file)){
                fileMapValue = (ArrayList) temp.get(file);
                position = (ArrayList) fileMapValue.get(0);
                wordTf = (float) position.size() / wordAll;
                temp.put(file, wordTf);
            }
        }
    }

    /**
     * Si le mot est present dans tous les fichiers, prendre le TF. Sinon, le TFIDF.
     * @param wordMap wordmap construit lors du pretraitement
     * @param totalFichier dans le dataset
     * @param mot de la requete
     */
    public void calculerTFIDF(WordMap wordMap, int totalFichier, String mot) {
        FileMap fileMap;
        ArrayList fileMapValue;
        prendreTFIDF = true;

        if (wordMap.containsKey(mot)) {
            fileMap = (FileMap) wordMap.get(mot);
            for (Object file : fileMap.keySet()) {
                fileMapValue = (ArrayList) fileMap.get(file);
                TFI = (ArrayList) fileMapValue.get(2);
                IDF = (float) Math.log(totalFichier / fileMap.size());
                TForTFIDF(file);
            }
            if (prendreTFIDF)
                System.out.println(fileTemp); //requete reussi
            else
                System.out.println(fileTemp2); //requete reussi

        } else {
            System.out.println("Dans le dataset fourni, il n'y a pas le mot : " + mot);
        }
    }

    private void TForTFIDF(Object file){
        if (IDF != 0.0) {
            float TFIDF = (float) TFI.get(0) * IDF;
            if (TFIDF > temp){
                temp = TFIDF;
                fileTemp = (String) file; // trouver le mot ayant le TFIDF le plus eleve
            }
        }

        else { //Tous les fichiers contiennent le mot, alors prendre le TF du fichier le plus eleve
            prendreTFIDF = false;
            float tfi = (float) TFI.get(0);
            if (tfi > temp1){
                temp1 = tfi;
                fileTemp2 = (String) file;
            }
        }
    }



}



