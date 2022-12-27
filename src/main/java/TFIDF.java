import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
/**
 * fonction : Chaîne de chemin entrant, retourne le texte sous la forme d’une chaîne
 * Code inspire https://blog.csdn.net/yeyu_xing/article/details/109084754
 */
public class TFIDF {

    public  TFIDF(WordMap wordMap, int totalFichier, String mot){
        calculerTFIDF(wordMap, totalFichier, mot);
    }

    public TFIDF( WordMap wordMap, int totalMot, String fichier, Boolean calculerTFI){
        calculerTFI(wordMap, totalMot, fichier);
    }

    public void calculerTFI(WordMap wordMap, int wordAll, String file){
        FileMap temp = new FileMap();
        float wordTf;
        ArrayList fileMapValue = new ArrayList();
        ArrayList position = new ArrayList();
        ArrayList TFI = new ArrayList<>();
        for (Object word: wordMap.keySet()) {
            temp = (FileMap) wordMap.get(word);
            if (temp.containsKey(file)){
                fileMapValue = (ArrayList) temp.get(file);
                position = (ArrayList) fileMapValue.get(0);

                wordTf = (float) position.size() / wordAll;
                temp.put(file, wordTf);
                //System.out.println("Mot : " + word + " FileMap : " + fileMapValue);
            }
        }
    }

    public void calculerTFI(WordMap wordMap, int wordAll){
        FileMap temp = new FileMap();
        float wordTf;
        ArrayList fileMapValue = new ArrayList();
        ArrayList position = new ArrayList();
        ArrayList TFI = new ArrayList<>();
        for (Object word: wordMap.keySet()) {
            temp = (FileMap) wordMap.get(word);
            for (Object file : temp.keySet()) {
                fileMapValue = (ArrayList) temp.get(file);
                position = (ArrayList) fileMapValue.get(0);
                TFI = (ArrayList) fileMapValue.get(2);
                if (TFI.size() == 0) {
                    wordTf = (float) position.size() / wordAll;
                    temp.put(file, wordTf);
                    System.out.println("Mot : " + word + " FileMap : " + fileMapValue);
                }
            }
        }
    }

    public void calculerTFIDF(WordMap wordMap, int totalFichier, String mot){
        FileMap fileMap;
        float TFIDF;
        float IDF;
        ArrayList fileMapValue = new ArrayList();
        ArrayList TFI = new ArrayList<>();
        float temp = 0;
        String fileTemp = "";
        float temp1 = 0;
        String fileTemp2 = "";
        float tfi;
        boolean prendreTFIDF = true;
        if (wordMap.containsKey(mot)){
            fileMap = (FileMap) wordMap.get(mot); // a la place, peut faire fileMap =  wordMap.get(mot) if filemap == null sout(mot pas dans wordMap) else ...
            for(Object file: fileMap.keySet()){
                fileMapValue = (ArrayList) fileMap.get(file);
                TFI = (ArrayList) fileMapValue.get(2);
                IDF = (float) Math.log(totalFichier/fileMap.size());
                if (IDF != 0.0) {
                    TFIDF = (float) TFI.get(0) * IDF;
                    if (TFIDF > temp){
                        temp = TFIDF;
                        fileTemp = (String) file;
                    }
                }
                else {
                    prendreTFIDF = false;
                    tfi = (float) TFI.get(0);
                    if (tfi > temp1){
                        temp1 = tfi;
                        fileTemp2 = (String) file;
                    }


                }
            }
            if (prendreTFIDF)
                System.out.println(fileTemp);
            else
                System.out.println(fileTemp2);

        } else {
            System.out.println("Dans le dataset fourni, il n'y a pas le mot : " + mot);
        }



    }




}



