/**
 * Construit le wordMap en emmagasinant les voisins droites de chaque mot et le TF du mot.
 * @author Kim Trinh (20215539)
 * @author Miliya Ai (20180783)
 */
public class Struct {
    private final WordMap wordMap = new WordMap();
    protected String[] allWords = null;

    // ------------------------------------ CONSTRUCTEUR  ------------------------------------ //
    public Struct(){}


    // ------------------------------------ NAISSANCE DE WORDMAP  ------------------------------------ //

    public void createWordMap(String str, String file){
        allWords = str.split(" ");
        int position = 1; //Position de chaque mot
        String motPrecedent = ""; //Le premier mot n'a pas de mot qui le precede

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

        new TFIDF(wordMap, position-1, file, true);


    }
    //------------------------------------ GETTERS ------------------------------------------------//

    public WordMap getWordMap(){
        return this.wordMap;
    }
}
