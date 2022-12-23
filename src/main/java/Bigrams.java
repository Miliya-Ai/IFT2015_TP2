import java.util.ArrayList;

public class Bigrams {
    //suggesting the next most probable word like an auto-complete function which is
    //related to the concept named bi-grams in NLP;
    WordMap wordMap;
    String word;
    int countWord;


    //constructor
    public Bigrams(Struct struct){
        this.wordMap = struct.getWordMap();
    }

    public Bigrams(){

    }

    public void bigrams(String word){
        this.word = word;

    }

    public double probabilityObserving2ndSword(ArrayList frequenceWord2){
        return (frequenceWord2.size()/countWord);
    }

    public void countWord(){
        ArrayList<FileMap> test = wordMap.getEntry(word).getValue();
        ArrayList value = null;
        //for tous les fileMap dans le array list, on va chercher le file qui nous interesse
        for(FileMap fileMap: test){
            if (fileMap.containsKey("file.txt")){
                value = (ArrayList) fileMap.get("file.txt");
            }
        }
        countWord = value.size();

    }



}

