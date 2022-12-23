public class Query {
    Struct struct;
    Bigrams bigrams;

    public Query(Struct struct) {
        init();
    }
    public Query(String pathDataSet){
        this.struct = new Struct(pathDataSet);
        this.bigrams = new Bigrams(this.struct);
        init();
    }


    public void init(){
        // lire le fichier query.txt



    }

    public void lire(){
        // quand voit
        // the most probable bigram of -> instancier Bigrams :
        // search -> instancier TFIDF
    }

}
