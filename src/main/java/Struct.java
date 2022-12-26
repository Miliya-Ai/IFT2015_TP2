import edu.stanford.nlp.pipeline.CoreDocument;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Struct {
    WordMap wordMap = new WordMap();
    String[] allWords = null;
    ArrayList wordConsecutif = new ArrayList<>();
    ArrayList position = new ArrayList<>();

    public ArrayList getString() {
        return string;
    }

    ArrayList string;


    public Struct(){
    }

    public void createWordMap(String str, String file){
        allWords = str.split(" ");
        int position = 1;

        for (String word: allWords)
            if (!(wordMap.containsKey(word))){
                FileMap fileMap = new FileMap();
                fileMap.put(file, position);
                wordMap.put(word, fileMap);
                position ++;
            } else {
                wordMap.getEntry(word).getValue().put(file, position);
                position ++;
            }

        //FileMap test = (FileMap) wordMap.get("Pluto");
        //System.out.println(test.get("900.txt"));
        TFIDF tfidf = new TFIDF(wordMap, position-1);
    }

    public void createBigram(String str, String file){
        string.add(str.split(" "));
    }

    public WordMap getWordMap(){
        return this.wordMap;
    }
}
