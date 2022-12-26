import java.util.ArrayList;

public class Bigrams<Mot> {
    //suggesting the next most probable word like an auto-complete function which is
    //related to the concept named bi-grams in NLP;
    String mot;
    int countWord;
    int index = 0;
    String[] contenu;
    String temp;
    ArrayList<String> mot2e = new ArrayList<>();
    ArrayList<int> frequence2eMot;

6 pirate
    3 in

    40 pirate
    10 terre
    //constructor
    public Bigrams(WordMap wordMap, String mot, ArrayList string){
        this.mot = mot;
        countWord(wordMap, string);

    }


    public double probabilityObserving2ndSword(ArrayList frequenceWord2){return (frequenceWord2.size()/countWord);}

    public void countWord(WordMap wordMap, ArrayList string){
        FileMap fileMap;
        ArrayList value;
        ArrayList position;
        if (wordMap.containsKey(this.mot)){
            fileMap = (FileMap) wordMap.get(this.mot);
            for(Object file: fileMap.keySet()){
                value = (ArrayList) fileMap.get(file);
                position = (ArrayList) value.get(0);
                countWord = position.size();
                bigram(string, position);
            }
        }

    }
[1,2,3]
    private void bigram(ArrayList string, ArrayList position) {
        temp = (String) string.get(index);
        contenu = temp.split(" ");
        int pos;
        int frequence2e;


        for (int i = 0; i < countWord; i++){
            pos = (int) position.get(i);
            mot2e.add(contenu[pos+1]);
        }


        index++;
    }


}

