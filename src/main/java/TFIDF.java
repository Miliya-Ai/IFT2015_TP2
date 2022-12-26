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

    /**
     * TFI
     * @param worMap wordMap du dataset
     * @param totalMot nombre total de mot pour chaque fichier
     */
    public TFIDF(WordMap worMap, int totalMot){
        calculerTFI(worMap, totalMot);

    }

    public  TFIDF(WordMap wordMap, int totalFichier, String mot){
        calculerTFIDF(wordMap, totalFichier, mot);
    }

    public static String getTxtString(String path) {
        StringBuilder sBuilder = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            char[] buffer = new char[512];
            int len;
            while ((len = br.read(buffer)) != -1) {
                sBuilder.append(new String(buffer, 0, len));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sBuilder.toString();
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
            fileMap = (FileMap) wordMap.get(mot);
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
/*
        //TFIDF
        FileMap fileMapTFIDF = new FileMap();
        int frequenceMot;
        for (String word: allWords)
            fileMapTFIDF = wordMap.getEntry(word).getValue();
        frequenceMot = fileMapTFIDF.getEntry(file).getValue().size();


 */


    /**
     *
     * Fonction: chaîne de texte entrant, fréquence de retour du mot
     * @param txtString
     * @return
     */
    public static Map<String, Integer> getCounter(String txtString) {
        Map<String, Integer> treeMap = new TreeMap<>();
        String[] strArr = txtString.split("[\\s?',!;.\u3000]+");
        for (String s : strArr) {
            treeMap.put(s, treeMap.getOrDefault(s, 0) + 1);
        }
        return treeMap;
    }


    public static void main(String[] args) {
        //String txtString = TFIDF.getTxtString("/Users/miliya.ai/Documents/GitHub/ift2015_TP2/dataset/dataset/901.txt");
        String txtString = TFIDF.getTxtString("C:\\Users\\Admin\\Documents\\GitHub\\ift2015_TP2\\dataset\\dataset\\903.txt");
        Map<String, Integer> treeMap = TFIDF.getCounter(txtString);
        //treeMap.forEach((key, value) -> System.out.println(key + ": " + value));
        int totalMot = 0;
        for (String treeKey : treeMap.keySet()) {
            totalMot += treeMap.get(treeKey);
        }
        System.out.println(totalMot);
    }

    }



