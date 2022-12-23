public class Struct {
    WordMap wordMap;
    int position = 0;

    public Struct(String pathDataset){
        init();
    }

    public void init(){
        //pour chaque file dans dataset
        addMot();

    }

    public void addMot(){
        //Lire le fichier deja traité

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
