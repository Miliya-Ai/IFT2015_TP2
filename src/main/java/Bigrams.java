import java.util.ArrayList;

public class Bigrams<Mot> {
    //suggesting the next most probable word like an auto-complete function which is
    //related to the concept named bi-grams in NLP;
    String mot;
    int countWord;
    int index = 0;
    String[] contenu;
    String temp;
    String motMaxTempDataSet = "";
    int frequenceMaxTempDataSet = 0;
    String motMaxDataSet = "";
    int frequenceMaxDataSet = 0;
    ArrayList<String> toutMotsSuivant = new ArrayList<>();

    //constructor
    public Bigrams(WordMap wordMap, String mot){
        this.mot = mot;
        bigram(wordMap, mot);

    }

    public void bigram(WordMap wordMap, String mot){
        FileMap fileMap;
        ArrayList value;
        ArrayList<Integer> position;
        ArrayList<String> bigram;

        if (wordMap.containsKey(this.mot)){
            fileMap = (FileMap) wordMap.get(this.mot);
            for (Object file: fileMap.keySet()){
                value = (ArrayList) fileMap.get(file);
                bigram = (ArrayList) value.get(1);
                toutMotsSuivant.addAll(bigram);
            }
            if (toutMotsSuivant.size() == 0){
                System.out.println("Il n'y a pas de bigramme pour le mot : " + mot);
            } else {
                bigramForFile(toutMotsSuivant);
                System.out.println(mot + " " + motMaxDataSet);
            }
        } else {
            System.out.println("Dans le dataset fourni, il n'existe pas ce mot : " + mot);
        }

    }
    public void bigramForFile(ArrayList<String> bigram) {
        String motMax = "";
        String motTemp = "";
        int frequenceMax = 0;
        int frequenceTemp = 0;




        for (int i = 0; i < bigram.size(); i++) {
            motTemp = bigram.get(i);

            for (int j = 0; j < bigram.size(); j++) {
                if (motTemp.equals(bigram.get(j))) {

                    frequenceTemp++;
                }
            }
            if (frequenceTemp > frequenceMax) {
                frequenceMax = frequenceTemp;
                motMax = motTemp;
            } else if (frequenceTemp == frequenceMax) {
                int comparaison;
                comparaison = motMax.compareTo(motTemp);
                if (comparaison < 0) {
                    motMax = motMax;
                } else if (comparaison > 0) {
                    motMax = motTemp;
                }
            }
            frequenceTemp = 0;
            //motTemp = "";

            frequenceMaxDataSet = frequenceMax;
            motMaxDataSet = motMax;
            //frequenceMaxTempDataSet = frequenceMax;
            //motMaxTempDataSet = motMax;
        }
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
                //bigram(string, position);
            }
        }

    }

    private void bigram(ArrayList string, ArrayList position) {
        temp = (String) string.get(index);
        contenu = temp.split(" ");
        int pos;
        int frequence2e;


        for (int i = 0; i < countWord; i++){
            pos = (int) position.get(i);
           // mot2e.add(contenu[pos+1]);
        }


        index++;
    }
    public static void main(String[] args) {
        String motMax = "a";
        String motTemp = "b";
        System.out.println(motMax.compareTo(motTemp));

        String motMax1 = "b";
        String motTemp1 = "a";
        System.out.println(motMax1.compareTo(motTemp1));

        String motMax2 = "a";
        String motTemp2 = "a";
        System.out.println(motMax2.compareTo(motTemp2));
    }

}

