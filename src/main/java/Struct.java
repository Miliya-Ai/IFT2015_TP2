import edu.stanford.nlp.pipeline.CoreDocument;

import java.io.File;
import java.util.ArrayList;

public class Struct {
    WordMap wordMap = new WordMap();
    String[] allWords = null;
    FileMap fileMap = new FileMap();


    public Struct(){

    }

    public void createWordMap(String str, String file){
        allWords = str.split(" ");
        int position = 1;

        for (String word: allWords)
            if (!(wordMap.containsKey(word))){
                FileMap fileMap = new FileMap();
                fileMap.put(word, position);
                position ++;
            } else {
                wordMap.getEntry(word).getValue().put();


            }
        /*
        1. pour chaque mot
        if new mot
            add dans worldMap
            FileMap: key = nom file , value = position
            position ++
         else mot deja vu
            if FileMap.key = currentFile
                value.add(position)
            else FileMap.key =/= currentFile
                add FileMap, key , value
         */
    }
    public WordMap getWordMap(){
        return this.wordMap;
    }
}
