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
        String motPrecedent = "";

        for (String word: allWords)
            if (!(wordMap.containsKey(word))){
                FileMap fileMap = new FileMap();
                fileMap.put(file, position);
                if (!motPrecedent.equals(""))
                    wordMap.getEntry(motPrecedent).getValue().put(file, word);
                wordMap.put(word, fileMap);
                motPrecedent = word;
                position ++;
            } else {
                wordMap.getEntry(word).getValue().put(file, position);
                if (!motPrecedent.equals(""))
                    wordMap.getEntry(motPrecedent).getValue().put(file, word);
                motPrecedent = word;
                position ++;
            }

        //FileMap test = (FileMap) wordMap.get("Pluto");
        //System.out.println(test.get("900.txt"));
        //TFIDF tfidf = new TFIDF(wordMap, position-1);
        TFIDF tfidf = new TFIDF(wordMap, position-1, file, true);


    }

    public void createBigram(String str, String file){
        string.add(str.split(" "));
    }

    public WordMap getWordMap(){
        return this.wordMap;
    }
}
