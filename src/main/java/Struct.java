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
        System.out.println(HashSet.toString(wordMap.entrySet()));
    }



    public WordMap getWordMap(){
        return this.wordMap;
    }
}
