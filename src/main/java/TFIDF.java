import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
/**
 * fonction : Chaîne de chemin entrant, retourne le texte sous la forme d’une chaîne
 * Code inspire https://blog.csdn.net/yeyu_xing/article/details/109084754
 */
public class TFIDF {
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

    public void calculerTFIDIF( String wordAll){
            FileMap<String, Integer> dict = new HashMap<String, Integer>();
            WordMap<String, Float> tf = new HashMap<String, Float>();
            int wordCount=0;

            for(String word:wordAll.split(" ")){
                wordCount++;
                if(dict.containsKey(word)){
                    dict.put(word,  dict.get(word)+1);
                }else{
                    dict.put(word, 1);
                }
            }

            for(Map.Entry<String, Integer> entry:dict.entrySet()){
                float wordTf=(float)entry.getValue()/wordCount;
                tf.put(entry.getKey(), wordTf);
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
    }

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



