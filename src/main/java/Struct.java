import edu.stanford.nlp.pipeline.CoreDocument;

import java.io.File;
import java.util.ArrayList;

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

        //TFIDF

        //Bigrams
        

    }



    public WordMap getWordMap(){
        return this.wordMap;
    }
}
