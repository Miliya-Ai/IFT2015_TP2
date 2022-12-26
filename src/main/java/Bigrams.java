import java.util.ArrayList;

public class Bigrams<Mot> {
    //suggesting the next most probable word like an auto-complete function which is
    //related to the concept named bi-grams in NLP;
    String mot;
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
                bigramAllFile(toutMotsSuivant);
                System.out.println(mot + " " + motMaxDataSet);
            }
        } else {
            System.out.println("Dans le dataset fourni, il n'existe pas ce mot : " + mot);
        }

    }
    public void bigramAllFile(ArrayList<String> bigram) {
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
            frequenceMaxDataSet = frequenceMax;
            motMaxDataSet = motMax;

        }
    }


}

